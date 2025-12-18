package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The rule will only be matched when the request contains the specified Header with the specified value. Up to 10 of these can be specified in the container.
 */
 @JacksonXmlRootElement(localName = "IncludeHeader")
public final class RoutingRuleIncludeHeader {
    @JacksonXmlProperty(localName = "Key")
    private String key;
 
    @JacksonXmlProperty(localName = "Equals")
    private String equals;
 
    @JacksonXmlProperty(localName = "StartsWith")
    private String startsWith;
 
    @JacksonXmlProperty(localName = "EndsWith")
    private String endsWith;

    public RoutingRuleIncludeHeader() {}

    private RoutingRuleIncludeHeader(Builder builder) {
        this.key = builder.key; 
        this.equals = builder.equals; 
        this.startsWith = builder.startsWith; 
        this.endsWith = builder.endsWith; 
    }

    /**
    * The rule will only be matched when the request contains this Header and its value is equal to the specified value.
    */
    public String key() {
        return this.key;
    }

    /**
    * The rule will only be matched when the request contains this Header and its value is equal to the specified value.
    */
    public String equals() {
        return this.equals;
    }

    /**
    * The rule will only be matched when the request contains this Header and its value starts with the specified value.
    */
    public String startsWith() {
        return this.startsWith;
    }

    /**
    * The rule will only be matched when the request contains this Header and its value ends with the specified value.
    */
    public String endsWith() {
        return this.endsWith;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String key;
        private String equals;
        private String startsWith;
        private String endsWith;
        
        /**
        * The rule will only be matched when the request contains this Header and its value is equal to the specified value.
        */
        public Builder key(String value) {
            requireNonNull(value);
            this.key = value;
            return this;
        }
        
        /**
        * The rule will only be matched when the request contains this Header and its value is equal to the specified value.
        */
        public Builder equals(String value) {
            requireNonNull(value);
            this.equals = value;
            return this;
        }
        
        /**
        * The rule will only be matched when the request contains this Header and its value starts with the specified value.
        */
        public Builder startsWith(String value) {
            requireNonNull(value);
            this.startsWith = value;
            return this;
        }
        
        /**
        * The rule will only be matched when the request contains this Header and its value ends with the specified value.
        */
        public Builder endsWith(String value) {
            requireNonNull(value);
            this.endsWith = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(RoutingRuleIncludeHeader from) {
            this.key = from.key; 
            this.equals = from.equals; 
            this.startsWith = from.startsWith; 
            this.endsWith = from.endsWith; 
        }

        public RoutingRuleIncludeHeader build() {
            return new RoutingRuleIncludeHeader(this);
        }
    }
}
