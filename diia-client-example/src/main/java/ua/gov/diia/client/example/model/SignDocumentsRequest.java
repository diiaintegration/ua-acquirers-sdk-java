package ua.gov.diia.client.example.model;

import org.springframework.web.multipart.MultipartFile;

public class SignDocumentsRequest {
    private String branchAndOfferId;
    private String requestId;
    private MultipartFile[] files;

    public String getBranchAndOfferId() {
        return branchAndOfferId;
    }

    public void setBranchAndOfferId(String branchAndOfferId) {
        this.branchAndOfferId = branchAndOfferId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }
}
