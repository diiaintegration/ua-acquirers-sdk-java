package ua.gov.diia.client.sdk.remote;

import ua.gov.diia.client.sdk.remote.model.DeepLinkRequest;
import ua.gov.diia.client.sdk.remote.model.DocumentRequest;

public interface DiiaSharingApi {
    boolean requestDocument(DocumentRequest documentRequest);
    String getDeepLink(String branchId, DeepLinkRequest deepLinkRequest);
}
