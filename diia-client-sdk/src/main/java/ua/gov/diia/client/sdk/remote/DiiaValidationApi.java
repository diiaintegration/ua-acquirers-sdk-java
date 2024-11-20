package ua.gov.diia.client.sdk.remote;

import ua.gov.diia.client.sdk.remote.model.DocumentRequest;

public interface DiiaValidationApi {
    boolean validateDocumentByBarcode(DocumentRequest documentRequest);
}
