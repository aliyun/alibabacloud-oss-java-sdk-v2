package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutAccessPointConfigForObjectProcess operation.
 */
public final class PutAccessPointConfigForObjectProcessResult extends ResultModel { 

    PutAccessPointConfigForObjectProcessResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutAccessPointConfigForObjectProcessResult build() {
            return new PutAccessPointConfigForObjectProcessResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutAccessPointConfigForObjectProcessResult result) {
            super(result);
        }
    }
}
