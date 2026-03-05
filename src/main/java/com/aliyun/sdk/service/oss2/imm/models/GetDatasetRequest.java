package com.aliyun.sdk.service.oss2.imm.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

import static java.util.Objects.requireNonNull;

/**
 * The request for the GetDataset operation.
 */
public final class GetDatasetRequest extends RequestModel {
    private final String bucket;
    private final String datasetName;
    private final Boolean withStatistics;

    private GetDatasetRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.datasetName = builder.datasetName;
        this.withStatistics = builder.withStatistics;
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

    public Boolean withStatistics() {
        return withStatistics;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private String datasetName;
        private Boolean withStatistics;

        private Builder() {
            super();
        }

        private Builder(GetDatasetRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.datasetName = request.datasetName;
            this.withStatistics = request.withStatistics;
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

        public Builder withStatistics(Boolean value) {
            this.withStatistics = value;
            return this;
        }

        public GetDatasetRequest build() {
            return new GetDatasetRequest(this);
        }
    }
}
