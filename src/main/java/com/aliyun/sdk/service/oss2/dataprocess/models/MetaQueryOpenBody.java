package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "MetaQuery")
public final class MetaQueryOpenBody {

    @JsonProperty("WorkflowParameters")
    @JacksonXmlProperty(localName = "WorkflowParameters")
    private WorkflowParameters workflowParameters;

    @JsonProperty("Filters")
    @JacksonXmlElementWrapper(localName = "Filters")
    @JacksonXmlProperty(localName = "Filter")
    private List<String> filters;

    @JsonProperty("NotificationAttributes")
    @JacksonXmlProperty(localName = "NotificationAttributes")
    private NotificationAttributes notificationAttributes;

    @JsonProperty("DatasetConfig")
    @JacksonXmlProperty(localName = "DatasetConfig")
    private DatasetConfig datasetConfig;

    @JsonProperty("IndexOptions")
    @JacksonXmlProperty(localName = "IndexOptions")
    private IndexOptions indexOptions;

    @JsonProperty("RouteRule")
    @JacksonXmlProperty(localName = "RouteRule")
    private RouteRule routeRule;

    public MetaQueryOpenBody() {}

    private MetaQueryOpenBody(Builder builder) {
        this.workflowParameters = builder.workflowParameters;
        this.filters = builder.filters;
        this.notificationAttributes = builder.notificationAttributes;
        this.datasetConfig = builder.datasetConfig;
        this.indexOptions = builder.indexOptions;
        this.routeRule = builder.routeRule;
    }

    public WorkflowParameters workflowParameters() { return this.workflowParameters; }
    public List<String> filters() { return this.filters; }
    public NotificationAttributes notificationAttributes() { return this.notificationAttributes; }
    public DatasetConfig datasetConfig() { return this.datasetConfig; }
    public IndexOptions indexOptions() { return this.indexOptions; }
    public RouteRule routeRule() { return this.routeRule; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private WorkflowParameters workflowParameters;
        private List<String> filters;
        private NotificationAttributes notificationAttributes;
        private DatasetConfig datasetConfig;
        private IndexOptions indexOptions;
        private RouteRule routeRule;

        public Builder workflowParameters(WorkflowParameters value) { this.workflowParameters = value; return this; }
        public Builder filters(List<String> value) { this.filters = value; return this; }
        public Builder notificationAttributes(NotificationAttributes value) { this.notificationAttributes = value; return this; }
        public Builder datasetConfig(DatasetConfig value) { this.datasetConfig = value; return this; }
        public Builder indexOptions(IndexOptions value) { this.indexOptions = value; return this; }
        public Builder routeRule(RouteRule value) { this.routeRule = value; return this; }

        private Builder() {}
        private Builder(MetaQueryOpenBody from) {
            this.workflowParameters = from.workflowParameters;
            this.filters = from.filters;
            this.notificationAttributes = from.notificationAttributes;
            this.datasetConfig = from.datasetConfig;
            this.indexOptions = from.indexOptions;
            this.routeRule = from.routeRule;
        }

        public MetaQueryOpenBody build() { return new MetaQueryOpenBody(this); }
    }
}
