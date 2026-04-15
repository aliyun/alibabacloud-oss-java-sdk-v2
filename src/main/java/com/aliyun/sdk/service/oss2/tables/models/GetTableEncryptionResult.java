package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.tables.models.internal.GetTableEncryptionResultJson;

import java.util.Optional;

public final class GetTableEncryptionResult extends ResultModel {
    private final GetTableEncryptionResultJson delegate;

    private GetTableEncryptionResult(Builder builder) {
        super(builder);
        this.delegate = (GetTableEncryptionResultJson) Optional.ofNullable(innerBody).orElse(new GetTableEncryptionResultJson());
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

        private Builder(GetTableEncryptionResult from) {
            super(from);
        }

        public GetTableEncryptionResult build() {
            return new GetTableEncryptionResult(this);
        }
    }
}
