package ua.gov.diia.client.sdk.service.impl;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ua.gov.diia.client.sdk.exception.DiiaClientException;
import ua.gov.diia.client.sdk.remote.model.SessionToken;
import ua.gov.diia.client.sdk.service.ObjectMapperHolder;
import ua.gov.diia.client.sdk.service.SessionTokenService;

import java.io.IOException;

public class SessionTokenServiceImpl implements SessionTokenService {
    private static final long SESSION_TOKEN_TIME_TO_LIVE = 2 * 3600 * 1000L;

    private volatile String sessionToken;
    private volatile long sessionTokenObtainTime = 0L;

    private final String diiaHost;
    private final String acquirerToken;
    private final OkHttpClient httpClient;

    public SessionTokenServiceImpl(String acquirerToken, String diiaHost, OkHttpClient httpClient) {
        this.acquirerToken = acquirerToken;
        this.diiaHost = diiaHost;
        this.httpClient = httpClient;
    }

    @Override
    public String getSessionToken() {
        long now = System.currentTimeMillis();
        if (SESSION_TOKEN_TIME_TO_LIVE <= now - sessionTokenObtainTime) {
            synchronized (this) {
                sessionToken = obtainSessionToken();
                sessionTokenObtainTime = now;
            }
        }
        return sessionToken;
    }

    /*
        curl -X GET "https://{diia_host}/api/v1/auth/acquirer/{acquirer_token}"
            -H  "accept: application/json"
            -H "Authorization: Basic {acquirer_token}"
     */
    private String obtainSessionToken() {
        Request request = new Request.Builder()
                .url(String.format("%s/api/v1/auth/acquirer/%s", diiaHost, acquirerToken))
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", String.format("Basic %s", acquirerToken))
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null)
                throw new DiiaClientException("Authentication error: " + response);
            return ObjectMapperHolder.getObjectMapper().readValue(response.body().string(), SessionToken.class).getToken();
        } catch (IOException e) {
            throw new DiiaClientException("Authentication error", e);
        }
    }
}
