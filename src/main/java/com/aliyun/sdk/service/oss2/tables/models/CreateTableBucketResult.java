package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.tables.models.internal.CreateTableBucketResultJson;

import java.util.Optional;

public final class CreateTableBucketResult extends ResultModel {
    private final CreateTableBucketResultJson delegate;

    private CreateTableBucketResult(Builder builder) {
        super(builder);
        this.delegate = (CreateTableBucketResultJson) Optional
                .ofNullable(innerBody)
                .orElse(new CreateTableBucketResultJson());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String arn() {
        return delegate.arn;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() {
            super();
        }

        private Builder(CreateTableBucketResult from) {
            super(from);
        }

        public CreateTableBucketResult build() {
            return new CreateTableBucketResult(this);
        }
    }
}
