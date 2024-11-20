package ua.gov.diia.client.sdk.remote.impl;

import okhttp3.HttpUrl;
import ua.gov.diia.client.sdk.exception.DiiaClientException;
import ua.gov.diia.client.sdk.remote.DiiaOfferApi;
import ua.gov.diia.client.sdk.remote.HttpMethodExecutor;
import ua.gov.diia.client.sdk.remote.model.Id;
import ua.gov.diia.client.sdk.remote.model.Offer;
import ua.gov.diia.client.sdk.remote.model.OfferList;

import java.io.IOException;
import java.util.Objects;

public class DiiaOfferApiImpl implements DiiaOfferApi {
    private final String baseDiiaUrl;
    private final HttpMethodExecutor httpMethodExecutor;

    public DiiaOfferApiImpl(String baseDiiaUrl, HttpMethodExecutor httpMethodExecutor) {
        this.baseDiiaUrl = baseDiiaUrl;
        this.httpMethodExecutor = httpMethodExecutor;
    }

    /*
        curl -X POST "https://{diia_host}/api/v1/acquirers/branch/{branch_id}/offer"
        -H  "accept: application/json"
        -H  "Authorization: Bearer {session_token}"
        -H  "Content-Type: application/json"
        -d "{ \"name\": \"Назва послуги\", \"scopes\": { \"sharing\": [\"passport\"] } }"
     */
    @Override
    public Id createOffer(String branchId, Offer offer) {
        try {
            String url = String.format("%s/api/v1/acquirers/branch/%s/offer", baseDiiaUrl, branchId);
            return httpMethodExecutor.doPost(url, offer, Id.class);
        } catch (IOException e) {
            throw new DiiaClientException("Offer creation error", e);
        }
    }

    /*
        curl -X GET "https://{diia_host}/api/v1/acquirers/branch/{branch_id}/offers?skip=0&limit=100"
        -H  "accept: application/json"
        -H  "Authorization: Bearer {session_token}"
    */
    @Override
    public OfferList getOffers(String branchId, Long skip, Long limit) {
        try {
            HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(
                            String.format("%s/api/v1/acquirers/branch/%s/offers", baseDiiaUrl, branchId)))
                    .newBuilder();
            if (skip != null) urlBuilder.addQueryParameter("skip", skip.toString());
            if (limit != null) urlBuilder.addQueryParameter("limit", limit.toString());
            String url = urlBuilder.build().toString();

            return httpMethodExecutor.doGet(url, OfferList.class);
        } catch (IOException e) {
            throw new DiiaClientException("Offer request error", e);
        }
    }

    /*
        curl -X DELETE "https://{diia_host}/api/v1/acquirers/branch/{branch_id}/offer/{offer_id}"
        -H "accept: *//*"
        -H "Authorization: Bearer {session_token}"
        -H "Content-Type: application/json"
    */
    @Override
    public void deleteOffer(String branchId, String offerId) {
        try {
            String url = String.format("%s/api/v1/acquirers/branch/%s/offer/%s", baseDiiaUrl, branchId, offerId);
            httpMethodExecutor.doDelete(url);
        } catch (IOException e) {
            throw new DiiaClientException("Offer deletion error", e);
        }
    }
}
