package ua.gov.diia.client.sdk.remote;

import okhttp3.*;
import ua.gov.diia.client.sdk.exception.DiiaClientException;
import ua.gov.diia.client.sdk.remote.model.ErrorMessage;
import ua.gov.diia.client.sdk.service.ObjectMapperHolder;
import ua.gov.diia.client.sdk.service.SessionTokenService;

import java.io.IOException;

public class HttpMethodExecutor {
    private static final MediaType MEDIA_TYPE_JSON = MediaType.get("application/json; charset=utf-8");

    private final SessionTokenService sessionTokenService;
    private final OkHttpClient httpClient;

    public HttpMethodExecutor(SessionTokenService sessionTokenService, OkHttpClient httpClient) {
        this.sessionTokenService = sessionTokenService;
        this.httpClient = httpClient;
    }

    public <T> T doGet(String url, Class<T> valueType) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", String.format("Bearer %s", sessionTokenService.getSessionToken()))
                .get()
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            handleError(response);
            return ObjectMapperHolder.getObjectMapper().readValue(response.body().string(), valueType);
        }
    }

    public <T> T doPost(String url, Object request, Class<T> valueType) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, ObjectMapperHolder.getObjectMapper().writeValueAsBytes(request));
        Request httpRequest = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", String.format("Bearer %s", sessionTokenService.getSessionToken()))
                .post(requestBody)
                .build();

        try (Response response = httpClient.newCall(httpRequest).execute()) {
            handleError(response);
            return ObjectMapperHolder.getObjectMapper().readValue(response.body().string(), valueType);
        }
    }

    public void doDelete(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "*/*")
                .addHeader("Authorization", String.format("Bearer %s", sessionTokenService.getSessionToken()))
                .delete()
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            handleError(response);
        }
    }

    public <T> T doPut(String url, Object request, Class<T> valueType) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, ObjectMapperHolder.getObjectMapper().writeValueAsBytes(request));
        Request httpRequest = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", String.format("Bearer %s", sessionTokenService.getSessionToken()))
                .put(requestBody)
                .build();

        try (Response response = httpClient.newCall(httpRequest).execute()) {
            handleError(response);
            return ObjectMapperHolder.getObjectMapper().readValue(response.body().string(), valueType);
        }
    }

    private void handleError(Response response) throws IOException {
        if (!response.isSuccessful()) {
            if (response.body() == null) {
                throw new DiiaClientException("Api call error: " + response);
            } else {
                ErrorMessage errorMessage = ObjectMapperHolder.getObjectMapper().readValue(response.body().string(), ErrorMessage.class);
                throw new DiiaClientException(errorMessage);
            }
        }
    }
}
