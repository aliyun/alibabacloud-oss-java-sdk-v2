package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The container that stores the returned tag of the bucket.
 */
@JacksonXmlRootElement(localName = "Tagging")
public final class Tagging {
    @JacksonXmlProperty(localName = "TagSet")
    private TagSet tagSet;

    public Tagging() {
    }

    private Tagging(Builder builder) {
        this.tagSet = builder.tagSet;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The tag set of the target object.
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


        private Builder(Tagging from) {
            this.tagSet = from.tagSet;
        }

        /**
         * The tag set of the target object.
         */
        public Builder tagSet(TagSet value) {
            requireNonNull(value);
            this.tagSet = value;
            return this;
        }

        public Tagging build() {
            return new Tagging(this);
        }
    }
}
