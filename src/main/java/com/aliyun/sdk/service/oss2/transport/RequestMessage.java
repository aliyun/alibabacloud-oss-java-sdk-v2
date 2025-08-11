package com.aliyun.sdk.service.oss2.transport;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.TreeMap;

import static java.util.Objects.requireNonNull;

public class RequestMessage {
    private final String method;
    private final URI uri;
    private final Map<String, String> headers;
    private final BinaryData body;

    private RequestMessage(Builder builder) {
        this.method = builder.method;
        this.uri = builder.uri;
        this.headers = builder.headers != null ? builder.headers : new TreeMap<>();
        this.body = builder.body;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String method() {
        return method;
    }

    public URI uri() {
        return uri;
    }

    public Map<String, String> headers() {
        return headers;
    }

    public BinaryData body() {
        return body;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String method;
        private URI uri;
        private Map<String, String> headers;
        private BinaryData body;

        protected Builder() {
        }

        protected Builder(RequestMessage request) {
            this.method = request.method;
            this.uri = request.uri;
            this.headers = request.headers;
            this.body = request.body;
        }

        public Builder method(String value) {
            requireNonNull(value);
            this.method = value;
            return this;
        }

        public Builder uri(URI value) {
            requireNonNull(value);
            this.uri = value;
            return this;
        }

        public Builder uri(String value) {
            requireNonNull(value);
            try {
                this.uri = new URI(value);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public Builder headers(Map<String, String> value) {
            requireNonNull(value);
            this.headers = value;
            return this;
        }

        public Builder body(BinaryData value) {
            requireNonNull(value);
            this.body = value;
            return this;
        }

        public RequestMessage build() {
            return new RequestMessage(this);
        }
    }
}