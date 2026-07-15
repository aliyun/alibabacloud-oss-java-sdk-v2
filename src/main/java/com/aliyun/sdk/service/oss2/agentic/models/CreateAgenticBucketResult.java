package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the CreateAgenticBucket operation.
 */
public final class CreateAgenticBucketResult extends ResultModel {

    CreateAgenticBucketResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        public CreateAgenticBucketResult build() {
            return new CreateAgenticBucketResult(this);
        }
    }
}
