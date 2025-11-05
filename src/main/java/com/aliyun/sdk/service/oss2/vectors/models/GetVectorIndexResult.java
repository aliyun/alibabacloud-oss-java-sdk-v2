package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.vectors.models.internal.GetVectorIndexResultJson;

import java.util.Optional;

/**
 * The result for the GetVectorIndex operation.
 */
public final class GetVectorIndexResult extends ResultModel {
    private final GetVectorIndexResultJson delegate;

    private GetVectorIndexResult(Builder builder) {
        super(builder);
        this.delegate = (GetVectorIndexResultJson) Optional
                .ofNullable(innerBody)
                .orElse(new GetVectorIndexResultJson());;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The index information.
     */
    public IndexSummary index() {
        return delegate.index;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(GetVectorIndexResult from) {
            super(from);
        }

        public GetVectorIndexResult build() {
            return new GetVectorIndexResult(this);
        }
    }
}