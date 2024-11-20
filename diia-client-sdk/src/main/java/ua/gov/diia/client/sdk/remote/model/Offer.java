package ua.gov.diia.client.sdk.remote.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class Offer {
    @JsonUnwrapped
    private Id id;
    private String name;
    private String returnLink;
    private OfferScopes scopes;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReturnLink() {
        return returnLink;
    }

    public void setReturnLink(String returnLink) {
        this.returnLink = returnLink;
    }

    public OfferScopes getScopes() {
        return scopes;
    }

    public void setScopes(OfferScopes scopes) {
        this.scopes = scopes;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", returnLink='" + returnLink + '\'' +
                ", scopes=" + scopes +
                '}';
    }
}
