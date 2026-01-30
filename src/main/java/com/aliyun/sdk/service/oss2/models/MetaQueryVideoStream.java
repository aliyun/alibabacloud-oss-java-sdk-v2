package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * Video stream information in the vector retrieval results of data indexing
 */
 @JacksonXmlRootElement(localName = "VideoStream")
public final class MetaQueryVideoStream {  
    @JacksonXmlProperty(localName = "BitDepth")
    private Long bitDepth;
 
    @JacksonXmlProperty(localName = "PixelFormat")
    private String pixelFormat;
 
    @JacksonXmlProperty(localName = "ColorSpace")
    private String colorSpace;
 
    @JacksonXmlProperty(localName = "CodecName")
    private String codecName;
 
    @JacksonXmlProperty(localName = "Language")
    private String language;
 
    @JacksonXmlProperty(localName = "Bitrate")
    private Long bitrate;
 
    @JacksonXmlProperty(localName = "FrameRate")
    private String frameRate;
 
    @JacksonXmlProperty(localName = "FrameCount")
    private Long frameCount;
 
    @JacksonXmlProperty(localName = "Width")
    private Long width;
 
    @JacksonXmlProperty(localName = "StartTime")
    private Double startTime;
 
    @JacksonXmlProperty(localName = "Duration")
    private Double duration;
 
    @JacksonXmlProperty(localName = "Height")
    private Long height;

    public MetaQueryVideoStream() {}

    private MetaQueryVideoStream(Builder builder) { 
        this.bitDepth = builder.bitDepth; 
        this.pixelFormat = builder.pixelFormat; 
        this.colorSpace = builder.colorSpace; 
        this.codecName = builder.codecName; 
        this.language = builder.language; 
        this.bitrate = builder.bitrate; 
        this.frameRate = builder.frameRate; 
        this.frameCount = builder.frameCount; 
        this.width = builder.width; 
        this.startTime = builder.startTime; 
        this.duration = builder.duration; 
        this.height = builder.height; 
    }

    /**
     * Pixel bit width
     */
    public Long bitDepth() {
        return this.bitDepth;
    }

    /**
     * Video stream pixel format
     */
    public String pixelFormat() {
        return this.pixelFormat;
    }

    /**
     * Color space
     */
    public String colorSpace() {
        return this.colorSpace;
    }

    /**
     * Encoder name
     */
    public String codecName() {
        return this.codecName;
    }

    /**
     * Language used in the video stream, in BCP 47 format
     */
    public String language() {
        return this.language;
    }

    /**
     * Bit rate, in bits per second (bit/s)
     */
    public Long bitrate() {
        return this.bitrate;
    }

    /**
     * Video stream frame rate
     */
    public String frameRate() {
        return this.frameRate;
    }

    /**
     * Number of video frames
     */
    public Long frameCount() {
        return this.frameCount;
    }

    /**
     * Video stream width, in pixels (px)
     */
    public Long width() {
        return this.width;
    }

    /**
     * Video stream start time, in seconds (s)
     */
    public Double startTime() {
        return this.startTime;
    }

    /**
     * Video stream duration, in seconds (s)
     */
    public Double duration() {
        return this.duration;
    }

    /**
     * Video stream height, in pixels (px)
     */
    public Long height() {
        return this.height;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private Long bitDepth;
        private String pixelFormat;
        private String colorSpace;
        private String codecName;
        private String language;
        private Long bitrate;
        private String frameRate;
        private Long frameCount;
        private Long width;
        private Double startTime;
        private Double duration;
        private Long height;
        
        /**
         * Pixel bit width
         */
        public Builder bitDepth(Long value) {
            requireNonNull(value);
            this.bitDepth = value;
            return this;
        }
        
        /**
         * Video stream pixel format
         */
        public Builder pixelFormat(String value) {
            requireNonNull(value);
            this.pixelFormat = value;
            return this;
        }
        
        /**
         * Color space
         */
        public Builder colorSpace(String value) {
            requireNonNull(value);
            this.colorSpace = value;
            return this;
        }
        
        /**
         * Encoder name
         */
        public Builder codecName(String value) {
            requireNonNull(value);
            this.codecName = value;
            return this;
        }
        
        /**
         * Language used in the video stream, in BCP 47 format
         */
        public Builder language(String value) {
            requireNonNull(value);
            this.language = value;
            return this;
        }
        
        /**
         * Bit rate, in bits per second (bit/s)
         */
        public Builder bitrate(Long value) {
            requireNonNull(value);
            this.bitrate = value;
            return this;
        }
        
        /**
         * Video stream frame rate
         */
        public Builder frameRate(String value) {
            requireNonNull(value);
            this.frameRate = value;
            return this;
        }
        
        /**
         * Number of video frames
         */
        public Builder frameCount(Long value) {
            requireNonNull(value);
            this.frameCount = value;
            return this;
        }
        
        /**
         * Video stream width, in pixels (px)
         */
        public Builder width(Long value) {
            requireNonNull(value);
            this.width = value;
            return this;
        }
        
        /**
         * Video stream start time, in seconds (s)
         */
        public Builder startTime(Double value) {
            requireNonNull(value);
            this.startTime = value;
            return this;
        }
        
        /**
         * Video stream duration, in seconds (s)
         */
        public Builder duration(Double value) {
            requireNonNull(value);
            this.duration = value;
            return this;
        }
        
        /**
         * Video stream height, in pixels (px)
         */
        public Builder height(Long value) {
            requireNonNull(value);
            this.height = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(MetaQueryVideoStream from) { 
            this.bitDepth = from.bitDepth; 
            this.pixelFormat = from.pixelFormat; 
            this.colorSpace = from.colorSpace; 
            this.codecName = from.codecName; 
            this.language = from.language; 
            this.bitrate = from.bitrate; 
            this.frameRate = from.frameRate; 
            this.frameCount = from.frameCount; 
            this.width = from.width; 
            this.startTime = from.startTime; 
            this.duration = from.duration; 
            this.height = from.height; 
        }

        public MetaQueryVideoStream build() {
            return new MetaQueryVideoStream(this);
        }
    }
}