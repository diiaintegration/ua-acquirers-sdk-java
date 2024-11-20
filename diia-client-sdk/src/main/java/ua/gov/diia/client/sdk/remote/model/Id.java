package ua.gov.diia.client.sdk.remote.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Id {
    @JsonProperty(value = "_id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Id{" +
                "id='" + id + '\'' +
                '}';
    }
}
