package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * Subtitles information in the vector retrieval results of data indexing
 */
 @JacksonXmlRootElement(localName = "Subtitles")
public final class MetaQuerySubtitles {  
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Subtitle")
    private List<MetaQuerySubtitle> subtitle;

    public MetaQuerySubtitles() {}

    private MetaQuerySubtitles(Builder builder) { 
        this.subtitle = builder.subtitle; 
    }

    /**
     * Subtitle information in the vector retrieval results of data indexing
     */
    public List<MetaQuerySubtitle> subtitle() {
        return this.subtitle;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<MetaQuerySubtitle> subtitle;
        
        /**
         * Subtitle information in the vector retrieval results of data indexing
         */
        public Builder subtitle(List<MetaQuerySubtitle> value) {
            requireNonNull(value);
            this.subtitle = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(MetaQuerySubtitles from) { 
            this.subtitle = from.subtitle; 
        }

        public MetaQuerySubtitles build() {
            return new MetaQuerySubtitles(this);
        }
    }
}