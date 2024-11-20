package ua.gov.diia.client.sdk.service;

public interface ValidationService {
    boolean validateDocumentByBarcode(String branchId, String barcode);
}
