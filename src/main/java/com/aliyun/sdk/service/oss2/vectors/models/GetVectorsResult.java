package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.vectors.models.internal.GetVectorsResultJson;

import java.util.Optional;
import java.util.List;

/**
 * The result for the GetVectors operation.
 */
public final class GetVectorsResult extends ResultModel {
    private final GetVectorsResultJson delegate;

    private GetVectorsResult(Builder builder) {
        super(builder);
        this.delegate = (GetVectorsResultJson) Optional
                .ofNullable(innerBody)
                .orElse(new GetVectorsResultJson());;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The vectors information.
     */
    public List<VectorsSummary> vectors() {
        return CastUtils.ensureList(delegate.vectors);
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(GetVectorsResult from) {
            super(from);
        }

        public GetVectorsResult build() {
            return new GetVectorsResult(this);
        }
    }
}