package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores allowed features.
 */
 @JacksonXmlRootElement(localName = "AllowedFeatures")
public final class ObjectProcessAllowedFeatures {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "AllowedFeature")
    private List<String> allowedFeature;

    public ObjectProcessAllowedFeatures() {}

    private ObjectProcessAllowedFeatures(Builder builder) {
        this.allowedFeature = builder.allowedFeature;
    }

    /**
    * Specifies that Function Compute supports Range GetObject requests.
    */
    public List<String> allowedFeature() {
        return this.allowedFeature;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<String> allowedFeature;
        
        /**
        * Specifies that Function Compute supports Range GetObject requests.
        */
        public Builder allowedFeature(List<String> value) {
            requireNonNull(value);
            this.allowedFeature = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(ObjectProcessAllowedFeatures from) {
            this.allowedFeature = from.allowedFeature;
        }

        public ObjectProcessAllowedFeatures build() {
            return new ObjectProcessAllowedFeatures(this);
        }
    }
}
