package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the information about access log collection.
 */
@JacksonXmlRootElement(localName = "LoggingEnabled")
public final class LoggingEnabled {  
    @JacksonXmlProperty(localName = "TargetBucket")
    private String targetBucket;
 
    @JacksonXmlProperty(localName = "TargetPrefix")
    private String targetPrefix;
    
    @JacksonXmlProperty(localName = "PushSuccessMarker")
    private Boolean pushSuccessMarker;
    
    @JacksonXmlProperty(localName = "LoggingRole")
    private String loggingRole;
    
    @JacksonXmlProperty(localName = "TargetSuffix")
    private TargetSuffix targetSuffix;

    public LoggingEnabled() {}

    private LoggingEnabled(Builder builder) { 
        this.targetBucket = builder.targetBucket; 
        this.targetPrefix = builder.targetPrefix; 
        this.pushSuccessMarker = builder.pushSuccessMarker;
        this.loggingRole = builder.loggingRole;
        this.targetSuffix = builder.targetSuffix;
    }

    /**
    * The bucket that stores access logs.
    */
    public String targetBucket() {
        return this.targetBucket;
    }

    /**
    * The prefix of the log objects. This parameter can be left empty.
    */
    public String targetPrefix() {
        return this.targetPrefix;
    }
    
    /**
    * Indicates whether to push a success marker.
    */
    public Boolean pushSuccessMarker() {
        return this.pushSuccessMarker;
    }
    
    /**
    * The logging role.
    */
    public String loggingRole() {
        return this.loggingRole;
    }
    
    /**
    * The target suffix configuration.
    */
    public TargetSuffix targetSuffix() {
        return this.targetSuffix;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String targetBucket;
        private String targetPrefix;
        private Boolean pushSuccessMarker;
        private String loggingRole;
        private TargetSuffix targetSuffix;
        
        /**
        * The bucket that stores access logs.
        */
        public Builder targetBucket(String value) {
            requireNonNull(value);
            this.targetBucket = value;
            return this;
        }
        
        /**
        * The prefix of the log objects. This parameter can be left empty.
        */
        public Builder targetPrefix(String value) {
            requireNonNull(value);
            this.targetPrefix = value;
            return this;
        }
        
        /**
        * Indicates whether to push a success marker.
        */
        public Builder pushSuccessMarker(Boolean value) {
            requireNonNull(value);
            this.pushSuccessMarker = value;
            return this;
        }
        
        /**
        * The logging role.
        */
        public Builder loggingRole(String value) {
            requireNonNull(value);
            this.loggingRole = value;
            return this;
        }
        
        /**
        * The target suffix configuration.
        */
        public Builder targetSuffix(TargetSuffix value) {
            requireNonNull(value);
            this.targetSuffix = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(LoggingEnabled from) { 
            this.targetBucket = from.targetBucket; 
            this.targetPrefix = from.targetPrefix; 
            this.pushSuccessMarker = from.pushSuccessMarker;
            this.loggingRole = from.loggingRole;
            this.targetSuffix = from.targetSuffix;
        }

        public LoggingEnabled build() {
            return new LoggingEnabled(this);
        }
    }
}