package ua.gov.diia.client.sdk.model;

/**
 * Довідка внутрішньо переміщеної особи (ВПО)
 */
public class ReferenceInternallyDisplacedPerson {
    private String docType;
    private String docNumber;
    private String department;
    private String lastName;
    private String firstName;
    private String middleName;
    private String issueDate;
    private String birthDate;
    private String gender;
    private String legalRepresentative;

    private DocIdentity docIdentity;
    public static class DocIdentity {
        private String number;
        private String issueDate;
        private String department;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getIssueDate() {
            return issueDate;
        }

        public void setIssueDate(String issueDate) {
            this.issueDate = issueDate;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        @Override
        public String toString() {
            return "DocIdentity{" +
                    "number='" + number + '\'' +
                    ", issueDate='" + issueDate + '\'' +
                    ", department='" + department + '\'' +
                    '}';
        }
    }

    private Address address;
    public static class Address {
        private String birth;
        private String registration;
        private String actual;

        public String getBirth() {
            return birth;
        }

        public void setBirth(String birth) {
            this.birth = birth;
        }

        public String getRegistration() {
            return registration;
        }

        public void setRegistration(String registration) {
            this.registration = registration;
        }

        public String getActual() {
            return actual;
        }

        public void setActual(String actual) {
            this.actual = actual;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "birth='" + birth + '\'' +
                    ", registration='" + registration + '\'' +
                    ", actual='" + actual + '\'' +
                    '}';
        }
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

    public DocIdentity getDocIdentity() {
        return docIdentity;
    }

    public void setDocIdentity(DocIdentity docIdentity) {
        this.docIdentity = docIdentity;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "ReferenceInternallyDisplacedPerson{" +
                "docType='" + docType + '\'' +
                ", docNumber='" + docNumber + '\'' +
                ", department='" + department + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", gender='" + gender + '\'' +
                ", legalRepresentative='" + legalRepresentative + '\'' +
                ", docIdentity=" + docIdentity +
                ", address=" + address +
                '}';
    }
}
