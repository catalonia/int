package com.tastesync.dataextraction.json.factual.crosswalk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Response {
    private List<Datum> data = new ArrayList<Datum>();
    private Integer included_rows;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Response withData(List<Datum> data) {
        this.data = data;

        return this;
    }

    public Integer getIncluded_rows() {
        return included_rows;
    }

    public void setIncluded_rows(Integer included_rows) {
        this.included_rows = included_rows;
    }

    public Response withIncluded_rows(Integer included_rows) {
        this.included_rows = included_rows;

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
        return "Response [data=" + data + ", included_rows=" + included_rows +
        ", additionalProperties=" + additionalProperties + "]";
    }
}
