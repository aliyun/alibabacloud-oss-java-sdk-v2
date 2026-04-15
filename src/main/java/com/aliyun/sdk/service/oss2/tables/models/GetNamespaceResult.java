package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.tables.models.internal.GetNamespaceResultJson;

import java.util.List;
import java.util.Optional;

public final class GetNamespaceResult extends ResultModel {
    private final GetNamespaceResultJson delegate;

    private GetNamespaceResult(Builder builder) {
        super(builder);
        this.delegate = (GetNamespaceResultJson) Optional
                .ofNullable(innerBody)
                .orElse(new GetNamespaceResultJson());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<String> namespace() {
        return delegate.namespace;
    }

    public String tableBucketId() {
        return delegate.tableBucketId;
    }

    public String ownerAccountId() {
        return delegate.ownerAccountId;
    }

    public String createdAt() {
        return delegate.createdAt;
    }

    public String createdBy() {
        return delegate.createdBy;
    }

    public String namespaceId() {
        return delegate.namespaceId;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() {
            super();
        }

        private Builder(GetNamespaceResult from) {
            super(from);
        }

        public GetNamespaceResult build() {
            return new GetNamespaceResult(this);
        }
    }
}
