package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OCRContents {

    @JsonProperty("Language")
    private String language;

    @JsonProperty("Contents")
    private String contents;

    @JsonProperty("Confidence")
    private Float confidence;

    @JsonProperty("Boundary")
    private Boundary boundary;

    public OCRContents() {
    }

    public String getLanguage() {
        return language;
    }

    public OCRContents setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getContents() {
        return contents;
    }

    public OCRContents setContents(String contents) {
        this.contents = contents;
        return this;
    }

    public Float getConfidence() {
        return confidence;
    }

    public OCRContents setConfidence(Float confidence) {
        this.confidence = confidence;
        return this;
    }

    public Boundary getBoundary() {
        return boundary;
    }

    public OCRContents setBoundary(Boundary boundary) {
        this.boundary = boundary;
        return this;
    }
}
