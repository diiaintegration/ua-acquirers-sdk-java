package ua.gov.diia.client.sdk.service;

import ua.gov.diia.client.sdk.remote.model.OfferList;

import java.util.List;

public interface OfferService {
    OfferList getOffers(String branchId, Long skip, Long limit);
    String createOffer(String branchId, String name, String returnLink, List<String> sharing, List<String> diiaId);
    void deleteOffer(String branchId, String offerId);
}
