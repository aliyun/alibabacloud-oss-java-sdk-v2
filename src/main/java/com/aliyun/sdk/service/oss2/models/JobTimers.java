package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Timer information for a batch operation job.
 */
public final class JobTimers {

    @JacksonXmlProperty(localName = "ElapsedTimeInActiveSeconds")
    private Long elapsedTimeInActiveSeconds;

    public JobTimers() {
    }

    private JobTimers(Builder builder) {
        this.elapsedTimeInActiveSeconds = builder.elapsedTimeInActiveSeconds;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The elapsed time in seconds that the job has been active.
     */
    public Long elapsedTimeInActiveSeconds() {
        return this.elapsedTimeInActiveSeconds;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Long elapsedTimeInActiveSeconds;

        private Builder() {
            super();
        }

        private Builder(JobTimers from) {
            this.elapsedTimeInActiveSeconds = from.elapsedTimeInActiveSeconds;
        }

        /**
         * The elapsed time in seconds that the job has been active.
         */
        public Builder elapsedTimeInActiveSeconds(Long value) {
            this.elapsedTimeInActiveSeconds = value;
            return this;
        }

        public JobTimers build() {
            return new JobTimers(this);
        }
    }
}
