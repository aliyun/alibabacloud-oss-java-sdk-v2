package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Figure")
public final class Figure {

    @JacksonXmlProperty(localName = "FigureId")
    private String figureId;

    @JacksonXmlProperty(localName = "FigureConfidence")
    private Float figureConfidence;

    @JacksonXmlProperty(localName = "FigureClusterId")
    private String figureClusterId;

    @JacksonXmlProperty(localName = "FigureClusterConfidence")
    private Float figureClusterConfidence;

    @JacksonXmlProperty(localName = "FigureType")
    private String figureType;

    @JacksonXmlProperty(localName = "Age")
    private Long age;

    @JacksonXmlProperty(localName = "AgeSD")
    private Float ageSD;

    @JacksonXmlProperty(localName = "Gender")
    private String gender;

    @JacksonXmlProperty(localName = "GenderConfidence")
    private Float genderConfidence;

    @JacksonXmlProperty(localName = "Emotion")
    private String emotion;

    @JacksonXmlProperty(localName = "EmotionConfidence")
    private Float emotionConfidence;

    @JacksonXmlProperty(localName = "FaceQuality")
    private Float faceQuality;

    @JacksonXmlProperty(localName = "Boundary")
    private Boundary boundary;

    @JacksonXmlProperty(localName = "Mouth")
    private String mouth;

    @JacksonXmlProperty(localName = "MouthConfidence")
    private Float mouthConfidence;

    @JacksonXmlProperty(localName = "Beard")
    private String beard;

    @JacksonXmlProperty(localName = "BeardConfidence")
    private Float beardConfidence;

    @JacksonXmlProperty(localName = "Hat")
    private String hat;

    @JacksonXmlProperty(localName = "HatConfidence")
    private Float hatConfidence;

    @JacksonXmlProperty(localName = "Mask")
    private String mask;

    @JacksonXmlProperty(localName = "MaskConfidence")
    private Float maskConfidence;

    @JacksonXmlProperty(localName = "Glasses")
    private String glasses;

    @JacksonXmlProperty(localName = "GlassesConfidence")
    private Float glassesConfidence;

    @JacksonXmlProperty(localName = "Sharpness")
    private Float sharpness;

    @JacksonXmlProperty(localName = "Attractive")
    private Float attractive;

    @JacksonXmlProperty(localName = "HeadPose")
    private HeadPose headPose;

    public Figure() {}

    private Figure(Builder builder) {
        this.figureId = builder.figureId;
        this.figureConfidence = builder.figureConfidence;
        this.figureClusterId = builder.figureClusterId;
        this.figureClusterConfidence = builder.figureClusterConfidence;
        this.figureType = builder.figureType;
        this.age = builder.age;
        this.ageSD = builder.ageSD;
        this.gender = builder.gender;
        this.genderConfidence = builder.genderConfidence;
        this.emotion = builder.emotion;
        this.emotionConfidence = builder.emotionConfidence;
        this.faceQuality = builder.faceQuality;
        this.boundary = builder.boundary;
        this.mouth = builder.mouth;
        this.mouthConfidence = builder.mouthConfidence;
        this.beard = builder.beard;
        this.beardConfidence = builder.beardConfidence;
        this.hat = builder.hat;
        this.hatConfidence = builder.hatConfidence;
        this.mask = builder.mask;
        this.maskConfidence = builder.maskConfidence;
        this.glasses = builder.glasses;
        this.glassesConfidence = builder.glassesConfidence;
        this.sharpness = builder.sharpness;
        this.attractive = builder.attractive;
        this.headPose = builder.headPose;
    }

    public String figureId() { return this.figureId; }
    public Float figureConfidence() { return this.figureConfidence; }
    public String figureClusterId() { return this.figureClusterId; }
    public Float figureClusterConfidence() { return this.figureClusterConfidence; }
    public String figureType() { return this.figureType; }
    public Long age() { return this.age; }
    public Float ageSD() { return this.ageSD; }
    public String gender() { return this.gender; }
    public Float genderConfidence() { return this.genderConfidence; }
    public String emotion() { return this.emotion; }
    public Float emotionConfidence() { return this.emotionConfidence; }
    public Float faceQuality() { return this.faceQuality; }
    public Boundary boundary() { return this.boundary; }
    public String mouth() { return this.mouth; }
    public Float mouthConfidence() { return this.mouthConfidence; }
    public String beard() { return this.beard; }
    public Float beardConfidence() { return this.beardConfidence; }
    public String hat() { return this.hat; }
    public Float hatConfidence() { return this.hatConfidence; }
    public String mask() { return this.mask; }
    public Float maskConfidence() { return this.maskConfidence; }
    public String glasses() { return this.glasses; }
    public Float glassesConfidence() { return this.glassesConfidence; }
    public Float sharpness() { return this.sharpness; }
    public Float attractive() { return this.attractive; }
    public HeadPose headPose() { return this.headPose; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private String figureId;
        private Float figureConfidence;
        private String figureClusterId;
        private Float figureClusterConfidence;
        private String figureType;
        private Long age;
        private Float ageSD;
        private String gender;
        private Float genderConfidence;
        private String emotion;
        private Float emotionConfidence;
        private Float faceQuality;
        private Boundary boundary;
        private String mouth;
        private Float mouthConfidence;
        private String beard;
        private Float beardConfidence;
        private String hat;
        private Float hatConfidence;
        private String mask;
        private Float maskConfidence;
        private String glasses;
        private Float glassesConfidence;
        private Float sharpness;
        private Float attractive;
        private HeadPose headPose;

        public Builder figureId(String value) { this.figureId = value; return this; }
        public Builder figureConfidence(Float value) { this.figureConfidence = value; return this; }
        public Builder figureClusterId(String value) { this.figureClusterId = value; return this; }
        public Builder figureClusterConfidence(Float value) { this.figureClusterConfidence = value; return this; }
        public Builder figureType(String value) { this.figureType = value; return this; }
        public Builder age(Long value) { this.age = value; return this; }
        public Builder ageSD(Float value) { this.ageSD = value; return this; }
        public Builder gender(String value) { this.gender = value; return this; }
        public Builder genderConfidence(Float value) { this.genderConfidence = value; return this; }
        public Builder emotion(String value) { this.emotion = value; return this; }
        public Builder emotionConfidence(Float value) { this.emotionConfidence = value; return this; }
        public Builder faceQuality(Float value) { this.faceQuality = value; return this; }
        public Builder boundary(Boundary value) { this.boundary = value; return this; }
        public Builder mouth(String value) { this.mouth = value; return this; }
        public Builder mouthConfidence(Float value) { this.mouthConfidence = value; return this; }
        public Builder beard(String value) { this.beard = value; return this; }
        public Builder beardConfidence(Float value) { this.beardConfidence = value; return this; }
        public Builder hat(String value) { this.hat = value; return this; }
        public Builder hatConfidence(Float value) { this.hatConfidence = value; return this; }
        public Builder mask(String value) { this.mask = value; return this; }
        public Builder maskConfidence(Float value) { this.maskConfidence = value; return this; }
        public Builder glasses(String value) { this.glasses = value; return this; }
        public Builder glassesConfidence(Float value) { this.glassesConfidence = value; return this; }
        public Builder sharpness(Float value) { this.sharpness = value; return this; }
        public Builder attractive(Float value) { this.attractive = value; return this; }
        public Builder headPose(HeadPose value) { this.headPose = value; return this; }

        private Builder() { super(); }

        private Builder(Figure from) {
            this.figureId = from.figureId;
            this.figureConfidence = from.figureConfidence;
            this.figureClusterId = from.figureClusterId;
            this.figureClusterConfidence = from.figureClusterConfidence;
            this.figureType = from.figureType;
            this.age = from.age;
            this.ageSD = from.ageSD;
            this.gender = from.gender;
            this.genderConfidence = from.genderConfidence;
            this.emotion = from.emotion;
            this.emotionConfidence = from.emotionConfidence;
            this.faceQuality = from.faceQuality;
            this.boundary = from.boundary;
            this.mouth = from.mouth;
            this.mouthConfidence = from.mouthConfidence;
            this.beard = from.beard;
            this.beardConfidence = from.beardConfidence;
            this.hat = from.hat;
            this.hatConfidence = from.hatConfidence;
            this.mask = from.mask;
            this.maskConfidence = from.maskConfidence;
            this.glasses = from.glasses;
            this.glassesConfidence = from.glassesConfidence;
            this.sharpness = from.sharpness;
            this.attractive = from.attractive;
            this.headPose = from.headPose;
        }

        public Figure build() { return new Figure(this); }
    }
}
