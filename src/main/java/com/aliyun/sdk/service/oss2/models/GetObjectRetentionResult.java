package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetObjectRetention operation.
 */
public final class GetObjectRetentionResult extends ResultModel {
    private final Retention retention;

    private GetObjectRetentionResult(Builder builder) {
        super(builder);
        this.retention = builder.retention;
    }

    /**
     * The container for object retention configuration.
     */
    public Retention retention() {
        return retention;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {
        private Retention retention;

        /**
         * The container for object retention configuration.
         */
        public Builder retention(Retention value) {
            this.retention = value;
            return this;
        }

        public GetObjectRetentionResult build() {
            return new GetObjectRetentionResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetObjectRetentionResult result) {
            super(result);
            this.retention = result.retention;
        }
    }
}
