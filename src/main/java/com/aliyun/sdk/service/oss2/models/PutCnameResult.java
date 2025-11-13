package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutCname operation.
 */
public final class PutCnameResult extends ResultModel { 

    PutCnameResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutCnameResult build() {
            return new PutCnameResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutCnameResult result) {
            super(result);
        }
    }
}
