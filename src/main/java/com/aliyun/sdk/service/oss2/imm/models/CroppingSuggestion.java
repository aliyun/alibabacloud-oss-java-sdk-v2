package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CroppingSuggestion {

    @JsonProperty("AspectRatio")
    private String aspectRatio;

    @JsonProperty("Confidence")
    private Float confidence;

    @JsonProperty("Boundary")
    private Boundary boundary;

    public CroppingSuggestion() {
    }

    public String getAspectRatio() {
        return aspectRatio;
    }

    public CroppingSuggestion setAspectRatio(String aspectRatio) {
        this.aspectRatio = aspectRatio;
        return this;
    }

    public Float getConfidence() {
        return confidence;
    }

    public CroppingSuggestion setConfidence(Float confidence) {
        this.confidence = confidence;
        return this;
    }

    public Boundary getBoundary() {
        return boundary;
    }

    public CroppingSuggestion setBoundary(Boundary boundary) {
        this.boundary = boundary;
        return this;
    }
}
