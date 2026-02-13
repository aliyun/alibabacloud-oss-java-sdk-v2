package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the custom forward headers list.
 */
@JacksonXmlRootElement(localName = "CustomForwardHeaders")
public final class ObjectProcessCustomForwardHeaders {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "CustomForwardHeader")
    private List<String> customForwardHeader;

    public ObjectProcessCustomForwardHeaders() {}

    private ObjectProcessCustomForwardHeaders(Builder builder) {
        this.customForwardHeader = builder.customForwardHeader;
    }

    /**
    * The list of custom forward headers.
    */
    public List<String> customForwardHeader() {
        return this.customForwardHeader;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<String> customForwardHeader;
        
        /**
        * The list of custom forward headers.
        */
        public Builder customForwardHeader(List<String> value) {
            requireNonNull(value);
            this.customForwardHeader = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(ObjectProcessCustomForwardHeaders from) {
            this.customForwardHeader = from.customForwardHeader;
        }

        public ObjectProcessCustomForwardHeaders build() {
            return new ObjectProcessCustomForwardHeaders(this);
        }
    }
}
