package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoInsight {

    @JsonProperty("Caption")
    private String caption;

    @JsonProperty("Description")
    private String description;

    public VideoInsight() {
    }

    public String getCaption() {
        return caption;
    }

    public VideoInsight setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public VideoInsight setDescription(String description) {
        this.description = description;
        return this;
    }
}
