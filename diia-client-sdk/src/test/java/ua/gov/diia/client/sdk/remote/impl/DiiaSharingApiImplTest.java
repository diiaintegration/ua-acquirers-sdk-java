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
import ua.gov.diia.client.sdk.remote.model.DeepLinkRequest;
import ua.gov.diia.client.sdk.remote.model.DocumentRequest;
import ua.gov.diia.client.sdk.service.SessionTokenService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

class DiiaSharingApiImplTest {
    private static final String ACCESS_TOKEN = "randomToken";

    private MockWebServer server;
    private DiiaSharingApiImpl diiaSharingApi;

    @BeforeEach
    void init() throws IOException {
        server = new MockWebServer();
        server.start();
        String baseUrl = server.url("").toString();
        baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        OkHttpClient okHttpClient = new OkHttpClient();
        SessionTokenService sessionTokenService = Mockito.mock(SessionTokenService.class);
        HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(sessionTokenService, okHttpClient);
        diiaSharingApi = new DiiaSharingApiImpl(baseUrl, httpMethodExecutor);

        doReturn(ACCESS_TOKEN).when(sessionTokenService).getSessionToken();
    }

    @Test
    void testRequestDocumentByBarCodeSuccess() throws InterruptedException, JSONException {
        String responseBody = "{ \"success\": true }";
        MockResponse mockResponse = new MockResponse()
                .setBody(responseBody)
                .setResponseCode(200);
        server.enqueue(mockResponse);
        String branchId = "1d441d305adc2bff98b1cff5bd2aebecb43f71f25";
        String barcode = "NWU4ZGVjY2NiNjc5MGQ3M2FkODM4YzVk";
        String requestId = "bff98b1cff5bd2aad838";
        String requestBody = String.format("{\"branchId\": \"%s\",\"barcode\": \"%s\", \"requestId\": \"%s\"}", branchId, barcode, requestId);
        DocumentRequest documentRequest = new DocumentRequest();
        documentRequest.setRequestId(requestId);
        documentRequest.setBarcode(barcode);
        documentRequest.setBranchId(branchId);

        assertTrue(diiaSharingApi.requestDocument(documentRequest));

        RecordedRequest recordedRequest = server.takeRequest();
        String actualBody = recordedRequest.getBody().readString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals(requestBody, actualBody, false);
        assertTrue(String.format("bearer %s", ACCESS_TOKEN).equalsIgnoreCase(recordedRequest.getHeader("authorization")));
        assertTrue("application/json".equalsIgnoreCase(recordedRequest.getHeader("accept")));
        assertTrue("application/json; charset=utf-8".equalsIgnoreCase(recordedRequest.getHeader("Content-Type")));
        assertEquals("POST", recordedRequest.getMethod());
        assertEquals("/api/v1/acquirers/document-request", recordedRequest.getPath());
    }

    @Test
    void testRequestDocumentByBarCodeFail() throws InterruptedException, JSONException {
        String responseBody = "{ \"success\": false }";
        MockResponse mockResponse = new MockResponse()
                .setBody(responseBody)
                .setResponseCode(200);
        server.enqueue(mockResponse);
        String branchId = "1d441d305adc2bff98b1cff5bd2aebecb43f71f25";
        String barcode = "NWU4ZGVjY2NiNjc5MGQ3M2FkODM4YzVk";
        String requestId = "bff98b1cff5bd2aad838";
        String requestBody = String.format("{\"branchId\": \"%s\",\"barcode\": \"%s\", \"requestId\": \"%s\"}", branchId, barcode, requestId);
        DocumentRequest documentRequest = new DocumentRequest();
        documentRequest.setRequestId(requestId);
        documentRequest.setBarcode(barcode);
        documentRequest.setBranchId(branchId);

        assertFalse(diiaSharingApi.requestDocument(documentRequest));

        RecordedRequest recordedRequest = server.takeRequest();
        String actualBody = recordedRequest.getBody().readString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals(requestBody, actualBody, false);
        assertTrue(String.format("bearer %s", ACCESS_TOKEN).equalsIgnoreCase(recordedRequest.getHeader("authorization")));
        assertTrue("application/json".equalsIgnoreCase(recordedRequest.getHeader("accept")));
        assertTrue("application/json; charset=utf-8".equalsIgnoreCase(recordedRequest.getHeader("Content-Type")));
        assertEquals("POST", recordedRequest.getMethod());
        assertEquals("/api/v1/acquirers/document-request", recordedRequest.getPath());
    }

    @Test
    void testGetDeepLink() throws InterruptedException, JSONException {
        String responseBody = "{\"deeplink\": \"https://diia.app/acquirers/offer-request/dynamic/9954\"}";
        MockResponse mockResponse = new MockResponse()
                .setBody(responseBody)
                .setResponseCode(200);
        server.enqueue(mockResponse);
        String branchId = "1d441d305adc2bff98b1cff5bd2aebecb43f71f25";
        String offerId = "5e8decccb6790d73ad838c5d";
        String requestId = "bff98b1cff5bd2aad838";
        String requestBody = String.format("{\"offerId\": \"%s\", \"requestId\": \"%s\"}", offerId, requestId);
        DeepLinkRequest deepLinkRequest = new DeepLinkRequest();
        deepLinkRequest.setRequestId(requestId);
        deepLinkRequest.setOfferId(offerId);

        String deepLink = diiaSharingApi.getDeepLink(branchId, deepLinkRequest);

        RecordedRequest recordedRequest = server.takeRequest();
        String actualBody = recordedRequest.getBody().readString(StandardCharsets.UTF_8);
        assertEquals("https://diia.app/acquirers/offer-request/dynamic/9954", deepLink);
        JSONAssert.assertEquals(requestBody, actualBody, false);
        assertTrue(String.format("bearer %s", ACCESS_TOKEN).equalsIgnoreCase(recordedRequest.getHeader("authorization")));
        assertTrue("application/json".equalsIgnoreCase(recordedRequest.getHeader("accept")));
        assertTrue("application/json; charset=utf-8".equalsIgnoreCase(recordedRequest.getHeader("Content-Type")));
        assertEquals("POST", recordedRequest.getMethod());
        assertEquals(String.format("/api/v2/acquirers/branch/%s/offer-request/dynamic", branchId), recordedRequest.getPath());
    }

}