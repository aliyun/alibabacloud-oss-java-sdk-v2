package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the PutVectors operation.
 */
public final class PutVectorsResult extends ResultModel {

    PutVectorsResult(Builder builder) {
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

        private Builder(PutVectorsResult result) {
            super(result);
        }

        public PutVectorsResult build() {
            return new PutVectorsResult(this);
        }
    }
}

