package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The container in which the Block Public Access configurations are stored.
 */
 @JacksonXmlRootElement(localName = "PublicAccessBlockConfiguration")
public final class BucketPublicAccessBlockConfiguration {
    @JacksonXmlProperty(localName = "BlockPublicAccess")
    private Boolean blockPublicAccess;

    public BucketPublicAccessBlockConfiguration() {}

    private BucketPublicAccessBlockConfiguration(Builder builder) {
        this.blockPublicAccess = builder.blockPublicAccess; 
    }

    /**
    * Specifies whether to enable Block Public Access.true: enables Block Public Access.false (default): disables Block Public Access.
    */
    public Boolean blockPublicAccess() {
        return this.blockPublicAccess;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private Boolean blockPublicAccess;
        
        /**
        * Specifies whether to enable Block Public Access.true: enables Block Public Access.false (default): disables Block Public Access.
        */
        public Builder blockPublicAccess(Boolean value) {
            requireNonNull(value);
            this.blockPublicAccess = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(BucketPublicAccessBlockConfiguration from) {
            this.blockPublicAccess = from.blockPublicAccess; 
        }

        public BucketPublicAccessBlockConfiguration build() {
            return new BucketPublicAccessBlockConfiguration(this);
        }
    }
}
