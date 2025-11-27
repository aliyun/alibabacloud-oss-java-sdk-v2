package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The authentication information for the origin server in mirror-based back-to-origin.
 */
 @JacksonXmlRootElement(localName = "MirrorAuth")
public final class MirrorAuth {  
    @JacksonXmlProperty(localName = "AccessKeyId")
    private String accessKeyId;
 
    @JacksonXmlProperty(localName = "AccessKeySecret")
    private String accessKeySecret;
 
    @JacksonXmlProperty(localName = "AuthType")
    private String authType;
 
    @JacksonXmlProperty(localName = "Region")
    private String region;

    public MirrorAuth() {}

    private MirrorAuth(Builder builder) { 
        this.accessKeyId = builder.accessKeyId; 
        this.accessKeySecret = builder.accessKeySecret; 
        this.authType = builder.authType; 
        this.region = builder.region; 
    }

    /**
    * The access key id for signature.
    */
    public String accessKeyId() {
        return this.accessKeyId;
    }

    /**
    * The access key secret for signature.
    */
    public String accessKeySecret() {
        return this.accessKeySecret;
    }

    /**
    * The authentication type.
    */
    public String authType() {
        return this.authType;
    }

    /**
    * The sign region for signature.
    */
    public String region() {
        return this.region;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String accessKeyId;
        private String accessKeySecret;
        private String authType;
        private String region;
        
        /**
        * The access key id for signature.
        */
        public Builder accessKeyId(String value) {
            requireNonNull(value);
            this.accessKeyId = value;
            return this;
        }
        
        /**
        * The access key secret for signature.
        */
        public Builder accessKeySecret(String value) {
            requireNonNull(value);
            this.accessKeySecret = value;
            return this;
        }
        
        /**
        * The authentication type.
        */
        public Builder authType(String value) {
            requireNonNull(value);
            this.authType = value;
            return this;
        }
        
        /**
        * The sign region for signature.
        */
        public Builder region(String value) {
            requireNonNull(value);
            this.region = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(MirrorAuth from) { 
            this.accessKeyId = from.accessKeyId; 
            this.accessKeySecret = from.accessKeySecret; 
            this.authType = from.authType; 
            this.region = from.region; 
        }

        public MirrorAuth build() {
            return new MirrorAuth(this);
        }
    }
}
