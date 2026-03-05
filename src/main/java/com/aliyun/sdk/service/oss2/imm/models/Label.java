package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Label {

    @JsonProperty("Language")
    private String language;

    @JsonProperty("LabelName")
    private String labelName;

    @JsonProperty("LabelLevel")
    private Long labelLevel;

    @JsonProperty("LabelConfidence")
    private Float labelConfidence;

    @JsonProperty("ParentLabelName")
    private String parentLabelName;

    @JsonProperty("CentricScore")
    private Float centricScore;

    @JsonProperty("LabelAlias")
    private String labelAlias;

    @JsonProperty("Clips")
    private List<Clip> clips;

    public Label() {
    }

    public String getLanguage() {
        return language;
    }

    public Label setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getLabelName() {
        return labelName;
    }

    public Label setLabelName(String labelName) {
        this.labelName = labelName;
        return this;
    }

    public Long getLabelLevel() {
        return labelLevel;
    }

    public Label setLabelLevel(Long labelLevel) {
        this.labelLevel = labelLevel;
        return this;
    }

    public Float getLabelConfidence() {
        return labelConfidence;
    }

    public Label setLabelConfidence(Float labelConfidence) {
        this.labelConfidence = labelConfidence;
        return this;
    }

    public String getParentLabelName() {
        return parentLabelName;
    }

    public Label setParentLabelName(String parentLabelName) {
        this.parentLabelName = parentLabelName;
        return this;
    }

    public Float getCentricScore() {
        return centricScore;
    }

    public Label setCentricScore(Float centricScore) {
        this.centricScore = centricScore;
        return this;
    }

    public String getLabelAlias() {
        return labelAlias;
    }

    public Label setLabelAlias(String labelAlias) {
        this.labelAlias = labelAlias;
        return this;
    }

    public List<Clip> getClips() {
        return clips;
    }

    public Label setClips(List<Clip> clips) {
        this.clips = clips;
        return this;
    }
}
