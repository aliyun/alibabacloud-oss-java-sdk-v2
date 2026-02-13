package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the WriteGetObjectResponse operation.
 */
public final class WriteGetObjectResponseResult extends ResultModel {
    public WriteGetObjectResponseResult() {
        super(new Builder());
    }

    private WriteGetObjectResponseResult(Builder builder) {
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

        private Builder(WriteGetObjectResponseResult from) {
            super(from);
        }

        public WriteGetObjectResponseResult build() {
            return new WriteGetObjectResponseResult(this);
        }
    }
}