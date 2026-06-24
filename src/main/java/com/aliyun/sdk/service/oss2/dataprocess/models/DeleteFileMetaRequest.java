package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

import static java.util.Objects.requireNonNull;

public final class DeleteFileMetaRequest extends RequestModel {
    private final String bucket;

    private DeleteFileMetaRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }

    public static Builder newBuilder() { return new Builder(); }

    public String bucket() { return bucket; }
    public String datasetName() { return parameters.get("datasetName"); }
    public String uri() { return parameters.get("uri"); }

    public Builder toBuilder() { return new Builder(this); }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;

        private Builder() { super(); }

        private Builder(DeleteFileMetaRequest request) {
            super(request);
            this.bucket = request.bucket;
        }

        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        public Builder datasetName(String value) {
            requireNonNull(value);
            this.parameters.put("datasetName", value);
            return this;
        }

        public Builder uri(String value) {
            requireNonNull(value);
            this.parameters.put("uri", value);
            return this;
        }

        public DeleteFileMetaRequest build() { return new DeleteFileMetaRequest(this); }
    }
}
