package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import static java.util.Objects.requireNonNull;

/**
 * The information about the vector bucket.
 */
public class VectorBucket {
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

    public VectorBucket() {
    }

    private VectorBucket(Builder builder) {
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
        return name;
    }

    /**
     * The region ID where the bucket is located.
     */
    public String location() {
        return location;
    }

    /**
     * The creation date of the bucket.
     */
    public Instant creationDate() {
        return creationDate;
    }

    /**
     * The public endpoint of the bucket.
     */
    public String extranetEndpoint() {
        return extranetEndpoint;
    }

    /**
     * The internal endpoint of the bucket.
     */
    public String intranetEndpoint() {
        return intranetEndpoint;
    }

    /**
     * The resource group ID of the bucket.
     */
    public String resourceGroupId() {
        return resourceGroupId;
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
        }

        private Builder(VectorBucket from) {
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
        public Builder name(String name) {
            requireNonNull(name);
            this.name = name;
            return this;
        }

        /**
         * The region ID where the bucket is located.
         */
        public Builder location(String location) {
            requireNonNull(location);
            this.location = location;
            return this;
        }

        /**
         * The creation date of the bucket.
         */
        public Builder creationDate(Instant creationDate) {
            requireNonNull(creationDate);
            this.creationDate = creationDate;
            return this;
        }

        /**
         * The public endpoint of the bucket.
         */
        public Builder extranetEndpoint(String extranetEndpoint) {
            requireNonNull(extranetEndpoint);
            this.extranetEndpoint = extranetEndpoint;
            return this;
        }

        /**
         * The internal endpoint of the bucket.
         */
        public Builder intranetEndpoint(String intranetEndpoint) {
            requireNonNull(intranetEndpoint);
            this.intranetEndpoint = intranetEndpoint;
            return this;
        }

        /**
         * The resource group ID of the bucket.
         */
        public Builder resourceGroupId(String resourceGroupId) {
            requireNonNull(resourceGroupId);
            this.resourceGroupId = resourceGroupId;
            return this;
        }

        public VectorBucket build() {
            return new VectorBucket(this);
        }
    }
}