package ua.gov.diia.client.sdk.remote;

import ua.gov.diia.client.sdk.remote.model.DeepLink;
import ua.gov.diia.client.sdk.remote.model.DeepLinkRequest;

public interface DiiaIdApi {
    DeepLink getDeepLink(String branchId, DeepLinkRequest request);
}
