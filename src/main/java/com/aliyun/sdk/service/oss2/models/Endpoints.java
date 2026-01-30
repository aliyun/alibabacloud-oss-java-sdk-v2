package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the endpoints of the Object FC Access Point.
 */
 @JacksonXmlRootElement(localName = "Endpoints")
public final class Endpoints {  
    @JacksonXmlProperty(localName = "PublicEndpoint")
    private String publicEndpoint;
 
    @JacksonXmlProperty(localName = "InternalEndpoint")
    private String internalEndpoint;

    public Endpoints() {}

    private Endpoints(Builder builder) { 
        this.publicEndpoint = builder.publicEndpoint; 
        this.internalEndpoint = builder.internalEndpoint; 
    }

    /**
    * The public endpoint of the Object FC Access Point.
    */
    public String publicEndpoint() {
        return this.publicEndpoint;
    }

    /**
    * The internal endpoint of the Object FC Access Point.
    */
    public String internalEndpoint() {
        return this.internalEndpoint;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String publicEndpoint;
        private String internalEndpoint;
        
        /**
        * The public endpoint of the Object FC Access Point.
        */
        public Builder publicEndpoint(String value) {
            requireNonNull(value);
            this.publicEndpoint = value;
            return this;
        }
        
        /**
        * The internal endpoint of the Object FC Access Point.
        */
        public Builder internalEndpoint(String value) {
            requireNonNull(value);
            this.internalEndpoint = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(Endpoints from) { 
            this.publicEndpoint = from.publicEndpoint; 
            this.internalEndpoint = from.internalEndpoint; 
        }

        public Endpoints build() {
            return new Endpoints(this);
        }
    }
}
