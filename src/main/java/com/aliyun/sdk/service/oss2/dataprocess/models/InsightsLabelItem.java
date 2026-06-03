package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Label item for InsightsConfig (DatasetConfig.Insights.Video.Label.UserDefined/Highlight).
 * Contains Name and Description.
 */
public final class InsightsLabelItem {

    @JsonProperty("Name")
    @JacksonXmlProperty(localName = "Name")
    private String name;

    @JsonProperty("Description")
    @JacksonXmlProperty(localName = "Description")
    private String description;

    public InsightsLabelItem() {}

    private InsightsLabelItem(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
    }

    public String name() { return this.name; }
    public String description() { return this.description; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private String name;
        private String description;

        public Builder name(String value) { this.name = value; return this; }
        public Builder description(String value) { this.description = value; return this; }

        private Builder() {}
        private Builder(InsightsLabelItem from) {
            this.name = from.name;
            this.description = from.description;
        }

        public InsightsLabelItem build() { return new InsightsLabelItem(this); }
    }
}
