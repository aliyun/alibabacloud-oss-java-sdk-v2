package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * Audio streams information in the vector retrieval results of data indexing
 */
 @JacksonXmlRootElement(localName = "AudioStreams")
public final class MetaQueryAudioStreams {  
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "AudioStream")
    private List<MetaQueryAudioStream> audioStream;

    public MetaQueryAudioStreams() {}

    private MetaQueryAudioStreams(Builder builder) { 
        this.audioStream = builder.audioStream; 
    }

    /**
     * Audio stream information in the vector retrieval results of data indexing
     */
    public List<MetaQueryAudioStream> audioStream() {
        return this.audioStream;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<MetaQueryAudioStream> audioStream;
        
        /**
         * Audio stream information in the vector retrieval results of data indexing
         */
        public Builder audioStream(List<MetaQueryAudioStream> value) {
            requireNonNull(value);
            this.audioStream = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(MetaQueryAudioStreams from) { 
            this.audioStream = from.audioStream; 
        }

        public MetaQueryAudioStreams build() {
            return new MetaQueryAudioStreams(this);
        }
    }
}