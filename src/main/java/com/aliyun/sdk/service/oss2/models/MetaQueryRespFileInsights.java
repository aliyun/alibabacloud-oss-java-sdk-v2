package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * Insights information for video and image files.
 */
 @JacksonXmlRootElement(localName = "MetaQueryRespFileInsights")
public final class MetaQueryRespFileInsights {  
    @JacksonXmlProperty(localName = "Video")
    private MetaQueryRespFileInsightsVideo video;
 
    @JacksonXmlProperty(localName = "Image")
    private MetaQueryRespFileInsightsImage image;

    public MetaQueryRespFileInsights() {}

    private MetaQueryRespFileInsights(Builder builder) { 
        this.video = builder.video; 
        this.image = builder.image; 
    }

    /**
     * Container for video file insights information.
     */
    public MetaQueryRespFileInsightsVideo video() {
        return this.video;
    }

    /**
     * Container for image file insights information.
     */
    public MetaQueryRespFileInsightsImage image() {
        return this.image;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private MetaQueryRespFileInsightsVideo video;
        private MetaQueryRespFileInsightsImage image;
        
        /**
         * Sets the container for video file insights information.
         */
        public Builder video(MetaQueryRespFileInsightsVideo value) {
            requireNonNull(value);
            this.video = value;
            return this;
        }
        
        /**
         * Sets the container for image file insights information.
         */
        public Builder image(MetaQueryRespFileInsightsImage value) {
            requireNonNull(value);
            this.image = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(MetaQueryRespFileInsights from) { 
            this.video = from.video; 
            this.image = from.image; 
        }

        public MetaQueryRespFileInsights build() {
            return new MetaQueryRespFileInsights(this);
        }
    }
}
