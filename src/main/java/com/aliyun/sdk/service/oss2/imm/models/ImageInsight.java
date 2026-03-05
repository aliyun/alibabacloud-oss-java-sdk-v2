package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageInsight {

    @JsonProperty("Caption")
    private String caption;

    @JsonProperty("Description")
    private String description;

    public ImageInsight() {
    }

    public String getCaption() {
        return caption;
    }

    public ImageInsight setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ImageInsight setDescription(String description) {
        this.description = description;
        return this;
    }
}
