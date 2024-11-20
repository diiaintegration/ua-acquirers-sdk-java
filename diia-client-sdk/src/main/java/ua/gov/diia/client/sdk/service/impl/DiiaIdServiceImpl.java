package ua.gov.diia.client.sdk.service.impl;

import org.jetbrains.annotations.NotNull;
import ua.gov.diia.client.crypto.api.CryptoService;
import ua.gov.diia.client.sdk.model.FileData;
import ua.gov.diia.client.sdk.model.FileHash;
import ua.gov.diia.client.sdk.remote.DiiaIdApi;
import ua.gov.diia.client.sdk.remote.model.AuthDeepLink;
import ua.gov.diia.client.sdk.remote.model.DeepLink;
import ua.gov.diia.client.sdk.remote.model.DeepLinkRequest;
import ua.gov.diia.client.sdk.service.DiiaIdService;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class DiiaIdServiceImpl implements DiiaIdService {
    private final CryptoService cryptoService;
    private final DiiaIdApi diiaIdApi;

    public DiiaIdServiceImpl(CryptoService cryptoService, DiiaIdApi diiaIdApi) {
        this.cryptoService = cryptoService;
        this.diiaIdApi = diiaIdApi;
    }

    @Override
    public String getSignDeepLink(String branchId, String offerId, String requestId, List<FileData> filesToSign) {
        DeepLinkRequest deepLinkRequest = createDeepLinkRequest(offerId, requestId);
        deepLinkRequest.setData(filesToHashData(filesToSign));
        DeepLink signDeepLink = diiaIdApi.getDeepLink(branchId, deepLinkRequest);
        return signDeepLink.getDeeplink();
    }

    @Override
    public AuthDeepLink getAuthDeepLink(String branchId, String offerId, String requestId) {
        String requestIdHash = cryptoService.getHash(requestId.getBytes(StandardCharsets.UTF_8));
        DeepLinkRequest deepLinkRequest = createDeepLinkRequest(offerId, requestIdHash);
        DeepLink deepLink = diiaIdApi.getDeepLink(branchId, deepLinkRequest);

        AuthDeepLink authDeepLink = new AuthDeepLink();
        authDeepLink.setRequestId(requestId);
        authDeepLink.setRequestIdHash(requestIdHash);
        authDeepLink.setDeepLink(deepLink.getDeeplink());
        return authDeepLink;
    }

    @NotNull
    private DeepLinkRequest createDeepLinkRequest(String offerId, String requestId) {
        DeepLinkRequest deepLinkRequest = new DeepLinkRequest();
        deepLinkRequest.setRequestId(requestId);
        deepLinkRequest.setOfferId(offerId);
        return deepLinkRequest;
    }

    @NotNull
    private DeepLinkRequest.Data filesToHashData(List<FileData> filesToSign) {
        DeepLinkRequest.Data data = new DeepLinkRequest.Data();
        DeepLinkRequest.Data.HashedFilesSigning hashedFilesSigning = new DeepLinkRequest.Data.HashedFilesSigning();
        List<FileHash> fileHashes = filesToSign.stream()
                .map(file -> {
                    FileHash fileHash = new FileHash();
                    fileHash.setFileHash(cryptoService.getHash(file.getData()));
                    fileHash.setFileName(file.getFileName());
                    return fileHash;
                })
                .collect(Collectors.toList());
        hashedFilesSigning.setHashedFiles(fileHashes);
        data.setHashedFilesSigning(hashedFilesSigning);
        return data;
    }
}
