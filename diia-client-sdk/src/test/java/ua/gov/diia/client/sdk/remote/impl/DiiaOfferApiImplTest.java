package ua.gov.diia.client.sdk.remote.impl;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.skyscreamer.jsonassert.JSONAssert;
import ua.gov.diia.client.sdk.remote.HttpMethodExecutor;
import ua.gov.diia.client.sdk.remote.model.*;
import ua.gov.diia.client.sdk.service.SessionTokenService;
import ua.gov.diia.client.sdk.util.StubsUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.junit.jupiter.api.Assertions.*;

class DiiaOfferApiImplTest {
    private static final String ACCESS_TOKEN = "randomToken";

    private MockWebServer server;
    private DiiaOfferApiImpl diiaOfferApi;

    @BeforeEach
    void init() throws IOException {
        server = new MockWebServer();
        server.start();
        String baseUrl = server.url("").toString();
        baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        OkHttpClient okHttpClient = new OkHttpClient();
        SessionTokenService sessionTokenService = Mockito.mock(SessionTokenService.class);
        HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(sessionTokenService, okHttpClient);
        diiaOfferApi = new DiiaOfferApiImpl(baseUrl, httpMethodExecutor);

        doReturn(ACCESS_TOKEN).when(sessionTokenService).getSessionToken();
    }

    @Test
    void testCreateOffer() throws InterruptedException, JSONException {
        String responseBody = "{ \"_id\": \"5e8decccb67d8d73ad838c5d\" }";
        MockResponse mockResponse = new MockResponse()
                .setBody(responseBody)
                .setResponseCode(200);
        server.enqueue(mockResponse);
        String expectedBody = StubsUtil.loadAsString("stub/offer/new_offer.json");
        Offer offerStub = StubsUtil.loadStub("stub/offer/new_offer.json", Offer.class);
        String branchId = "1d441d305adc2bff98b1cff5bd2aebecb43f71f25";

        Id offerId = diiaOfferApi.createOffer(branchId, offerStub);

        assertEquals("5e8decccb67d8d73ad838c5d", offerId.getId());
        RecordedRequest recordedRequest = server.takeRequest();
        String actualBody = recordedRequest.getBody().readString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals(expectedBody, actualBody, false);
        assertTrue(String.format("bearer %s", ACCESS_TOKEN).equalsIgnoreCase(recordedRequest.getHeader("authorization")));
        assertTrue("application/json".equalsIgnoreCase(recordedRequest.getHeader("accept")));
        assertTrue("application/json; charset=utf-8".equalsIgnoreCase(recordedRequest.getHeader("Content-Type")));
        assertEquals("POST", recordedRequest.getMethod());
        assertEquals(String.format("/api/v1/acquirers/branch/%s/offer", branchId), recordedRequest.getPath());
    }

    @Test
    void testGetOffers() throws InterruptedException {
        String responseBody = StubsUtil.loadAsString("stub/offer/offer_list.json");
        MockResponse mockResponse = new MockResponse()
                .setBody(responseBody)
                .setResponseCode(200);
        server.enqueue(mockResponse);
        OfferList expectedResponse = StubsUtil.loadStub("stub/offer/offer_list.json", OfferList.class);
        String branchId = "1d441d305adc2bff98b1cff5bd2aebecb43f71f25";

        OfferList actual = diiaOfferApi.getOffers(branchId, null, null);

        RecordedRequest recordedRequest = server.takeRequest();

        assertEquals(expectedResponse.getTotal(), actual.getTotal());
        List<Offer> expectedOffers = expectedResponse.getOffers();
        List<Offer> actualOffers = actual.getOffers();
        assertEquals(expectedOffers.size(), actualOffers.size());

        for (int i = 0; i < expectedOffers.size(); i++) {
            Offer expectedOffer = expectedOffers.get(i);
            Offer actualOffer = actualOffers.get(i);
            assertTrue(new ReflectionEquals(expectedOffer, "id", "scopes").matches(actualOffer));
            assertTrue(new ReflectionEquals(expectedOffer.getScopes()).matches(actualOffer.getScopes()));
            assertEquals(expectedOffer.getId().getId(), actualOffer.getId().getId());
        }

        assertTrue(String.format("bearer %s", ACCESS_TOKEN).equalsIgnoreCase(recordedRequest.getHeader("authorization")));
        assertTrue("application/json".equalsIgnoreCase(recordedRequest.getHeader("accept")));
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals(String.format("/api/v1/acquirers/branch/%s/offers", branchId), recordedRequest.getPath());
    }

    @Test
    void testGetOfferListSkipQuery() throws InterruptedException {
        String responseBody = StubsUtil.loadAsString("stub/offer/offer_list.json");
        MockResponse mockResponse = new MockResponse()
                .setBody(responseBody)
                .setResponseCode(200);
        server.enqueue(mockResponse);
        String branchId = "1d441d305adc2bff98b1cff5bd2aebecb43f71f25";

        long skipValue = 3L;

        diiaOfferApi.getOffers(branchId, skipValue, null);

        RecordedRequest recordedRequest = server.takeRequest();

        assertTrue(String.format("bearer %s", ACCESS_TOKEN).equalsIgnoreCase(recordedRequest.getHeader("authorization")));
        assertTrue("application/json".equalsIgnoreCase(recordedRequest.getHeader("accept")));
        assertEquals("GET", recordedRequest.getMethod());
        assertNotNull(recordedRequest.getRequestUrl());
        assertEquals(String.valueOf(skipValue), recordedRequest.getRequestUrl().queryParameter("skip"));
    }

    @Test
    void testGetOfferListLimitQuery() throws InterruptedException {
        String responseBody = StubsUtil.loadAsString("stub/offer/offer_list.json");
        MockResponse mockResponse = new MockResponse()
                .setBody(responseBody)
                .setResponseCode(200);
        server.enqueue(mockResponse);
        String branchId = "1d441d305adc2bff98b1cff5bd2aebecb43f71f25";

        long limitValue = 75555L;

        diiaOfferApi.getOffers(branchId, null, limitValue);

        RecordedRequest recordedRequest = server.takeRequest();

        assertTrue(String.format("bearer %s", ACCESS_TOKEN).equalsIgnoreCase(recordedRequest.getHeader("authorization")));
        assertTrue("application/json".equalsIgnoreCase(recordedRequest.getHeader("accept")));
        assertEquals("GET", recordedRequest.getMethod());
        assertNotNull(recordedRequest.getRequestUrl());
        assertEquals(String.valueOf(limitValue), recordedRequest.getRequestUrl().queryParameter("limit"));
    }

    @Test
    void testDeleteOffer() throws InterruptedException {
        MockResponse mockResponse = new MockResponse()
                .setResponseCode(200);
        server.enqueue(mockResponse);
        String branchId = "1d441d305adc2bff98b1cff5bd2aebecb43f71f25";
        String offerId = "5e8decccb6790d73ad838c5d";

        diiaOfferApi.deleteOffer(branchId, offerId);

        RecordedRequest recordedRequest = server.takeRequest();

        assertTrue(String.format("bearer %s", ACCESS_TOKEN).equalsIgnoreCase(recordedRequest.getHeader("authorization")));
        assertTrue("*/*".equalsIgnoreCase(recordedRequest.getHeader("accept")));
        assertEquals("DELETE", recordedRequest.getMethod());
        assertEquals(String.format("/api/v1/acquirers/branch/%s/offer/%s", branchId, offerId), recordedRequest.getPath());
    }
}