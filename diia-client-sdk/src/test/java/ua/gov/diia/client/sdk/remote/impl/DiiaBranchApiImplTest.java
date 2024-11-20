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
import ua.gov.diia.client.sdk.remote.model.Branch;
import ua.gov.diia.client.sdk.remote.model.BranchList;
import ua.gov.diia.client.sdk.remote.model.Id;
import ua.gov.diia.client.sdk.service.SessionTokenService;
import ua.gov.diia.client.sdk.util.StubsUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

class DiiaBranchApiImplTest {
    private static final String ACCESS_TOKEN = "randomToken";

    private MockWebServer server;
    private DiiaBranchApiImpl diiaBranchApi;

    @BeforeEach
    void init() throws IOException {
        server = new MockWebServer();
        server.start();
        String baseUrl = server.url("").toString();
        baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        OkHttpClient okHttpClient = new OkHttpClient();
        SessionTokenService sessionTokenService = Mockito.mock(SessionTokenService.class);
        HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(sessionTokenService, okHttpClient);
        diiaBranchApi = new DiiaBranchApiImpl(baseUrl, httpMethodExecutor);

        doReturn(ACCESS_TOKEN).when(sessionTokenService).getSessionToken();
    }

    @Test
    void testCreateBranch() throws InterruptedException, JSONException {
        String responseBody = "{ \"_id\": \"5e8decccb92d8d73ad838c5d\" }";
        MockResponse mockResponse = new MockResponse()
                .setBody(responseBody)
                .setResponseCode(200);
        server.enqueue(mockResponse);
        Branch newBranch = StubsUtil.loadStub("stub/branch/new_branch.json", Branch.class);
        String expectedBody = StubsUtil.loadAsString("stub/branch/new_branch.json");

        Id branchId = diiaBranchApi.createBranch(newBranch);

        RecordedRequest recordedRequest = server.takeRequest();
        String actualBody = recordedRequest.getBody().readString(StandardCharsets.UTF_8);
        assertEquals("5e8decccb92d8d73ad838c5d", branchId.getId());
        JSONAssert.assertEquals(expectedBody, actualBody, false);
        assertTrue(String.format("bearer %s", ACCESS_TOKEN).equalsIgnoreCase(recordedRequest.getHeader("authorization")));
        assertTrue("application/json".equalsIgnoreCase(recordedRequest.getHeader("accept")));
        assertTrue("application/json; charset=utf-8".equalsIgnoreCase(recordedRequest.getHeader("Content-Type")));
        assertEquals("POST", recordedRequest.getMethod());
        assertEquals("/api/v2/acquirers/branch", recordedRequest.getPath());
    }

    @Test
    void testGetBranchById() throws InterruptedException {
        String responseBody = StubsUtil.loadAsString("stub/branch/existing_branch.json");
        MockResponse mockResponse = new MockResponse()
                .setBody(responseBody)
                .setResponseCode(200);
        server.enqueue(mockResponse);
        Branch expected = StubsUtil.loadStub("stub/branch/existing_branch.json", Branch.class);
        String branchId = "1d441d305adc2bff982fde159f8c91706a68a80b1cff5bd2aebecb43f71f25";

        Branch actual = diiaBranchApi.getBranchById(branchId);

        RecordedRequest recordedRequest = server.takeRequest();

        assertEqualsBranch(expected, actual);

        assertTrue(String.format("bearer %s", ACCESS_TOKEN).equalsIgnoreCase(recordedRequest.getHeader("authorization")));
        assertTrue("application/json".equalsIgnoreCase(recordedRequest.getHeader("accept")));
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/api/v2/acquirers/branch/" + branchId, recordedRequest.getPath());
    }

    @Test
    void testDeleteBranchById() throws InterruptedException {
        MockResponse mockResponse = new MockResponse()
                .setResponseCode(200);
        server.enqueue(mockResponse);
        String branchId = "1d441d305adc2bff982fde159f8c91706a68a80b1cff5bd2aebecb43f71f25";

        diiaBranchApi.deleteBranchById(branchId);

        RecordedRequest recordedRequest = server.takeRequest();

        assertTrue(String.format("bearer %s", ACCESS_TOKEN).equalsIgnoreCase(recordedRequest.getHeader("authorization")));
        assertTrue("*/*".equalsIgnoreCase(recordedRequest.getHeader("accept")));
        assertEquals("DELETE", recordedRequest.getMethod());
        assertEquals("/api/v2/acquirers/branch/" + branchId, recordedRequest.getPath());
    }

    @Test
    void testGetBranchList() throws InterruptedException {
        String responseBody = StubsUtil.loadAsString("stub/branch/branch_list.json");
        MockResponse mockResponse = new MockResponse()
                .setBody(responseBody)
                .setResponseCode(200);
        server.enqueue(mockResponse);
        BranchList expected = StubsUtil.loadStub("stub/branch/branch_list.json", BranchList.class);

        BranchList actual = diiaBranchApi.getBranches(null, null);

        RecordedRequest recordedRequest = server.takeRequest();
        assertEquals(expected.getTotal(), actual.getTotal());
        List<Branch> expectedBranches = expected.getBranches();
        List<Branch> actualBranches = actual.getBranches();
        assertEquals(expectedBranches.size(), actualBranches.size());

        for (int i = 0; i < expectedBranches.size(); i++) {
            assertEqualsBranch(expectedBranches.get(i), actualBranches.get(i));
        }

        assertTrue(String.format("bearer %s", ACCESS_TOKEN).equalsIgnoreCase(recordedRequest.getHeader("authorization")));
        assertTrue("application/json".equalsIgnoreCase(recordedRequest.getHeader("accept")));
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/api/v2/acquirers/branches", recordedRequest.getPath());
    }

    @Test
    void testGetBranchListSkipQuery() throws InterruptedException {
        String responseBody = StubsUtil.loadAsString("stub/branch/branch_list.json");
        MockResponse mockResponse = new MockResponse()
                .setBody(responseBody)
                .setResponseCode(200);
        server.enqueue(mockResponse);

        long skipValue = 3L;

        diiaBranchApi.getBranches(skipValue, null);

        RecordedRequest recordedRequest = server.takeRequest();

        assertTrue(String.format("bearer %s", ACCESS_TOKEN).equalsIgnoreCase(recordedRequest.getHeader("authorization")));
        assertTrue("application/json".equalsIgnoreCase(recordedRequest.getHeader("accept")));
        assertEquals("GET", recordedRequest.getMethod());
        assertNotNull(recordedRequest.getRequestUrl());
        assertEquals(String.valueOf(skipValue), recordedRequest.getRequestUrl().queryParameter("skip"));
    }

    @Test
    void testGetBranchListLimitQuery() throws InterruptedException {
        String responseBody = StubsUtil.loadAsString("stub/branch/branch_list.json");
        MockResponse mockResponse = new MockResponse()
                .setBody(responseBody)
                .setResponseCode(200);
        server.enqueue(mockResponse);

        long limitValue = 75555L;

        diiaBranchApi.getBranches(null, limitValue);

        RecordedRequest recordedRequest = server.takeRequest();

        assertTrue(String.format("bearer %s", ACCESS_TOKEN).equalsIgnoreCase(recordedRequest.getHeader("authorization")));
        assertTrue("application/json".equalsIgnoreCase(recordedRequest.getHeader("accept")));
        assertEquals("GET", recordedRequest.getMethod());
        assertNotNull(recordedRequest.getRequestUrl());
        assertEquals(String.valueOf(limitValue), recordedRequest.getRequestUrl().queryParameter("limit"));
    }

    @Test
    void testUpdateBranch() throws InterruptedException, JSONException {
        String responseBody = "{ \"_id\": \"1d441d305adc2bff982fde159f8c91706a68a80b1cff5bd2aebecb43f71f25\" }";
        MockResponse mockResponse = new MockResponse()
                .setBody(responseBody)
                .setResponseCode(200);
        server.enqueue(mockResponse);
        String branchInJson = StubsUtil.loadAsString("stub/branch/existing_branch.json");
        Branch newBranch = StubsUtil.loadStub("stub/branch/existing_branch.json", Branch.class);

        diiaBranchApi.updateBranch(newBranch);

        RecordedRequest recordedRequest = server.takeRequest();
        String actualBody = recordedRequest.getBody().readString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals(branchInJson, actualBody, false);
        assertTrue(String.format("bearer %s", ACCESS_TOKEN).equalsIgnoreCase(recordedRequest.getHeader("authorization")));
        assertTrue("application/json".equalsIgnoreCase(recordedRequest.getHeader("accept")));
        assertTrue("application/json; charset=utf-8".equalsIgnoreCase(recordedRequest.getHeader("Content-Type")));
        assertEquals("PUT", recordedRequest.getMethod());
        assertEquals("/api/v2/acquirers/branch/" + newBranch.getId().getId(), recordedRequest.getPath());
    }

    private void assertEqualsBranch(Branch expected, Branch actual) {
        assertTrue(new ReflectionEquals(expected, "id", "scopes").matches(actual));
        assertTrue(new ReflectionEquals(expected.getScopes()).matches(actual.getScopes()));
        assertEquals(expected.getId().getId(), actual.getId().getId());
    }
}
