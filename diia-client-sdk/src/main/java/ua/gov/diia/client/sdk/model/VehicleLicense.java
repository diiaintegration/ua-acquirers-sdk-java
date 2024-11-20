package ua.gov.diia.client.sdk.model;

/**
 * Тех паспорт
 */
public class VehicleLicense {
    private String licensePlate;
    private String docNumber;
    private String brand;
    private String model;
    private String vin;
    private String color;
    private String kindBody;
    private String makeYear;
    private String totalWeight;
    private String ownWeight;
    private String capacity;
    private String fuel;
    private String rankCategory;
    private String seatsNumber;
    private String standingNumber;
    private String dateFirstReg;
    private String dateReg;
    private String ownerType;
    private String lastNameUA;
    private String firstNameUA;
    private String middleNameUA;
    private String birthday;
    private String address;

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getKindBody() {
        return kindBody;
    }

    public void setKindBody(String kindBody) {
        this.kindBody = kindBody;
    }

    public String getMakeYear() {
        return makeYear;
    }

    public void setMakeYear(String makeYear) {
        this.makeYear = makeYear;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getOwnWeight() {
        return ownWeight;
    }

    public void setOwnWeight(String ownWeight) {
        this.ownWeight = ownWeight;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getRankCategory() {
        return rankCategory;
    }

    public void setRankCategory(String rankCategory) {
        this.rankCategory = rankCategory;
    }

    public String getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(String seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public String getStandingNumber() {
        return standingNumber;
    }

    public void setStandingNumber(String standingNumber) {
        this.standingNumber = standingNumber;
    }

    public String getDateFirstReg() {
        return dateFirstReg;
    }

    public void setDateFirstReg(String dateFirstReg) {
        this.dateFirstReg = dateFirstReg;
    }

    public String getDateReg() {
        return dateReg;
    }

    public void setDateReg(String dateReg) {
        this.dateReg = dateReg;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public String getLastNameUA() {
        return lastNameUA;
    }

    public void setLastNameUA(String lastNameUA) {
        this.lastNameUA = lastNameUA;
    }

    public String getFirstNameUA() {
        return firstNameUA;
    }

    public void setFirstNameUA(String firstNameUA) {
        this.firstNameUA = firstNameUA;
    }

    public String getMiddleNameUA() {
        return middleNameUA;
    }

    public void setMiddleNameUA(String middleNameUA) {
        this.middleNameUA = middleNameUA;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "VehicleLicense{" +
                "licensePlate='" + licensePlate + '\'' +
                ", docNumber='" + docNumber + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", vin='" + vin + '\'' +
                ", color='" + color + '\'' +
                ", kindBody='" + kindBody + '\'' +
                ", makeYear='" + makeYear + '\'' +
                ", totalWeight='" + totalWeight + '\'' +
                ", ownWeight='" + ownWeight + '\'' +
                ", capacity='" + capacity + '\'' +
                ", fuel='" + fuel + '\'' +
                ", rankCategory='" + rankCategory + '\'' +
                ", seatsNumber='" + seatsNumber + '\'' +
                ", standingNumber='" + standingNumber + '\'' +
                ", dateFirstReg='" + dateFirstReg + '\'' +
                ", dateReg='" + dateReg + '\'' +
                ", ownerType='" + ownerType + '\'' +
                ", lastNameUA='" + lastNameUA + '\'' +
                ", firstNameUA='" + firstNameUA + '\'' +
                ", middleNameUA='" + middleNameUA + '\'' +
                ", birthday='" + birthday + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
