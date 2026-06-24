package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

import static java.util.Objects.requireNonNull;

public final class DeleteSmartClusterRequest extends RequestModel {
    private final String bucket;

    private DeleteSmartClusterRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }

    public static Builder newBuilder() { return new Builder(); }

    public String bucket() { return bucket; }
    public String datasetName() { return parameters.get("datasetName"); }
    public String objectId() { return parameters.get("objectId"); }

    public Builder toBuilder() { return new Builder(this); }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;

        private Builder() { super(); }

        private Builder(DeleteSmartClusterRequest request) {
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

        public Builder objectId(String value) {
            requireNonNull(value);
            this.parameters.put("objectId", value);
            return this;
        }

        public DeleteSmartClusterRequest build() { return new DeleteSmartClusterRequest(this); }
    }
}
