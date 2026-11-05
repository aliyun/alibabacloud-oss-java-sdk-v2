package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A map type defining key-value mappings; keys cannot be null.
 */
public class IcebergMapType implements IcebergComplexType {

    @JsonProperty("type")
    private String type = "map";

    @JsonProperty("key-id")
    private Integer keyId;

    @JsonProperty("key")
    private Object key;

    @JsonProperty("value-id")
    private Integer valueId;

    @JsonProperty("value")
    private Object value;

    @JsonProperty("value-required")
    private Boolean valueRequired;

    public IcebergMapType() {
    }

    private IcebergMapType(Builder builder) {
        this.keyId = builder.keyId;
        this.key = builder.key;
        this.valueId = builder.valueId;
        this.value = builder.value;
        this.valueRequired = builder.valueRequired;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public String type() {
        return type;
    }

    public Integer keyId() {
        return keyId;
    }

    /**
     * The key type: a primitive type name ({@link String}) or a complex type ({@link IcebergComplexType}).
     */
    public Object key() {
        return key;
    }

    public Integer valueId() {
        return valueId;
    }

    /**
     * The value type: a primitive type name ({@link String}) or a complex type ({@link IcebergComplexType}).
     */
    public Object value() {
        return value;
    }

    public Boolean valueRequired() {
        return valueRequired;
    }

    public static class Builder {
        private Integer keyId;
        private Object key;
        private Integer valueId;
        private Object value;
        private Boolean valueRequired;

        private Builder() {
        }

        private Builder(IcebergMapType model) {
            this.keyId = model.keyId;
            this.key = model.key;
            this.valueId = model.valueId;
            this.value = model.value;
            this.valueRequired = model.valueRequired;
        }

        public Builder keyId(Integer keyId) {
            this.keyId = keyId;
            return this;
        }

        /**
         * Sets the primitive key type name, such as "long" or "string".
         */
        public Builder key(String key) {
            this.key = key;
            return this;
        }

        /**
         * Sets the key type to a struct, list, or map.
         */
        public Builder key(IcebergComplexType key) {
            this.key = key;
            return this;
        }

        public Builder valueId(Integer valueId) {
            this.valueId = valueId;
            return this;
        }

        /**
         * Sets the primitive value type name, such as "long" or "string".
         */
        public Builder value(String value) {
            this.value = value;
            return this;
        }

        /**
         * Sets the value type to a struct, list, or map.
         */
        public Builder value(IcebergComplexType value) {
            this.value = value;
            return this;
        }

        public Builder valueRequired(Boolean valueRequired) {
            this.valueRequired = valueRequired;
            return this;
        }

        public IcebergMapType build() {
            return new IcebergMapType(this);
        }
    }
}
