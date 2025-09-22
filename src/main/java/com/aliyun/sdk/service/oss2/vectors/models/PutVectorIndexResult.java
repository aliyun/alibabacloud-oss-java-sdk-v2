package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the PutVectorIndex operation.
 */
public final class PutVectorIndexResult extends ResultModel {

    PutVectorIndexResult(Builder builder) {
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

        private Builder(PutVectorIndexResult result) {
            super(result);
        }

        public PutVectorIndexResult build() {
            return new PutVectorIndexResult(this);
        }
    }
}
