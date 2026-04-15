package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.tables.models.internal.GetTableBucketEncryptionResultJson;

import java.util.Optional;

public final class GetTableBucketEncryptionResult extends ResultModel {
    private final GetTableBucketEncryptionResultJson delegate;

    private GetTableBucketEncryptionResult(Builder builder) {
        super(builder);
        this.delegate = (GetTableBucketEncryptionResultJson) Optional
                .ofNullable(innerBody)
                .orElse(new GetTableBucketEncryptionResultJson());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public EncryptionConfiguration encryptionConfiguration() {
        return delegate.encryptionConfiguration;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() {
            super();
        }

        private Builder(GetTableBucketEncryptionResult from) {
            super(from);
        }

        public GetTableBucketEncryptionResult build() {
            return new GetTableBucketEncryptionResult(this);
        }
    }
}
