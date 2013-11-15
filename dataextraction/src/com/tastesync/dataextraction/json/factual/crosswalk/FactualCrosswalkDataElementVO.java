package com.tastesync.dataextraction.json.factual.crosswalk;

import java.util.HashMap;
import java.util.Map;


public class FactualCrosswalkDataElementVO {
    private Integer version;
    private String status;
    private Response response;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public FactualCrosswalkDataElementVO withVersion(Integer version) {
        this.version = version;

        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FactualCrosswalkDataElementVO withStatus(String status) {
        this.status = status;

        return this;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public FactualCrosswalkDataElementVO withResponse(Response response) {
        this.response = response;

        return this;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "FactualCrosswalkDataElementVO [version=" + version +
        ", status=" + status + ", response=" + response +
        ", additionalProperties=" + additionalProperties + "]";
    }
}
