package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

public final class DeleteTableBucketEncryptionResult extends ResultModel {
    DeleteTableBucketEncryptionResult(Builder builder) {
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

        private Builder(DeleteTableBucketEncryptionResult from) {
            super(from);
        }

        public DeleteTableBucketEncryptionResult build() {
            return new DeleteTableBucketEncryptionResult(this);
        }
    }
}
