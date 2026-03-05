package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ElementContent {

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Content")
    private String content;

    @JsonProperty("URL")
    private String url;

    @JsonProperty("TimeRange")
    private List<Long> timeRange;

    public ElementContent() {
    }

    public String getType() {
        return type;
    }

    public ElementContent setType(String type) {
        this.type = type;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ElementContent setContent(String content) {
        this.content = content;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ElementContent setUrl(String url) {
        this.url = url;
        return this;
    }

    public List<Long> getTimeRange() {
        return timeRange;
    }

    public ElementContent setTimeRange(List<Long> timeRange) {
        this.timeRange = timeRange;
        return this;
    }
}
