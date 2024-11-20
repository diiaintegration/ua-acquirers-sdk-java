package ua.gov.diia.client.example.model;

public class SharingByDeeplinkRequest {
    private String branchAndOfferId;
    private String requestId;

    public String getBranchAndOfferId() {
        return branchAndOfferId;
    }

    public void setBranchAndOfferId(String branchAndOfferId) {
        this.branchAndOfferId = branchAndOfferId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
