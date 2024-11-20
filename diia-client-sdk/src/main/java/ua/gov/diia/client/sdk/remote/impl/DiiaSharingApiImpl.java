package ua.gov.diia.client.sdk.remote.impl;

import ua.gov.diia.client.sdk.exception.DiiaClientException;
import ua.gov.diia.client.sdk.remote.DiiaSharingApi;
import ua.gov.diia.client.sdk.remote.HttpMethodExecutor;
import ua.gov.diia.client.sdk.remote.model.DeepLink;
import ua.gov.diia.client.sdk.remote.model.DeepLinkRequest;
import ua.gov.diia.client.sdk.remote.model.DocumentRequest;
import ua.gov.diia.client.sdk.remote.model.SimpleResponse;

import java.io.IOException;

public class DiiaSharingApiImpl implements DiiaSharingApi {
    private final String baseDiiaUrl;
    private final HttpMethodExecutor httpMethodExecutor;

    public DiiaSharingApiImpl(String baseDiiaUrl, HttpMethodExecutor httpMethodExecutor) {
        this.baseDiiaUrl = baseDiiaUrl;
        this.httpMethodExecutor = httpMethodExecutor;
    }

    /*
        curl -X POST "https://{diia_host}/api/v1/acquirers/document-request"
        -H  "accept: application/json"
        -H  "Authorization: Bearer {session_token}"
        -H  "Content-Type: application/json"
        -d "{ \"branchId\": \"{branch_id}\", \"barcode\": \"{barcode}\", \"requestId\": \"{request_id}\" }" or
        -d "{ \"branchId\": \"{branch_id}\", \"qrcode\": \"{qrcode}\", \"requestId\": \"{request_id}\" }"
    */
    @Override
    public boolean requestDocument(DocumentRequest documentRequest) {
        try {
            String url = String.format("%s/api/v1/acquirers/document-request", baseDiiaUrl);
            SimpleResponse response = httpMethodExecutor.doPost(url, documentRequest, SimpleResponse.class);
            return response.isSuccess();
        } catch (IOException e) {
            throw new DiiaClientException("Document request error", e);
        }
    }

    /*
        curl -X POST "https://{diia_host}/api/v2/acquirers/branch/{branch_id}/offer-request/dynamic"
        -H  "accept: application/json"
        -H  "Authorization: Bearer {session_token}"
        -H  "Content-Type: application/json"
        -d "{ \"offerId\": \"{offer_id}\", \"requestId\": \"{request_id}\" }"
    */
    @Override
    public String getDeepLink(String branchId, DeepLinkRequest deepLinkRequest) {
        try {
            String url = String.format("%s/api/v2/acquirers/branch/%s/offer-request/dynamic", baseDiiaUrl, branchId);
            DeepLink deepLink = httpMethodExecutor.doPost(url, deepLinkRequest, DeepLink.class);
            return deepLink.getDeeplink();
        } catch (IOException e) {
            throw new DiiaClientException("DeepLink request error", e);
        }
    }
}
