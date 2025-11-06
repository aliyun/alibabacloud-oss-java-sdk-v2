package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.vectors.models.internal.QueryVectorsJson;
import com.aliyun.sdk.service.oss2.vectors.models.VectorsSummary;
import java.util.Optional;
import java.util.List;

/**
 * The result for the QueryVectors operation.
 */
public final class QueryVectorsResult extends ResultModel {
    private final QueryVectorsJson delegate;

    private QueryVectorsResult(Builder builder) {
        super(builder);
        this.delegate = (QueryVectorsJson) Optional
                .ofNullable(innerBody)
                .orElse(new QueryVectorsJson());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The list of query result vectors.
     */
    public List<QueryVectorsSummary> vectors() {
        return CastUtils.ensureList(delegate.vectors);
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(QueryVectorsResult from) {
            super(from);
        }

        public QueryVectorsResult build() {
            return new QueryVectorsResult(this);
        }
    }
}