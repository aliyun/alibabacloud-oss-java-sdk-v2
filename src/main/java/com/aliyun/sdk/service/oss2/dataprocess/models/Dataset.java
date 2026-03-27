package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "Dataset")
public final class Dataset {

    @JacksonXmlProperty(localName = "BindCount")
    private Long bindCount;

    @JacksonXmlProperty(localName = "CreateTime")
    private String createTime;

    @JacksonXmlProperty(localName = "DatasetMaxBindCount")
    private Long datasetMaxBindCount;

    @JacksonXmlProperty(localName = "DatasetMaxEntityCount")
    private Long datasetMaxEntityCount;

    @JacksonXmlProperty(localName = "DatasetMaxFileCount")
    private Long datasetMaxFileCount;

    @JacksonXmlProperty(localName = "DatasetMaxRelationCount")
    private Long datasetMaxRelationCount;

    @JacksonXmlProperty(localName = "DatasetMaxTotalFileSize")
    private Long datasetMaxTotalFileSize;

    @JacksonXmlProperty(localName = "DatasetName")
    private String datasetName;

    @JacksonXmlProperty(localName = "Description")
    private String description;

    @JacksonXmlProperty(localName = "FileCount")
    private Long fileCount;

    @JacksonXmlProperty(localName = "TemplateId")
    private String templateId;

    @JacksonXmlProperty(localName = "TotalFileSize")
    private Long totalFileSize;

    @JacksonXmlProperty(localName = "UpdateTime")
    private String updateTime;

    @JacksonXmlProperty(localName = "WorkflowParameters")
    private WorkflowParameters workflowParameters;

    @JacksonXmlProperty(localName = "DatasetConfig")
    private DatasetConfig datasetConfig;

    public Dataset() {
    }

    private Dataset(Builder builder) {
        this.bindCount = builder.bindCount;
        this.createTime = builder.createTime;
        this.datasetMaxBindCount = builder.datasetMaxBindCount;
        this.datasetMaxEntityCount = builder.datasetMaxEntityCount;
        this.datasetMaxFileCount = builder.datasetMaxFileCount;
        this.datasetMaxRelationCount = builder.datasetMaxRelationCount;
        this.datasetMaxTotalFileSize = builder.datasetMaxTotalFileSize;
        this.datasetName = builder.datasetName;
        this.description = builder.description;
        this.fileCount = builder.fileCount;
        this.templateId = builder.templateId;
        this.totalFileSize = builder.totalFileSize;
        this.updateTime = builder.updateTime;
        this.workflowParameters = builder.workflowParameters;
        this.datasetConfig = builder.datasetConfig;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public Long bindCount() {
        return this.bindCount;
    }

    public String createTime() {
        return this.createTime;
    }

    public Long datasetMaxBindCount() {
        return this.datasetMaxBindCount;
    }

    public Long datasetMaxEntityCount() {
        return this.datasetMaxEntityCount;
    }

    public Long datasetMaxFileCount() {
        return this.datasetMaxFileCount;
    }

    public Long datasetMaxRelationCount() {
        return this.datasetMaxRelationCount;
    }

    public Long datasetMaxTotalFileSize() {
        return this.datasetMaxTotalFileSize;
    }

    public String datasetName() {
        return this.datasetName;
    }

    public String description() {
        return this.description;
    }

    public Long fileCount() {
        return this.fileCount;
    }

    public String templateId() {
        return this.templateId;
    }

    public Long totalFileSize() {
        return this.totalFileSize;
    }

    public String updateTime() {
        return this.updateTime;
    }

    public WorkflowParameters workflowParameters() {
        return this.workflowParameters;
    }

    public DatasetConfig datasetConfig() {
        return this.datasetConfig;
    }

    public static class Builder {
        private Long bindCount;
        private String createTime;
        private Long datasetMaxBindCount;
        private Long datasetMaxEntityCount;
        private Long datasetMaxFileCount;
        private Long datasetMaxRelationCount;
        private Long datasetMaxTotalFileSize;
        private String datasetName;
        private String description;
        private Long fileCount;
        private String templateId;
        private Long totalFileSize;
        private String updateTime;
        private WorkflowParameters workflowParameters;
        private DatasetConfig datasetConfig;

        private Builder() {
        }

        private Builder(Dataset dataset) {
            this.bindCount = dataset.bindCount;
            this.createTime = dataset.createTime;
            this.datasetMaxBindCount = dataset.datasetMaxBindCount;
            this.datasetMaxEntityCount = dataset.datasetMaxEntityCount;
            this.datasetMaxFileCount = dataset.datasetMaxFileCount;
            this.datasetMaxRelationCount = dataset.datasetMaxRelationCount;
            this.datasetMaxTotalFileSize = dataset.datasetMaxTotalFileSize;
            this.datasetName = dataset.datasetName;
            this.description = dataset.description;
            this.fileCount = dataset.fileCount;
            this.templateId = dataset.templateId;
            this.totalFileSize = dataset.totalFileSize;
            this.updateTime = dataset.updateTime;
            this.workflowParameters = dataset.workflowParameters;
            this.datasetConfig = dataset.datasetConfig;
        }

        public Builder bindCount(Long bindCount) {
            this.bindCount = bindCount;
            return this;
        }

        public Builder createTime(String createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder datasetMaxBindCount(Long datasetMaxBindCount) {
            this.datasetMaxBindCount = datasetMaxBindCount;
            return this;
        }

        public Builder datasetMaxEntityCount(Long datasetMaxEntityCount) {
            this.datasetMaxEntityCount = datasetMaxEntityCount;
            return this;
        }

        public Builder datasetMaxFileCount(Long datasetMaxFileCount) {
            this.datasetMaxFileCount = datasetMaxFileCount;
            return this;
        }

        public Builder datasetMaxRelationCount(Long datasetMaxRelationCount) {
            this.datasetMaxRelationCount = datasetMaxRelationCount;
            return this;
        }

        public Builder datasetMaxTotalFileSize(Long datasetMaxTotalFileSize) {
            this.datasetMaxTotalFileSize = datasetMaxTotalFileSize;
            return this;
        }

        public Builder datasetName(String datasetName) {
            this.datasetName = datasetName;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder fileCount(Long fileCount) {
            this.fileCount = fileCount;
            return this;
        }

        public Builder templateId(String templateId) {
            this.templateId = templateId;
            return this;
        }

        public Builder totalFileSize(Long totalFileSize) {
            this.totalFileSize = totalFileSize;
            return this;
        }

        public Builder updateTime(String updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public Builder workflowParameters(WorkflowParameters workflowParameters) {
            this.workflowParameters = workflowParameters;
            return this;
        }

        public Builder datasetConfig(DatasetConfig datasetConfig) {
            this.datasetConfig = datasetConfig;
            return this;
        }

        public Dataset build() {
            return new Dataset(this);
        }
    }
}
