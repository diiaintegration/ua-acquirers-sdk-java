package ua.gov.diia.client.sdk.service;

import ua.gov.diia.client.sdk.model.FileData;
import ua.gov.diia.client.sdk.remote.model.AuthDeepLink;

import java.util.List;

public interface DiiaIdService {
    String getSignDeepLink(String branchId, String offerId, String requestId, List<FileData> filesToSign);
    AuthDeepLink getAuthDeepLink(String branchId, String offerId, String requestId);
}
