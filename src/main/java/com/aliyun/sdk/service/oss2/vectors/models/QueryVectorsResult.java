package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.vectors.models.internal.VectorListJson;
import java.util.List;
import java.util.Map;

/**
 * The result for the QueryVectors operation.
 */
public final class QueryVectorsResult extends ResultModel {
    private final VectorListJson delegate;

    private QueryVectorsResult(Builder builder) {
        super(builder);
        this.delegate = (VectorListJson) innerBody;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The list of query result vectors.
     */
    public List<Map<String, Object>> vectors() {
        return delegate != null ? delegate.vectors : null;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(QueryVectorsResult result) {
            super(result);
        }

        public QueryVectorsResult build() {
            return new QueryVectorsResult(this);
        }
    }
}
