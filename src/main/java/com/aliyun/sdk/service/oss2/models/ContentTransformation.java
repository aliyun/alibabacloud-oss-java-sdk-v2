package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the content of the transformation configurations.
 */
 @JacksonXmlRootElement(localName = "ContentTransformation")
public final class ContentTransformation {  
    @JacksonXmlProperty(localName = "FunctionCompute")
    private ObjectProcessFunctionCompute functionCompute;
 
    @JacksonXmlProperty(localName = "AdditionalFeatures")
    private ObjectProcessAdditionalFeatures additionalFeatures;

    public ContentTransformation() {}

    private ContentTransformation(Builder builder) { 
        this.functionCompute = builder.functionCompute; 
        this.additionalFeatures = builder.additionalFeatures; 
    }

    /**
    * The container that stores the information about Function Compute.
    */
    public ObjectProcessFunctionCompute functionCompute() {
        return this.functionCompute;
    }

    /**
    * The container that stores the information about additional features.
    */
    public ObjectProcessAdditionalFeatures additionalFeatures() {
        return this.additionalFeatures;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private ObjectProcessFunctionCompute functionCompute;
        private ObjectProcessAdditionalFeatures additionalFeatures;
        
        /**
        * The container that stores the information about Function Compute.
        */
        public Builder functionCompute(ObjectProcessFunctionCompute value) {
            requireNonNull(value);
            this.functionCompute = value;
            return this;
        }
        
        /**
        * Sets the additional features configuration.
        *
        * @param value The additional features configuration.
        * @return This builder instance.
        */
        public Builder additionalFeatures(ObjectProcessAdditionalFeatures value) {
            requireNonNull(value);
            this.additionalFeatures = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(ContentTransformation from) { 
            this.functionCompute = from.functionCompute; 
            this.additionalFeatures = from.additionalFeatures; 
        }

        public ContentTransformation build() {
            return new ContentTransformation(this);
        }
    }
}
