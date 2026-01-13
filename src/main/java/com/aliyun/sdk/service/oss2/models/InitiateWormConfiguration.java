package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the root node.
 */
 @JacksonXmlRootElement(localName = "InitiateWormConfiguration")
public final class InitiateWormConfiguration {  
    @JacksonXmlProperty(localName = "RetentionPeriodInDays")
    private Integer retentionPeriodInDays;

    public InitiateWormConfiguration() {}

    private InitiateWormConfiguration(Builder builder) { 
        this.retentionPeriodInDays = builder.retentionPeriodInDays; 
    }

    /**
    * The number of days for which objects can be retained.
    */
    public Integer retentionPeriodInDays() {
        return this.retentionPeriodInDays;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private Integer retentionPeriodInDays;
        
        /**
        * The number of days for which objects can be retained.
        */
        public Builder retentionPeriodInDays(Integer value) {
            requireNonNull(value);
            this.retentionPeriodInDays = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(InitiateWormConfiguration from) { 
            this.retentionPeriodInDays = from.retentionPeriodInDays; 
        }

        public InitiateWormConfiguration build() {
            return new InitiateWormConfiguration(this);
        }
    }
}
