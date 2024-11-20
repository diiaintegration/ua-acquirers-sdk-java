package ua.gov.diia.client.sdk.remote.model;

import java.util.Collections;
import java.util.List;

public class BranchScopes {
    private List<String> sharing;
    private List<String> documentIdentification;
    private List<String> identification = Collections.emptyList();
    private List<String> diiaId;

    public List<String> getSharing() {
        return sharing;
    }

    public void setSharing(List<String> sharing) {
        this.sharing = sharing;
    }

    public List<String> getDocumentIdentification() {
        return documentIdentification;
    }

    public void setDocumentIdentification(List<String> documentIdentification) {
        this.documentIdentification = documentIdentification;
    }

    public List<String> getIdentification() {
        return identification;
    }

    public void setIdentification(List<String> identification) {
        this.identification = identification;
    }

    public List<String> getDiiaId() {
        return diiaId;
    }

    public void setDiiaId(List<String> diiaId) {
        this.diiaId = diiaId;
    }

    @Override
    public String toString() {
        return "BranchScopes{" +
                "sharing=" + sharing +
                ", documentIdentification=" + documentIdentification +
                ", identification=" + identification +
                ", diiaId=" + diiaId +
                '}';
    }
}
