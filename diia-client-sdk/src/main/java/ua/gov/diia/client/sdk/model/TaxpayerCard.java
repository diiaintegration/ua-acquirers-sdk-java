package ua.gov.diia.client.sdk.model;

/**
 * РНОКПП (ІПН)
 */
public class TaxpayerCard {
    private String creationDate;
    private String docNumber;
    private String lastNameUA;
    private String firstNameUA;
    private String middleNameUA;
    private String birthday;

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
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

    @Override
    public String toString() {
        return "TaxpayerCard{" +
                "creationDate='" + creationDate + '\'' +
                ", docNumber='" + docNumber + '\'' +
                ", lastNameUA='" + lastNameUA + '\'' +
                ", firstNameUA='" + firstNameUA + '\'' +
                ", middleNameUA='" + middleNameUA + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
