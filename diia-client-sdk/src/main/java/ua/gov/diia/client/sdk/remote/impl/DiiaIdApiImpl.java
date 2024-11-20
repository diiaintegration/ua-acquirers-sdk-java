package ua.gov.diia.client.sdk.remote.impl;

import ua.gov.diia.client.sdk.exception.DiiaClientException;
import ua.gov.diia.client.sdk.remote.DiiaIdApi;
import ua.gov.diia.client.sdk.remote.HttpMethodExecutor;
import ua.gov.diia.client.sdk.remote.model.DeepLink;
import ua.gov.diia.client.sdk.remote.model.DeepLinkRequest;

import java.io.IOException;

public class DiiaIdApiImpl implements DiiaIdApi {
    private final String baseDiiaUrl;
    private final HttpMethodExecutor httpMethodExecutor;

    public DiiaIdApiImpl(String baseDiiaUrl, HttpMethodExecutor httpMethodExecutor) {
        this.baseDiiaUrl = baseDiiaUrl;
        this.httpMethodExecutor = httpMethodExecutor;
    }

    /*
        curl -X POST "https://{diia_host}/api/v2/acquirers/branch/{branch_id}/offer-request/dynamic"
        -H  "accept: application/json"
        -H  "Authorization: Bearer {session_token}"
        -H  "Content-Type: application/json"
        -d "{\"offerId\": \"{offer_id}\", \"returnLink\": \"{return_link}\", \"requestId\": \"{request_id}\", \"data\": {
        \"hashedFilesSigning\": {\"hashedFiles\": [{\"fileName\": \"{file_name}\", \"fileHash\": \"{file_data}\"}]}}}"
    */
    @Override
    public DeepLink getDeepLink(String branchId, DeepLinkRequest request) {
        try {
            String url = String.format("%s/api/v2/acquirers/branch/%s/offer-request/dynamic", baseDiiaUrl, branchId);
            return httpMethodExecutor.doPost(url, request, DeepLink.class);
        } catch (IOException e) {
            throw new DiiaClientException("DeepLink request error", e);
        }
    }
}
