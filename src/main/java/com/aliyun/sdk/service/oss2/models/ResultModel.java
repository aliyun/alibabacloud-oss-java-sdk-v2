package com.aliyun.sdk.service.oss2.models;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import static java.util.Objects.requireNonNull;

public class ResultModel {
    protected Map<String, String> headers;
    protected String status;
    protected int statusCode;
    protected Object innerBody;

    protected ResultModel(Builder<?> builder) {
        this.headers = Optional.ofNullable(builder.headers).orElse(new TreeMap<>(String.CASE_INSENSITIVE_ORDER));
        this.status = Optional.ofNullable(builder.status).orElse("");
        this.statusCode = builder.statusCode;
        this.innerBody = builder.innerBody;
    }

    public Map<String, String> headers() {
        return headers;
    }

    public String status() {
        return status;
    }

    public int statusCode() {
        return statusCode;
    }

    public String requestId() {
        return headers.getOrDefault("x-oss-request-id", "");
    }

    protected static class Builder<BuilderT> {
        protected Map<String, String> headers;
        protected String status;
        protected int statusCode;
        protected Object innerBody;

        protected Builder() {
        }

        protected Builder(ResultModel result) {
            this.headers = result.headers;
            this.status = result.status;
            this.statusCode = result.statusCode;
            this.innerBody = result.innerBody;
        }

        @SuppressWarnings("unchecked")
        public BuilderT headers(Map<String, String> value) {
            requireNonNull(value);
            this.headers = value;
            return (BuilderT) this;
        }

        @SuppressWarnings("unchecked")
        public BuilderT status(String value) {
            requireNonNull(value);
            this.status = value;
            return (BuilderT) this;
        }

        @SuppressWarnings("unchecked")
        public BuilderT statusCode(int value) {
            this.statusCode = value;
            return (BuilderT) this;
        }

        @SuppressWarnings("unchecked")
        public BuilderT innerBody(Object value) {
            this.innerBody = value;
            return (BuilderT) this;
        }
    }

}
