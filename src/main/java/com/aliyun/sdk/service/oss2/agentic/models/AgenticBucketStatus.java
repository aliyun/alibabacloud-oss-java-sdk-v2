package com.aliyun.sdk.service.oss2.agentic.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The status of an agentic bucket.
 */
@JacksonXmlRootElement(localName = "AgenticBucketStatus")
public final class AgenticBucketStatus {
    @JacksonXmlProperty(localName = "Status")
    private String status;

    public AgenticBucketStatus() {
    }

    private AgenticBucketStatus(Builder builder) {
        this.status = builder.status;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The status of the bucket.
     */
    public String status() {
        return this.status;
    }

    public static class Builder {
        private String status;

        private Builder() {}

        /**
         * The status of the bucket.
         */
        public Builder status(String value) {
            requireNonNull(value);
            this.status = value;
            return this;
        }

        public AgenticBucketStatus build() {
            return new AgenticBucketStatus(this);
        }
    }
}
