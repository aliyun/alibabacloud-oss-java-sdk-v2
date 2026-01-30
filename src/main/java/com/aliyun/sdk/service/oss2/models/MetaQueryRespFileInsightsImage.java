package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * Insights information for image files.
 */
 @JacksonXmlRootElement(localName = "MetaQueryRespFileInsightsImage")
public final class MetaQueryRespFileInsightsImage {  
    @JacksonXmlProperty(localName = "Caption")
    private String caption;
 
    @JacksonXmlProperty(localName = "Description")
    private String description;

    public MetaQueryRespFileInsightsImage() {}

    private MetaQueryRespFileInsightsImage(Builder builder) { 
        this.caption = builder.caption; 
        this.description = builder.description; 
    }

    /**
     * Brief caption information for the image.
     */
    public String caption() {
        return this.caption;
    }

    /**
     * Detailed description information for the image.
     */
    public String description() {
        return this.description;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String caption;
        private String description;
        
        /**
         * Sets the brief caption information for the image.
         */
        public Builder caption(String value) {
            requireNonNull(value);
            this.caption = value;
            return this;
        }
        
        /**
         * Sets the detailed description information for the image.
         */
        public Builder description(String value) {
            requireNonNull(value);
            this.description = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(MetaQueryRespFileInsightsImage from) { 
            this.caption = from.caption; 
            this.description = from.description; 
        }

        public MetaQueryRespFileInsightsImage build() {
            return new MetaQueryRespFileInsightsImage(this);
        }
    }
}
