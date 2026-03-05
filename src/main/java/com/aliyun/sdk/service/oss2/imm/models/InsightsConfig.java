package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InsightsConfig {

    @JsonProperty("Language")
    private String language;

    public InsightsConfig() {
    }

    public String getLanguage() {
        return language;
    }

    public InsightsConfig setLanguage(String language) {
        this.language = language;
        return this;
    }
}
