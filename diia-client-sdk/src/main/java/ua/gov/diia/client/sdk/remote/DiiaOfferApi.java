package ua.gov.diia.client.sdk.remote;

import ua.gov.diia.client.sdk.remote.model.Id;
import ua.gov.diia.client.sdk.remote.model.Offer;
import ua.gov.diia.client.sdk.remote.model.OfferList;

public interface DiiaOfferApi {
    Id createOffer(String branchId, Offer offer);
    OfferList getOffers(String branchId, Long skip, Long limit);
    void deleteOffer(String branchId, String offerId);
}
