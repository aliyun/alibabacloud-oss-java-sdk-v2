package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.vectors.models.internal.ListVectorsResultJson;
import java.util.Optional;
import java.util.List;

/**
 * The result for the ListVectors operation.
 */
public final class ListVectorsResult extends ResultModel {
    private final ListVectorsResultJson delegate;

    private ListVectorsResult(Builder builder) {
        super(builder);
        this.delegate = (ListVectorsResultJson) Optional
                .ofNullable(innerBody)
                .orElse(new ListVectorsResultJson());;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The list of vectors.
     */
    public List<VectorsSummary> vectors() {
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

        private Builder(ListVectorsResult from) {
            super(from);
        }

        public ListVectorsResult build() {
            return new ListVectorsResult(this);
        }
    }
}