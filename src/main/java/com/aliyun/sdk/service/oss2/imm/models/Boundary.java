package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Boundary {

    @JsonProperty("Width")
    private Long width;

    @JsonProperty("Height")
    private Long height;

    @JsonProperty("Left")
    private Long left;

    @JsonProperty("Top")
    private Long top;

    @JsonProperty("Polygon")
    private List<PointInt64> polygon;

    public Boundary() {
    }

    public Long getWidth() {
        return width;
    }

    public Boundary setWidth(Long width) {
        this.width = width;
        return this;
    }

    public Long getHeight() {
        return height;
    }

    public Boundary setHeight(Long height) {
        this.height = height;
        return this;
    }

    public Long getLeft() {
        return left;
    }

    public Boundary setLeft(Long left) {
        this.left = left;
        return this;
    }

    public Long getTop() {
        return top;
    }

    public Boundary setTop(Long top) {
        this.top = top;
        return this;
    }

    public List<PointInt64> getPolygon() {
        return polygon;
    }

    public Boundary setPolygon(List<PointInt64> polygon) {
        this.polygon = polygon;
        return this;
    }
}
