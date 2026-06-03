package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

import static java.util.Objects.requireNonNull;

public final class CloseMetaQueryRequest extends RequestModel {
    private final String bucket;

    private CloseMetaQueryRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }

    public static Builder newBuilder() { return new Builder(); }

    public String bucket() { return bucket; }

    public Builder toBuilder() { return new Builder(this); }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;

        private Builder() { super(); }

        private Builder(CloseMetaQueryRequest request) {
            super(request);
            this.bucket = request.bucket;
        }

        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        public CloseMetaQueryRequest build() { return new CloseMetaQueryRequest(this); }
    }
}
