package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the PutAgenticBucketEncryption operation.
 */
public final class PutAgenticBucketEncryptionResult extends ResultModel {
    PutAgenticBucketEncryptionResult(Builder builder) { super(builder); }
    public static Builder newBuilder() { return new Builder(); }
    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() { super(); }
        public PutAgenticBucketEncryptionResult build() { return new PutAgenticBucketEncryptionResult(this); }
    }
}
