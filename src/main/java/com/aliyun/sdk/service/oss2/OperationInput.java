package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class OperationInput {
    private final String opName;
    private final String method;
    private final Map<String, String> headers;
    private final Map<String, String> parameters;
    private final BinaryData body;
    private final String bucket;
    private final String key;
    private final AttributeMap opMetadata;

    private OperationInput(Builder builder) {
        this.opName = builder.opName != null ? builder.opName : "";
        this.method = builder.method;
        this.headers = builder.headers != null ? builder.headers : MapUtils.caseInsensitiveMap();
        this.parameters = builder.parameters != null ? builder.parameters : MapUtils.caseSensitiveMap();
        this.body = builder.body;
        this.bucket = builder.bucket;
        this.key = builder.key;
        this.opMetadata = builder.opMetadata != null ? builder.opMetadata : AttributeMap.empty();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String opName() {
        return opName;
    }

    public String method() {
        return method;
    }

    public Map<String, String> headers() {
        return headers;
    }

    public Map<String, String> parameters() {
        return parameters;
    }

    public Optional<BinaryData> body() {
        return Optional.ofNullable(body);
    }

    public Optional<String> bucket() {
        return Optional.ofNullable(bucket);
    }

    public Optional<String> key() {
        return Optional.ofNullable(key);
    }

    public AttributeMap opMetadata() {
        return opMetadata;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String opName;
        private String method;
        private Map<String, String> headers;
        private Map<String, String> parameters;
        private BinaryData body;
        private String bucket;
        private String key;
        private AttributeMap opMetadata;

        private Builder() {
        }

        private Builder(OperationInput input) {
            this.opName = input.opName;
            this.method = input.method;
            this.headers = input.headers;
            this.parameters = input.parameters;
            this.body = input.body;
            this.bucket = input.bucket;
            this.key = input.key;
            this.opMetadata = input.opMetadata;
        }

        public Builder opName(String value) {
            requireNonNull(value);
            this.opName = value;
            return this;
        }

        public Builder method(String value) {
            requireNonNull(value);
            this.method = value;
            return this;
        }

        public Builder headers(Map<String, String> value) {
            requireNonNull(value);
            this.headers = value;
            return this;
        }

        public Builder parameters(Map<String, String> value) {
            requireNonNull(value);
            this.parameters = value;
            return this;
        }

        public Builder body(BinaryData value) {
            this.body = value;
            return this;
        }

        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        public Builder key(String value) {
            requireNonNull(value);
            this.key = value;
            return this;
        }

        public Builder opMetadata(AttributeMap value) {
            requireNonNull(value);
            this.opMetadata = value;
            return this;
        }

        public OperationInput build() {
            return new OperationInput(this);
        }
    }
}
