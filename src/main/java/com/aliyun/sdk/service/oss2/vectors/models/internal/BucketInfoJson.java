package com.aliyun.sdk.service.oss2.vectors.models.internal;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.time.Instant;
import static java.util.Objects.requireNonNull;

/**
 * BucketInfo defines Bucket information.
 */

public final class BucketInfoJson {
    @JsonProperty("Name")
    private String name;

    @JsonProperty("Location")
    private String location;

    @JsonProperty("CreationDate")
    private Instant creationDate;

    @JsonProperty("ExtranetEndpoint")
    private String extranetEndpoint;

    @JsonProperty("IntranetEndpoint")
    private String intranetEndpoint;

    @JsonProperty("ResourceGroupId")
    private String resourceGroupId;

    public BucketInfoJson() {
    }

    private BucketInfoJson(Builder builder) {
        this.name = builder.name;
        this.location = builder.location;
        this.creationDate = builder.creationDate;
        this.extranetEndpoint = builder.extranetEndpoint;
        this.intranetEndpoint = builder.intranetEndpoint;
        this.resourceGroupId = builder.resourceGroupId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The name of the bucket.
     */
    public String name() {
        return this.name;
    }

    /**
     * The region in which the bucket is located.
     */
    public String location() {
        return this.location;
    }

    /**
     * The time when the bucket is created. The time is in UTC.
     */
    public Instant creationDate() {
        return this.creationDate;
    }

    /**
     * The public endpoint that is used to access the bucket over the Internet.
     */
    public String extranetEndpoint() {
        return this.extranetEndpoint;
    }

    /**
     * The internal endpoint that is used to access the bucket from Elastic.
     */
    public String intranetEndpoint() {
        return this.intranetEndpoint;
    }

    /**
     * The ID of the resource group to which the bucket belongs.
     */
    public String resourceGroupId() {
        return this.resourceGroupId;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String name;
        private String location;
        private Instant creationDate;
        private String extranetEndpoint;
        private String intranetEndpoint;
        private String resourceGroupId;

        private Builder() {
            super();
        }

        private Builder(BucketInfoJson from) {
            this.name = from.name;
            this.location = from.location;
            this.creationDate = from.creationDate;
            this.extranetEndpoint = from.extranetEndpoint;
            this.intranetEndpoint = from.intranetEndpoint;
            this.resourceGroupId = from.resourceGroupId;
        }

        /**
         * The name of the bucket.
         */
        public Builder name(String value) {
            requireNonNull(value);
            this.name = value;
            return this;
        }

        /**
         * The region in which the bucket is located.
         */
        public Builder location(String value) {
            this.location = value;
            return this;
        }

        /**
         * The time when the bucket is created. The time is in UTC.
         */
        public Builder creationDate(Instant value) {
            this.creationDate = value;
            return this;
        }

        /**
         * The public endpoint that is used to access the bucket over the Internet.
         */
        public Builder extranetEndpoint(String value) {
            this.extranetEndpoint = value;
            return this;
        }

        /**
         * The internal endpoint that is used to access the bucket from Elastic.
         */
        public Builder intranetEndpoint(String value) {
            this.intranetEndpoint = value;
            return this;
        }

        /**
         * The ID of the resource group to which the bucket belongs.
         */
        public Builder resourceGroupId(String value) {
            this.resourceGroupId = value;
            return this;
        }

        public BucketInfoJson build() {
            return new BucketInfoJson(this);
        }
    }
}
