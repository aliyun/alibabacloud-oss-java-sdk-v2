package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the DeleteAgenticBucket operation.
 */
public final class DeleteAgenticBucketResult extends ResultModel {

    DeleteAgenticBucketResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        public DeleteAgenticBucketResult build() {
            return new DeleteAgenticBucketResult(this);
        }
    }
}
