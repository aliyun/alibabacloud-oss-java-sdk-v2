package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores Transport Layer Security (TLS) version configurations.
 */
 @JacksonXmlRootElement(localName = "HttpsConfiguration")
public final class HttpsConfiguration {  
    @JacksonXmlProperty(localName = "TLS")
    private TLS tls;
 
    @JacksonXmlProperty(localName = "CipherSuite")
    private CipherSuite cipherSuite;

    public HttpsConfiguration() {}

    private HttpsConfiguration(Builder builder) { 
        this.tls = builder.tls; 
        this.cipherSuite = builder.cipherSuite; 
    }

    /**
    * The container that stores TLS version configurations.
    */
    public TLS tls() {
        return this.tls;
    }

    /**
    * The container that stores cipher suite configurations.
    */
    public CipherSuite cipherSuite() {
        return this.cipherSuite;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private TLS tls;
        private CipherSuite cipherSuite;
        
        /**
        * The container that stores TLS version configurations.
        */
        public Builder tls(TLS value) {
            requireNonNull(value);
            this.tls = value;
            return this;
        }
        
        /**
        * The container that stores cipher suite configurations.
        */
        public Builder cipherSuite(CipherSuite value) {
            requireNonNull(value);
            this.cipherSuite = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(HttpsConfiguration from) { 
            this.tls = from.tls; 
            this.cipherSuite = from.cipherSuite; 
        }

        public HttpsConfiguration build() {
            return new HttpsConfiguration(this);
        }
    }
}
