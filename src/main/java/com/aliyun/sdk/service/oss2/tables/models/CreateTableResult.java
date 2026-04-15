package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.tables.models.internal.CreateTableResultJson;

import java.util.Optional;

public final class CreateTableResult extends ResultModel {
    private final CreateTableResultJson delegate;

    private CreateTableResult(Builder builder) {
        super(builder);
        this.delegate = (CreateTableResultJson) Optional.ofNullable(innerBody).orElse(new CreateTableResultJson());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String tableARN() {
        return delegate.tableARN;
    }

    public String versionToken() {
        return delegate.versionToken;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() {
            super();
        }

        private Builder(CreateTableResult from) {
            super(from);
        }

        public CreateTableResult build() {
            return new CreateTableResult(this);
        }
    }
}