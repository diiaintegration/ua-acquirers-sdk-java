package ua.gov.diia.client.sdk.model;

public class Signature {
    private FileData signature;
    private String requestIdHash;

    public FileData getSignature() {
        return signature;
    }

    public void setSignature(FileData signature) {
        this.signature = signature;
    }

    public String getRequestIdHash() {
        return requestIdHash;
    }

    public void setRequestIdHash(String requestIdHash) {
        this.requestIdHash = requestIdHash;
    }

    @Override
    public String toString() {
        return "AuthData{" +
                "signature='" + signature + '\'' +
                ", requestId='" + requestIdHash + '\'' +
                '}';
    }
}
