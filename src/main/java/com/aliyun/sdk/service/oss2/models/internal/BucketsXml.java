package com.aliyun.sdk.service.oss2.models.internal;

import com.aliyun.sdk.service.oss2.models.BucketSummary;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The container that stores the information about multiple buckets.
 */
@JacksonXmlRootElement(localName = "Buckets")
public final class BucketsXml {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Bucket")
    private List<BucketSummary> buckets;

    public BucketsXml() {
    }

    private BucketsXml(Builder builder) {
        this.buckets = builder.buckets;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The container that stores the information list of multiple buckets.
     */
    public List<BucketSummary> buckets() {
        return this.buckets;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private List<BucketSummary> buckets;

        private Builder() {
            super();
        }


        private Builder(BucketsXml from) {
            this.buckets = from.buckets;
        }

        /**
         * The container that stores the information list of multiple buckets.
         */
        public Builder buckets(List<BucketSummary> value) {
            requireNonNull(value);
            this.buckets = value;
            return this;
        }

        public BucketsXml build() {
            return new BucketsXml(this);
        }
    }
}
