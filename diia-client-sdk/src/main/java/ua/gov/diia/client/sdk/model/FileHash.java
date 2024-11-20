package ua.gov.diia.client.sdk.model;

public class FileHash {
    private String fileName;
    private String fileHash;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    @Override
    public String toString() {
        return "FileHash{" +
                "fileName='" + fileName + '\'' +
                ", fileHash=" + fileHash +
                '}';
    }
}
