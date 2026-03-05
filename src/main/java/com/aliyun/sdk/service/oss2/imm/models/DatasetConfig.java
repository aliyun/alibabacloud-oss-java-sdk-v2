package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatasetConfig {

    @JsonProperty("Insights")
    private InsightsConfig insights;

    public DatasetConfig() {
    }

    public InsightsConfig getInsights() {
        return insights;
    }

    public DatasetConfig setInsights(InsightsConfig insights) {
        this.insights = insights;
        return this;
    }
}
