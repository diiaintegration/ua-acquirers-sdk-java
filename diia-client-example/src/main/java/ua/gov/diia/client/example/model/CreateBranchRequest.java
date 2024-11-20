package ua.gov.diia.client.example.model;

public class CreateBranchRequest {
    private String name;
    private String email;
    private String region;
    private String district;
    private String location;
    private String street;
    private String house;

    private String customFullName;
    private String customFullAddress;

    private String offerRequestType;
    private String sharing;
    private String documentIdentification;
    private String diiaId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getCustomFullName() {
        return customFullName;
    }

    public void setCustomFullName(String customFullName) {
        this.customFullName = customFullName;
    }

    public String getCustomFullAddress() {
        return customFullAddress;
    }

    public void setCustomFullAddress(String customFullAddress) {
        this.customFullAddress = customFullAddress;
    }

    public String getOfferRequestType() {
        return offerRequestType;
    }

    public void setOfferRequestType(String offerRequestType) {
        this.offerRequestType = offerRequestType;
    }

    public String getSharing() {
        return sharing;
    }

    public void setSharing(String sharing) {
        this.sharing = sharing;
    }

    public String getDocumentIdentification() {
        return documentIdentification;
    }

    public void setDocumentIdentification(String documentIdentification) {
        this.documentIdentification = documentIdentification;
    }

    public String getDiiaId() {
        return diiaId;
    }

    public void setDiiaId(String diiaId) {
        this.diiaId = diiaId;
    }
}
