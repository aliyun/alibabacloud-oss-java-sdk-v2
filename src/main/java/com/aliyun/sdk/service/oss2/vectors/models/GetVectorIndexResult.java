package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import java.util.Map;

/**
 * The result for the GetVectorIndex operation.
 */
public final class GetVectorIndexResult extends ResultModel {
    private final IndexInfo delegate;

    private GetVectorIndexResult(Builder builder) {
        super(builder);
        this.delegate = (IndexInfo) innerBody;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The index information.
     */
    public Map<String, Object> index() {
        return delegate != null ? delegate.index() : null;
    }

    /**
     * Convert to IndexSummary object.
     */
    public IndexSummary asIndex() {
        Map<String, Object> indexMap = index();
        if (indexMap == null) {
            return null;
        }

        IndexSummary.Builder builder = IndexSummary.newBuilder();
        builder.createTime((String) indexMap.get("createTime"));
        builder.indexName((String) indexMap.get("indexName"));
        builder.dataType((String) indexMap.get("dataType"));
        builder.dimension((Integer) indexMap.get("dimension"));
        builder.distanceMetric((String) indexMap.get("distanceMetric"));
        builder.metadata((Map<String, Object>) indexMap.get("metadata"));
        builder.vectorBucketName((String) indexMap.get("vectorBucketName"));
        builder.status((String) indexMap.get("status"));

        return builder.build();
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