package ua.gov.diia.client.sdk.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static ua.gov.diia.client.sdk.model.DocumentTypeConstant.*;

public class BarcodeMetadata {
    private String barcode;
    private List<String> documentTypes;

    private Data data;
    public static class Data {
        @JsonProperty(DOC_TYPE_INTERNAL_PASSPORT)
        private InternalPassport internalPassport;
        @JsonProperty(DOC_TYPE_FOREIGN_PASSPORT)
        private ForeignPassport foreignPassport;
        @JsonProperty(DOC_TYPE_TAXPAYER_CARD)
        private TaxpayerCard taxpayerCard;
        @JsonProperty(DOC_TYPE_REFERENCE_INTERNALLY_DISPLACED_PERSON)
        private ReferenceInternallyDisplacedPerson referenceInternallyDisplacedPerson;
        @JsonProperty(DOC_TYPE_BIRTH_CERTIFICATE)
        private BirthCertificate birthCertificate;
        @JsonProperty(DOC_TYPE_DRIVER_LICENSE)
        private DriverLicense driverLicense;
        @JsonProperty(DOC_TYPE_VEHICLE_LICENSE)
        private VehicleLicense vehicleLicense;

        public InternalPassport getInternalPassport() {
            return internalPassport;
        }

        public void setInternalPassport(InternalPassport internalPassport) {
            this.internalPassport = internalPassport;
        }

        public ForeignPassport getForeignPassport() {
            return foreignPassport;
        }

        public void setForeignPassport(ForeignPassport foreignPassport) {
            this.foreignPassport = foreignPassport;
        }

        public TaxpayerCard getTaxpayerCard() {
            return taxpayerCard;
        }

        public void setTaxpayerCard(TaxpayerCard taxpayerCard) {
            this.taxpayerCard = taxpayerCard;
        }

        public ReferenceInternallyDisplacedPerson getReferenceInternallyDisplacedPerson() {
            return referenceInternallyDisplacedPerson;
        }

        public void setReferenceInternallyDisplacedPerson(ReferenceInternallyDisplacedPerson referenceInternallyDisplacedPerson) {
            this.referenceInternallyDisplacedPerson = referenceInternallyDisplacedPerson;
        }

        public BirthCertificate getBirthCertificate() {
            return birthCertificate;
        }

        public void setBirthCertificate(BirthCertificate birthCertificate) {
            this.birthCertificate = birthCertificate;
        }

        public DriverLicense getDriverLicense() {
            return driverLicense;
        }

        public void setDriverLicense(DriverLicense driverLicense) {
            this.driverLicense = driverLicense;
        }

        public VehicleLicense getVehicleLicense() {
            return vehicleLicense;
        }

        public void setVehicleLicense(VehicleLicense vehicleLicense) {
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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
        return "BarcodeMetadata{" +
                "barcode='" + barcode + '\'' +
                ", documentTypes=" + documentTypes +
                ", data=" + data +
                '}';
    }
}
