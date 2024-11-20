package ua.gov.diia.client.sdk.remote.model;

public class SessionToken {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "SessionToken{" +
                "token='" + token + '\'' +
                '}';
    }
}
