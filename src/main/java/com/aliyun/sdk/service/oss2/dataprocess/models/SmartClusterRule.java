package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * SmartCluster rule model.
 *
 * <p>Note: the {@code Files} field exists in the {@code gomodels.SmartClusterRule} vendor copy but
 * is NOT defined in the IMM POP cspec ({@code open_struct_SmartClusterRule}). The backend does not
 * parse or return it, so it has been removed from this SDK model per the latest interface
 * reference document.</p>
 */
@JacksonXmlRootElement(localName = "Rule")
public final class SmartClusterRule {

    @JsonProperty("RuleType")
    @JacksonXmlProperty(localName = "RuleType")
    private String ruleType;

    @JsonProperty("BaseURIs")
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "BaseURIs")
    private List<String> baseURIs;

    @JsonProperty("Keywords")
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Keywords")
    private List<String> keywords;

    @JsonProperty("Sensitivity")
    @JacksonXmlProperty(localName = "Sensitivity")
    private Float sensitivity;

    public SmartClusterRule() {}

    private SmartClusterRule(Builder builder) {
        this.ruleType = builder.ruleType;
        this.baseURIs = builder.baseURIs;
        this.keywords = builder.keywords;
        this.sensitivity = builder.sensitivity;
    }

    public String ruleType() { return this.ruleType; }
    public List<String> baseURIs() { return this.baseURIs; }
    public List<String> keywords() { return this.keywords; }
    public Float sensitivity() { return this.sensitivity; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private String ruleType;
        private List<String> baseURIs;
        private List<String> keywords;
        private Float sensitivity;

        public Builder ruleType(String value) { this.ruleType = value; return this; }
        public Builder baseURIs(List<String> value) { this.baseURIs = value; return this; }
        public Builder keywords(List<String> value) { this.keywords = value; return this; }
        public Builder sensitivity(Float value) { this.sensitivity = value; return this; }

        private Builder() {}
        private Builder(SmartClusterRule from) {
            this.ruleType = from.ruleType;
            this.baseURIs = from.baseURIs;
            this.keywords = from.keywords;
            this.sensitivity = from.sensitivity;
        }

        public SmartClusterRule build() { return new SmartClusterRule(this); }
    }
}
