package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.vectors.models.internal.ListVectorIndexesResultJson;
import java.util.Optional;
import java.util.List;

/**
 * The result for the ListVectorsIndex operation.
 */
public final class ListVectorIndexesResult extends ResultModel {
    private final ListVectorIndexesResultJson delegate;

    private ListVectorIndexesResult(Builder builder) {
        super(builder);
        this.delegate = (ListVectorIndexesResultJson) Optional
                .ofNullable(innerBody)
                .orElse(new ListVectorIndexesResultJson());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The list of vector index summaries.
     */
    public List<IndexSummary> indexes() {
        return CastUtils.ensureList(delegate.indexes);
    }
    
    /**
     * The token for the next page of indexes.
     */
    public String nextToken() {
        return delegate.nextToken;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(ListVectorIndexesResult from) {
            super(from);
        }

        public ListVectorIndexesResult build() {
            return new ListVectorIndexesResult(this);
        }
    }
}