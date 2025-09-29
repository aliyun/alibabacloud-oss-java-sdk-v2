package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.vectors.models.internal.ListVectorIndexesResultJson;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

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

    /**
     * The list of vector index summaries as IndexSummary objects.
     */
    public List<IndexSummary> asIndex() {
        if (delegate == null || delegate.indexes == null) {
            return null;
        }

        return delegate.indexes.stream()
                .map(indexMap -> {
                    if (indexMap == null) {
                        return IndexSummary.newBuilder().build();
                    }

                    IndexSummary index = IndexSummary.newBuilder()
                            .createTime((String) indexMap.get("createTime"))
                            .indexName((String) indexMap.get("indexName"))
                            .dataType((String) indexMap.get("dataType"))
                            .dimension(getIntegerValue(indexMap, "dimension"))
                            .distanceMetric((String) indexMap.get("distanceMetric"))
                            .metadata(getMapValue(indexMap, "metadata"))
                            .vectorBucketName((String) indexMap.get("vectorBucketName"))
                            .status((String) indexMap.get("status"))
                            .build();
                    return index;
                })
                .collect(Collectors.toList());
    }


    private Integer getIntegerValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return null;
            }
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getMapValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof Map) {
            return (Map<String, Object>) value;
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

        private Builder(ListVectorIndexesResult result) {
            super(result);
        }

        public ListVectorIndexesResult build() {
            return new ListVectorIndexesResult(this);
        }
    }
}