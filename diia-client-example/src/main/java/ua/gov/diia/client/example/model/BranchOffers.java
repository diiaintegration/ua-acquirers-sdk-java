package ua.gov.diia.client.example.model;

import ua.gov.diia.client.sdk.remote.model.Branch;
import ua.gov.diia.client.sdk.remote.model.Offer;

import java.util.List;

public class BranchOffers {
    private final Branch branch;
    private final List<Offer> offers;

    public BranchOffers(Branch branch, List<Offer> offers) {
        this.branch = branch;
        this.offers = offers;
    }

    public Branch getBranch() {
        return branch;
    }

    public List<Offer> getOffers() {
        return offers;
    }
}
