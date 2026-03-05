package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Image {

    @JsonProperty("ImageWidth")
    private Long imageWidth;

    @JsonProperty("ImageHeight")
    private Long imageHeight;

    @JsonProperty("EXIF")
    private String exif;

    @JsonProperty("ImageScore")
    private ImageScore imageScore;

    @JsonProperty("CroppingSuggestions")
    private List<CroppingSuggestion> croppingSuggestions;

    @JsonProperty("OCRContents")
    private List<OCRContents> ocrContents;

    public Image() {
    }

    public Long getImageWidth() {
        return imageWidth;
    }

    public Image setImageWidth(Long imageWidth) {
        this.imageWidth = imageWidth;
        return this;
    }

    public Long getImageHeight() {
        return imageHeight;
    }

    public Image setImageHeight(Long imageHeight) {
        this.imageHeight = imageHeight;
        return this;
    }

    public String getExif() {
        return exif;
    }

    public Image setExif(String exif) {
        this.exif = exif;
        return this;
    }

    public ImageScore getImageScore() {
        return imageScore;
    }

    public Image setImageScore(ImageScore imageScore) {
        this.imageScore = imageScore;
        return this;
    }

    public List<CroppingSuggestion> getCroppingSuggestions() {
        return croppingSuggestions;
    }

    public Image setCroppingSuggestions(List<CroppingSuggestion> croppingSuggestions) {
        this.croppingSuggestions = croppingSuggestions;
        return this;
    }

    public List<OCRContents> getOcrContents() {
        return ocrContents;
    }

    public Image setOcrContents(List<OCRContents> ocrContents) {
        this.ocrContents = ocrContents;
        return this;
    }
}
