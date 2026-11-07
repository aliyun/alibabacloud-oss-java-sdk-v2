package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * The failure reasons wrapper for a batch operation job.
 */
@JacksonXmlRootElement(localName = "FailureReasons")
public final class FailureReasons {

    @JacksonXmlProperty(localName = "JobFailure")
    private JobFailure jobFailure;

    public FailureReasons() {
    }

    private FailureReasons(Builder builder) {
        this.jobFailure = builder.jobFailure;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Gets the job failure detail.
     */
    public JobFailure jobFailure() {
        return this.jobFailure;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private JobFailure jobFailure;

        private Builder() {
            super();
        }

        private Builder(FailureReasons from) {
            this.jobFailure = from.jobFailure;
        }

        public Builder jobFailure(JobFailure value) {
            this.jobFailure = value;
            return this;
        }

        public FailureReasons build() {
            return new FailureReasons(this);
        }
    }
}
