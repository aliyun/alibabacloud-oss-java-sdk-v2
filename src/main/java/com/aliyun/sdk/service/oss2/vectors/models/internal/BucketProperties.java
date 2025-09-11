package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import static java.util.Objects.requireNonNull;

/**
 * Stores the metadata of the bucket.
 */
public final class BucketProperties {
    @JsonProperty("ExtranetEndpoint")
    private String extranetEndpoint;

    @JsonProperty("ResourceGroupId")
    private String resourceGroupId;

    @JsonProperty("Location")
    private String location;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("CreationDate")
    private Instant creationDate;

    @JsonProperty("IntranetEndpoint")
    private String intranetEndpoint;

    @JsonProperty("Region")
    private String region;

    public BucketProperties() {
    }

    private BucketProperties(Builder builder) {
        this.extranetEndpoint = builder.extranetEndpoint;
        this.resourceGroupId = builder.resourceGroupId;
        this.location = builder.location;
        this.name = builder.name;
        this.creationDate = builder.creationDate;
        this.intranetEndpoint = builder.intranetEndpoint;
        this.region = builder.region;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The public endpoint used to access the bucket over the Internet.
     */
    public String extranetEndpoint() {
        return this.extranetEndpoint;
    }

    /**
     * The ID of the resource group to which the bucket belongs.
     */
    public String resourceGroupId() {
        return this.resourceGroupId;
    }

    /**
     * The data center in which the bucket is located.
     */
    public String location() {
        return this.location;
    }

    /**
     * The name of the bucket.
     */
    public String name() {
        return this.name;
    }

    /**
     * The time when the bucket was created.
     */
    public Instant creationDate() {
        return this.creationDate;
    }

    /**
     * The internal endpoint that is used to access the bucket from ECS instances
     * that reside in the same region as the bucket.
     */
    public String intranetEndpoint() {
        return this.intranetEndpoint;
    }

    /**
     * The region in which the bucket is located.
     */
    public String region() {
        return this.region;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String extranetEndpoint;
        private String resourceGroupId;
        private String location;
        private String name;
        private Instant creationDate;
        private String intranetEndpoint;
        private String region;

        private Builder() {
            super();
        }

        private Builder(BucketProperties from) {
            this.extranetEndpoint = from.extranetEndpoint;
            this.resourceGroupId = from.resourceGroupId;
            this.location = from.location;
            this.name = from.name;
            this.creationDate = from.creationDate;
            this.intranetEndpoint = from.intranetEndpoint;
            this.region = from.region;
        }

        /**
         * The public endpoint used to access the bucket over the Internet.
         */
        public Builder extranetEndpoint(String value) {
            this.extranetEndpoint = value;
            return this;
        }

        /**
         * The ID of the resource group to which the bucket belongs.
         */
        public Builder resourceGroupId(String value) {
            this.resourceGroupId = value;
            return this;
        }

        /**
         * The data center in which the bucket is located.
         */
        public Builder location(String value) {
            this.location = value;
            return this;
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
         * The time when the bucket was created.
         */
        public Builder creationDate(Instant value) {
            this.creationDate = value;
            return this;
        }

        /**
         * The internal endpoint that is used to access the bucket from ECS instances
         * that reside in the same region as the bucket.
         */
        public Builder intranetEndpoint(String value) {
            this.intranetEndpoint = value;
            return this;
        }

        /**
         * The region in which the bucket is located.
         */
        public Builder region(String value) {
            this.region = value;
            return this;
        }

        public BucketProperties build() {
            return new BucketProperties(this);
        }
    }
}

