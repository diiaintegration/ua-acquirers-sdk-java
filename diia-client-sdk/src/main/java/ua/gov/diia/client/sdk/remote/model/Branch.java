package ua.gov.diia.client.sdk.remote.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public class Branch {
    @JsonUnwrapped
    private Id id;
    private String name;
    private String email;
    private String region;
    private String district;
    private String location;
    private String street;
    private String house;

    private String customFullName;
    private String customFullAddress;

    private List<String> deliveryTypes;
    private String offerRequestType;

    private BranchScopes scopes;

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

    public List<String> getDeliveryTypes() {
        return deliveryTypes;
    }

    public void setDeliveryTypes(List<String> deliveryTypes) {
        this.deliveryTypes = deliveryTypes;
    }

    public String getOfferRequestType() {
        return offerRequestType;
    }

    public void setOfferRequestType(String offerRequestType) {
        this.offerRequestType = offerRequestType;
    }

    public BranchScopes getScopes() {
        return scopes;
    }

    public void setScopes(BranchScopes scopes) {
        this.scopes = scopes;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", region='" + region + '\'' +
                ", district='" + district + '\'' +
                ", location='" + location + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", customFullName='" + customFullName + '\'' +
                ", customFullAddress='" + customFullAddress + '\'' +
                ", deliveryTypes=" + deliveryTypes +
                ", offerRequestType='" + offerRequestType + '\'' +
                ", scopes=" + scopes +
                '}';
    }
}
