package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutAccessPointPolicyForObjectProcess operation.
 */
public final class PutAccessPointPolicyForObjectProcessResult extends ResultModel { 

    PutAccessPointPolicyForObjectProcessResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutAccessPointPolicyForObjectProcessResult build() {
            return new PutAccessPointPolicyForObjectProcessResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutAccessPointPolicyForObjectProcessResult result) {
            super(result);
        }
    }
}
