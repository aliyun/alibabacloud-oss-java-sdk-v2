package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the CNAME information.
 */
 @JacksonXmlRootElement(localName = "Cname")
public final class Cname {  
    @JacksonXmlProperty(localName = "Domain")
    private String domain;
 
    @JacksonXmlProperty(localName = "CertificateConfiguration")
    private CertificateConfiguration certificateConfiguration;

    @JacksonXmlProperty(localName = "IsWildCard")
    private Boolean isWildCard;

    public Cname() {}

    private Cname(Builder builder) {
        this.domain = builder.domain;
        this.certificateConfiguration = builder.certificateConfiguration;
        this.isWildCard = builder.isWildCard;
    }

    /**
    * The custom domain name.
    */
    public String domain() {
        return this.domain;
    }

    /**
    * The container for which the certificate is configured.
    */
    public CertificateConfiguration certificateConfiguration() {
        return this.certificateConfiguration;
    }

    /**
    * Specifies whether the custom domain name is a wildcard domain name.
    */
    @JsonIgnore
    public Boolean isWildCard() {
        return this.isWildCard;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String domain;
        private CertificateConfiguration certificateConfiguration;
        private Boolean isWildCard;

        /**
        * The custom domain name.
        */
        public Builder domain(String value) {
            requireNonNull(value);
            this.domain = value;
            return this;
        }
        
        /**
        * The container for which the certificate is configured.
        */
        public Builder certificateConfiguration(CertificateConfiguration value) {
            requireNonNull(value);
            this.certificateConfiguration = value;
            return this;
        }

        /**
        * Specifies whether the custom domain name is a wildcard domain name.
        */
        public Builder isWildCard(Boolean value) {
            requireNonNull(value);
            this.isWildCard = value;
            return this;
        }


        private Builder() {
            super();
        }

        private Builder(Cname from) {
            this.domain = from.domain;
            this.certificateConfiguration = from.certificateConfiguration;
            this.isWildCard = from.isWildCard;
        }

        public Cname build() {
            return new Cname(this);
        }
    }
}
