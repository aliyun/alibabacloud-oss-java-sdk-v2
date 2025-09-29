package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.vectors.models.ListVectorsInfo;
import com.aliyun.sdk.service.oss2.vectors.models.internal.ListVectorsResultJson;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The result for the ListVectors operation.
 */
public final class ListVectorsResult extends ResultModel {
    private final ListVectorsResultJson delegate;

    private ListVectorsResult(Builder builder) {
        super(builder);
        this.delegate = (ListVectorsResultJson) innerBody;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The token for the next page of vectors.
     */
    public String nextToken() {
        return delegate != null ? delegate.nextToken : null;
    }

    /**
     * The list of vectors retrieved.
     */
    public List<Map<String, Object>> vectors() {
        return delegate != null ? delegate.vectors : null;
    }

    /**
     * The list of vectors retrieved as ListOutputVector objects with nextToken.
     */
    public ListVectorsInfo asVectors() {
        if (delegate == null) {
            return null;
        }
        
        List<ListOutputVector> vectorList = null;
        if (delegate.vectors != null) {
            vectorList = delegate.vectors.stream()
                    .map(vectorMap -> {
                        if (vectorMap == null) {
                            return ListOutputVector.newBuilder().build();
                        }

                        ListOutputVector vector = ListOutputVector.newBuilder()
                                .data(getMapValue(vectorMap, "data"))
                                .key(getStringValue(vectorMap, "key"))
                                .metadata(getMapValue(vectorMap, "metadata"))
                                .build();
                        return vector;
                    })
                    .collect(Collectors.toList());
        }
        
        return ListVectorsInfo.newBuilder()
                .nextToken(delegate.nextToken)
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

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(ListVectorsResult result) {
            super(result);
        }

        public ListVectorsResult build() {
            return new ListVectorsResult(this);
        }
    }
}