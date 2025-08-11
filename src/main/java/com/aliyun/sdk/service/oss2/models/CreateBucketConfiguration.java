package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The configurations of the bucket storage class and redundancy type.
 */
@JacksonXmlRootElement(localName = "CreateBucketConfiguration")
public final class CreateBucketConfiguration {
    @JacksonXmlProperty(localName = "StorageClass")
    private String storageClass;

    @JacksonXmlProperty(localName = "DataRedundancyType")
    private String dataRedundancyType;

    public CreateBucketConfiguration() {
    }

    private CreateBucketConfiguration(Builder builder) {
        this.storageClass = builder.storageClass;
        this.dataRedundancyType = builder.dataRedundancyType;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The storage class of the bucket. Valid values:*   Standard (default)*   IA*   Archive*   ColdArchive
     */
    public String storageClass() {
        return this.storageClass;
    }

    /**
     * The redundancy type of the bucket.*   LRS (default)    LRS stores multiple copies of your data on multiple devices in the same zone. LRS ensures data durability and availability even if hardware failures occur on two devices.*   ZRS    ZRS stores multiple copies of your data across three zones in the same region. Even if a zone becomes unavailable due to unexpected events, such as power outages and fires, data can still be accessed.  You cannot set the redundancy type of Archive buckets to ZRS.
     */
    public String dataRedundancyType() {
        return this.dataRedundancyType;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String storageClass;
        private String dataRedundancyType;

        private Builder() {
            super();
        }

        private Builder(CreateBucketConfiguration from) {
            this.storageClass = from.storageClass;
            this.dataRedundancyType = from.dataRedundancyType;
        }

        /**
         * The storage class of the bucket. Valid values:*   Standard (default)*   IA*   Archive*   ColdArchive
         */
        public Builder storageClass(String value) {
            requireNonNull(value);
            this.storageClass = value;
            return this;
        }

        /**
         * The redundancy type of the bucket.*   LRS (default)    LRS stores multiple copies of your data on multiple devices in the same zone. LRS ensures data durability and availability even if hardware failures occur on two devices.*   ZRS    ZRS stores multiple copies of your data across three zones in the same region. Even if a zone becomes unavailable due to unexpected events, such as power outages and fires, data can still be accessed.  You cannot set the redundancy type of Archive buckets to ZRS.
         */
        public Builder dataRedundancyType(String value) {
            requireNonNull(value);
            this.dataRedundancyType = value;
            return this;
        }

        public CreateBucketConfiguration build() {
            return new CreateBucketConfiguration(this);
        }
    }
}
