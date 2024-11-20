package ua.gov.diia.client.sdk.remote.model;

public class DeepLink {
    private String deeplink;

    public String getDeeplink() {
        return deeplink;
    }

    public void setDeeplink(String deeplink) {
        this.deeplink = deeplink;
    }

    @Override
    public String toString() {
        return "DeepLink{" +
                "deeplink='" + deeplink + '\'' +
                '}';
    }
}
