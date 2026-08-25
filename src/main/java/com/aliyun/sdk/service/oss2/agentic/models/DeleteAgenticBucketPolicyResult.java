package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the DeleteAgenticBucketPolicy operation.
 */
public final class DeleteAgenticBucketPolicyResult extends ResultModel {
    DeleteAgenticBucketPolicyResult(Builder builder) { super(builder); }
    public static Builder newBuilder() { return new Builder(); }
    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() { super(); }
        public DeleteAgenticBucketPolicyResult build() { return new DeleteAgenticBucketPolicyResult(this); }
    }
}
