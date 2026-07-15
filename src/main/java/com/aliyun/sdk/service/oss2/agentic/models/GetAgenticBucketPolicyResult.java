package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the GetAgenticBucketPolicy operation.
 */
public final class GetAgenticBucketPolicyResult extends ResultModel {
    /**
     * The policy of the bucket, as a JSON string.
     */
    public String policy() { return innerBody != null ? (String) innerBody : null; }
    GetAgenticBucketPolicyResult(Builder builder) { super(builder); }
    public static Builder newBuilder() { return new Builder(); }
    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() { super(); }
        public GetAgenticBucketPolicyResult build() { return new GetAgenticBucketPolicyResult(this); }
    }
}
