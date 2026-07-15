package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.AccessControlPolicy;
import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the GetAgenticBucketAcl operation.
 */
public final class GetAgenticBucketAclResult extends ResultModel {
    /**
     * The access control policy of the bucket.
     */
    public AccessControlPolicy accessControlPolicy() { return (AccessControlPolicy) innerBody; }

    GetAgenticBucketAclResult(Builder builder) { super(builder); }
    public static Builder newBuilder() { return new Builder(); }

    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() { super(); }
        public GetAgenticBucketAclResult build() { return new GetAgenticBucketAclResult(this); }
    }
}
