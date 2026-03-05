package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Figure {

    @JsonProperty("FigureId")
    private String figureId;

    @JsonProperty("FigureConfidence")
    private Float figureConfidence;

    @JsonProperty("FigureClusterId")
    private String figureClusterId;

    @JsonProperty("FigureClusterConfidence")
    private Float figureClusterConfidence;

    @JsonProperty("FigureType")
    private String figureType;

    @JsonProperty("Age")
    private Long age;

    @JsonProperty("AgeSD")
    private Float ageSD;

    @JsonProperty("Gender")
    private String gender;

    @JsonProperty("GenderConfidence")
    private Float genderConfidence;

    @JsonProperty("Emotion")
    private String emotion;

    @JsonProperty("EmotionConfidence")
    private Float emotionConfidence;

    @JsonProperty("FaceQuality")
    private Float faceQuality;

    @JsonProperty("Boundary")
    private Boundary boundary;

    @JsonProperty("Mouth")
    private String mouth;

    @JsonProperty("MouthConfidence")
    private Float mouthConfidence;

    @JsonProperty("Beard")
    private String beard;

    @JsonProperty("BeardConfidence")
    private Float beardConfidence;

    @JsonProperty("Hat")
    private String hat;

    @JsonProperty("HatConfidence")
    private Float hatConfidence;

    @JsonProperty("Mask")
    private String mask;

    @JsonProperty("MaskConfidence")
    private Float maskConfidence;

    @JsonProperty("Glasses")
    private String glasses;

    @JsonProperty("GlassesConfidence")
    private Float glassesConfidence;

    @JsonProperty("Sharpness")
    private Float sharpness;

    @JsonProperty("Attractive")
    private Float attractive;

    @JsonProperty("HeadPose")
    private HeadPose headPose;

    public Figure() {
    }

    public String getFigureId() {
        return figureId;
    }

    public Figure setFigureId(String figureId) {
        this.figureId = figureId;
        return this;
    }

    public Float getFigureConfidence() {
        return figureConfidence;
    }

    public Figure setFigureConfidence(Float figureConfidence) {
        this.figureConfidence = figureConfidence;
        return this;
    }

    public String getFigureClusterId() {
        return figureClusterId;
    }

    public Figure setFigureClusterId(String figureClusterId) {
        this.figureClusterId = figureClusterId;
        return this;
    }

    public Float getFigureClusterConfidence() {
        return figureClusterConfidence;
    }

    public Figure setFigureClusterConfidence(Float figureClusterConfidence) {
        this.figureClusterConfidence = figureClusterConfidence;
        return this;
    }

    public String getFigureType() {
        return figureType;
    }

    public Figure setFigureType(String figureType) {
        this.figureType = figureType;
        return this;
    }

    public Long getAge() {
        return age;
    }

    public Figure setAge(Long age) {
        this.age = age;
        return this;
    }

    public Float getAgeSD() {
        return ageSD;
    }

    public Figure setAgeSD(Float ageSD) {
        this.ageSD = ageSD;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public Figure setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public Float getGenderConfidence() {
        return genderConfidence;
    }

    public Figure setGenderConfidence(Float genderConfidence) {
        this.genderConfidence = genderConfidence;
        return this;
    }

    public String getEmotion() {
        return emotion;
    }

    public Figure setEmotion(String emotion) {
        this.emotion = emotion;
        return this;
    }

    public Float getEmotionConfidence() {
        return emotionConfidence;
    }

    public Figure setEmotionConfidence(Float emotionConfidence) {
        this.emotionConfidence = emotionConfidence;
        return this;
    }

    public Float getFaceQuality() {
        return faceQuality;
    }

    public Figure setFaceQuality(Float faceQuality) {
        this.faceQuality = faceQuality;
        return this;
    }

    public Boundary getBoundary() {
        return boundary;
    }

    public Figure setBoundary(Boundary boundary) {
        this.boundary = boundary;
        return this;
    }

    public String getMouth() {
        return mouth;
    }

    public Figure setMouth(String mouth) {
        this.mouth = mouth;
        return this;
    }

    public Float getMouthConfidence() {
        return mouthConfidence;
    }

    public Figure setMouthConfidence(Float mouthConfidence) {
        this.mouthConfidence = mouthConfidence;
        return this;
    }

    public String getBeard() {
        return beard;
    }

    public Figure setBeard(String beard) {
        this.beard = beard;
        return this;
    }

    public Float getBeardConfidence() {
        return beardConfidence;
    }

    public Figure setBeardConfidence(Float beardConfidence) {
        this.beardConfidence = beardConfidence;
        return this;
    }

    public String getHat() {
        return hat;
    }

    public Figure setHat(String hat) {
        this.hat = hat;
        return this;
    }

    public Float getHatConfidence() {
        return hatConfidence;
    }

    public Figure setHatConfidence(Float hatConfidence) {
        this.hatConfidence = hatConfidence;
        return this;
    }

    public String getMask() {
        return mask;
    }

    public Figure setMask(String mask) {
        this.mask = mask;
        return this;
    }

    public Float getMaskConfidence() {
        return maskConfidence;
    }

    public Figure setMaskConfidence(Float maskConfidence) {
        this.maskConfidence = maskConfidence;
        return this;
    }

    public String getGlasses() {
        return glasses;
    }

    public Figure setGlasses(String glasses) {
        this.glasses = glasses;
        return this;
    }

    public Float getGlassesConfidence() {
        return glassesConfidence;
    }

    public Figure setGlassesConfidence(Float glassesConfidence) {
        this.glassesConfidence = glassesConfidence;
        return this;
    }

    public Float getSharpness() {
        return sharpness;
    }

    public Figure setSharpness(Float sharpness) {
        this.sharpness = sharpness;
        return this;
    }

    public Float getAttractive() {
        return attractive;
    }

    public Figure setAttractive(Float attractive) {
        this.attractive = attractive;
        return this;
    }

    public HeadPose getHeadPose() {
        return headPose;
    }

    public Figure setHeadPose(HeadPose headPose) {
        this.headPose = headPose;
        return this;
    }
}
