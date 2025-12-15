package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the transfer acceleration configurations.
 */
 @JacksonXmlRootElement(localName = "TransferAccelerationConfiguration")
public final class TransferAccelerationConfiguration {  
    @JacksonXmlProperty(localName = "Enabled")
    private Boolean enabled;

    public TransferAccelerationConfiguration() {}

    private TransferAccelerationConfiguration(Builder builder) { 
        this.enabled = builder.enabled; 
    }

    /**
    * Whether the transfer acceleration is enabled for this bucket.
    */
    public Boolean enabled() {
        return this.enabled;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private Boolean enabled;
        
        /**
        * Whether the transfer acceleration is enabled for this bucket.
        */
        public Builder enabled(Boolean value) {
            requireNonNull(value);
            this.enabled = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(TransferAccelerationConfiguration from) { 
            this.enabled = from.enabled; 
        }

        public TransferAccelerationConfiguration build() {
            return new TransferAccelerationConfiguration(this);
        }
    }
}
