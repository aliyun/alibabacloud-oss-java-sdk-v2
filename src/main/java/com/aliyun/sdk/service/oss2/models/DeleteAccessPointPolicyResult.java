package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the DeleteAccessPointPolicy operation.
 */
public final class DeleteAccessPointPolicyResult extends ResultModel { 

    DeleteAccessPointPolicyResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public DeleteAccessPointPolicyResult build() {
            return new DeleteAccessPointPolicyResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteAccessPointPolicyResult result) {
            super(result);
        }
    }
}
