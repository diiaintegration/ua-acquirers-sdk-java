package ua.gov.diia.client.sdk.remote.impl;

import ua.gov.diia.client.sdk.exception.DiiaClientException;
import ua.gov.diia.client.sdk.remote.DiiaValidationApi;
import ua.gov.diia.client.sdk.remote.HttpMethodExecutor;
import ua.gov.diia.client.sdk.remote.model.DocumentRequest;
import ua.gov.diia.client.sdk.remote.model.SimpleResponse;

import java.io.IOException;

public class DiiaValidationApiImpl implements DiiaValidationApi {
    private final String baseDiiaUrl;
    private final HttpMethodExecutor httpMethodExecutor;

    public DiiaValidationApiImpl(String baseDiiaUrl, HttpMethodExecutor httpMethodExecutor) {
        this.baseDiiaUrl = baseDiiaUrl;
        this.httpMethodExecutor = httpMethodExecutor;
    }

    /*
            curl -X POST "https://{diia_host}/api/v1/acquirers/document-identification"
            -H "accept: application/json"
            -H "Authorization: Bearer {session_token}"
            -H "Content-Type: application/json"
            -d "{\"branchId\":\"{branch_id}\",\"barcode\":\"{barcode}\"}"
        */
    @Override
    public boolean validateDocumentByBarcode(DocumentRequest documentRequest) {
        try {
            String url = String.format("%s/api/v1/acquirers/document-identification", baseDiiaUrl);
            SimpleResponse response = httpMethodExecutor.doPost(url, documentRequest, SimpleResponse.class);
            return response.isSuccess();
        } catch (IOException e) {
            throw new DiiaClientException("Document validation error", e);
        }
    }
}
