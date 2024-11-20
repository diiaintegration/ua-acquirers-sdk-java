package ua.gov.diia.client.sdk.service;

public interface SharingService {
    String getDeepLink(String branchId, String offerId, String requestId);
    boolean requestDocumentByBarCode(String branchId, String barcode, String requestId);
    boolean requestDocumentByQRCode(String branchId, String qrcode, String requestId);
}
