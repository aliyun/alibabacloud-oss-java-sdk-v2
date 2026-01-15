package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * Subtitle information in the vector retrieval results of data indexing
 */
 @JacksonXmlRootElement(localName = "Subtitle")
public final class MetaQuerySubtitle {  
    @JacksonXmlProperty(localName = "CodecName")
    private String codecName;
 
    @JacksonXmlProperty(localName = "Language")
    private String language;
 
    @JacksonXmlProperty(localName = "StartTime")
    private Double startTime;
 
    @JacksonXmlProperty(localName = "Duration")
    private Double duration;

    public MetaQuerySubtitle() {}

    private MetaQuerySubtitle(Builder builder) { 
        this.codecName = builder.codecName; 
        this.language = builder.language; 
        this.startTime = builder.startTime; 
        this.duration = builder.duration; 
    }

    /**
     * Encoder name
     */
    public String codecName() {
        return this.codecName;
    }

    /**
     * Language used in the subtitle, in BCP 47 format
     */
    public String language() {
        return this.language;
    }

    /**
     * Subtitle start time, in seconds (s)
     */
    public Double startTime() {
        return this.startTime;
    }

    /**
     * Subtitle duration, in seconds (s)
     */
    public Double duration() {
        return this.duration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String codecName;
        private String language;
        private Double startTime;
        private Double duration;
        
        /**
         * Encoder name
         */
        public Builder codecName(String value) {
            requireNonNull(value);
            this.codecName = value;
            return this;
        }
        
        /**
         * Language used in the subtitle, in BCP 47 format
         */
        public Builder language(String value) {
            requireNonNull(value);
            this.language = value;
            return this;
        }
        
        /**
         * Subtitle start time, in seconds (s)
         */
        public Builder startTime(Double value) {
            requireNonNull(value);
            this.startTime = value;
            return this;
        }
        
        /**
         * Subtitle duration, in seconds (s)
         */
        public Builder duration(Double value) {
            requireNonNull(value);
            this.duration = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(MetaQuerySubtitle from) { 
            this.codecName = from.codecName; 
            this.language = from.language; 
            this.startTime = from.startTime; 
            this.duration = from.duration; 
        }

        public MetaQuerySubtitle build() {
            return new MetaQuerySubtitle(this);
        }
    }
}