package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The information about the CNAME records.
 */
 @JacksonXmlRootElement(localName = "CnameInfo")
public final class CnameInfo {  
    @JacksonXmlProperty(localName = "Domain")
    private String domain;
 
    @JacksonXmlProperty(localName = "LastModified")
    private String lastModified;
 
    @JacksonXmlProperty(localName = "Status")
    private String status;
 
    @JacksonXmlProperty(localName = "Certificate")
    private CnameCertificate certificate;

    public CnameInfo() {}

    private CnameInfo(Builder builder) { 
        this.domain = builder.domain; 
        this.lastModified = builder.lastModified; 
        this.status = builder.status; 
        this.certificate = builder.certificate; 
    }

    /**
    * The custom domain name.
    */
    public String domain() {
        return this.domain;
    }

    /**
    * The time when the custom domain name was mapped.
    */
    public String lastModified() {
        return this.lastModified;
    }

    /**
    * The status of the domain name. Valid values:*   Enabled*   Disabled
    */
    public String status() {
        return this.status;
    }

    /**
    * The container in which the certificate information is stored.
    */
    public CnameCertificate certificate() {
        return this.certificate;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String domain;
        private String lastModified;
        private String status;
        private CnameCertificate certificate;
        
        /**
        * The custom domain name.
        */
        public Builder domain(String value) {
            requireNonNull(value);
            this.domain = value;
            return this;
        }
        
        /**
        * The time when the custom domain name was mapped.
        */
        public Builder lastModified(String value) {
            requireNonNull(value);
            this.lastModified = value;
            return this;
        }
        
        /**
        * The status of the domain name. Valid values:*   Enabled*   Disabled
        */
        public Builder status(String value) {
            requireNonNull(value);
            this.status = value;
            return this;
        }
        
        /**
        * The container in which the certificate information is stored.
        */
        public Builder certificate(CnameCertificate value) {
            requireNonNull(value);
            this.certificate = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(CnameInfo from) { 
            this.domain = from.domain; 
            this.lastModified = from.lastModified; 
            this.status = from.status; 
            this.certificate = from.certificate; 
        }

        public CnameInfo build() {
            return new CnameInfo(this);
        }
    }
}
