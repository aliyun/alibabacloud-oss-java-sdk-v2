package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import static java.util.Objects.requireNonNull;

public class VectorRequestModel {
    protected Map<String, String> headers;
    protected Map<String, String> parameters;
    protected Map<String, Object> bodyFields;

    protected VectorRequestModel(VectorRequestModel.Builder<?> builder) {
        this.headers = Optional.ofNullable(builder.headers).orElse(new TreeMap<>(String.CASE_INSENSITIVE_ORDER));
        this.parameters = Optional.ofNullable(builder.parameters).orElse(new HashMap<>());
        this.bodyFields = Optional.ofNullable(builder.bodyFields).orElse(new HashMap<>());
    }

    public Map<String, String> headers() {
        return headers;
    }

    public Map<String, String> parameters() {
        return parameters;
    }

    public Map<String, Object> bodyFields() {
        return bodyFields;
    }

    protected static class Builder<BuilderT> {
        protected Map<String, String> headers;
        protected Map<String, String> parameters;
        protected Map<String, Object> bodyFields;

        protected Builder() {
            this.headers = MapUtils.caseInsensitiveMap();
            this.parameters = MapUtils.caseSensitiveMap();
            this.bodyFields = new HashMap<>();
        }

        protected Builder(VectorRequestModel request) {
            this.headers = request.headers;
            this.parameters = request.parameters;
            this.bodyFields = request.bodyFields;
        }

        @SuppressWarnings("unchecked")
        public BuilderT headers(Map<String, String> value) {
            requireNonNull(value);
            this.headers.putAll(value);
            return (BuilderT) this;
        }

        @SuppressWarnings("unchecked")
        public BuilderT parameters(Map<String, String> value) {
            requireNonNull(value);
            this.parameters.putAll(value);
            return (BuilderT) this;
        }

        @SuppressWarnings("unchecked")
        public BuilderT bodyFields(Map<String, Object> value) {
            requireNonNull(value);
            this.bodyFields.putAll(value);
            return (BuilderT) this;
        }

        @SuppressWarnings("unchecked")
        public BuilderT header(String key, String value) {
            requireNonNull(key);
            requireNonNull(value);
            this.headers.put(key, value);
            return (BuilderT) this;
        }

        @SuppressWarnings("unchecked")
        public BuilderT parameter(String key, String value) {
            requireNonNull(key);
            this.parameters.put(key, value);
            return (BuilderT) this;
        }

        @SuppressWarnings("unchecked")
        public BuilderT bodyField(String key, Object value) {
            requireNonNull(key);
            this.bodyFields.put(key, value);
            return (BuilderT) this;
        }
    }
}
