package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the target suffix configuration for bucket logging.
 */
@JacksonXmlRootElement(localName = "TargetSuffix")
public final class TargetSuffix {
    @JacksonXmlProperty(localName = "UseRandomPart")
    private Boolean useRandomPart;

    public TargetSuffix() {
    }

    private TargetSuffix(Builder builder) {
        this.useRandomPart = builder.useRandomPart;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Indicates whether to use a random part in the target suffix.
     */
    public Boolean useRandomPart() {
        return this.useRandomPart;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Boolean useRandomPart;

        private Builder() {
            super();
        }

        private Builder(TargetSuffix from) {
            this.useRandomPart = from.useRandomPart;
        }

        /**
         * Indicates whether to use a random part in the target suffix.
         */
        public Builder useRandomPart(Boolean value) {
            requireNonNull(value);
            this.useRandomPart = value;
            return this;
        }

        public TargetSuffix build() {
            return new TargetSuffix(this);
        }
    }
}