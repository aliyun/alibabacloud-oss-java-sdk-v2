package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the PutVectorIndexFusion operation.
 */
public final class PutVectorIndexFusionResult extends ResultModel {

    PutVectorIndexFusionResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(PutVectorIndexFusionResult result) {
            super(result);
        }

        public PutVectorIndexFusionResult build() {
            return new PutVectorIndexFusionResult(this);
        }
    }
}
