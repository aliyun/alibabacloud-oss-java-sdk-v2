package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The container that stores the configurations of custom URL parameters.
 */
 @JacksonXmlRootElement(localName = "ParamSet")
public final class LoggingParamSet {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "parameter")
    private List<String> parameters;

    public LoggingParamSet() {}

    private LoggingParamSet(Builder builder) {
        this.parameters = builder.parameters; 
    }

    /**
    * The list of the custom URL parameters.
    */
    public List<String> parameters() {
        return this.parameters;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<String> parameters;
        
        /**
        * The list of the custom URL parameters.
        */
        public Builder parameters(List<String> value) {
            requireNonNull(value);
            this.parameters = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(LoggingParamSet from) {
            this.parameters = from.parameters; 
        }

        public LoggingParamSet build() {
            return new LoggingParamSet(this);
        }
    }
}
