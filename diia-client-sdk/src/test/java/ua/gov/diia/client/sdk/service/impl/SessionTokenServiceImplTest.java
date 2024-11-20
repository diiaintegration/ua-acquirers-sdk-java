package ua.gov.diia.client.sdk.service.impl;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class SessionTokenServiceImplTest {
    private static final String ACQUIRER_TOKEN = "randomToken";

    private MockWebServer server;
    private SessionTokenServiceImpl sessionTokenService;

    @BeforeEach
    void init() throws IOException {
        server = new MockWebServer();
        server.start();
        String baseUrl = server.url("").toString();
        baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        OkHttpClient okHttpClient = new OkHttpClient();
        sessionTokenService = new SessionTokenServiceImpl(ACQUIRER_TOKEN, baseUrl, okHttpClient);
    }

    @Test
    void testGetSessionToken() throws InterruptedException {
        String responseBody = "{ \"token\": \"bXi124jbs3cFas...Sjf\" }";
        MockResponse mockResponse = new MockResponse()
                .setBody(responseBody)
                .setResponseCode(200);
        server.enqueue(mockResponse);

        String sessionToken = sessionTokenService.getSessionToken();

        assertTrue(server.getRequestCount() > 0);
        RecordedRequest recordedRequest = server.takeRequest(5, TimeUnit.SECONDS);
        assertNotNull(recordedRequest);
        assertEquals("bXi124jbs3cFas...Sjf", sessionToken);
        assertTrue(String.format("basic %s", ACQUIRER_TOKEN).equalsIgnoreCase(recordedRequest.getHeader("authorization")));
        assertTrue("application/json".equalsIgnoreCase(recordedRequest.getHeader("accept")));
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals(String.format("/api/v1/auth/acquirer/%s", ACQUIRER_TOKEN), recordedRequest.getPath());
    }
}