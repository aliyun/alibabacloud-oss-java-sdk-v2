package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the progress of the data replication task. This parameter is returned only when the data replication task is in the doing state.
 */
 @JacksonXmlRootElement(localName = "Progress")
public final class Progress {  
    @JacksonXmlProperty(localName = "HistoricalObject")
    private String historicalObject;
 
    @JacksonXmlProperty(localName = "NewObject")
    private String newObject;

    public Progress() {}

    private Progress(Builder builder) { 
        this.historicalObject = builder.historicalObject; 
        this.newObject = builder.newObject; 
    }

    /**
    * The percentage of the replicated historical data. This parameter is valid only when HistoricalObjectReplication is set to enabled.
    */
    public String historicalObject() {
        return this.historicalObject;
    }

    /**
    * The time used to determine whether data is replicated to the destination bucket. Data that is written to the source bucket before the time is replicated to the destination bucket. The value of this parameter is in the GMT format. Example: Thu, 24 Sep 2015 15:39:18 GMT.
    */
    public String newObject() {
        return this.newObject;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String historicalObject;
        private String newObject;
        
        /**
        * The percentage of the replicated historical data. This parameter is valid only when HistoricalObjectReplication is set to enabled.
        */
        public Builder historicalObject(String value) {
            requireNonNull(value);
            this.historicalObject = value;
            return this;
        }
        
        /**
        * The time used to determine whether data is replicated to the destination bucket. Data that is written to the source bucket before the time is replicated to the destination bucket. The value of this parameter is in the GMT format. Example: Thu, 24 Sep 2015 15:39:18 GMT.
        */
        public Builder newObject(String value) {
            requireNonNull(value);
            this.newObject = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(Progress from) { 
            this.historicalObject = from.historicalObject; 
            this.newObject = from.newObject; 
        }

        public Progress build() {
            return new Progress(this);
        }
    }
}
