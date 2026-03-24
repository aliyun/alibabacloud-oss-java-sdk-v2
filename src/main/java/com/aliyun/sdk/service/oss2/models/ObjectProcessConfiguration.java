package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the processing information about the Object FC Access Point.
 */
 @JacksonXmlRootElement(localName = "ObjectProcessConfiguration")
public final class ObjectProcessConfiguration {  
    @JacksonXmlProperty(localName = "AllowedFeatures")
    private ObjectProcessAllowedFeatures allowedFeatures;
 
    @JacksonXmlProperty(localName = "TransformationConfigurations")
    private TransformationConfigurations transformationConfigurations;

    public ObjectProcessConfiguration() {}

    private ObjectProcessConfiguration(Builder builder) { 
        this.allowedFeatures = builder.allowedFeatures; 
        this.transformationConfigurations = builder.transformationConfigurations; 
    }

    /**
    * The container that stores allowed features.
    */
    public ObjectProcessAllowedFeatures allowedFeatures() {
        return this.allowedFeatures;
    }

    /**
    * The container that stores the transformation configurations.
    */
    public TransformationConfigurations transformationConfigurations() {
        return this.transformationConfigurations;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private ObjectProcessAllowedFeatures allowedFeatures;
        private TransformationConfigurations transformationConfigurations;
        
        /**
        * The container that stores allowed features.
        */
        public Builder allowedFeatures(ObjectProcessAllowedFeatures value) {
            requireNonNull(value);
            this.allowedFeatures = value;
            return this;
        }
        
        /**
        * The container that stores the transformation configurations.
        */
        public Builder transformationConfigurations(TransformationConfigurations value) {
            requireNonNull(value);
            this.transformationConfigurations = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(ObjectProcessConfiguration from) { 
            this.allowedFeatures = from.allowedFeatures; 
            this.transformationConfigurations = from.transformationConfigurations; 
        }

        public ObjectProcessConfiguration build() {
            return new ObjectProcessConfiguration(this);
        }
    }
}
