package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores Replication Time Control (RTC) configurations.
 */
 @JacksonXmlRootElement(localName = "ReplicationRule")
public final class RtcConfiguration {  
    @JacksonXmlProperty(localName = "RTC")
    private RTC rtc;
 
    @JacksonXmlProperty(localName = "ID")
    private String id;

    public RtcConfiguration() {}

    private RtcConfiguration(Builder builder) { 
        this.rtc = builder.rtc;
        this.id = builder.id;
    }

    /**
    * The container that stores the status of RTC.
    */
    public RTC rtc() {
        return this.rtc;
    }

    /**
    * The ID of the data replication rule for which you want to configure RTC.
    */
    public String id() {
        return this.id;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private RTC rtc;
        private String id;
        
        /**
        * The container that stores the status of RTC.
        */
        public Builder rtc(RTC value) {
            requireNonNull(value);
            this.rtc = value;
            return this;
        }
        
        /**
        * The ID of the data replication rule for which you want to configure RTC.
        */
        public Builder id(String value) {
            requireNonNull(value);
            this.id = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(RtcConfiguration from) { 
            this.rtc = from.rtc;
            this.id = from.id;
        }

        public RtcConfiguration build() {
            return new RtcConfiguration(this);
        }
    }
}
