package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "Boundary")
public final class Boundary {

    @JacksonXmlProperty(localName = "Width")
    private Long width;

    @JacksonXmlProperty(localName = "Height")
    private Long height;

    @JacksonXmlProperty(localName = "Left")
    private Long left;

    @JacksonXmlProperty(localName = "Top")
    private Long top;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Polygon")
    private List<PointInt64> polygon;

    public Boundary() {
    }

    private Boundary(Builder builder) {
        this.width = builder.width;
        this.height = builder.height;
        this.left = builder.left;
        this.top = builder.top;
        this.polygon = builder.polygon;
    }

    public Long width() {
        return this.width;
    }

    public Long height() {
        return this.height;
    }

    public Long left() {
        return this.left;
    }

    public Long top() {
        return this.top;
    }

    public List<PointInt64> polygon() {
        return this.polygon;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Long width;
        private Long height;
        private Long left;
        private Long top;
        private List<PointInt64> polygon;

        public Builder width(Long value) {
            this.width = value;
            return this;
        }

        public Builder height(Long value) {
            this.height = value;
            return this;
        }

        public Builder left(Long value) {
            this.left = value;
            return this;
        }

        public Builder top(Long value) {
            this.top = value;
            return this;
        }

        public Builder polygon(List<PointInt64> value) {
            this.polygon = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(Boundary from) {
            this.width = from.width;
            this.height = from.height;
            this.left = from.left;
            this.top = from.top;
            this.polygon = from.polygon;
        }

        public Boundary build() {
            return new Boundary(this);
        }
    }
}
