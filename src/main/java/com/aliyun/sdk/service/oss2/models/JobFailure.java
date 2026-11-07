package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * The failure detail of a batch operation job.
 */
public final class JobFailure {

    @JacksonXmlProperty(localName = "FailureCode")
    private String failureCode;

    @JacksonXmlProperty(localName = "FailureReason")
    private String failureReason;

    public JobFailure() {
    }

    private JobFailure(Builder builder) {
        this.failureCode = builder.failureCode;
        this.failureReason = builder.failureReason;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The failure code.
     */
    public String failureCode() {
        return this.failureCode;
    }

    /**
     * The failure reason.
     */
    public String failureReason() {
        return this.failureReason;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String failureCode;
        private String failureReason;

        private Builder() {
            super();
        }

        private Builder(JobFailure from) {
            this.failureCode = from.failureCode;
            this.failureReason = from.failureReason;
        }

        public Builder failureCode(String value) {
            this.failureCode = value;
            return this;
        }

        public Builder failureReason(String value) {
            this.failureReason = value;
            return this;
        }

        public JobFailure build() {
            return new JobFailure(this);
        }
    }
}
