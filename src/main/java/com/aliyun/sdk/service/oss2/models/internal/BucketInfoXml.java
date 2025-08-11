package com.aliyun.sdk.service.oss2.models.internal;

import com.aliyun.sdk.service.oss2.models.BucketInfo;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The container that stores the information about the bucket.
 */
@JacksonXmlRootElement(localName = "BucketInfo")
public final class BucketInfoXml {
    @JacksonXmlProperty(localName = "Bucket")
    private BucketInfo bucket;

    public BucketInfoXml() {
    }

    private BucketInfoXml(Builder builder) {
        this.bucket = builder.bucket;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The container that stores the bucket information.
     */
    public BucketInfo bucket() {
        return this.bucket;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private BucketInfo bucket;

        private Builder() {
            super();
        }


        private Builder(BucketInfoXml from) {
            this.bucket = from.bucket;
        }

        /**
         * The container that stores the bucket information.
         */
        public Builder bucket(BucketInfo value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        public BucketInfoXml build() {
            return new BucketInfoXml(this);
        }
    }
}
