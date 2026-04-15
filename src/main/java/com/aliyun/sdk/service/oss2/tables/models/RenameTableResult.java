package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

public final class RenameTableResult extends ResultModel {
    RenameTableResult(Builder builder) {
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

        private Builder(RenameTableResult from) {
            super(from);
        }

        public RenameTableResult build() {
            return new RenameTableResult(this);
        }
    }
}
