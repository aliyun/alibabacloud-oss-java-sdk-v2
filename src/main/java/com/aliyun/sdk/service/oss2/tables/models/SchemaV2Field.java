package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A field definition for {@link IcebergSchemaV2} or a nested {@link IcebergStructType}.
 */
public class SchemaV2Field {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private Object type;

    @JsonProperty("required")
    private Boolean required;

    @JsonProperty("doc")
    private String doc;

    public SchemaV2Field() {
    }

    private SchemaV2Field(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.type = builder.type;
        this.required = builder.required;
        this.doc = builder.doc;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public Integer id() {
        return id;
    }

    public String name() {
        return name;
    }

    /**
     * The field type: a primitive type name ({@link String}) or a complex type ({@link IcebergComplexType}).
     */
    public Object type() {
        return type;
    }

    public Boolean required() {
        return required;
    }

    public String doc() {
        return doc;
    }

    public static class Builder {
        private Integer id;
        private String name;
        private Object type;
        private Boolean required;
        private String doc;

        private Builder() {
        }

        private Builder(SchemaV2Field model) {
            this.id = model.id;
            this.name = model.name;
            this.type = model.type;
            this.required = model.required;
            this.doc = model.doc;
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the primitive type name, such as "long" or "string".
         */
        public Builder type(String type) {
            this.type = type;
            return this;
        }

        /**
         * Sets the field type to a struct, list, or map.
         */
        public Builder type(IcebergComplexType type) {
            this.type = type;
            return this;
        }

        public Builder required(Boolean required) {
            this.required = required;
            return this;
        }

        public Builder doc(String doc) {
            this.doc = doc;
            return this;
        }

        public SchemaV2Field build() {
            return new SchemaV2Field(this);
        }
    }
}
