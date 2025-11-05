package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the configurations of custom request headers.
 */
 @JacksonXmlRootElement(localName = "HeaderSet")
public final class LoggingHeaderSet {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "header")
    private List<String> headers;

    public LoggingHeaderSet() {}

    private LoggingHeaderSet(Builder builder) {
        this.headers = builder.headers; 
    }

    /**
    * The list of the custom request headers.
    */
    public List<String> headers() {
        return this.headers;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<String> headers;
        
        /**
        * The list of the custom request headers.
        */
        public Builder headers(List<String> value) {
            requireNonNull(value);
            this.headers = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(LoggingHeaderSet from) {
            this.headers = from.headers; 
        }

        public LoggingHeaderSet build() {
            return new LoggingHeaderSet(this);
        }
    }
}
