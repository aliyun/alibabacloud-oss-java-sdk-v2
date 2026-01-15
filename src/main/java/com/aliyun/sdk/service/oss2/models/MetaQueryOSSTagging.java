package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The list of object tags.
 */
 @JacksonXmlRootElement(localName = "OSSTagging")
public final class MetaQueryOSSTagging {  
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Tagging")
    private List<MetaQueryTagging> tagging;

    public MetaQueryOSSTagging() {}

    private MetaQueryOSSTagging(Builder builder) { 
        this.tagging = builder.tagging; 
    }

    /**
    * The tags.
    */
    public List<MetaQueryTagging> tagging() {
        return this.tagging;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<MetaQueryTagging> tagging;
        
        /**
        * The tags.
        */
        public Builder tagging(List<MetaQueryTagging> value) {
            requireNonNull(value);
            this.tagging = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(MetaQueryOSSTagging from) { 
            this.tagging = from.tagging; 
        }

        public MetaQueryOSSTagging build() {
            return new MetaQueryOSSTagging(this);
        }
    }
}