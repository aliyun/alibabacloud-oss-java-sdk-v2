package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container for which the certificate is configured.
 */
 @JacksonXmlRootElement(localName = "CertificateConfiguration")
public final class CertificateConfiguration {  
    @JacksonXmlProperty(localName = "DeleteCertificate")
    private Boolean deleteCertificate;
 
    @JacksonXmlProperty(localName = "CertId")
    private String certId;
 
    @JacksonXmlProperty(localName = "Certificate")
    private String certificate;
 
    @JacksonXmlProperty(localName = "PrivateKey")
    private String privateKey;
 
    @JacksonXmlProperty(localName = "PreviousCertId")
    private String previousCertId;
 
    @JacksonXmlProperty(localName = "Force")
    private Boolean force;

    public CertificateConfiguration() {}

    private CertificateConfiguration(Builder builder) { 
        this.deleteCertificate = builder.deleteCertificate; 
        this.certId = builder.certId; 
        this.certificate = builder.certificate; 
        this.privateKey = builder.privateKey; 
        this.previousCertId = builder.previousCertId; 
        this.force = builder.force; 
    }

    /**
    * Specifies whether to delete the certificate. Valid values:- true: deletes the certificate.- false: does not delete the certificate.
    */
    public Boolean deleteCertificate() {
        return this.deleteCertificate;
    }

    /**
    * The ID of the certificate.
    */
    public String certId() {
        return this.certId;
    }

    /**
    * The public key of the certificate.
    */
    public String certificate() {
        return this.certificate;
    }

    /**
    * The private key of the certificate.
    */
    public String privateKey() {
        return this.privateKey;
    }

    /**
    * The ID of the certificate. If the Force parameter is not set to true, the OSS server checks whether the value of the Force parameter matches the current certificate ID. If the value does not match the certificate ID, an error is returned.noticeIf you do not specify the PreviousCertId parameter when you bind a certificate, you must set the Force parameter to true./notice
    */
    public String previousCertId() {
        return this.previousCertId;
    }

    /**
    * Specifies whether to overwrite the certificate. Valid values:- true: overwrites the certificate.- false: does not overwrite the certificate.
    */
    public Boolean force() {
        return this.force;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private Boolean deleteCertificate;
        private String certId;
        private String certificate;
        private String privateKey;
        private String previousCertId;
        private Boolean force;
        
        /**
        * Specifies whether to delete the certificate. Valid values:- true: deletes the certificate.- false: does not delete the certificate.
        */
        public Builder deleteCertificate(Boolean value) {
            requireNonNull(value);
            this.deleteCertificate = value;
            return this;
        }
        
        /**
        * The ID of the certificate.
        */
        public Builder certId(String value) {
            requireNonNull(value);
            this.certId = value;
            return this;
        }
        
        /**
        * The public key of the certificate.
        */
        public Builder certificate(String value) {
            requireNonNull(value);
            this.certificate = value;
            return this;
        }
        
        /**
        * The private key of the certificate.
        */
        public Builder privateKey(String value) {
            requireNonNull(value);
            this.privateKey = value;
            return this;
        }
        
        /**
        * The ID of the certificate. If the Force parameter is not set to true, the OSS server checks whether the value of the Force parameter matches the current certificate ID. If the value does not match the certificate ID, an error is returned.noticeIf you do not specify the PreviousCertId parameter when you bind a certificate, you must set the Force parameter to true./notice
        */
        public Builder previousCertId(String value) {
            requireNonNull(value);
            this.previousCertId = value;
            return this;
        }
        
        /**
        * Specifies whether to overwrite the certificate. Valid values:- true: overwrites the certificate.- false: does not overwrite the certificate.
        */
        public Builder force(Boolean value) {
            requireNonNull(value);
            this.force = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(CertificateConfiguration from) { 
            this.deleteCertificate = from.deleteCertificate; 
            this.certId = from.certId; 
            this.certificate = from.certificate; 
            this.privateKey = from.privateKey; 
            this.previousCertId = from.previousCertId; 
            this.force = from.force; 
        }

        public CertificateConfiguration build() {
            return new CertificateConfiguration(this);
        }
    }
}
