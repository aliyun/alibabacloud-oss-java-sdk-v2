package com.aliyun.sdk.service.oss2.imm.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

import static java.util.Objects.requireNonNull;

/**
 * The request for the DeleteDataset operation.
 */
public final class DeleteDatasetRequest extends RequestModel {
    private final String bucket;
    private final String datasetName;

    private DeleteDatasetRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.datasetName = builder.datasetName;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String bucket() {
        return bucket;
    }

    public String datasetName() {
        return datasetName;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private String datasetName;

        private Builder() {
            super();
        }

        private Builder(DeleteDatasetRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.datasetName = request.datasetName;
        }

        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        public Builder datasetName(String value) {
            requireNonNull(value);
            this.datasetName = value;
            return this;
        }

        public DeleteDatasetRequest build() {
            return new DeleteDatasetRequest(this);
        }
    }
}
