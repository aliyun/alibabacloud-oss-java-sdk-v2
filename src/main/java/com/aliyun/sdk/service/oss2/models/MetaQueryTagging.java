package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * Object tagging
 */
 @JacksonXmlRootElement(localName = "Tagging")
public final class MetaQueryTagging {  
    @JacksonXmlProperty(localName = "Key")
    private String key;
 
    @JacksonXmlProperty(localName = "Value")
    private String value;

    public MetaQueryTagging() {}

    private MetaQueryTagging(Builder builder) { 
        this.key = builder.key; 
        this.value = builder.value; 
    }

    /**
     * Object tag key
     */
    public String key() {
        return this.key;
    }

    /**
     * Object tag value
     */
    public String value() {
        return this.value;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String key;
        private String value;
        
        /**
         * Object tag key
         */
        public Builder key(String value) {
            requireNonNull(value);
            this.key = value;
            return this;
        }
        
        /**
         * Object tag value
         */
        public Builder value(String value) {
            requireNonNull(value);
            this.value = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(MetaQueryTagging from) { 
            this.key = from.key; 
            this.value = from.value; 
        }

        public MetaQueryTagging build() {
            return new MetaQueryTagging(this);
        }
    }
}