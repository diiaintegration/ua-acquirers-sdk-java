package ua.gov.diia.client.sdk.model;

import java.util.List;

public class SignedFileHashes {
    private String requestIdHash;
    private List<FileData> signedHashes;

    public String getRequestIdHash() {
        return requestIdHash;
    }

    public void setRequestIdHash(String requestIdHash) {
        this.requestIdHash = requestIdHash;
    }

    public List<FileData> getSignedHashes() {
        return signedHashes;
    }

    public void setSignedHashes(List<FileData> signedHashes) {
        this.signedHashes = signedHashes;
    }

    @Override
    public String toString() {
        return "SignedHashes{" +
                "requestIdHash='" + requestIdHash + '\'' +
                ", signedHashes=" + signedHashes +
                '}';
    }
}
