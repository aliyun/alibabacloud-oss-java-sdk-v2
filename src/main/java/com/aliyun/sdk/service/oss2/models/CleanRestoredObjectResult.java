package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the CleanRestoredObject operation.
 */
public final class CleanRestoredObjectResult extends ResultModel {

    CleanRestoredObjectResult(Builder builder) {
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

        private Builder(CleanRestoredObjectResult result) {
            super(result);
        }

        public CleanRestoredObjectResult build() {
            return new CleanRestoredObjectResult(this);
        }
    }
}
