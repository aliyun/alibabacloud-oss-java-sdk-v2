package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A list type containing multiple elements of the same type.
 */
public class IcebergListType implements IcebergComplexType {

    @JsonProperty("type")
    private String type = "list";

    @JsonProperty("element-id")
    private Integer elementId;

    @JsonProperty("element")
    private Object element;

    @JsonProperty("element-required")
    private Boolean elementRequired;

    public IcebergListType() {
    }

    private IcebergListType(Builder builder) {
        this.elementId = builder.elementId;
        this.element = builder.element;
        this.elementRequired = builder.elementRequired;
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

    public Integer elementId() {
        return elementId;
    }

    /**
     * The element type: a primitive type name ({@link String}) or a complex type ({@link IcebergComplexType}).
     */
    public Object element() {
        return element;
    }

    public Boolean elementRequired() {
        return elementRequired;
    }

    public static class Builder {
        private Integer elementId;
        private Object element;
        private Boolean elementRequired;

        private Builder() {
        }

        private Builder(IcebergListType model) {
            this.elementId = model.elementId;
            this.element = model.element;
            this.elementRequired = model.elementRequired;
        }

        public Builder elementId(Integer elementId) {
            this.elementId = elementId;
            return this;
        }

        /**
         * Sets the primitive element type name, such as "long" or "string".
         */
        public Builder element(String element) {
            this.element = element;
            return this;
        }

        /**
         * Sets the element type to a struct, list, or map.
         */
        public Builder element(IcebergComplexType element) {
            this.element = element;
            return this;
        }

        public Builder elementRequired(Boolean elementRequired) {
            this.elementRequired = elementRequired;
            return this;
        }

        public IcebergListType build() {
            return new IcebergListType(this);
        }
    }
}
