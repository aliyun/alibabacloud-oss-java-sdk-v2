package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * Group aggregation result list
 */
@JacksonXmlRootElement(localName = "Group")
public final class MetaQueryGroup {
    @JacksonXmlProperty(localName = "Value")
    private String value;

    @JacksonXmlProperty(localName = "Count")
    private Long count;

    public MetaQueryGroup() {}

    private MetaQueryGroup(Builder builder) {
        this.value = builder.value;
        this.count = builder.count;
    }

    /**
     * Group aggregation value
     */
    public String value() {
        return value;
    }

    /**
     * Total count of group aggregations
     */
    public Long count() {
        return count;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String value;
        private Long count;

        /**
         * Sets the group aggregation value
         *
         * @param value the value
         * @return the builder instance
         */
        public Builder value(String value) {
            requireNonNull(value);
            this.value = value;
            return this;
        }

        /**
         * Sets the total count of group aggregations
         *
         * @param value the count
         * @return the builder instance
         */
        public Builder count(Long value) {
            requireNonNull(value);
            this.count = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(MetaQueryGroup from) {
            this.value = from.value;
            this.count = from.count;
        }

        public MetaQueryGroup build() {
            return new MetaQueryGroup(this);
        }
    }
}