package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.tables.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.tables.models.internal.ListTablesResultJson;

import java.util.List;
import java.util.Optional;

public final class ListTablesResult extends ResultModel {
    private final ListTablesResultJson delegate;

    private ListTablesResult(Builder builder) {
        super(builder);
        this.delegate = (ListTablesResultJson) Optional.ofNullable(innerBody).orElse(new ListTablesResultJson());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<TableSummary> tables() {
        return CastUtils.ensureList(delegate.tables);
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

        private Builder(ListTablesResult from) {
            super(from);
        }

        public ListTablesResult build() {
            return new ListTablesResult(this);
        }
    }
}
