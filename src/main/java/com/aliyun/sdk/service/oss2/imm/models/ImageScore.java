package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageScore {

    @JsonProperty("OverallQualityScore")
    private Float overallQualityScore;

    public ImageScore() {
    }

    public Float getOverallQualityScore() {
        return overallQualityScore;
    }

    public ImageScore setOverallQualityScore(Float overallQualityScore) {
        this.overallQualityScore = overallQualityScore;
        return this;
    }
}
