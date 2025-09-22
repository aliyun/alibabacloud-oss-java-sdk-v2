package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.vectors.models.internal.VectorListJson;
import java.util.List;
import java.util.Map;

/**
 * The result for the GetVectors operation.
 */
public final class GetVectorsResult extends ResultModel {
    private final VectorListJson delegate;

    private GetVectorsResult(Builder builder) {
        super(builder);
        this.delegate = (VectorListJson) innerBody;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The list of vectors retrieved.
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

        private Builder(GetVectorsResult result) {
            super(result);
        }

        public GetVectorsResult build() {
            return new GetVectorsResult(this);
        }
    }
}
