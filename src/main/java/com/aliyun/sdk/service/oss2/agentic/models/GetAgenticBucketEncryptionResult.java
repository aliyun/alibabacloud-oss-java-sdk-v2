package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.models.ServerSideEncryptionRule;

/**
 * The result for the GetAgenticBucketEncryption operation.
 */
public final class GetAgenticBucketEncryptionResult extends ResultModel {
    /**
     * The server-side encryption rule of the bucket.
     */
    public ServerSideEncryptionRule serverSideEncryptionRule() { return (ServerSideEncryptionRule) innerBody; }
    GetAgenticBucketEncryptionResult(Builder builder) { super(builder); }
    public static Builder newBuilder() { return new Builder(); }
    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() { super(); }
        public GetAgenticBucketEncryptionResult build() { return new GetAgenticBucketEncryptionResult(this); }
    }
}
