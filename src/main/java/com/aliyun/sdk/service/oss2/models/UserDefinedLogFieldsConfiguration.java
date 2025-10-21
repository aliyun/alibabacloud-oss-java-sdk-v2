package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The specified field configurations of real-time logs in a bucket.
 */
 @JacksonXmlRootElement(localName = "UserDefinedLogFieldsConfiguration")
public final class UserDefinedLogFieldsConfiguration {  
    @JacksonXmlProperty(localName = "HeaderSet")
    private HeaderSet headerSet;
 
    @JacksonXmlProperty(localName = "ParamSet")
    private ParamSet paramSet;

    public UserDefinedLogFieldsConfiguration() {}

    private UserDefinedLogFieldsConfiguration(Builder builder) { 
        this.headerSet = builder.headerSet; 
        this.paramSet = builder.paramSet; 
    }

    /**
    * The container that stores the configurations of custom request headers.
    */
    public HeaderSet headerSet() {
        return this.headerSet;
    }

    /**
    * The container that stores the configurations of custom URL parameters.
    */
    public ParamSet paramSet() {
        return this.paramSet;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private HeaderSet headerSet;
        private ParamSet paramSet;
        
        /**
        * The container that stores the configurations of custom request headers.
        */
        public Builder headerSet(HeaderSet value) {
            requireNonNull(value);
            this.headerSet = value;
            return this;
        }
        
        /**
        * The container that stores the configurations of custom URL parameters.
        */
        public Builder paramSet(ParamSet value) {
            requireNonNull(value);
            this.paramSet = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(UserDefinedLogFieldsConfiguration from) { 
            this.headerSet = from.headerSet; 
            this.paramSet = from.paramSet; 
        }

        public UserDefinedLogFieldsConfiguration build() {
            return new UserDefinedLogFieldsConfiguration(this);
        }
    }
}
