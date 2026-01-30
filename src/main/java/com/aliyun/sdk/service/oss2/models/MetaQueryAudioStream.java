package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * Audio stream information in the vector retrieval results of data indexing
 */
 @JacksonXmlRootElement(localName = "AudioStream")
public final class MetaQueryAudioStream {  
    @JacksonXmlProperty(localName = "Bitrate")
    private Long bitrate;
 
    @JacksonXmlProperty(localName = "Channels")
    private Long channels;
 
    @JacksonXmlProperty(localName = "CodecName")
    private String codecName;
 
    @JacksonXmlProperty(localName = "Duration")
    private Double duration;
 
    @JacksonXmlProperty(localName = "Language")
    private String language;
 
    @JacksonXmlProperty(localName = "SampleRate")
    private Long sampleRate;
 
    @JacksonXmlProperty(localName = "StartTime")
    private Double startTime;

    public MetaQueryAudioStream() {}

    private MetaQueryAudioStream(Builder builder) { 
        this.bitrate = builder.bitrate; 
        this.channels = builder.channels; 
        this.codecName = builder.codecName; 
        this.duration = builder.duration; 
        this.language = builder.language; 
        this.sampleRate = builder.sampleRate; 
        this.startTime = builder.startTime; 
    }

    /**
     * Bit rate, in bits per second (bit/s)
     */
    public Long bitrate() {
        return this.bitrate;
    }

    /**
     * Number of audio channels
     */
    public Long channels() {
        return this.channels;
    }

    /**
     * Encoder name
     */
    public String codecName() {
        return this.codecName;
    }

    /**
     * Audio stream duration, in seconds (s)
     */
    public Double duration() {
        return this.duration;
    }

    /**
     * Language used in the audio stream, in BCP 47 format
     */
    public String language() {
        return this.language;
    }

    /**
     * Sample rate, in hertz (Hz)
     */
    public Long sampleRate() {
        return this.sampleRate;
    }

    /**
     * Audio stream start time, in seconds (s)
     */
    public Double startTime() {
        return this.startTime;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private Long bitrate;
        private Long channels;
        private String codecName;
        private Double duration;
        private String language;
        private Long sampleRate;
        private Double startTime;
        
        /**
         * Bit rate, in bits per second (bit/s)
         */
        public Builder bitrate(Long value) {
            requireNonNull(value);
            this.bitrate = value;
            return this;
        }
        
        /**
         * Number of audio channels
         */
        public Builder channels(Long value) {
            requireNonNull(value);
            this.channels = value;
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
         * Audio stream duration, in seconds (s)
         */
        public Builder duration(Double value) {
            requireNonNull(value);
            this.duration = value;
            return this;
        }
        
        /**
         * Language used in the audio stream, in BCP 47 format
         */
        public Builder language(String value) {
            requireNonNull(value);
            this.language = value;
            return this;
        }
        
        /**
         * Sample rate, in hertz (Hz)
         */
        public Builder sampleRate(Long value) {
            requireNonNull(value);
            this.sampleRate = value;
            return this;
        }
        
        /**
         * Audio stream start time, in seconds (s)
         */
        public Builder startTime(Double value) {
            requireNonNull(value);
            this.startTime = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(MetaQueryAudioStream from) { 
            this.bitrate = from.bitrate; 
            this.channels = from.channels; 
            this.codecName = from.codecName; 
            this.duration = from.duration; 
            this.language = from.language; 
            this.sampleRate = from.sampleRate; 
            this.startTime = from.startTime; 
        }

        public MetaQueryAudioStream build() {
            return new MetaQueryAudioStream(this);
        }
    }
}