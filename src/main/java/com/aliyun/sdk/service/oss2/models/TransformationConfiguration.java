package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the transformation configurations.
 */
 @JacksonXmlRootElement(localName = "TransformationConfiguration")
public final class TransformationConfiguration {  
    @JacksonXmlProperty(localName = "Actions")
    private AccessPointActions actions;
 
    @JacksonXmlProperty(localName = "ContentTransformation")
    private ContentTransformation contentTransformation;

    public TransformationConfiguration() {}

    private TransformationConfiguration(Builder builder) { 
        this.actions = builder.actions; 
        this.contentTransformation = builder.contentTransformation; 
    }

    /**
    * The container that stores the operations.
    */
    public AccessPointActions actions() {
        return this.actions;
    }

    /**
    * The container that stores the content of the transformation configurations.
    */
    public ContentTransformation contentTransformation() {
        return this.contentTransformation;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private AccessPointActions actions;
        private ContentTransformation contentTransformation;
        
        /**
        * The container that stores the operations.
        */
        public Builder actions(AccessPointActions value) {
            requireNonNull(value);
            this.actions = value;
            return this;
        }
        
        /**
        * The container that stores the content of the transformation configurations.
        */
        public Builder contentTransformation(ContentTransformation value) {
            requireNonNull(value);
            this.contentTransformation = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(TransformationConfiguration from) { 
            this.actions = from.actions; 
            this.contentTransformation = from.contentTransformation; 
        }

        public TransformationConfiguration build() {
            return new TransformationConfiguration(this);
        }
    }
}
