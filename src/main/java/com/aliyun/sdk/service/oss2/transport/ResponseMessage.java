package com.aliyun.sdk.service.oss2.transport;

import java.io.Closeable;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class ResponseMessage implements Closeable {
    private final Map<String, String> headers;
    private final RequestMessage request;
    private final int statusCode;
    private final BinaryData body;

    private ResponseMessage(Builder builder) {
        this.headers = builder.headers;
        this.request = builder.request;
        this.statusCode = builder.statusCode;
        this.body = builder.body;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public Map<String, String> headers() {
        return headers;
    }

    public RequestMessage request() {
        return request;
    }

    public int statusCode() {
        return statusCode;
    }

    public BinaryData body() {
        return body;
    }

    @Override
    public void close() {
        if (body != null && body instanceof InputStreamBinaryData) {
            ((InputStreamBinaryData) body).tryClose();
        }
    }

    public static class Builder {
        private Map<String, String> headers;
        private RequestMessage request;
        private int statusCode;
        private BinaryData body;

        protected Builder() {
        }

        protected Builder(ResponseMessage response) {
            this.headers = response.headers;
            this.request = response.request;
            this.statusCode = response.statusCode;
            this.body = response.body;
        }

        public Builder headers(Map<String, String> value) {
            requireNonNull(value);
            this.headers = value;
            return this;
        }

        public Builder request(RequestMessage value) {
            requireNonNull(value);
            this.request = value;
            return this;
        }

        public Builder statusCode(int value) {
            this.statusCode = value;
            return this;
        }

        public Builder body(BinaryData value) {
            requireNonNull(value);
            this.body = value;
            return this;
        }

        public ResponseMessage build() {
            return new ResponseMessage(this);
        }

    }

}