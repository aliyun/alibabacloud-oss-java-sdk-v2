package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The container that stores cipher suite configurations.
 */
 @JacksonXmlRootElement(localName = "CipherSuite")
public final class CipherSuite {  
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "CustomCipherSuite")
    private List<String> customCipherSuites;
 
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "TLS13CustomCipherSuite")
    private List<String> tls13CustomCipherSuites;
 
    @JacksonXmlProperty(localName = "Enable")
    private Boolean enable;
 
    @JacksonXmlProperty(localName = "StrongCipherSuite")
    private Boolean strongCipherSuite;

    public CipherSuite() {}

    private CipherSuite(Builder builder) { 
        this.customCipherSuites = builder.customCipherSuites; 
        this.tls13CustomCipherSuites = builder.tls13CustomCipherSuites; 
        this.enable = builder.enable; 
        this.strongCipherSuite = builder.strongCipherSuite; 
    }

    /**
    * The custom cipher suites.
    */
    public List<String> customCipherSuites() {
        return this.customCipherSuites;
    }

    /**
    * The TLS 1.3 custom cipher suites.
    */
    public List<String> tls13CustomCipherSuites() {
        return this.tls13CustomCipherSuites;
    }

    /**
    * Specifies whether to enable cipher suite management for the bucket.
    */
    public Boolean enable() {
        return this.enable;
    }

    /**
    * Specifies whether to enable strong cipher suites.
    */
    public Boolean strongCipherSuite() {
        return this.strongCipherSuite;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<String> customCipherSuites;
        private List<String> tls13CustomCipherSuites;
        private Boolean enable;
        private Boolean strongCipherSuite;
        
        /**
        * The custom cipher suites.
        */
        public Builder customCipherSuites(List<String> value) {
            requireNonNull(value);
            this.customCipherSuites = value;
            return this;
        }
        
        /**
        * The TLS 1.3 custom cipher suites.
        */
        public Builder tls13CustomCipherSuites(List<String> value) {
            requireNonNull(value);
            this.tls13CustomCipherSuites = value;
            return this;
        }
        
        /**
        * Specifies whether to enable cipher suite management for the bucket.
        */
        public Builder enable(Boolean value) {
            requireNonNull(value);
            this.enable = value;
            return this;
        }
        
        /**
        * Specifies whether to enable strong cipher suites.
        */
        public Builder strongCipherSuite(Boolean value) {
            requireNonNull(value);
            this.strongCipherSuite = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(CipherSuite from) { 
            this.customCipherSuites = from.customCipherSuites; 
            this.tls13CustomCipherSuites = from.tls13CustomCipherSuites; 
            this.enable = from.enable; 
            this.strongCipherSuite = from.strongCipherSuite; 
        }

        public CipherSuite build() {
            return new CipherSuite(this);
        }
    }
}
