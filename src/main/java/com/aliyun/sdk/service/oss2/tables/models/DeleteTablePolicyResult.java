package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

public final class DeleteTablePolicyResult extends ResultModel {
    DeleteTablePolicyResult(Builder builder) {
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

        private Builder(DeleteTablePolicyResult from) {
            super(from);
        }

        public DeleteTablePolicyResult build() {
            return new DeleteTablePolicyResult(this);
        }
    }
}
