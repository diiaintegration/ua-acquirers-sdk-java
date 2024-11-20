package ua.gov.diia.client.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.gov.diia.client.sdk.Diia;
import ua.gov.diia.client.sdk.model.*;
import ua.gov.diia.client.sdk.service.ObjectMapperHolder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class UploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
    private static final ObjectMapper objectMapper = ObjectMapperHolder.getObjectMapper();
    private static final String SUCCESSFUL_RESPONSE_BODY = "{\"success\": true}";

    @Value("${documents.base.dir}")
    private String baseDocDir;

    private final Diia diia;

    public UploadController(Diia diia) {
        this.diia = diia;
    }

    @PostMapping(
            value = "/doc/upload",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE},
            headers = "!x-diia-id-action",
            params = "!encryptedFileName")
    public ResponseEntity<String> receiveDocuments(
            @RequestHeader Map<String, String> headers,
            @RequestHeader(name = "X-Document-Request-Trace-Id", required = false) String requestId,
            @RequestPart(name = "internal-passport", required = false) MultipartFile internalPassport,
            @RequestPart(name = "foreign-passport", required = false) MultipartFile foreignPassport,
            @RequestPart(name = "taxpayer-card", required = false) MultipartFile taxpayerCard,
            @RequestPart(name = "reference-internally-displaced-person", required = false) MultipartFile referenceInternallyDisplacedPerson,
            @RequestPart(name = "birth-certificate", required = false) MultipartFile birthCertificate,
            @RequestPart(name = "driver-license", required = false) MultipartFile driverLicense,
            @RequestPart(name = "vehicle-license", required = false) MultipartFile vehicleLicense,
            @RequestParam(value = "encodeData") String encodeData
    ) {
        String dirName = createDocumentPackDirectory(requestId);

        // save all received encoded documents
        Stream.of(internalPassport, foreignPassport, taxpayerCard, referenceInternallyDisplacedPerson, birthCertificate,
                        driverLicense, vehicleLicense)
                .filter(Objects::nonNull)
                .forEach(document -> saveEncodedDocumentToDir(document, dirName));

        // save encoded metadata
        saveEncodedMetadataToDir(encodeData, dirName);

        // map received documents to EncodedFile structure
        List<EncodedFile> multipartBody = Stream.of(internalPassport, foreignPassport, taxpayerCard,
                        referenceInternallyDisplacedPerson, birthCertificate, driverLicense, vehicleLicense)
                .filter(Objects::nonNull)
                .map(this::mapMultipartFileToEncodedFile)
                .collect(Collectors.toList());

        // just some checks to be sure request is correct
        Assert.notEmpty(multipartBody, "There are no documents in the request body");
        Assert.notNull(encodeData, "There is no metadata in request body");

        // decode all documents
        DocumentPackage decodedDocumentPackage = diia.decodeDocumentPackage(headers, multipartBody, encodeData);

        // save all decoded PDF documents
        decodedDocumentPackage.getDecodedFiles().forEach(document -> saveDecodedDocumentToDir(document, dirName));

        // save documents metadata
        saveDecodedMetadataToDir(decodedDocumentPackage.getData(), dirName);

        // return successful response
        return ResponseEntity.ok(SUCCESSFUL_RESPONSE_BODY);
    }

    @PostMapping(
            value = "/doc/upload",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE},
            headers = "!x-diia-id-action",
            params = "encryptedFileName")
    public ResponseEntity<String> receiveDocumentByBarcode(
            @RequestHeader Map<String, String> headers,
            @RequestHeader(name = "X-Document-Request-Trace-Id", required = false) String requestId,
            @RequestPart(name = "encryptedFile", required = false) MultipartFile encryptedFile,
            @RequestParam(value = "encodeData") String encodeData,
            @RequestParam(value = "encryptedFileName") String encryptedFileName
    ) {
        // just some checks to be sure request is correct
        Assert.notNull(encryptedFile, "There are no document in the request body");
        Assert.notNull(encodeData, "There is no metadata in request body");

        String dirName = createDocumentPackDirectory(requestId);

        saveEncodedDocumentToDir(encryptedFile, dirName);
        saveEncodedMetadataToDir(encodeData, dirName);
        EncodedFile encodedFile = mapMultipartFileToEncodedFile(encryptedFile);
        DocumentPackage decodedDocumentPackage = diia.decodeDocumentReceivedByBarcode(headers, encodedFile, encodeData);
        decodedDocumentPackage.getDecodedFiles().forEach(document -> saveDecodedDocumentToDir(document, dirName));
        saveDecodedMetadataToDir(decodedDocumentPackage.getBarcodeMetadata(), dirName);
        return ResponseEntity.ok(SUCCESSFUL_RESPONSE_BODY);
    }

    @PostMapping(
            value = "/doc/upload",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.MULTIPART_MIXED_VALUE},
            headers = "x-diia-id-action=auth"
    )
    public ResponseEntity<String> receiveSignature(
            @RequestHeader(name = "X-Document-Request-Trace-Id", required = false) String requestId,
            @RequestParam(value = "encodeData", required = false) String encodeData
    ) {
        String dirName = createDocumentPackDirectory(requestId.replaceAll("\\W+", "_"));
        saveEncodedMetadataToDir(encodeData, dirName);
        Signature signature = diia.decodeSignature(requestId, encodeData);
        saveSignatureToDir(signature, dirName);

        return ResponseEntity.ok(SUCCESSFUL_RESPONSE_BODY);
    }

    @PostMapping(
            value = "/doc/upload",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.MULTIPART_MIXED_VALUE},
            headers = "x-diia-id-action=hashedFilesSigning"
    )
    public ResponseEntity<String> receiveSignedFileHash(
            @RequestHeader(name = "X-Document-Request-Trace-Id", required = false) String requestId,
            @RequestParam(value = "encodeData", required = false) String encodeData
    ) {
        String dirName = createDocumentPackDirectory(requestId);
        saveEncodedMetadataToDir(encodeData, dirName);
        SignedFileHashes signedFileHashes = diia.decodeSignedHashes(requestId, encodeData);
        saveSignedFileHashesToDir(signedFileHashes, dirName);

        return ResponseEntity.ok(SUCCESSFUL_RESPONSE_BODY);
    }

    private void saveEncodedDocumentToDir(MultipartFile document, String dirName) {
        String fileName = dirName + "/encoded-" + document.getOriginalFilename();
        try {
            saveDocumentToFile(fileName, document.getBytes());
        } catch (IOException e) {
            LOGGER.error("Can't save encoded document {}", fileName, e);
        }
    }

    private void saveEncodedMetadataToDir(String encodeData, String dirName) {
        String fileName = dirName + "/encoded-metadata.txt";
        try {
            saveDocumentToFile(fileName, encodeData.getBytes());
        } catch (IOException e) {
            LOGGER.error("Can't save encoded metadata", e);
        }
    }

    private void saveDecodedDocumentToDir(FileData document, String dirName) {
        String fileName = dirName + "/" + document.getFileName();
        try {
            saveDocumentToFile(fileName, document.getData());
        } catch (IOException e) {
            LOGGER.error("Can't save decoded document {}", fileName, e);
        }
    }

    private void saveDecodedMetadataToDir(Object metadata, String dirName) {
        String fileName = dirName + "/metadata.json";
        try {
            saveDocumentToFile(fileName, objectMapper.writeValueAsBytes(metadata));
        } catch (IOException e) {
            LOGGER.error("Can't save decoded metadata {}", fileName, e);
        }
    }

    private void saveSignatureToDir(Signature signature, String dirName) {
        FileData signatureFile = signature.getSignature();
        String fileName = dirName + "/" + signatureFile.getFileName();
        try {
            saveDocumentToFile(fileName, signatureFile.getData());
        } catch (IOException e) {
            LOGGER.error("Can't save signature {}", fileName, e);
        }
    }

    private void saveSignedFileHashesToDir(SignedFileHashes signedFileHashes, String dirName) {
        for (FileData signedHash : signedFileHashes.getSignedHashes()) {
            String fileName = dirName + "/" + replaceFileExtensionByP7s(signedHash.getFileName());
            try {
                saveDocumentToFile(fileName, signedHash.getData());
            } catch (IOException e) {
                LOGGER.error("Can't save signedFileHashes {}", fileName, e);
            }
        }

        zipFiles(signedFileHashes, dirName);
    }

    private void saveDocumentToFile(String fileName, byte[] data) throws IOException {
        File file = new File(fileName);
        if (!file.createNewFile()) throw new AccessDeniedException(file.getAbsolutePath());
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(data);
        }
    }

    private EncodedFile mapMultipartFileToEncodedFile(MultipartFile src) {
        EncodedFile tgt = new EncodedFile();
        tgt.setName(src.getName() + ".pdf");
        tgt.setData(convertMultipartFileDataToString(src));
        return tgt;
    }

    private String convertMultipartFileDataToString(MultipartFile src) {
        try {
            return new String(src.getBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error("Can't read document data from {}", src.getName(), e);
            return null;
        }
    }

    private String createDocumentPackDirectory(String requestId) {
        String dirName = Paths.get(baseDocDir, dateFormat.format(new Date()) + (requestId == null ? "" : "-" + requestId)).toString();
        File dir = new File(dirName);
        if (!dir.exists()) {
            boolean result = dir.mkdirs();
            if (!result) throw new UncheckedIOException(new AccessDeniedException(dir.getAbsolutePath()));
        }
        return dirName;
    }

    private void zipFiles(SignedFileHashes signedFileHashes, String dirName) {
        String filePath = dirName + "/files.zip";
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(filePath))) {
            for (FileData fileData : signedFileHashes.getSignedHashes()) {
                zipOut.putNextEntry(new ZipEntry(replaceFileExtensionByP7s(fileData.getFileName())));
                zipOut.write(fileData.getData());
            }
        } catch (IOException e) {
            LOGGER.error("Can't save zip {}", filePath, e);
        }
    }

    private String replaceFileExtensionByP7s(String originalFileName) {
        return originalFileName.substring(0, originalFileName.lastIndexOf('.')) + ".p7s";
    }
}
