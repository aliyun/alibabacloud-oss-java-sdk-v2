package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Video Label configuration within InsightsConfig.
 * Contains System, UserDefined, and Highlight sub-configs.
 */
public final class InsightsVideoLabelConfig {

    @JsonProperty("System")
    @JacksonXmlProperty(localName = "System")
    private EnableConfig system;

    @JsonProperty("UserDefined")
    @JacksonXmlProperty(localName = "UserDefined")
    private InsightsLabelUserDefinedConfig userDefined;

    @JsonProperty("Highlight")
    @JacksonXmlProperty(localName = "Highlight")
    private InsightsLabelHighlightConfig highlight;

    public InsightsVideoLabelConfig() {}

    private InsightsVideoLabelConfig(Builder builder) {
        this.system = builder.system;
        this.userDefined = builder.userDefined;
        this.highlight = builder.highlight;
    }

    public EnableConfig system() { return this.system; }
    public InsightsLabelUserDefinedConfig userDefined() { return this.userDefined; }
    public InsightsLabelHighlightConfig highlight() { return this.highlight; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private EnableConfig system;
        private InsightsLabelUserDefinedConfig userDefined;
        private InsightsLabelHighlightConfig highlight;

        public Builder system(EnableConfig value) { this.system = value; return this; }
        public Builder userDefined(InsightsLabelUserDefinedConfig value) { this.userDefined = value; return this; }
        public Builder highlight(InsightsLabelHighlightConfig value) { this.highlight = value; return this; }

        private Builder() {}
        private Builder(InsightsVideoLabelConfig from) {
            this.system = from.system;
            this.userDefined = from.userDefined;
            this.highlight = from.highlight;
        }

        public InsightsVideoLabelConfig build() { return new InsightsVideoLabelConfig(this); }
    }
}
