package ua.gov.diia.client.sdk.remote.model;

import java.util.List;

public class BranchList {
    private long total;
    private List<Branch> branches;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    @Override
    public String toString() {
        return "BranchList{" +
                "total=" + total +
                ", branches=" + branches +
                '}';
    }
}
