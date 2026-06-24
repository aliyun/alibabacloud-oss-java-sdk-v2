package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Notification")
public final class SmartClusterNotificationInfo {

    @JsonProperty("MNS")
    @JacksonXmlProperty(localName = "MNS")
    private SmartClusterMNS mns;

    public SmartClusterNotificationInfo() {}

    private SmartClusterNotificationInfo(Builder builder) {
        this.mns = builder.mns;
    }

    public SmartClusterMNS mns() { return this.mns; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private SmartClusterMNS mns;

        public Builder mns(SmartClusterMNS value) { this.mns = value; return this; }

        private Builder() {}
        private Builder(SmartClusterNotificationInfo from) { this.mns = from.mns; }

        public SmartClusterNotificationInfo build() { return new SmartClusterNotificationInfo(this); }
    }

    @JacksonXmlRootElement(localName = "MNS")
    public static final class SmartClusterMNS {

        @JsonProperty("TopicName")
        @JacksonXmlProperty(localName = "TopicName")
        private String topicName;

        public SmartClusterMNS() {}

        private SmartClusterMNS(Builder builder) { this.topicName = builder.topicName; }

        public String topicName() { return this.topicName; }

        public static Builder newBuilder() { return new Builder(); }
        public Builder toBuilder() { return new Builder(this); }

        public static class Builder {
            private String topicName;
            public Builder topicName(String value) { this.topicName = value; return this; }
            private Builder() {}
            private Builder(SmartClusterMNS from) { this.topicName = from.topicName; }
            public SmartClusterMNS build() { return new SmartClusterMNS(this); }
        }
    }
}
