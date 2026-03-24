package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores additional feature configurations.
 */
@JacksonXmlRootElement(localName = "AdditionalFeatures")
public final class ObjectProcessAdditionalFeatures {
    @JacksonXmlProperty(localName = "CustomForwardHeaders")
    private ObjectProcessCustomForwardHeaders customForwardHeaders;

    public ObjectProcessAdditionalFeatures() {}

    private ObjectProcessAdditionalFeatures(Builder builder) {
        this.customForwardHeaders = builder.customForwardHeaders; 
    }

    /**
    * The container that stores the custom forward headers list.
    */
    public ObjectProcessCustomForwardHeaders customForwardHeaders() {
        return this.customForwardHeaders;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private ObjectProcessCustomForwardHeaders customForwardHeaders;
        
        /**
        * The container that stores the custom forward headers list.
        */
        public Builder customForwardHeaders(ObjectProcessCustomForwardHeaders value) {
            requireNonNull(value);
            this.customForwardHeaders = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(ObjectProcessAdditionalFeatures from) {
            this.customForwardHeaders = from.customForwardHeaders; 
        }

        public ObjectProcessAdditionalFeatures build() {
            return new ObjectProcessAdditionalFeatures(this);
        }
    }
}
