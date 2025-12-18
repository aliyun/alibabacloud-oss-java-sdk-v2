package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the default 404 page.
 */
 @JacksonXmlRootElement(localName = "ErrorDocument")
public final class ErrorDocument {  
    @JacksonXmlProperty(localName = "HttpStatus")
    private Long httpStatus;
 
    @JacksonXmlProperty(localName = "Key")
    private String key;

    public ErrorDocument() {}

    private ErrorDocument(Builder builder) { 
        this.httpStatus = builder.httpStatus; 
        this.key = builder.key; 
    }

    /**
    * The HTTP status code returned with the error page.
    */
    public Long httpStatus() {
        return this.httpStatus;
    }

    /**
    * The error page.
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
        private Long httpStatus;
        private String key;
        
        /**
        * The HTTP status code returned with the error page.
        */
        public Builder httpStatus(Long value) {
            requireNonNull(value);
            this.httpStatus = value;
            return this;
        }
        
        /**
        * The error page.
        */
        public Builder key(String value) {
            requireNonNull(value);
            this.key = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(ErrorDocument from) { 
            this.httpStatus = from.httpStatus; 
            this.key = from.key; 
        }

        public ErrorDocument build() {
            return new ErrorDocument(this);
        }
    }
}
