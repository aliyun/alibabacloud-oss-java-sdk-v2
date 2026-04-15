package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.tables.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.tables.models.internal.ListNamespacesResultJson;

import java.util.List;
import java.util.Optional;

public final class ListNamespacesResult extends ResultModel {
    private final ListNamespacesResultJson delegate;

    private ListNamespacesResult(Builder builder) {
        super(builder);
        this.delegate = (ListNamespacesResultJson) Optional
                .ofNullable(innerBody)
                .orElse(new ListNamespacesResultJson());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<NamespaceSummary> namespaces() {
        return CastUtils.ensureList(delegate.namespaces);
    }

    public String continuationToken() {
        return delegate.continuationToken;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() {
            super();
        }

        private Builder(ListNamespacesResult from) {
            super(from);
        }

        public ListNamespacesResult build() {
            return new ListNamespacesResult(this);
        }
    }
}
