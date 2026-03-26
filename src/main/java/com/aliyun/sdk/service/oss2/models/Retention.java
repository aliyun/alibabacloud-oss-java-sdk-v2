package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container for object retention configuration.
 */
@JacksonXmlRootElement(localName = "Retention")
public final class Retention {
    @JacksonXmlProperty(localName = "Mode")
    private String mode;

    @JacksonXmlProperty(localName = "RetainUntilDate")
    private String retainUntilDate;

    public Retention() {}

    private Retention(Builder builder) {
        this.mode = builder.mode;
        this.retainUntilDate = builder.retainUntilDate;
    }

    /**
     * The retention mode. Valid values: GOVERNANCE, COMPLIANCE.
     */
    public String mode() {
        return this.mode;
    }

    /**
     * The date until which the object is retained. ISO 8601 format (e.g., '2025-11-10T16:00:00.000Z').
     */
    public String retainUntilDate() {
        return this.retainUntilDate;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String mode;
        private String retainUntilDate;

        /**
         * The retention mode. Valid values: GOVERNANCE, COMPLIANCE.
         */
        public Builder mode(String value) {
            requireNonNull(value);
            this.mode = value;
            return this;
        }

        /**
         * The retention mode. Valid values: GOVERNANCE, COMPLIANCE.
         */
        public Builder mode(ObjectRetentionModeType value) {
            requireNonNull(value);
            this.mode = value.toString();
            return this;
        }

        /**
         * The date until which the object is retained. ISO 8601 format (e.g., '2025-11-10T16:00:00.000Z').
         */
        public Builder retainUntilDate(String value) {
            requireNonNull(value);
            this.retainUntilDate = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(Retention from) {
            this.mode = from.mode;
            this.retainUntilDate = from.retainUntilDate;
        }

        public Retention build() {
            return new Retention(this);
        }
    }
}
