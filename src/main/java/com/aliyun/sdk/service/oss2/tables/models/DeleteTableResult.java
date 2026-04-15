package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

public final class DeleteTableResult extends ResultModel {
    DeleteTableResult(Builder builder) {
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

        private Builder(DeleteTableResult from) {
            super(from);
        }

        public DeleteTableResult build() {
            return new DeleteTableResult(this);
        }
    }
}
