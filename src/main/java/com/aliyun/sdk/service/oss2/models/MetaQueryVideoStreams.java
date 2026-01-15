package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * Video streams information in the vector retrieval results of data indexing
 */
 @JacksonXmlRootElement(localName = "VideoStreams")
public final class MetaQueryVideoStreams {  
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "VideoStream")
    private List<MetaQueryVideoStream> videoStream;

    public MetaQueryVideoStreams() {}

    private MetaQueryVideoStreams(Builder builder) { 
        this.videoStream = builder.videoStream; 
    }

    /**
     * Video stream information in the vector retrieval results of data indexing
     */
    public List<MetaQueryVideoStream> videoStream() {
        return this.videoStream;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<MetaQueryVideoStream> videoStream;
        
        /**
         * Video stream information in the vector retrieval results of data indexing
         */
            public Builder videoStream(List<MetaQueryVideoStream> value) {
            requireNonNull(value);
            this.videoStream = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(MetaQueryVideoStreams from) { 
            this.videoStream = from.videoStream; 
        }

        public MetaQueryVideoStreams build() {
            return new MetaQueryVideoStreams(this);
        }
    }
}