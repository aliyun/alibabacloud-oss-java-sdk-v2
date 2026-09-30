package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.vectors.models.internal.QueryVectorsFusionJson;
import java.util.Optional;
import java.util.List;

/**
 * The result for the QueryVectorsFusion operation.
 */
public final class QueryVectorsFusionResult extends ResultModel {
    private final QueryVectorsFusionJson delegate;

    private QueryVectorsFusionResult(Builder builder) {
        super(builder);
        this.delegate = (QueryVectorsFusionJson) Optional
                .ofNullable(innerBody)
                .orElse(new QueryVectorsFusionJson());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The list of the query result vectors.
     */
    public List<QueryVectorsFusionSummary> vectors() {
        return CastUtils.ensureList(delegate.vectors);
    }

    /**
     * The token for the next page of vectors.
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

        private Builder(QueryVectorsFusionResult from) {
            super(from);
        }

        public QueryVectorsFusionResult build() {
            return new QueryVectorsFusionResult(this);
        }
    }
}
