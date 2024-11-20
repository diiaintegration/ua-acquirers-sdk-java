package ua.gov.diia.client.sdk.remote.model;

import ua.gov.diia.client.sdk.model.FileHash;

import java.util.List;

public class DeepLinkRequest {
    private String offerId;
    private String requestId;
    private String returnLink;
    private Data data;
    public static class Data {
        private HashedFilesSigning hashedFilesSigning;
        public static class HashedFilesSigning {
            private List<FileHash> hashedFiles;

            public List<FileHash> getHashedFiles() {
                return hashedFiles;
            }

            public void setHashedFiles(List<FileHash> hashedFiles) {
                this.hashedFiles = hashedFiles;
            }

            @Override
            public String toString() {
                return "HashedFilesSigning{" +
                        "hashedFiles=" + hashedFiles +
                        '}';
            }
        }

        public HashedFilesSigning getHashedFilesSigning() {
            return hashedFilesSigning;
        }

        public void setHashedFilesSigning(HashedFilesSigning hashedFilesSigning) {
            this.hashedFilesSigning = hashedFilesSigning;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "hashedFilesSigning=" + hashedFilesSigning +
                    '}';
        }
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getReturnLink() {
        return returnLink;
    }

    public void setReturnLink(String returnLink) {
        this.returnLink = returnLink;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DeepLinkRequest{" +
                "offerId='" + offerId + '\'' +
                ", requestId='" + requestId + '\'' +
                ", returnLink='" + returnLink + '\'' +
                ", data=" + data +
                '}';
    }
}
