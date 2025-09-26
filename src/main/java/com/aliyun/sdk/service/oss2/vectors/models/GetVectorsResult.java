package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.vectors.models.internal.GetOutputVector;
import com.aliyun.sdk.service.oss2.vectors.models.internal.VectorListJson;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    /**
     * The token for the next page of vectors.
     */
    public String nextToken() {
        return delegate != null ? delegate.nextToken : null;
    }

    /**
     * The list of vectors retrieved as GetOutputVector objects.
     */
    public List<GetOutputVector> asGetOutputVectors() {
        if (delegate == null || delegate.vectors == null) {
            return null;
        }
        return delegate.vectors.stream()
                .map(vectorMap -> {
                    if (vectorMap == null) {
                        return GetOutputVector.newBuilder().build();
                    }

                    GetOutputVector vector = GetOutputVector.newBuilder()
                            .data(getMapValue(vectorMap, "data"))
                            .key(getStringValue(vectorMap, "key"))
                            .metadata(getMapValue(vectorMap, "metadata"))
                            .build();
                    return vector;
                })
                .collect(Collectors.toList());
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

        private Builder(GetVectorsResult result) {
            super(result);
        }

        public GetVectorsResult build() {
            return new GetVectorsResult(this);
        }
    }
}
