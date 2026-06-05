package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutStyle operation.
 */
public final class PutStyleResult extends ResultModel { 

    PutStyleResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutStyleResult build() {
            return new PutStyleResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutStyleResult result) {
            super(result);
        }
    }
}
