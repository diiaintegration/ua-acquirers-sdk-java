package ua.gov.diia.client.sdk.remote.impl;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import ua.gov.diia.client.sdk.remote.HttpMethodExecutor;
import ua.gov.diia.client.sdk.remote.model.DocumentRequest;
import ua.gov.diia.client.sdk.service.SessionTokenService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

class DiiaValidationApiImplTest {
    private static final String ACCESS_TOKEN = "randomToken";

    private MockWebServer server;
    private DiiaValidationApiImpl diiaValidationApi;

    @BeforeEach
    void init() throws IOException {
        server = new MockWebServer();
        server.start();
        String baseUrl = server.url("").toString();
        baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        OkHttpClient okHttpClient = new OkHttpClient();
        SessionTokenService sessionTokenService = Mockito.mock(SessionTokenService.class);
        HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(sessionTokenService, okHttpClient);
        diiaValidationApi = new DiiaValidationApiImpl(baseUrl, httpMethodExecutor);

        doReturn(ACCESS_TOKEN).when(sessionTokenService).getSessionToken();
    }

    @Test
    void testValidateDocumentByBarcodeSuccess() throws InterruptedException, JSONException {
        String responseBody = "{ \"success\": true }";
        MockResponse mockResponse = new MockResponse()
                .setBody(responseBody)
                .setResponseCode(200);
        server.enqueue(mockResponse);
        String branchId = "1d441d305adc2bff98b1cff5bd2aebecb43f71f25";
        String barcode = "NWU4ZGVjY2NiNjc5MGQ3M2FkODM4YzVk";
        String requestBody = String.format("{\"branchId\":\"%s\",\"barcode\":\"%s\"}", branchId, barcode);
        DocumentRequest documentRequest = new DocumentRequest();
        documentRequest.setBarcode(barcode);
        documentRequest.setBranchId(branchId);

        assertTrue(diiaValidationApi.validateDocumentByBarcode(documentRequest));

        RecordedRequest recordedRequest = server.takeRequest();
        String actualBody = recordedRequest.getBody().readString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals(requestBody, actualBody, false);
        assertTrue(String.format("bearer %s", ACCESS_TOKEN).equalsIgnoreCase(recordedRequest.getHeader("authorization")));
        assertTrue("application/json".equalsIgnoreCase(recordedRequest.getHeader("accept")));
        assertTrue("application/json; charset=utf-8".equalsIgnoreCase(recordedRequest.getHeader("Content-Type")));
        assertEquals("POST", recordedRequest.getMethod());
        assertEquals("/api/v1/acquirers/document-identification", recordedRequest.getPath());
    }

    @Test
    void testValidateDocumentByBarcodeFail() throws InterruptedException, JSONException {
        String responseBody = "{ \"success\": false }";
        MockResponse mockResponse = new MockResponse()
                .setBody(responseBody)
                .setResponseCode(200);
        server.enqueue(mockResponse);
        String branchId = "1d441d305adc2bff98b1cff5bd2aebecb43f71f25";
        String barcode = "NWU4ZGVjY2NiNjc5MGQ3M2FkODM4YzVk";
        String requestBody = String.format("{\"branchId\":\"%s\",\"barcode\":\"%s\"}", branchId, barcode);
        DocumentRequest documentRequest = new DocumentRequest();
        documentRequest.setBarcode(barcode);
        documentRequest.setBranchId(branchId);

        assertFalse(diiaValidationApi.validateDocumentByBarcode(documentRequest));

        RecordedRequest recordedRequest = server.takeRequest();
        String actualBody = recordedRequest.getBody().readString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals(requestBody, actualBody, false);
        assertTrue(String.format("bearer %s", ACCESS_TOKEN).equalsIgnoreCase(recordedRequest.getHeader("authorization")));
        assertTrue("application/json".equalsIgnoreCase(recordedRequest.getHeader("accept")));
        assertTrue("application/json; charset=utf-8".equalsIgnoreCase(recordedRequest.getHeader("Content-Type")));
        assertEquals("POST", recordedRequest.getMethod());
        assertEquals("/api/v1/acquirers/document-identification", recordedRequest.getPath());
    }
}