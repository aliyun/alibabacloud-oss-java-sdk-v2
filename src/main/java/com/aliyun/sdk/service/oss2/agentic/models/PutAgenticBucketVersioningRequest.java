package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import com.aliyun.sdk.service.oss2.models.VersioningConfiguration;
import static java.util.Objects.requireNonNull;

/**
 * The request for the PutAgenticBucketVersioning operation.
 */
public final class PutAgenticBucketVersioningRequest extends RequestModel {
    private final String bucket;
    private final VersioningConfiguration versioningConfiguration;

    private PutAgenticBucketVersioningRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.versioningConfiguration = builder.versioningConfiguration;
    }

    public static Builder newBuilder() { return new Builder(); }

    /**
     * The name of the bucket.
     */
    public String bucket() { return bucket; }

    /**
     * The versioning configuration of the bucket.
     */
    public VersioningConfiguration versioningConfiguration() { return versioningConfiguration; }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private VersioningConfiguration versioningConfiguration;
        private Builder() { super(); }
        private Builder(PutAgenticBucketVersioningRequest request) {
            super(request); this.bucket = request.bucket; this.versioningConfiguration = request.versioningConfiguration;
        }

        /**
         * The name of the bucket.
         */
        public Builder bucket(String value) { requireNonNull(value); this.bucket = value; return this; }

        /**
         * The versioning configuration of the bucket.
         */
        public Builder versioningConfiguration(VersioningConfiguration value) { this.versioningConfiguration = value; return this; }
        public PutAgenticBucketVersioningRequest build() { return new PutAgenticBucketVersioningRequest(this); }
    }
}
