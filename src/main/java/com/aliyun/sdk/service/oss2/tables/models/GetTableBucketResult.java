package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.tables.models.internal.GetTableBucketResultJson;

import java.util.Optional;

public final class GetTableBucketResult extends ResultModel {
    private final GetTableBucketResultJson delegate;

    private GetTableBucketResult(Builder builder) {
        super(builder);
        this.delegate = (GetTableBucketResultJson) Optional
                .ofNullable(innerBody)
                .orElse(new GetTableBucketResultJson());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String arn() {
        return delegate.arn;
    }

    public String name() {
        return delegate.name;
    }

    public String ownerAccountId() {
        return delegate.ownerAccountId;
    }

    public String createdAt() {
        return delegate.createdAt;
    }

    public String tableBucketId() {
        return delegate.tableBucketId;
    }

    public String type() {
        return delegate.type;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() {
            super();
        }

        private Builder(GetTableBucketResult from) {
            super(from);
        }

        public GetTableBucketResult build() {
            return new GetTableBucketResult(this);
        }
    }
}
