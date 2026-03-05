package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Insights {

    @JsonProperty("Video")
    private VideoInsight video;

    @JsonProperty("Image")
    private ImageInsight image;

    public Insights() {
    }

    public VideoInsight getVideo() {
        return video;
    }

    public Insights setVideo(VideoInsight video) {
        this.video = video;
        return this;
    }

    public ImageInsight getImage() {
        return image;
    }

    public Insights setImage(ImageInsight image) {
        this.image = image;
        return this;
    }
}
