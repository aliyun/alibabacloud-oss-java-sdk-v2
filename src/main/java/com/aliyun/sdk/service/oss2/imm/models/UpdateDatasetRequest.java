package com.aliyun.sdk.service.oss2.imm.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The request for the UpdateDataset operation.
 */
public final class UpdateDatasetRequest extends RequestModel {
    private final String bucket;
    private final String datasetName;
    private final String description;
    private final String templateId;
    private final Long datasetMaxBindCount;
    private final Long datasetMaxFileCount;
    private final Long datasetMaxEntityCount;
    private final Long datasetMaxRelationCount;
    private final Long datasetMaxTotalFileSize;
    private final List<WorkflowParameter> workflowParameters;
    private final DatasetConfig datasetConfig;

    private UpdateDatasetRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.datasetName = builder.datasetName;
        this.description = builder.description;
        this.templateId = builder.templateId;
        this.datasetMaxBindCount = builder.datasetMaxBindCount;
        this.datasetMaxFileCount = builder.datasetMaxFileCount;
        this.datasetMaxEntityCount = builder.datasetMaxEntityCount;
        this.datasetMaxRelationCount = builder.datasetMaxRelationCount;
        this.datasetMaxTotalFileSize = builder.datasetMaxTotalFileSize;
        this.workflowParameters = builder.workflowParameters;
        this.datasetConfig = builder.datasetConfig;
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

    public String description() {
        return description;
    }

    public String templateId() {
        return templateId;
    }

    public Long datasetMaxBindCount() {
        return datasetMaxBindCount;
    }

    public Long datasetMaxFileCount() {
        return datasetMaxFileCount;
    }

    public Long datasetMaxEntityCount() {
        return datasetMaxEntityCount;
    }

    public Long datasetMaxRelationCount() {
        return datasetMaxRelationCount;
    }

    public Long datasetMaxTotalFileSize() {
        return datasetMaxTotalFileSize;
    }

    public List<WorkflowParameter> workflowParameters() {
        return workflowParameters;
    }

    public DatasetConfig datasetConfig() {
        return datasetConfig;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private String datasetName;
        private String description;
        private String templateId;
        private Long datasetMaxBindCount;
        private Long datasetMaxFileCount;
        private Long datasetMaxEntityCount;
        private Long datasetMaxRelationCount;
        private Long datasetMaxTotalFileSize;
        private List<WorkflowParameter> workflowParameters;
        private DatasetConfig datasetConfig;

        private Builder() {
            super();
        }

        private Builder(UpdateDatasetRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.datasetName = request.datasetName;
            this.description = request.description;
            this.templateId = request.templateId;
            this.datasetMaxBindCount = request.datasetMaxBindCount;
            this.datasetMaxFileCount = request.datasetMaxFileCount;
            this.datasetMaxEntityCount = request.datasetMaxEntityCount;
            this.datasetMaxRelationCount = request.datasetMaxRelationCount;
            this.datasetMaxTotalFileSize = request.datasetMaxTotalFileSize;
            this.workflowParameters = request.workflowParameters;
            this.datasetConfig = request.datasetConfig;
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

        public Builder description(String value) {
            this.description = value;
            return this;
        }

        public Builder templateId(String value) {
            this.templateId = value;
            return this;
        }

        public Builder datasetMaxBindCount(Long value) {
            this.datasetMaxBindCount = value;
            return this;
        }

        public Builder datasetMaxFileCount(Long value) {
            this.datasetMaxFileCount = value;
            return this;
        }

        public Builder datasetMaxEntityCount(Long value) {
            this.datasetMaxEntityCount = value;
            return this;
        }

        public Builder datasetMaxRelationCount(Long value) {
            this.datasetMaxRelationCount = value;
            return this;
        }

        public Builder datasetMaxTotalFileSize(Long value) {
            this.datasetMaxTotalFileSize = value;
            return this;
        }

        public Builder workflowParameters(List<WorkflowParameter> value) {
            this.workflowParameters = value;
            return this;
        }

        public Builder datasetConfig(DatasetConfig value) {
            this.datasetConfig = value;
            return this;
        }

        public UpdateDatasetRequest build() {
            return new UpdateDatasetRequest(this);
        }
    }
}
