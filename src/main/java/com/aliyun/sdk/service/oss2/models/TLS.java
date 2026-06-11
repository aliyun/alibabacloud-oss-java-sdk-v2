package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores TLS version configurations.
 */
 @JacksonXmlRootElement(localName = "TLS")
public final class TLS {  
    @JacksonXmlProperty(localName = "Enable")
    private Boolean enable;
 
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "TLSVersion")
    private List<String> tlsVersions;

    public TLS() {}

    private TLS(Builder builder) { 
        this.enable = builder.enable; 
        this.tlsVersions = builder.tlsVersions; 
    }

    /**
    * Specifies whether to enable TLS version management for the bucket.Valid values:*   true            *   false            
    */
    public Boolean enable() {
        return this.enable;
    }

    /**
    * The TLS versions.
    */
    public List<String> tlsVersions() {
        return this.tlsVersions;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private Boolean enable;
        private List<String> tlsVersions;
        
        /**
        * Specifies whether to enable TLS version management for the bucket.Valid values:*   true            *   false            
        */
        public Builder enable(Boolean value) {
            requireNonNull(value);
            this.enable = value;
            return this;
        }
        
        /**
        * The TLS versions.
        */
        public Builder tlsVersions(List<String> value) {
            requireNonNull(value);
            this.tlsVersions = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(TLS from) { 
            this.enable = from.enable; 
            this.tlsVersions = from.tlsVersions; 
        }

        public TLS build() {
            return new TLS(this);
        }
    }
}
