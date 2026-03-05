package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Dataset {

    @JsonProperty("BindCount")
    private Long bindCount;

    @JsonProperty("CreateTime")
    private String createTime;

    @JsonProperty("DatasetMaxBindCount")
    private Long datasetMaxBindCount;

    @JsonProperty("DatasetMaxEntityCount")
    private Long datasetMaxEntityCount;

    @JsonProperty("DatasetMaxFileCount")
    private Long datasetMaxFileCount;

    @JsonProperty("DatasetMaxRelationCount")
    private Long datasetMaxRelationCount;

    @JsonProperty("DatasetMaxTotalFileSize")
    private Long datasetMaxTotalFileSize;

    @JsonProperty("DatasetName")
    private String datasetName;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("FileCount")
    private Long fileCount;

    @JsonProperty("TemplateId")
    private String templateId;

    @JsonProperty("TotalFileSize")
    private Long totalFileSize;

    @JsonProperty("UpdateTime")
    private String updateTime;

    @JsonProperty("WorkflowParameters")
    private List<WorkflowParameter> workflowParameters;

    @JsonProperty("DatasetConfig")
    private DatasetConfig datasetConfig;

    public Dataset() {
    }

    public Long getBindCount() {
        return bindCount;
    }

    public Dataset setBindCount(Long bindCount) {
        this.bindCount = bindCount;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Dataset setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public Long getDatasetMaxBindCount() {
        return datasetMaxBindCount;
    }

    public Dataset setDatasetMaxBindCount(Long datasetMaxBindCount) {
        this.datasetMaxBindCount = datasetMaxBindCount;
        return this;
    }

    public Long getDatasetMaxEntityCount() {
        return datasetMaxEntityCount;
    }

    public Dataset setDatasetMaxEntityCount(Long datasetMaxEntityCount) {
        this.datasetMaxEntityCount = datasetMaxEntityCount;
        return this;
    }

    public Long getDatasetMaxFileCount() {
        return datasetMaxFileCount;
    }

    public Dataset setDatasetMaxFileCount(Long datasetMaxFileCount) {
        this.datasetMaxFileCount = datasetMaxFileCount;
        return this;
    }

    public Long getDatasetMaxRelationCount() {
        return datasetMaxRelationCount;
    }

    public Dataset setDatasetMaxRelationCount(Long datasetMaxRelationCount) {
        this.datasetMaxRelationCount = datasetMaxRelationCount;
        return this;
    }

    public Long getDatasetMaxTotalFileSize() {
        return datasetMaxTotalFileSize;
    }

    public Dataset setDatasetMaxTotalFileSize(Long datasetMaxTotalFileSize) {
        this.datasetMaxTotalFileSize = datasetMaxTotalFileSize;
        return this;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public Dataset setDatasetName(String datasetName) {
        this.datasetName = datasetName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Dataset setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getFileCount() {
        return fileCount;
    }

    public Dataset setFileCount(Long fileCount) {
        this.fileCount = fileCount;
        return this;
    }

    public String getTemplateId() {
        return templateId;
    }

    public Dataset setTemplateId(String templateId) {
        this.templateId = templateId;
        return this;
    }

    public Long getTotalFileSize() {
        return totalFileSize;
    }

    public Dataset setTotalFileSize(Long totalFileSize) {
        this.totalFileSize = totalFileSize;
        return this;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public Dataset setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public List<WorkflowParameter> getWorkflowParameters() {
        return workflowParameters;
    }

    public Dataset setWorkflowParameters(List<WorkflowParameter> workflowParameters) {
        this.workflowParameters = workflowParameters;
        return this;
    }

    public DatasetConfig getDatasetConfig() {
        return datasetConfig;
    }

    public Dataset setDatasetConfig(DatasetConfig datasetConfig) {
        this.datasetConfig = datasetConfig;
        return this;
    }
}
