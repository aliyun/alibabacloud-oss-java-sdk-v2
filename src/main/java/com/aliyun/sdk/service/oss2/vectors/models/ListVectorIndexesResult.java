package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.vectors.models.internal.ListVectorIndexesResultJson;
import java.util.List;
import java.util.Map;

/**
 * The result for the ListVectorsIndex operation.
 */
public final class ListVectorIndexesResult extends ResultModel {
    private final ListVectorIndexesResultJson delegate;

    private ListVectorIndexesResult(Builder builder) {
        super(builder);
        this.delegate = (ListVectorIndexesResultJson) innerBody;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The list of vector index summaries.
     */
    public List<Map<String, Object>> indexes() {
        return delegate != null ? delegate.indexes : null;
    }

    /**
     * The token for the next page of indexes.
     */
    public String nextToken() {
        return delegate != null ? delegate.nextToken : null;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(ListVectorIndexesResult result) {
            super(result);
        }

        public ListVectorIndexesResult build() {
            return new ListVectorIndexesResult(this);
        }
    }
}

