package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the PutAgenticBucketStatus operation.
 */
public final class PutAgenticBucketStatusResult extends ResultModel {

    PutAgenticBucketStatusResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() { super(); }

        public PutAgenticBucketStatusResult build() {
            return new PutAgenticBucketStatusResult(this);
        }
    }
}
