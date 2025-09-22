package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.vectors.models.internal.IndexInfoJson;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/**
 * The result for the GetVectorIndex operation.
 */
public final class GetVectorIndexResult extends ResultModel {
    private final IndexInfoJson delegate;

    private GetVectorIndexResult(Builder builder) {
        super(builder);
        this.delegate = (IndexInfoJson) innerBody;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The vector index information.
     */
    public Map<String, Object> index() {
        return delegate != null ? delegate.index : null;
    }


    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(GetVectorIndexResult result) {
            super(result);
        }

        public GetVectorIndexResult build() {
            return new GetVectorIndexResult(this);
        }
    }
}

