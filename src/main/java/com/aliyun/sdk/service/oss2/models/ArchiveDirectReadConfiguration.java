package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * Archive direct read configuration
 */
@JacksonXmlRootElement(localName = "ArchiveDirectReadConfiguration")
public final class  ArchiveDirectReadConfiguration {
    @JacksonXmlProperty(localName = "Enabled")
    private Boolean enabled;

    public ArchiveDirectReadConfiguration() {}

    private ArchiveDirectReadConfiguration(Builder builder) {
        this.enabled = builder.enabled;
    }

    /**
     * Returns whether archive direct read is enabled.
     */
    public Boolean enabled() {
        return this.enabled;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Boolean enabled;

        /**
         * Sets whether archive direct read is enabled.
         */
        public Builder enabled(Boolean value) {
            requireNonNull(value);
            this.enabled = value;
            return this;
        }

        public ArchiveDirectReadConfiguration build() {
            return new ArchiveDirectReadConfiguration(this);
        }

        private Builder() {
            super();
        }

        private Builder(ArchiveDirectReadConfiguration from) {
            this.enabled = from.enabled;
        }
    }
}