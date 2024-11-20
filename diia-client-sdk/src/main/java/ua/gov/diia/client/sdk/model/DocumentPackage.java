package ua.gov.diia.client.sdk.model;

import java.util.List;

public class DocumentPackage {
    private String requestId;
    private List<FileData> decodedFiles;
    private Metadata data;
    private BarcodeMetadata barcodeMetadata;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public List<FileData> getDecodedFiles() {
        return decodedFiles;
    }

    public void setDecodedFiles(List<FileData> decodedFiles) {
        this.decodedFiles = decodedFiles;
    }

    public Metadata getData() {
        return data;
    }

    public void setData(Metadata data) {
        this.data = data;
    }

    public BarcodeMetadata getBarcodeMetadata() {
        return barcodeMetadata;
    }

    public void setBarcodeMetadata(BarcodeMetadata barcodeMetadata) {
        this.barcodeMetadata = barcodeMetadata;
    }

    @Override
    public String toString() {
        return "DocumentPackage{" +
                "requestId='" + requestId + '\'' +
                ", decodedFiles=" + decodedFiles +
                ", data=" + data +
                ", barcodeMetadata=" + barcodeMetadata +
                '}';
    }
}
