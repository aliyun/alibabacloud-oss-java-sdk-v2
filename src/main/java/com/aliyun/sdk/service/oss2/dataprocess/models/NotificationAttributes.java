package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "NotificationAttributes")
public final class NotificationAttributes {

    @JsonProperty("Notifications")
    @JacksonXmlProperty(localName = "Notifications")
    private MetaQueryNotifications notifications;

    @JsonProperty("WithFields")
    @JacksonXmlElementWrapper(localName = "WithFields")
    @JacksonXmlProperty(localName = "WithField")
    private List<String> withFields;

    public NotificationAttributes() {}

    private NotificationAttributes(Builder builder) {
        this.notifications = builder.notifications;
        this.withFields = builder.withFields;
    }

    public MetaQueryNotifications notifications() { return this.notifications; }
    public List<String> withFields() { return this.withFields; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private MetaQueryNotifications notifications;
        private List<String> withFields;

        public Builder notifications(MetaQueryNotifications value) { this.notifications = value; return this; }
        public Builder withFields(List<String> value) { this.withFields = value; return this; }

        private Builder() {}
        private Builder(NotificationAttributes from) {
            this.notifications = from.notifications;
            this.withFields = from.withFields;
        }

        public NotificationAttributes build() { return new NotificationAttributes(this); }
    }
}
