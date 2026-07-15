package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the DeleteAgenticBucketEncryption operation.
 */
public final class DeleteAgenticBucketEncryptionResult extends ResultModel {
    DeleteAgenticBucketEncryptionResult(Builder builder) { super(builder); }
    public static Builder newBuilder() { return new Builder(); }
    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() { super(); }
        public DeleteAgenticBucketEncryptionResult build() { return new DeleteAgenticBucketEncryptionResult(this); }
    }
}
