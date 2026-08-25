package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the PutAgenticBucketPolicy operation.
 */
public final class PutAgenticBucketPolicyResult extends ResultModel {
    PutAgenticBucketPolicyResult(Builder builder) { super(builder); }
    public static Builder newBuilder() { return new Builder(); }
    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() { super(); }
        public PutAgenticBucketPolicyResult build() { return new PutAgenticBucketPolicyResult(this); }
    }
}
