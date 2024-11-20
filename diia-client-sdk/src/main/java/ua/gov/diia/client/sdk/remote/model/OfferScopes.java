package ua.gov.diia.client.sdk.remote.model;

import java.util.List;

public class OfferScopes {
    private List<String> sharing;
    private List<String> diiaId;

    public List<String> getSharing() {
        return sharing;
    }

    public void setSharing(List<String> sharing) {
        this.sharing = sharing;
    }

    public List<String> getDiiaId() {
        return diiaId;
    }

    public void setDiiaId(List<String> diiaId) {
        this.diiaId = diiaId;
    }

    @Override
    public String toString() {
        return "OfferScopes{" +
                "sharing=" + sharing +
                ", diiaId=" + diiaId +
                '}';
    }
}
