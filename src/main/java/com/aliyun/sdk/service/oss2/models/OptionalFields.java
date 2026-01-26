package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.Objects.requireNonNull;

/**
 * Configuration container for incremental inventory file attributes
 */
 @JacksonXmlRootElement(localName = "OptionalFields")
public final class OptionalFields {  
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Field")
    private List<String> fields;

    public OptionalFields() {}

    private OptionalFields(Builder builder) { 
        this.fields = builder.fields; 
    }

    /**
    * List of incremental inventory export fields
    */
    public List<String> fields() {
        return this.fields;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<String> fields;
        
        /**
        * List of incremental inventory export fields
        */
        public Builder fields(List<String> value) {
            requireNonNull(value);
            this.fields = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(OptionalFields from) { 
            this.fields = from.fields; 
        }

        public OptionalFields build() {
            return new OptionalFields(this);
        }
    }
}