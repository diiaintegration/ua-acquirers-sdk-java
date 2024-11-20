package ua.gov.diia.client.sdk.remote.model;

public class AuthDeepLink {
    private String requestId;
    private String requestIdHash;
    private String deepLink;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestIdHash() {
        return requestIdHash;
    }

    public void setRequestIdHash(String requestIdHash) {
        this.requestIdHash = requestIdHash;
    }

    public String getDeepLink() {
        return deepLink;
    }

    public void setDeepLink(String deepLink) {
        this.deepLink = deepLink;
    }

    @Override
    public String toString() {
        return "AuthDeepLink{" +
                "requestId='" + requestId + '\'' +
                ", requestIdHash='" + requestIdHash + '\'' +
                ", deepLink='" + deepLink + '\'' +
                '}';
    }
}
