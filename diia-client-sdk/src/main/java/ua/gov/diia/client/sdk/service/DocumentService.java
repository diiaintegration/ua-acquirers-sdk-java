package ua.gov.diia.client.sdk.service;

import ua.gov.diia.client.sdk.model.DocumentPackage;
import ua.gov.diia.client.sdk.model.EncodedFile;
import ua.gov.diia.client.sdk.model.Signature;
import ua.gov.diia.client.sdk.model.SignedFileHashes;

import java.util.List;
import java.util.Map;

public interface DocumentService {
    DocumentPackage processDocumentPackage(Map<String,String> headers, List<EncodedFile> multipartBody, String encodedJsonData);
    DocumentPackage processDocumentReceivedByBarcode(Map<String,String> headers, EncodedFile multipartBody, String encodedJsonData);
    Signature decodeSignature(String requestId, String encodedData);
    SignedFileHashes decodeSignedHashes(String requestId, String encodedData);
}
