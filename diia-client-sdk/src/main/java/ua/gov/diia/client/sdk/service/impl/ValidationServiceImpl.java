package ua.gov.diia.client.sdk.service.impl;

import ua.gov.diia.client.sdk.remote.DiiaValidationApi;
import ua.gov.diia.client.sdk.remote.model.DocumentRequest;
import ua.gov.diia.client.sdk.service.ValidationService;

public class ValidationServiceImpl implements ValidationService {
    private final DiiaValidationApi diiaValidationApi;

    public ValidationServiceImpl(DiiaValidationApi diiaValidationApi) {
        this.diiaValidationApi = diiaValidationApi;
    }

    @Override
    public boolean validateDocumentByBarcode(String branchId, String barcode) {
        DocumentRequest documentRequest = new DocumentRequest();
        documentRequest.setBranchId(branchId);
        documentRequest.setBarcode(barcode);

        return diiaValidationApi.validateDocumentByBarcode(documentRequest);
    }
}
