package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the information about retention policies of the bucket.
 */
 @JacksonXmlRootElement(localName = "WormConfiguration")
public final class WormConfiguration {  
    @JacksonXmlProperty(localName = "ExpirationDate")
    private String expirationDate;
 
    @JacksonXmlProperty(localName = "WormId")
    private String wormId;
 
    @JacksonXmlProperty(localName = "State")
    private String state;
 
    @JacksonXmlProperty(localName = "RetentionPeriodInDays")
    private Integer retentionPeriodInDays;
 
    @JacksonXmlProperty(localName = "CreationDate")
    private String creationDate;

    public WormConfiguration() {}

    private WormConfiguration(Builder builder) { 
        this.expirationDate = builder.expirationDate; 
        this.wormId = builder.wormId; 
        this.state = builder.state; 
        this.retentionPeriodInDays = builder.retentionPeriodInDays; 
        this.creationDate = builder.creationDate; 
    }

    /**
    * The time at which the retention policy will be expired.
    */
    public String expirationDate() {
        return this.expirationDate;
    }

    /**
    * The ID of the retention policy.Note If the specified retention policy ID that is used to query the retention policy configurations of the bucket does not exist, OSS returns the 404 error code.
    */
    public String wormId() {
        return this.wormId;
    }

    /**
    * The status of the retention policy. Valid values:- InProgress: indicates that the retention policy is in the InProgress state. By default, a retention policy is in the InProgress state after it is created. The policy remains in this state for 24 hours.- Locked: indicates that the retention policy is in the Locked state.
    */
    public String state() {
        return this.state;
    }

    /**
    * The number of days for which objects can be retained.
    */
    public Integer retentionPeriodInDays() {
        return this.retentionPeriodInDays;
    }

    /**
    * The time at which the retention policy was created.
    */
    public String creationDate() {
        return this.creationDate;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String expirationDate;
        private String wormId;
        private String state;
        private Integer retentionPeriodInDays;
        private String creationDate;
        
        /**
        * The time at which the retention policy will be expired.
        */
        public Builder expirationDate(String value) {
            requireNonNull(value);
            this.expirationDate = value;
            return this;
        }
        
        /**
        * The ID of the retention policy.Note If the specified retention policy ID that is used to query the retention policy configurations of the bucket does not exist, OSS returns the 404 error code.
        */
        public Builder wormId(String value) {
            requireNonNull(value);
            this.wormId = value;
            return this;
        }
        
        /**
        * The status of the retention policy. Valid values:- InProgress: indicates that the retention policy is in the InProgress state. By default, a retention policy is in the InProgress state after it is created. The policy remains in this state for 24 hours.- Locked: indicates that the retention policy is in the Locked state.
        */
        public Builder state(String value) {
            requireNonNull(value);
            this.state = value;
            return this;
        }
        
        /**
        * The number of days for which objects can be retained.
        */
        public Builder retentionPeriodInDays(Integer value) {
            requireNonNull(value);
            this.retentionPeriodInDays = value;
            return this;
        }
        
        /**
        * The time at which the retention policy was created.
        */
        public Builder creationDate(String value) {
            requireNonNull(value);
            this.creationDate = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(WormConfiguration from) { 
            this.expirationDate = from.expirationDate; 
            this.wormId = from.wormId; 
            this.state = from.state; 
            this.retentionPeriodInDays = from.retentionPeriodInDays; 
            this.creationDate = from.creationDate; 
        }

        public WormConfiguration build() {
            return new WormConfiguration(this);
        }
    }
}
