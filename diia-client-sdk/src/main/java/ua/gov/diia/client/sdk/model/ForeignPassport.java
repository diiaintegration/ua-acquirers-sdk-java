package ua.gov.diia.client.sdk.model;

/**
 * Закордонний паспорт
 */
public class ForeignPassport {
    private String taxpayerNumber;
    private String residenceUA;
    private String docNumber;
    private String genderUA;
    private String nationalityUA;
    private String lastNameUA;
    private String firstNameUA;
    private String middleNameUA;
    private String birthday;
    private String birthPlaceUA;
    private String issueDate;
    private String expirationDate;
    private String recordNumber;
    private String departmentUA;
    private String countryCode;
    private String genderEN;
    private String lastNameEN;
    private String firstNameEN;
    private String departmentEN;

    public String getTaxpayerNumber() {
        return taxpayerNumber;
    }

    public void setTaxpayerNumber(String taxpayerNumber) {
        this.taxpayerNumber = taxpayerNumber;
    }

    public String getResidenceUA() {
        return residenceUA;
    }

    public void setResidenceUA(String residenceUA) {
        this.residenceUA = residenceUA;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getGenderUA() {
        return genderUA;
    }

    public void setGenderUA(String genderUA) {
        this.genderUA = genderUA;
    }

    public String getNationalityUA() {
        return nationalityUA;
    }

    public void setNationalityUA(String nationalityUA) {
        this.nationalityUA = nationalityUA;
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

    public String getBirthPlaceUA() {
        return birthPlaceUA;
    }

    public void setBirthPlaceUA(String birthPlaceUA) {
        this.birthPlaceUA = birthPlaceUA;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }

    public String getDepartmentUA() {
        return departmentUA;
    }

    public void setDepartmentUA(String departmentUA) {
        this.departmentUA = departmentUA;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getGenderEN() {
        return genderEN;
    }

    public void setGenderEN(String genderEN) {
        this.genderEN = genderEN;
    }

    public String getLastNameEN() {
        return lastNameEN;
    }

    public void setLastNameEN(String lastNameEN) {
        this.lastNameEN = lastNameEN;
    }

    public String getFirstNameEN() {
        return firstNameEN;
    }

    public void setFirstNameEN(String firstNameEN) {
        this.firstNameEN = firstNameEN;
    }

    public String getDepartmentEN() {
        return departmentEN;
    }

    public void setDepartmentEN(String departmentEN) {
        this.departmentEN = departmentEN;
    }

    @Override
    public String toString() {
        return "ForeignPassport{" +
                "taxpayerNumber='" + taxpayerNumber + '\'' +
                ", residenceUA='" + residenceUA + '\'' +
                ", docNumber='" + docNumber + '\'' +
                ", genderUA='" + genderUA + '\'' +
                ", nationalityUA='" + nationalityUA + '\'' +
                ", lastNameUA='" + lastNameUA + '\'' +
                ", firstNameUA='" + firstNameUA + '\'' +
                ", middleNameUA='" + middleNameUA + '\'' +
                ", birthday='" + birthday + '\'' +
                ", birthPlaceUA='" + birthPlaceUA + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", recordNumber='" + recordNumber + '\'' +
                ", departmentUA='" + departmentUA + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", genderEN='" + genderEN + '\'' +
                ", lastNameEN='" + lastNameEN + '\'' +
                ", firstNameEN='" + firstNameEN + '\'' +
                ", departmentEN='" + departmentEN + '\'' +
                '}';
    }
}
