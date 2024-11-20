package ua.gov.diia.client.sdk.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static ua.gov.diia.client.sdk.model.DocumentTypeConstant.*;

public class Metadata {
    private String requestId;
    private List<String> documentTypes;

    private Data data;
    public static class Data {
        @JsonProperty(DOC_TYPE_INTERNAL_PASSPORT)
        private List<InternalPassport> internalPassport;
        @JsonProperty(DOC_TYPE_FOREIGN_PASSPORT)
        private List<ForeignPassport> foreignPassport;
        @JsonProperty(DOC_TYPE_TAXPAYER_CARD)
        private List<TaxpayerCard> taxpayerCard;
        @JsonProperty(DOC_TYPE_REFERENCE_INTERNALLY_DISPLACED_PERSON)
        private List<ReferenceInternallyDisplacedPerson> referenceInternallyDisplacedPerson;
        @JsonProperty(DOC_TYPE_BIRTH_CERTIFICATE)
        private List<BirthCertificate> birthCertificate;
        @JsonProperty(DOC_TYPE_DRIVER_LICENSE)
        private List<DriverLicense> driverLicense;
        @JsonProperty(DOC_TYPE_VEHICLE_LICENSE)
        private List<VehicleLicense> vehicleLicense;

        public List<InternalPassport> getInternalPassport() {
            return internalPassport;
        }

        public void setInternalPassport(List<InternalPassport> internalPassport) {
            this.internalPassport = internalPassport;
        }

        public List<ForeignPassport> getForeignPassport() {
            return foreignPassport;
        }

        public void setForeignPassport(List<ForeignPassport> foreignPassport) {
            this.foreignPassport = foreignPassport;
        }

        public List<TaxpayerCard> getTaxpayerCard() {
            return taxpayerCard;
        }

        public void setTaxpayerCard(List<TaxpayerCard> taxpayerCard) {
            this.taxpayerCard = taxpayerCard;
        }

        public List<ReferenceInternallyDisplacedPerson> getReferenceInternallyDisplacedPerson() {
            return referenceInternallyDisplacedPerson;
        }

        public void setReferenceInternallyDisplacedPerson(List<ReferenceInternallyDisplacedPerson> referenceInternallyDisplacedPerson) {
            this.referenceInternallyDisplacedPerson = referenceInternallyDisplacedPerson;
        }

        public List<BirthCertificate> getBirthCertificate() {
            return birthCertificate;
        }

        public void setBirthCertificate(List<BirthCertificate> birthCertificate) {
            this.birthCertificate = birthCertificate;
        }

        public List<DriverLicense> getDriverLicense() {
            return driverLicense;
        }

        public void setDriverLicense(List<DriverLicense> driverLicense) {
            this.driverLicense = driverLicense;
        }

        public List<VehicleLicense> getVehicleLicense() {
            return vehicleLicense;
        }

        public void setVehicleLicense(List<VehicleLicense> vehicleLicense) {
            this.vehicleLicense = vehicleLicense;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "internalPassport=" + internalPassport +
                    ", foreignPassport=" + foreignPassport +
                    ", taxpayerCard=" + taxpayerCard +
                    ", referenceInternallyDisplacedPerson=" + referenceInternallyDisplacedPerson +
                    ", birthCertificate=" + birthCertificate +
                    ", driverLicense=" + driverLicense +
                    ", vehicleLicense=" + vehicleLicense +
                    '}';
        }
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public List<String> getDocumentTypes() {
        return documentTypes;
    }

    public void setDocumentTypes(List<String> documentTypes) {
        this.documentTypes = documentTypes;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Metadata{" +
                "requestId='" + requestId + '\'' +
                ", documentTypes=" + documentTypes +
                ", data=" + data +
                '}';
    }
}
