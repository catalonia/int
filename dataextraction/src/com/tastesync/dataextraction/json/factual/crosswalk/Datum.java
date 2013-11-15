package com.tastesync.dataextraction.json.factual.crosswalk;

import java.util.HashMap;
import java.util.Map;

public class Datum {
    private String factual_id;
    private String namespace;
    private String namespace_id;
    private String url;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getFactual_id() {
        return factual_id;
    }

    public void setFactual_id(String factual_id) {
        this.factual_id = factual_id;
    }

    public Datum withFactual_id(String factual_id) {
        this.factual_id = factual_id;

        return this;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public Datum withNamespace(String namespace) {
        this.namespace = namespace;

        return this;
    }

    public String getNamespace_id() {
        return namespace_id;
    }

    public void setNamespace_id(String namespace_id) {
        this.namespace_id = namespace_id;
    }

    public Datum withNamespace_id(String namespace_id) {
        this.namespace_id = namespace_id;

        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Datum withUrl(String url) {
        this.url = url;

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
        return "Datum [factual_id=" + factual_id + ", namespace=" + namespace +
        ", namespace_id=" + namespace_id + ", url=" + url +
        ", additionalProperties=" + additionalProperties + "]";
    }
}
