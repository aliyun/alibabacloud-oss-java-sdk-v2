package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * Container for incremental inventory export cycle information
 */
 @JacksonXmlRootElement(localName = "IncrementInventorySchedule")
public final class IncrementInventorySchedule {  
    @JacksonXmlProperty(localName = "Frequency")
    private Long frequency;

    public IncrementInventorySchedule() {}

    private IncrementInventorySchedule(Builder builder) { 
        this.frequency = builder.frequency; 
    }

    /**
    * Describes the frequency at which incremental inventory files are exported, in seconds, currently fixed at 10 minutes.
    */
    public Long frequency() {
        return this.frequency;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private Long frequency;
        
        /**
        * Describes the frequency at which incremental inventory files are exported, in seconds, currently fixed at 10 minutes.
        */
        public Builder frequency(Long value) {
            requireNonNull(value);
            this.frequency = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(IncrementInventorySchedule from) { 
            this.frequency = from.frequency; 
        }

        public IncrementInventorySchedule build() {
            return new IncrementInventorySchedule(this);
        }
    }
}