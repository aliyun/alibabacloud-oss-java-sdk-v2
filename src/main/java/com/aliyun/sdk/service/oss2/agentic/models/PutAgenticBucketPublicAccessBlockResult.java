package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the PutAgenticBucketPublicAccessBlock operation.
 */
public final class PutAgenticBucketPublicAccessBlockResult extends ResultModel {
    PutAgenticBucketPublicAccessBlockResult(Builder builder) { super(builder); }
    public static Builder newBuilder() { return new Builder(); }
    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() { super(); }
        public PutAgenticBucketPublicAccessBlockResult build() { return new PutAgenticBucketPublicAccessBlockResult(this); }
    }
}
