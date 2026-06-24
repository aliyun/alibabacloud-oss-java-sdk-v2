package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "SmartCluster")
public final class SmartClusterInfo {

    @JacksonXmlProperty(localName = "ObjectId")
    private String objectId;

    @JacksonXmlProperty(localName = "ClusterType")
    private String clusterType;

    @JacksonXmlProperty(localName = "Name")
    private String name;

    @JacksonXmlProperty(localName = "Description")
    private String description;

    @JacksonXmlElementWrapper(localName = "Rules")
    @JacksonXmlProperty(localName = "Rule")
    private List<SmartClusterRule> rules;

    @JacksonXmlProperty(localName = "Reason")
    private String reason;

    @JacksonXmlProperty(localName = "Notification")
    private SmartClusterNotificationInfo notification;

    @JacksonXmlProperty(localName = "CreateTime")
    private String createTime;

    @JacksonXmlProperty(localName = "UpdateTime")
    private String updateTime;

    public SmartClusterInfo() {}

    private SmartClusterInfo(Builder builder) {
        this.objectId = builder.objectId;
        this.clusterType = builder.clusterType;
        this.name = builder.name;
        this.description = builder.description;
        this.rules = builder.rules;
        this.reason = builder.reason;
        this.notification = builder.notification;
        this.createTime = builder.createTime;
        this.updateTime = builder.updateTime;
    }

    public String objectId() { return this.objectId; }
    public String clusterType() { return this.clusterType; }
    public String name() { return this.name; }
    public String description() { return this.description; }
    public List<SmartClusterRule> rules() { return this.rules; }
    public String reason() { return this.reason; }
    public SmartClusterNotificationInfo notification() { return this.notification; }
    public String createTime() { return this.createTime; }
    public String updateTime() { return this.updateTime; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private String objectId;
        private String clusterType;
        private String name;
        private String description;
        private List<SmartClusterRule> rules;
        private String reason;
        private SmartClusterNotificationInfo notification;
        private String createTime;
        private String updateTime;

        public Builder objectId(String value) { this.objectId = value; return this; }
        public Builder clusterType(String value) { this.clusterType = value; return this; }
        public Builder name(String value) { this.name = value; return this; }
        public Builder description(String value) { this.description = value; return this; }
        public Builder rules(List<SmartClusterRule> value) { this.rules = value; return this; }
        public Builder reason(String value) { this.reason = value; return this; }
        public Builder notification(SmartClusterNotificationInfo value) { this.notification = value; return this; }
        public Builder createTime(String value) { this.createTime = value; return this; }
        public Builder updateTime(String value) { this.updateTime = value; return this; }

        private Builder() {}
        private Builder(SmartClusterInfo from) {
            this.objectId = from.objectId;
            this.clusterType = from.clusterType;
            this.name = from.name;
            this.description = from.description;
            this.rules = from.rules;
            this.reason = from.reason;
            this.notification = from.notification;
            this.createTime = from.createTime;
            this.updateTime = from.updateTime;
        }

        public SmartClusterInfo build() { return new SmartClusterInfo(this); }
    }
}
