package ua.gov.diia.client.sdk.service.impl;

import ua.gov.diia.client.sdk.remote.DiiaOfferApi;
import ua.gov.diia.client.sdk.remote.model.Offer;
import ua.gov.diia.client.sdk.remote.model.OfferList;
import ua.gov.diia.client.sdk.remote.model.OfferScopes;
import ua.gov.diia.client.sdk.service.OfferService;

import java.util.List;

public class OfferServiceImpl implements OfferService {
    private final DiiaOfferApi diiaOfferApi;

    public OfferServiceImpl(DiiaOfferApi diiaOfferApi) {
        this.diiaOfferApi = diiaOfferApi;
    }

    @Override
    public OfferList getOffers(String branchId, Long skip, Long limit) {
        return diiaOfferApi.getOffers(branchId, skip, limit);
    }

    @Override
    public String createOffer(String branchId, String name, String returnLink, List<String> sharing, List<String> diiaId) {
        OfferScopes scopes = new OfferScopes();
        scopes.setSharing(sharing);
        scopes.setDiiaId(diiaId);

        Offer offer = new Offer();
        offer.setName(name);
        offer.setReturnLink(returnLink);
        offer.setScopes(scopes);

        return diiaOfferApi.createOffer(branchId, offer).getId();
    }

    @Override
    public void deleteOffer(String branchId, String offerId) {
        diiaOfferApi.deleteOffer(branchId, offerId);
    }
}
