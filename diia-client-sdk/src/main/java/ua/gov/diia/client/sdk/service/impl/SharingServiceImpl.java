package ua.gov.diia.client.sdk.service.impl;

import ua.gov.diia.client.sdk.remote.DiiaSharingApi;
import ua.gov.diia.client.sdk.remote.model.DeepLinkRequest;
import ua.gov.diia.client.sdk.remote.model.DocumentRequest;
import ua.gov.diia.client.sdk.service.SharingService;

public class SharingServiceImpl implements SharingService {
    private final DiiaSharingApi diiaSharingApi;

    public SharingServiceImpl(DiiaSharingApi diiaSharingApi) {
        this.diiaSharingApi = diiaSharingApi;
    }

    @Override
    public String getDeepLink(String branchId, String offerId, String requestId) {
        DeepLinkRequest deepLinkRequest = new DeepLinkRequest();
        deepLinkRequest.setRequestId(requestId);
        deepLinkRequest.setOfferId(offerId);

        return diiaSharingApi.getDeepLink(branchId, deepLinkRequest);
    }

    @Override
    public boolean requestDocumentByBarCode(String branchId, String barcode, String requestId) {
        DocumentRequest documentRequest = new DocumentRequest();
        documentRequest.setBranchId(branchId);
        documentRequest.setBarcode(barcode);
        documentRequest.setRequestId(requestId);

        return diiaSharingApi.requestDocument(documentRequest);
    }

    @Override
    public boolean requestDocumentByQRCode(String branchId, String qrCode, String requestId) {
        return false;
    }
}
