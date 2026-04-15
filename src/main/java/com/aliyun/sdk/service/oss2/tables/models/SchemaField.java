package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SchemaField {

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String type;

    @JsonProperty("required")
    private Boolean required;

    public SchemaField() {
    }

    private SchemaField(Builder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.required = builder.required;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public String name() {
        return name;
    }

    public String type() {
        return type;
    }

    public Boolean required() {
        return required;
    }

    public static class Builder {
        private String name;
        private String type;
        private Boolean required;

        private Builder() {
        }

        private Builder(SchemaField model) {
            this.name = model.name;
            this.type = model.type;
            this.required = model.required;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder required(Boolean required) {
            this.required = required;
            return this;
        }

        public SchemaField build() {
            return new SchemaField(this);
        }
    }
}
