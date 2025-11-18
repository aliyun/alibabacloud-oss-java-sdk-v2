package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores information about the Replication Time Control (RTC) status.
 */
 @JacksonXmlRootElement(localName = "RTC")
public final class RTC {  
    @JacksonXmlProperty(localName = "Status")
    private String status;

    public RTC() {}

    private RTC(Builder builder) { 
        this.status = builder.status; 
    }

    /**
    * Specifies whether to enable RTC.Valid values:*   disabled            *   enabled            
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
        * Specifies whether to enable RTC.Valid values:*   disabled            *   enabled            
        */
        public Builder status(String value) {
            requireNonNull(value);
            this.status = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(RTC from) { 
            this.status = from.status; 
        }

        public RTC build() {
            return new RTC(this);
        }
    }
}
