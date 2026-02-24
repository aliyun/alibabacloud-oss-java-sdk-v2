package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The configuration for putting access point config for object process.
 */
@JacksonXmlRootElement(localName = "PutAccessPointConfigForObjectProcessConfiguration")
public final class PutAccessPointConfigForObjectProcessConfiguration {  
    @JacksonXmlProperty(localName = "ObjectProcessConfiguration")
    private ObjectProcessConfiguration objectProcessConfiguration;
 
    @JacksonXmlProperty(localName = "PublicAccessBlockConfiguration")
    private PublicAccessBlockConfiguration publicAccessBlockConfiguration;

    public PutAccessPointConfigForObjectProcessConfiguration() {}

    private PutAccessPointConfigForObjectProcessConfiguration(Builder builder) { 
        this.objectProcessConfiguration = builder.objectProcessConfiguration; 
        this.publicAccessBlockConfiguration = builder.publicAccessBlockConfiguration; 
    }

    /**
    * The object process configuration.
    */
    public ObjectProcessConfiguration objectProcessConfiguration() {
        return this.objectProcessConfiguration;
    }

    /**
    * The public access block configuration.
    */
    public PublicAccessBlockConfiguration publicAccessBlockConfiguration() {
        return this.publicAccessBlockConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private ObjectProcessConfiguration objectProcessConfiguration;
        private PublicAccessBlockConfiguration publicAccessBlockConfiguration;
        
        /**
        * The object process configuration.
        */
        public Builder objectProcessConfiguration(ObjectProcessConfiguration value) {
            requireNonNull(value);
            this.objectProcessConfiguration = value;
            return this;
        }
        
        /**
        * The public access block configuration.
        */
        public Builder publicAccessBlockConfiguration(PublicAccessBlockConfiguration value) {
            requireNonNull(value);
            this.publicAccessBlockConfiguration = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(PutAccessPointConfigForObjectProcessConfiguration from) { 
            this.objectProcessConfiguration = from.objectProcessConfiguration; 
            this.publicAccessBlockConfiguration = from.publicAccessBlockConfiguration; 
        }

        public PutAccessPointConfigForObjectProcessConfiguration build() {
            return new PutAccessPointConfigForObjectProcessConfiguration(this);
        }
    }
}