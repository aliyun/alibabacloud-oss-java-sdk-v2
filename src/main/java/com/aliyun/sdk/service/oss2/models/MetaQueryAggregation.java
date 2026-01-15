package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the information about a single aggregate operation.
 */
@JacksonXmlRootElement(localName = "Aggregation")
public final class MetaQueryAggregation {
    @JacksonXmlProperty(localName = "Field")
    private String field;

    @JacksonXmlProperty(localName = "Operation")
    private String operation;

    @JacksonXmlProperty(localName = "Value")
    private Double value;

    @JacksonXmlProperty(localName = "Groups")
    private MetaQueryGroups groups;

    public MetaQueryAggregation() {}

    private MetaQueryAggregation(Builder builder) {
        this.field = builder.field;
        this.operation = builder.operation;
        this.value = builder.value;
        this.groups = builder.groups;
    }

    /**
     * The field name.
     */
    public String field() {
        return field;
    }

    /**
     * The operator for aggregate operations.
     * *   min
     * *   max
     * *   average
     * *   sum
     * *   count
     * *   distinct
     * *   group
     */
    public String operation() {
        return operation;
    }

    /**
     * The result of the aggregate operation.
     */
    public Double value() {
        return value;
    }

    /**
     * The grouped aggregations.
     */
    public MetaQueryGroups groups() {
        return groups;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String field;
        private String operation;
        private Double value;
        private MetaQueryGroups groups;

        /**
         * Sets the field name.
         *
         * @param value the field name
         * @return the builder instance
         */
        public Builder field(String value) {
            requireNonNull(value);
            this.field = value;
            return this;
        }

        /**
         * Sets the operator for aggregate operations.
         *
         * @param value the operation
         * @return the builder instance
         */
        public Builder operation(String value) {
            requireNonNull(value);
            this.operation = value;
            return this;
        }

        /**
         * Sets the result of the aggregate operation.
         *
         * @param value the value
         * @return the builder instance
         */
        public Builder value(Double value) {
            this.value = value;
            return this;
        }

        /**
         * Sets the grouped aggregations.
         *
         * @param value the groups
         * @return the builder instance
         */
        public Builder groups(MetaQueryGroups value) {
            this.groups = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(MetaQueryAggregation from) {
            this.field = from.field;
            this.operation = from.operation;
            this.value = from.value;
            this.groups = from.groups;
        }

        public MetaQueryAggregation build() {
            return new MetaQueryAggregation(this);
        }
    }
}