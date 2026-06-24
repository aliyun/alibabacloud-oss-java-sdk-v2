package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Notification")
public final class MetaQueryNotification {

    @JsonProperty("MNS")
    @JacksonXmlProperty(localName = "MNS")
    private String mns;

    public MetaQueryNotification() {}

    private MetaQueryNotification(Builder builder) {
        this.mns = builder.mns;
    }

    public String mns() { return this.mns; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private String mns;

        public Builder mns(String value) { this.mns = value; return this; }

        private Builder() {}
        private Builder(MetaQueryNotification from) { this.mns = from.mns; }

        public MetaQueryNotification build() { return new MetaQueryNotification(this); }
    }
}
