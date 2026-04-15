package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

public final class DeleteTableBucketResult extends ResultModel {
    DeleteTableBucketResult(Builder builder) {
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

        private Builder(DeleteTableBucketResult from) {
            super(from);
        }

        public DeleteTableBucketResult build() {
            return new DeleteTableBucketResult(this);
        }
    }
}
