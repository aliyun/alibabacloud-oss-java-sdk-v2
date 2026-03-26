package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutObjectRetention operation.
 */
public final class PutObjectRetentionResult extends ResultModel {

    private PutObjectRetentionResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutObjectRetentionResult build() {
            return new PutObjectRetentionResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutObjectRetentionResult result) {
            super(result);
        }
    }
}
