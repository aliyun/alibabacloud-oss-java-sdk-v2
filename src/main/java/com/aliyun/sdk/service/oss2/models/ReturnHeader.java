package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The rule list for setting response headers in mirror-based back-to-origin.
 */
 @JacksonXmlRootElement(localName = "ReturnHeader")
public final class ReturnHeader {  
    @JacksonXmlProperty(localName = "Value")
    private String value;
 
    @JacksonXmlProperty(localName = "Key")
    private String key;

    public ReturnHeader() {}

    private ReturnHeader(Builder builder) { 
        this.value = builder.value; 
        this.key = builder.key; 
    }

    /**
    * The rule for setting response header value for a specific header.
    */
    public String value() {
        return this.value;
    }

    /**
    * The response header.
    */
    public String key() {
        return this.key;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String value;
        private String key;
        
        /**
        * The rule for setting response header value for a specific header.
        */
        public Builder value(String value) {
            requireNonNull(value);
            this.value = value;
            return this;
        }
        
        /**
        * The response header.
        */
        public Builder key(String value) {
            requireNonNull(value);
            this.key = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(ReturnHeader from) { 
            this.value = from.value; 
            this.key = from.key; 
        }

        public ReturnHeader build() {
            return new ReturnHeader(this);
        }
    }
}
