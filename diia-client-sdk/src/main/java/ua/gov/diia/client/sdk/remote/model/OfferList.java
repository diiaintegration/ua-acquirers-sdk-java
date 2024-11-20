package ua.gov.diia.client.sdk.remote.model;

import java.util.List;

public class OfferList {
    private long total;
    private List<Offer> offers;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    @Override
    public String toString() {
        return "OfferList{" +
                "total=" + total +
                ", offers=" + offers +
                '}';
    }
}
