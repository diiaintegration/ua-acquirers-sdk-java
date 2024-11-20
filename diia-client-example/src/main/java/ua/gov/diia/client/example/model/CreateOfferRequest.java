package ua.gov.diia.client.example.model;

public class CreateOfferRequest {
    private String branchId;
    private String name;
    private String returnLink;
    private String sharing;
    private String diiaId;

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
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

    public String getSharing() {
        return sharing;
    }

    public void setSharing(String sharing) {
        this.sharing = sharing;
    }

    public String getDiiaId() {
        return diiaId;
    }

    public void setDiiaId(String diiaId) {
        this.diiaId = diiaId;
    }
}
