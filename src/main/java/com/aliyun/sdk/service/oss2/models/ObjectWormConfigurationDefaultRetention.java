package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the default retention settings.
 */
@JacksonXmlRootElement(localName = "DefaultRetention")
public final class ObjectWormConfigurationDefaultRetention {
    @JacksonXmlProperty(localName = "Mode")
    private String mode;

    @JacksonXmlProperty(localName = "Days")
    private Integer days;

    @JacksonXmlProperty(localName = "Years")
    private Integer years;

    public ObjectWormConfigurationDefaultRetention() {}

    private ObjectWormConfigurationDefaultRetention(Builder builder) {
        this.mode = builder.mode;
        this.days = builder.days;
        this.years = builder.years;
    }

    /**
     * Object-level retention strategy pattern. Valid values: GOVERNANCE, COMPLIANCE.
     */
    public String mode() {
        return this.mode;
    }

    /**
     * Object-level retention policy days (max 36500).
     */
    public Integer days() {
        return this.days;
    }

    /**
     * Bucket object level retention policy years (max 100).
     */
    public Integer years() {
        return this.years;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String mode;
        private Integer days;
        private Integer years;

        /**
         * Object-level retention strategy pattern. Valid values: GOVERNANCE, COMPLIANCE.
         */
        public Builder mode(String value) {
            requireNonNull(value);
            this.mode = value;
            return this;
        }

        /**
         * Object-level retention strategy pattern. Valid values: GOVERNANCE, COMPLIANCE.
         */
        public Builder mode(ObjectWormConfigurationModeType value) {
            requireNonNull(value);
            this.mode = value.toString();
            return this;
        }

        /**
         * Object-level retention policy days (max 36500).
         */
        public Builder days(Integer value) {
            this.days = value;
            return this;
        }

        /**
         * Bucket object level retention policy years (max 100).
         */
        public Builder years(Integer value) {
            this.years = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(ObjectWormConfigurationDefaultRetention from) {
            this.mode = from.mode;
            this.days = from.days;
            this.years = from.years;
        }

        public ObjectWormConfigurationDefaultRetention build() {
            return new ObjectWormConfigurationDefaultRetention(this);
        }
    }
}
