package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.vectors.models.internal.ListVectorsResultJson;
import com.aliyun.sdk.service.oss2.vectors.models.internal.QueryVectorsJson;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The result for the QueryVectors operation.
 */
public final class QueryVectorsResult extends ResultModel {
    private final QueryVectorsJson delegate;

    private QueryVectorsResult(Builder builder) {
        super(builder);
        this.delegate = (QueryVectorsJson) innerBody;
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

    /**
     * The list of vectors retrieved as QueryOutputVector and QueryVectorsInfo objects.
     */
    public QueryVectorsInfo asVectors() {
        if (delegate == null) {
            return null;
        }
        
        List<QueryOutputVector> vectorList = null;
        if (delegate.vectors != null) {
            vectorList = delegate.vectors.stream()
                    .map(vectorMap -> {
                        if (vectorMap == null) {
                            return QueryOutputVector.newBuilder().build();
                        }

                        QueryOutputVector vector = QueryOutputVector.newBuilder()
                                .distance(getIntegerValue(vectorMap, "distance"))
                                .key(getStringValue(vectorMap, "key"))
                                .metadata(getMapValue(vectorMap, "metadata"))
                                .build();
                        return vector;
                    })
                    .collect(Collectors.toList());
        }
        
        return QueryVectorsInfo.newBuilder()
                .vectors(vectorList)
                .build();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getMapValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof Map) {
            return (Map<String, Object>) value;
        }
        return null;
    }

    private String getStringValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof String) {
            return (String) value;
        }
        return null;
    }
    
    private Integer getIntegerValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        }
        return null;
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