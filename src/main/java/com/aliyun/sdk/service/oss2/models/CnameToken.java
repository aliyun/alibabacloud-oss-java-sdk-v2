package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the CNAME token.
 */
 @JacksonXmlRootElement(localName = "CnameToken")
public final class CnameToken {  
    @JacksonXmlProperty(localName = "Bucket")
    private String bucket;
 
    @JacksonXmlProperty(localName = "Cname")
    private String cname;
 
    @JacksonXmlProperty(localName = "Token")
    private String token;
 
    @JacksonXmlProperty(localName = "ExpireTime")
    private String expireTime;

    public CnameToken() {}

    private CnameToken(Builder builder) { 
        this.bucket = builder.bucket; 
        this.cname = builder.cname; 
        this.token = builder.token; 
        this.expireTime = builder.expireTime; 
    }

    /**
    * The name of the bucket to which the CNAME record is mapped.
    */
    public String bucket() {
        return this.bucket;
    }

    /**
    * The name of the CNAME record that is mapped to the bucket.
    */
    public String cname() {
        return this.cname;
    }

    /**
    * The CNAME token that is returned by OSS.
    */
    public String token() {
        return this.token;
    }

    /**
    * The time when the CNAME token expires.
    */
    public String expireTime() {
        return this.expireTime;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String bucket;
        private String cname;
        private String token;
        private String expireTime;
        
        /**
        * The name of the bucket to which the CNAME record is mapped.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }
        
        /**
        * The name of the CNAME record that is mapped to the bucket.
        */
        public Builder cname(String value) {
            requireNonNull(value);
            this.cname = value;
            return this;
        }
        
        /**
        * The CNAME token that is returned by OSS.
        */
        public Builder token(String value) {
            requireNonNull(value);
            this.token = value;
            return this;
        }
        
        /**
        * The time when the CNAME token expires.
        */
        public Builder expireTime(String value) {
            requireNonNull(value);
            this.expireTime = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(CnameToken from) { 
            this.bucket = from.bucket; 
            this.cname = from.cname; 
            this.token = from.token; 
            this.expireTime = from.expireTime; 
        }

        public CnameToken build() {
            return new CnameToken(this);
        }
    }
}
