package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * Add object tagging operation for batch job.
 */
@JacksonXmlRootElement(localName = "AddObjectTagging")
public final class JobAddObjectTagging {

    @JacksonXmlProperty(localName = "TagSet")
    private TagSet tagSet;

    public JobAddObjectTagging() {
    }

    private JobAddObjectTagging(Builder builder) {
        this.tagSet = builder.tagSet;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The tag set.
     */
    public TagSet tagSet() {
        return this.tagSet;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private TagSet tagSet;

        private Builder() {
            super();
        }

        private Builder(JobAddObjectTagging from) {
            this.tagSet = from.tagSet;
        }

        /**
         * The tag set.
         */
        public Builder tagSet(TagSet value) {
            requireNonNull(value);
            this.tagSet = value;
            return this;
        }

        public JobAddObjectTagging build() {
            return new JobAddObjectTagging(this);
        }
    }
}
