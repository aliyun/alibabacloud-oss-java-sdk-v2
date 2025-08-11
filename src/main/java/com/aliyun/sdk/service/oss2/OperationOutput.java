package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;
import java.util.Optional;

public class OperationOutput {
    //TODO
    public final String status;
    public final int statusCode;
    public final Map<String, String> headers;
    public final BinaryData body;

    private final OperationInput input;
    private final AttributeMap opMetadata;

    private OperationOutput(Builder builder) {
        this.status = Optional.ofNullable(builder.status).orElse("");
        this.statusCode = builder.statusCode;
        this.headers = Optional.ofNullable(builder.headers).orElse(MapUtils.caseInsensitiveMap());
        this.body = builder.body;
        this.input = builder.input;
        this.opMetadata = builder.opMetadata;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String status() {
        return status;
    }

    public int statusCode() {
        return statusCode;
    }

    public Map<String, String> headers() {
        return headers;
    }

    public Optional<BinaryData> body() {
        return Optional.ofNullable(body);
    }

    public Optional<OperationInput> input() {
        return Optional.ofNullable(input);
    }

    public Optional<AttributeMap> opMetadata() {
        return Optional.ofNullable(opMetadata);
    }

    public static class Builder {
        private String status;
        private int statusCode;
        private Map<String, String> headers;
        private BinaryData body;

        private OperationInput input;
        private AttributeMap opMetadata;

        public Builder status(String value) {
            //requireNonNull(value);
            this.status = value;
            return this;
        }

        public Builder statusCode(int value) {
            this.statusCode = value;
            return this;
        }

        public Builder headers(Map<String, String> value) {
            //requireNonNull(value);
            this.headers = value;
            return this;
        }

        public Builder body(BinaryData value) {
            //requireNonNull(value);
            this.body = value;
            return this;
        }

        public Builder input(OperationInput value) {
            //requireNonNull(value);
            this.input = value;
            return this;
        }

        public Builder opMetadata(AttributeMap value) {
            //requireNonNull(value);
            this.opMetadata = value;
            return this;
        }

        public OperationOutput build() {
            return new OperationOutput(this);
        }
    }
}
