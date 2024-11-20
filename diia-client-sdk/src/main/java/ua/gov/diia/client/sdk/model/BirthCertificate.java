package ua.gov.diia.client.sdk.model;

/**
 * Свідоцтво про народження дитини
 */
public class BirthCertificate {
    private String id;
    private Document document;
    public static class Document {
        private String series;
        private String number;
        private String department;
        private String issueDate;

        public String getSeries() {
            return series;
        }

        public void setSeries(String series) {
            this.series = series;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getIssueDate() {
            return issueDate;
        }

        public void setIssueDate(String issueDate) {
            this.issueDate = issueDate;
        }

        @Override
        public String toString() {
            return "Document{" +
                    "series='" + series + '\'' +
                    ", number='" + number + '\'' +
                    ", department='" + department + '\'' +
                    ", issueDate='" + issueDate + '\'' +
                    '}';
        }
    }

    private Child child;
    public static class Child {
        private String lastName;
        private String firstName;
        private String middleName;
        private String birthDate;
        private String birthPlace;
        private String currentRegistrationPlaceUA;
        private String citizenship;

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

        public String getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public String getBirthPlace() {
            return birthPlace;
        }

        public void setBirthPlace(String birthPlace) {
            this.birthPlace = birthPlace;
        }

        public String getCurrentRegistrationPlaceUA() {
            return currentRegistrationPlaceUA;
        }

        public void setCurrentRegistrationPlaceUA(String currentRegistrationPlaceUA) {
            this.currentRegistrationPlaceUA = currentRegistrationPlaceUA;
        }

        public String getCitizenship() {
            return citizenship;
        }

        public void setCitizenship(String citizenship) {
            this.citizenship = citizenship;
        }

        @Override
        public String toString() {
            return "Child{" +
                    "lastName='" + lastName + '\'' +
                    ", firstName='" + firstName + '\'' +
                    ", middleName='" + middleName + '\'' +
                    ", birthDate='" + birthDate + '\'' +
                    ", birthPlace='" + birthPlace + '\'' +
                    ", currentRegistrationPlaceUA='" + currentRegistrationPlaceUA + '\'' +
                    ", citizenship='" + citizenship + '\'' +
                    '}';
        }
    }

    private Parents parents;
    public static class Parents {
        private Parent father;
        private Parent mother;
        public static class Parent {
            private String fullName;
            private String citizenship;

            public String getFullName() {
                return fullName;
            }

            public void setFullName(String fullName) {
                this.fullName = fullName;
            }

            public String getCitizenship() {
                return citizenship;
            }

            public void setCitizenship(String citizenship) {
                this.citizenship = citizenship;
            }

            @Override
            public String toString() {
                return "Parent{" +
                        "fullName='" + fullName + '\'' +
                        ", citizenship='" + citizenship + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Parents{" +
                    "father=" + father +
                    ", mother=" + mother +
                    '}';
        }
    }

    private Act act;
    public static class Act {
        private String name;
        private String registrationPlace;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRegistrationPlace() {
            return registrationPlace;
        }

        public void setRegistrationPlace(String registrationPlace) {
            this.registrationPlace = registrationPlace;
        }

        @Override
        public String toString() {
            return "Act{" +
                    "name='" + name + '\'' +
                    ", registrationPlace='" + registrationPlace + '\'' +
                    '}';
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public Parents getParents() {
        return parents;
    }

    public void setParents(Parents parents) {
        this.parents = parents;
    }

    public Act getAct() {
        return act;
    }

    public void setAct(Act act) {
        this.act = act;
    }

    @Override
    public String toString() {
        return "BirthCertificate{" +
                "id='" + id + '\'' +
                ", document=" + document +
                ", child=" + child +
                ", parents=" + parents +
                ", act=" + act +
                '}';
    }
}
