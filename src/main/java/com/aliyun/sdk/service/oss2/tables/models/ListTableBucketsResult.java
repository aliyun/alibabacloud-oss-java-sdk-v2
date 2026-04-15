package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.tables.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.tables.models.internal.ListTableBucketsResultJson;

import java.util.List;
import java.util.Optional;

public final class ListTableBucketsResult extends ResultModel {
    private final ListTableBucketsResultJson delegate;

    private ListTableBucketsResult(Builder builder) {
        super(builder);
        this.delegate = (ListTableBucketsResultJson) Optional
                .ofNullable(innerBody)
                .orElse(new ListTableBucketsResultJson());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<TableBucketSummary> tableBuckets() {
        return CastUtils.ensureList(delegate.tableBuckets);
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

        private Builder(ListTableBucketsResult from) {
            super(from);
        }

        public ListTableBucketsResult build() {
            return new ListTableBucketsResult(this);
        }
    }
}
