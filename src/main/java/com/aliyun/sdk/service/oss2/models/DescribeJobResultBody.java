package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * The XML body for the DescribeJob response.
 */
@JacksonXmlRootElement(localName = "DescribeJobResult")
public final class DescribeJobResultBody {

    @JacksonXmlProperty(localName = "Job")
    private JobDetail job;

    public DescribeJobResultBody() {
    }

    private DescribeJobResultBody(Builder builder) {
        this.job = builder.job;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The detailed information about the batch operation job.
     */
    public JobDetail job() {
        return this.job;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private JobDetail job;

        private Builder() {
            super();
        }

        private Builder(DescribeJobResultBody from) {
            this.job = from.job;
        }

        /**
         * The detailed information about the batch operation job.
         */
        public Builder job(JobDetail value) {
            this.job = value;
            return this;
        }

        public DescribeJobResultBody build() {
            return new DescribeJobResultBody(this);
        }
    }
}
