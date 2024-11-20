package ua.gov.diia.client.sdk.remote.model;

public class AuthData {
    private String requestId;
    private String signature;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "AuthData{" +
                "requestId='" + requestId + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
