package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "Label")
public final class Label {

    @JacksonXmlProperty(localName = "Language")
    private String language;

    @JacksonXmlProperty(localName = "LabelName")
    private String labelName;

    @JacksonXmlProperty(localName = "LabelLevel")
    private Long labelLevel;

    @JacksonXmlProperty(localName = "LabelConfidence")
    private Float labelConfidence;

    @JacksonXmlProperty(localName = "ParentLabelName")
    private String parentLabelName;

    @JacksonXmlProperty(localName = "CentricScore")
    private Float centricScore;

    @JacksonXmlProperty(localName = "LabelAlias")
    private String labelAlias;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Clip")
    private List<Clip> clips;

    public Label() {}

    private Label(Builder builder) {
        this.language = builder.language;
        this.labelName = builder.labelName;
        this.labelLevel = builder.labelLevel;
        this.labelConfidence = builder.labelConfidence;
        this.parentLabelName = builder.parentLabelName;
        this.centricScore = builder.centricScore;
        this.labelAlias = builder.labelAlias;
        this.clips = builder.clips;
    }

    public String language() { return this.language; }
    public String labelName() { return this.labelName; }
    public Long labelLevel() { return this.labelLevel; }
    public Float labelConfidence() { return this.labelConfidence; }
    public String parentLabelName() { return this.parentLabelName; }
    public Float centricScore() { return this.centricScore; }
    public String labelAlias() { return this.labelAlias; }
    public List<Clip> clips() { return this.clips; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private String language;
        private String labelName;
        private Long labelLevel;
        private Float labelConfidence;
        private String parentLabelName;
        private Float centricScore;
        private String labelAlias;
        private List<Clip> clips;

        public Builder language(String value) { this.language = value; return this; }
        public Builder labelName(String value) { this.labelName = value; return this; }
        public Builder labelLevel(Long value) { this.labelLevel = value; return this; }
        public Builder labelConfidence(Float value) { this.labelConfidence = value; return this; }
        public Builder parentLabelName(String value) { this.parentLabelName = value; return this; }
        public Builder centricScore(Float value) { this.centricScore = value; return this; }
        public Builder labelAlias(String value) { this.labelAlias = value; return this; }
        public Builder clips(List<Clip> value) { this.clips = value; return this; }

        private Builder() { super(); }

        private Builder(Label from) {
            this.language = from.language;
            this.labelName = from.labelName;
            this.labelLevel = from.labelLevel;
            this.labelConfidence = from.labelConfidence;
            this.parentLabelName = from.parentLabelName;
            this.centricScore = from.centricScore;
            this.labelAlias = from.labelAlias;
            this.clips = from.clips;
        }

        public Label build() { return new Label(this); }
    }
}
