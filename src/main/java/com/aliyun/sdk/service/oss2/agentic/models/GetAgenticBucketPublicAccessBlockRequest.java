package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import static java.util.Objects.requireNonNull;

/**
 * The request for the GetAgenticBucketPublicAccessBlock operation.
 */
public final class GetAgenticBucketPublicAccessBlockRequest extends RequestModel {
    private final String bucket;
    private GetAgenticBucketPublicAccessBlockRequest(Builder builder) { super(builder); this.bucket = builder.bucket; }
    public static Builder newBuilder() { return new Builder(); }

    /**
     * The name of the bucket.
     */
    public String bucket() { return bucket; }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private Builder() { super(); }
        private Builder(GetAgenticBucketPublicAccessBlockRequest request) { super(request); this.bucket = request.bucket; }
        /**
         * The name of the bucket.
         */
        public Builder bucket(String value) { requireNonNull(value); this.bucket = value; return this; }
        public GetAgenticBucketPublicAccessBlockRequest build() { return new GetAgenticBucketPublicAccessBlockRequest(this); }
    }
}
