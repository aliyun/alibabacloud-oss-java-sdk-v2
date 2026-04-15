package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.tables.models.internal.CreateNamespaceResultJson;

import java.util.List;
import java.util.Optional;

public final class CreateNamespaceResult extends ResultModel {
    private final CreateNamespaceResultJson delegate;

    private CreateNamespaceResult(Builder builder) {
        super(builder);
        this.delegate = (CreateNamespaceResultJson) Optional
                .ofNullable(innerBody)
                .orElse(new CreateNamespaceResultJson());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String tableBucketARN() {
        return delegate.tableBucketARN;
    }

    public List<String> namespace() {
        return delegate.namespace;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() {
            super();
        }

        private Builder(CreateNamespaceResult from) {
            super(from);
        }

        public CreateNamespaceResult build() {
            return new CreateNamespaceResult(this);
        }
    }
}
