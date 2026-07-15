package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the DeleteAgenticBucketPublicAccessBlock operation.
 */
public final class DeleteAgenticBucketPublicAccessBlockResult extends ResultModel {
    DeleteAgenticBucketPublicAccessBlockResult(Builder builder) { super(builder); }
    public static Builder newBuilder() { return new Builder(); }
    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() { super(); }
        public DeleteAgenticBucketPublicAccessBlockResult build() { return new DeleteAgenticBucketPublicAccessBlockResult(this); }
    }
}
