package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The rule list for setting tags.
 */
 @JacksonXmlRootElement(localName = "Taggings")
public final class MirrorTagging {
    @JacksonXmlProperty(localName = "Key")
    private String key;
 
    @JacksonXmlProperty(localName = "Value")
    private String value;

    public MirrorTagging() {}

    private MirrorTagging(Builder builder) {
        this.key = builder.key; 
        this.value = builder.value; 
    }

    /**
    * The tag key.
    */
    public String key() {
        return this.key;
    }

    /**
    * The rule for setting tag value for a specific tag key.
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
        * The tag key.
        */
        public Builder key(String value) {
            requireNonNull(value);
            this.key = value;
            return this;
        }
        
        /**
        * The rule for setting tag value for a specific tag key.
        */
        public Builder value(String value) {
            requireNonNull(value);
            this.value = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(MirrorTagging from) {
            this.key = from.key; 
            this.value = from.value; 
        }

        public MirrorTagging build() {
            return new MirrorTagging(this);
        }
    }
}
