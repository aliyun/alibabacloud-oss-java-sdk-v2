package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Delete object tagging operation for batch job.
 * This is an empty container node.
 */
@JacksonXmlRootElement(localName = "DeleteObjectTagging")
public final class JobDeleteObjectTagging {

    public JobDeleteObjectTagging() {
    }

    private JobDeleteObjectTagging(Builder builder) {
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {

        private Builder() {
            super();
        }

        private Builder(JobDeleteObjectTagging from) {
        }

        public JobDeleteObjectTagging build() {
            return new JobDeleteObjectTagging(this);
        }
    }
}
