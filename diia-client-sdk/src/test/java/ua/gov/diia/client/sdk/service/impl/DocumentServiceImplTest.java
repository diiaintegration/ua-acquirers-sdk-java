package ua.gov.diia.client.sdk.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import ua.gov.diia.client.crypto.api.CryptoService;
import ua.gov.diia.client.sdk.model.*;
import ua.gov.diia.client.sdk.util.StubsUtil;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class DocumentServiceImplTest {
    private DocumentServiceImpl documentService;
    private CryptoService cryptoService;

    @BeforeEach
    void init() {
        cryptoService = Mockito.mock(CryptoService.class);
        documentService = new DocumentServiceImpl(cryptoService);
    }

    @Test
    void testProcessDocumentPackage() {
        String requestId = "abc1234d-1234-1234-1234-1a2b3c4d5e6f";
        String fileName = "someFileName";
        String encodedFileData = "RaNdOM_STRING";
        String encodedJsonMetaData = "encodedJsonStringMetaData";
        byte[] decodedFileData = {3, 4, 2, 1};

        Map<String, String> headers = Collections.singletonMap("x-document-request-trace-id", requestId);
        EncodedFile encodedFile1 = new EncodedFile();
        encodedFile1.setData(encodedFileData);
        encodedFile1.setName(fileName);
        EncodedFile encodedFile2 = new EncodedFile();
        encodedFile2.setData(encodedFileData);
        encodedFile2.setName(fileName);
        List<EncodedFile> encodedFiles = Arrays.asList(encodedFile1, encodedFile2);

        byte[] decodedMetaData =
                StubsUtil.loadAsString("stub/document_package/response-pretty-encodedData.json").getBytes(StandardCharsets.UTF_8);

        doReturn(decodedMetaData).when(cryptoService).unwrap(eq(encodedJsonMetaData));
        doReturn(decodedFileData).when(cryptoService).unwrap(eq(encodedFileData));

        DocumentPackage documentPackage = documentService.processDocumentPackage(headers, encodedFiles, encodedJsonMetaData);

        ForeignPassport foreignPassport = StubsUtil.loadStub("stub/document_package/foreign_passport.json", ForeignPassport.class);
        InternalPassport internalPassport = StubsUtil.loadStub("stub/document_package/internal_passport.json", InternalPassport.class);
        assertTrue(new ReflectionEquals(foreignPassport).matches(documentPackage.getData().getData().getForeignPassport().get(0)));
        assertTrue(new ReflectionEquals(internalPassport).matches(documentPackage.getData().getData().getInternalPassport().get(0)));
        assertEquals(requestId, documentPackage.getRequestId());
        FileData decodedFile = documentPackage.getDecodedFiles().get(0);
        assertEquals(fileName, decodedFile.getFileName());
        assertEquals(decodedFileData, decodedFile.getData());

        FileData decodedFile2 = documentPackage.getDecodedFiles().get(1);
        assertEquals(fileName, decodedFile2.getFileName());
        assertEquals(decodedFileData, decodedFile2.getData());

        verify(cryptoService, times(encodedFiles.size() + 1)).unwrap(any());
        verifyNoMoreInteractions(cryptoService);
    }
}