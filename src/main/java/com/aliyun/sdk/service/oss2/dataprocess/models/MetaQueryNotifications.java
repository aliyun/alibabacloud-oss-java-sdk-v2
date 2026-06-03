package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "Notifications")
public final class MetaQueryNotifications {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Notification")
    private List<MetaQueryNotification> notifications;

    public MetaQueryNotifications() {}

    private MetaQueryNotifications(Builder builder) {
        this.notifications = builder.notifications;
    }

    public List<MetaQueryNotification> notifications() { return this.notifications; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private List<MetaQueryNotification> notifications;

        public Builder notifications(List<MetaQueryNotification> value) { this.notifications = value; return this; }

        private Builder() {}
        private Builder(MetaQueryNotifications from) { this.notifications = from.notifications; }

        public MetaQueryNotifications build() { return new MetaQueryNotifications(this); }
    }
}
