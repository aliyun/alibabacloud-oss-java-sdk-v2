package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Image Label configuration within Insights.Image.
 * Contains System and UserDefined sub-configs.
 * Note: Highlight is intentionally not supported for image labels.
 */
public final class InsightsImageLabelConfig {

    @JsonProperty("System")
    @JacksonXmlProperty(localName = "System")
    private EnableConfig system;

    @JsonProperty("UserDefined")
    @JacksonXmlProperty(localName = "UserDefined")
    private InsightsLabelUserDefinedConfig userDefined;

    public InsightsImageLabelConfig() {}

    private InsightsImageLabelConfig(Builder builder) {
        this.system = builder.system;
        this.userDefined = builder.userDefined;
    }

    public EnableConfig system() { return this.system; }
    public InsightsLabelUserDefinedConfig userDefined() { return this.userDefined; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private EnableConfig system;
        private InsightsLabelUserDefinedConfig userDefined;

        public Builder system(EnableConfig value) { this.system = value; return this; }
        public Builder userDefined(InsightsLabelUserDefinedConfig value) { this.userDefined = value; return this; }

        private Builder() {}
        private Builder(InsightsImageLabelConfig from) {
            this.system = from.system;
            this.userDefined = from.userDefined;
        }

        public InsightsImageLabelConfig build() { return new InsightsImageLabelConfig(this); }
    }
}
