package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "MetaQueryStatus")
public final class MetaQueryStatus {

    @JacksonXmlProperty(localName = "State")
    private String state;

    @JacksonXmlProperty(localName = "Phase")
    private String phase;

    @JacksonXmlProperty(localName = "CreateTime")
    private String createTime;

    @JacksonXmlProperty(localName = "UpdateTime")
    private String updateTime;

    @JacksonXmlProperty(localName = "MetaQueryMode")
    private String metaQueryMode;

    @JacksonXmlProperty(localName = "WorkflowParameters")
    private WorkflowParameters workflowParameters;

    @JacksonXmlProperty(localName = "IndexOptions")
    private IndexOptions indexOptions;

    @JacksonXmlProperty(localName = "RouteRule")
    private RouteRule routeRule;

    @JacksonXmlProperty(localName = "NotificationAttributes")
    private NotificationAttributes notificationAttributes;

    @JacksonXmlProperty(localName = "DatasetConfig")
    private DatasetConfig datasetConfig;

    @JacksonXmlElementWrapper(localName = "Filters")
    @JacksonXmlProperty(localName = "Filter")
    private List<String> filters;

    public MetaQueryStatus() {}

    private MetaQueryStatus(Builder builder) {
        this.state = builder.state;
        this.phase = builder.phase;
        this.createTime = builder.createTime;
        this.updateTime = builder.updateTime;
        this.metaQueryMode = builder.metaQueryMode;
        this.workflowParameters = builder.workflowParameters;
        this.indexOptions = builder.indexOptions;
        this.routeRule = builder.routeRule;
        this.notificationAttributes = builder.notificationAttributes;
        this.datasetConfig = builder.datasetConfig;
        this.filters = builder.filters;
    }

    public String state() { return this.state; }
    public String phase() { return this.phase; }
    public String createTime() { return this.createTime; }
    public String updateTime() { return this.updateTime; }
    public String metaQueryMode() { return this.metaQueryMode; }
    public WorkflowParameters workflowParameters() { return this.workflowParameters; }
    public IndexOptions indexOptions() { return this.indexOptions; }
    public RouteRule routeRule() { return this.routeRule; }
    public NotificationAttributes notificationAttributes() { return this.notificationAttributes; }
    public DatasetConfig datasetConfig() { return this.datasetConfig; }
    public List<String> filters() { return this.filters; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private String state;
        private String phase;
        private String createTime;
        private String updateTime;
        private String metaQueryMode;
        private WorkflowParameters workflowParameters;
        private IndexOptions indexOptions;
        private RouteRule routeRule;
        private NotificationAttributes notificationAttributes;
        private DatasetConfig datasetConfig;
        private List<String> filters;

        public Builder state(String value) { this.state = value; return this; }
        public Builder phase(String value) { this.phase = value; return this; }
        public Builder createTime(String value) { this.createTime = value; return this; }
        public Builder updateTime(String value) { this.updateTime = value; return this; }
        public Builder metaQueryMode(String value) { this.metaQueryMode = value; return this; }
        public Builder workflowParameters(WorkflowParameters value) { this.workflowParameters = value; return this; }
        public Builder indexOptions(IndexOptions value) { this.indexOptions = value; return this; }
        public Builder routeRule(RouteRule value) { this.routeRule = value; return this; }
        public Builder notificationAttributes(NotificationAttributes value) { this.notificationAttributes = value; return this; }
        public Builder datasetConfig(DatasetConfig value) { this.datasetConfig = value; return this; }
        public Builder filters(List<String> value) { this.filters = value; return this; }

        private Builder() {}
        private Builder(MetaQueryStatus from) {
            this.state = from.state;
            this.phase = from.phase;
            this.createTime = from.createTime;
            this.updateTime = from.updateTime;
            this.metaQueryMode = from.metaQueryMode;
            this.workflowParameters = from.workflowParameters;
            this.indexOptions = from.indexOptions;
            this.routeRule = from.routeRule;
            this.notificationAttributes = from.notificationAttributes;
            this.datasetConfig = from.datasetConfig;
            this.filters = from.filters;
        }

        public MetaQueryStatus build() { return new MetaQueryStatus(this); }
    }
}
