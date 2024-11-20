package ua.gov.diia.client.sdk.remote.model;

import java.util.List;

public class SignedHashData {
    private List<SignedHash> signedItems;
    public static class SignedHash {
        private String name;
        private String signature;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        @Override
        public String toString() {
            return "SignedHash{" +
                    "name='" + name + '\'' +
                    ", signature='" + signature + '\'' +
                    '}';
        }
    }

    public List<SignedHash> getSignedItems() {
        return signedItems;
    }

    public void setSignedItems(List<SignedHash> signedItems) {
        this.signedItems = signedItems;
    }

    @Override
    public String toString() {
        return "SignedHashData{" +
                "signedItems=" + signedItems +
                '}';
    }
}
