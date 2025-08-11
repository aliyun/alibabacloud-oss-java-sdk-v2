package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The container that stores the versioning state of the bucket.
 */
 @JacksonXmlRootElement(localName = "VersioningConfiguration")
public final class VersioningConfiguration {  
    @JacksonXmlProperty(localName = "Status")
    private String status;

    public VersioningConfiguration() {}

    private VersioningConfiguration(Builder builder) { 
        this.status = builder.status; 
    }

    /**
    * The versioning state of the bucket.
    */
    public String status() {
        return this.status;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String status;
        
        /**
        * The versioning state of the bucket.
        */
        public Builder status(String value) {
            requireNonNull(value);
            this.status = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(VersioningConfiguration from) { 
            this.status = from.status; 
        }

        public VersioningConfiguration build() {
            return new VersioningConfiguration(this);
        }
    }
}
