package ua.gov.diia.client.sdk.service.impl;

import ua.gov.diia.client.crypto.api.CryptoService;
import ua.gov.diia.client.sdk.exception.DiiaClientException;
import ua.gov.diia.client.sdk.model.*;
import ua.gov.diia.client.sdk.remote.model.AuthData;
import ua.gov.diia.client.sdk.model.SignedFileHashes;
import ua.gov.diia.client.sdk.remote.model.SignedHashData;
import ua.gov.diia.client.sdk.service.DocumentService;
import ua.gov.diia.client.sdk.service.ObjectMapperHolder;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DocumentServiceImpl implements DocumentService {
    private static final String HEADER_NAME_REQUEST_ID = "X-Document-Request-Trace-Id";

    private final CryptoService cryptoService;

    public DocumentServiceImpl(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @Override
    public DocumentPackage processDocumentPackage(Map<String,String> headers, List<EncodedFile> multipartBody, String encodedJsonData) {
        try {
            DocumentPackage documentPackage = new DocumentPackage();

            documentPackage.setRequestId(getRequestId(headers));

            Metadata decryptedMetaData = ObjectMapperHolder.getObjectMapper().readValue(
                    cryptoService.unwrap(encodedJsonData), Metadata.class);
            documentPackage.setData(decryptedMetaData);

            List<FileData> decodedFiles = multipartBody.stream()
                    .map(encodedFile -> {
                        FileData decodedFile = new FileData();
                        decodedFile.setFileName(encodedFile.getName());

                        decodedFile.setData(cryptoService.unwrap(encodedFile.getData()));
                        return decodedFile;
                    })
                    .collect(Collectors.toList());

            documentPackage.setDecodedFiles(decodedFiles);
            return documentPackage;
        } catch (IOException e) {
            throw new DiiaClientException("Failed to deserialize metadata", e);
        }
    }

    @Override
    public DocumentPackage processDocumentReceivedByBarcode(Map<String, String> headers, EncodedFile multipartBody, String encodedJsonData) {
        try {
            DocumentPackage documentPackage = new DocumentPackage();

            documentPackage.setRequestId(getRequestId(headers));

            BarcodeMetadata decryptedMetaData = ObjectMapperHolder.getObjectMapper().readValue(
                    cryptoService.unwrap(encodedJsonData), BarcodeMetadata.class);
            documentPackage.setBarcodeMetadata(decryptedMetaData);

            FileData decodedFile = new FileData();
            decodedFile.setFileName(multipartBody.getName());

            decodedFile.setData(cryptoService.unwrap(multipartBody.getData()));

            documentPackage.setDecodedFiles(Collections.singletonList(decodedFile));
            return documentPackage;
        } catch (IOException e) {
            throw new DiiaClientException("Failed to deserialize metadata", e);
        }
    }

    @Override
    public Signature decodeSignature(String requestId, String encodedData) {
        byte[] decodedData = Base64.getDecoder().decode(encodedData);
        try {
            AuthData authData = ObjectMapperHolder.getObjectMapper()
                    .readValue(decodedData, AuthData.class);
            Signature signature = new Signature();
            signature.setRequestIdHash(authData.getRequestId());
            FileData fileData = new FileData();
            fileData.setFileName("auth.p7s");
            fileData.setData(Base64.getDecoder().decode(authData.getSignature()));
            signature.setSignature(fileData);
            return signature;
        } catch (IOException e) {
            throw new DiiaClientException(e);
        }
    }

    @Override
    public SignedFileHashes decodeSignedHashes(String requestId, String encodedData) {
        byte[] decodedData = Base64.getDecoder().decode(encodedData);
        try {
            SignedHashData signedHashData = ObjectMapperHolder.getObjectMapper()
                    .readValue(decodedData, SignedHashData.class);
            SignedFileHashes signedFileHashes = new SignedFileHashes();
            signedFileHashes.setRequestIdHash(requestId);
            List<FileData> files = signedHashData.getSignedItems().stream()
                    .map(si -> {
                        FileData fileData = new FileData();
                        fileData.setFileName(si.getName());
                        fileData.setData(Base64.getDecoder().decode(si.getSignature()));
                        return fileData;
                    })
                    .collect(Collectors.toList());
            signedFileHashes.setSignedHashes(files);
            return signedFileHashes;
        } catch (IOException e) {
            throw new DiiaClientException(e);
        }
    }

    private String getRequestId(Map<String, String> headers) {
        for (Map.Entry<String, String> header : headers.entrySet()) {
            if (HEADER_NAME_REQUEST_ID.equalsIgnoreCase(header.getKey())) {
                return header.getValue();
            }
        }
        throw new DiiaClientException(String.format("Header %s is not found. Received headers: %s", HEADER_NAME_REQUEST_ID, headers));
    }
}
