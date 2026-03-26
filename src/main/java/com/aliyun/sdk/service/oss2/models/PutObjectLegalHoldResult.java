package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutObjectLegalHold operation.
 */
public final class PutObjectLegalHoldResult extends ResultModel {

    private PutObjectLegalHoldResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutObjectLegalHoldResult build() {
            return new PutObjectLegalHoldResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutObjectLegalHoldResult result) {
            super(result);
        }
    }
}
