package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.PublicAccessBlockConfiguration;
import com.aliyun.sdk.service.oss2.models.RequestModel;
import static java.util.Objects.requireNonNull;

/**
 * The request for the PutAgenticBucketPublicAccessBlock operation.
 */
public final class PutAgenticBucketPublicAccessBlockRequest extends RequestModel {
    private final String bucket;
    private final PublicAccessBlockConfiguration publicAccessBlockConfiguration;

    private PutAgenticBucketPublicAccessBlockRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.publicAccessBlockConfiguration = builder.publicAccessBlockConfiguration;
    }

    public static Builder newBuilder() { return new Builder(); }

    /**
     * The name of the bucket.
     */
    public String bucket() { return bucket; }

    /**
     * The public access block configuration of the bucket.
     */
    public PublicAccessBlockConfiguration publicAccessBlockConfiguration() { return publicAccessBlockConfiguration; }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private PublicAccessBlockConfiguration publicAccessBlockConfiguration;
        private Builder() { super(); }
        private Builder(PutAgenticBucketPublicAccessBlockRequest request) {
            super(request); this.bucket = request.bucket; this.publicAccessBlockConfiguration = request.publicAccessBlockConfiguration;
        }
        /**
         * The name of the bucket.
         */
        public Builder bucket(String value) { requireNonNull(value); this.bucket = value; return this; }

        /**
         * The public access block configuration of the bucket.
         */
        public Builder publicAccessBlockConfiguration(PublicAccessBlockConfiguration value) { this.publicAccessBlockConfiguration = value; return this; }
        public PutAgenticBucketPublicAccessBlockRequest build() { return new PutAgenticBucketPublicAccessBlockRequest(this); }
    }
}
