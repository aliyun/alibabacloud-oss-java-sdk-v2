package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutAccessPointPolicy operation.
 */
public final class PutAccessPointPolicyResult extends ResultModel { 

    PutAccessPointPolicyResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutAccessPointPolicyResult build() {
            return new PutAccessPointPolicyResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutAccessPointPolicyResult result) {
            super(result);
        }
    }
}
