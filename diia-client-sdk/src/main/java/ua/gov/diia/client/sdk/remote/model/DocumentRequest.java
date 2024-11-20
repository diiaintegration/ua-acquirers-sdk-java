package ua.gov.diia.client.sdk.remote.model;

public class DocumentRequest {
    private String branchId;
    private String barcode;
    private String qrcode;
    private String requestId;

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "DocumentRequest{" +
                "branchId='" + branchId + '\'' +
                ", barcode='" + barcode + '\'' +
                ", qrcode='" + qrcode + '\'' +
                ", requestId='" + requestId + '\'' +
                '}';
    }
}
