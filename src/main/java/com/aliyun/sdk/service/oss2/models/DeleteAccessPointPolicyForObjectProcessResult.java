package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the DeleteAccessPointPolicyForObjectProcess operation.
 */
public final class DeleteAccessPointPolicyForObjectProcessResult extends ResultModel { 

    DeleteAccessPointPolicyForObjectProcessResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public DeleteAccessPointPolicyForObjectProcessResult build() {
            return new DeleteAccessPointPolicyForObjectProcessResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteAccessPointPolicyForObjectProcessResult result) {
            super(result);
        }
    }
}
