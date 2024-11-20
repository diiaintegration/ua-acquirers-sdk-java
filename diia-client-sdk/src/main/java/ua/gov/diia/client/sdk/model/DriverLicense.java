package ua.gov.diia.client.sdk.model;

/**
 * Посвідчення водія
 */
public class DriverLicense {
    private String expirationDate;
    private String categories;
    private String serialNumber;
    private String lastNameUA;
    private String firstNameUA;
    private String middleNameUA;
    private String birthday;
    private String department;

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "DriverLicense{" +
                "expirationDate='" + expirationDate + '\'' +
                ", categories='" + categories + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", lastNameUA='" + lastNameUA + '\'' +
                ", firstNameUA='" + firstNameUA + '\'' +
                ", middleNameUA='" + middleNameUA + '\'' +
                ", birthday='" + birthday + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
