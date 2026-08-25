package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the PutAgenticBucketAcl operation.
 */
public final class PutAgenticBucketAclResult extends ResultModel {
    PutAgenticBucketAclResult(Builder builder) { super(builder); }
    public static Builder newBuilder() { return new Builder(); }

    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() { super(); }
        public PutAgenticBucketAclResult build() { return new PutAgenticBucketAclResult(this); }
    }
}
