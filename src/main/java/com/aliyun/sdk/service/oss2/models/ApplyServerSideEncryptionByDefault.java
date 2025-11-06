package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the default server-side encryption method.
 */
 @JacksonXmlRootElement(localName = "ApplyServerSideEncryptionByDefault")
public final class ApplyServerSideEncryptionByDefault {  
    @JacksonXmlProperty(localName = "SSEAlgorithm")
    private String sSEAlgorithm;
 
    @JacksonXmlProperty(localName = "KMSMasterKeyID")
    private String kMSMasterKeyID;
 
    @JacksonXmlProperty(localName = "KMSDataEncryption")
    private String kMSDataEncryption;

    public ApplyServerSideEncryptionByDefault() {}

    private ApplyServerSideEncryptionByDefault(Builder builder) { 
        this.sSEAlgorithm = builder.sSEAlgorithm; 
        this.kMSMasterKeyID = builder.kMSMasterKeyID; 
        this.kMSDataEncryption = builder.kMSDataEncryption; 
    }

    /**
    * The default server-side encryption method. Valid values: KMS, AES256, and SM4. You are charged when you call API operations to encrypt or decrypt data by using CMKs managed by KMS. For more information, see [Billing of KMS](~~52608~~). If the default server-side encryption method is configured for the destination bucket and ReplicaCMKID is configured in the CRR rule:*   If objects in the source bucket are not encrypted, they are encrypted by using the default encryption method of the destination bucket after they are replicated.*   If objects in the source bucket are encrypted by using SSE-KMS or SSE-OSS, they are encrypted by using the same method after they are replicated.For more information, see [Use data replication with server-side encryption](~~177216~~).
    */
    public String sSEAlgorithm() {
        return this.sSEAlgorithm;
    }

    /**
    * The CMK ID that is specified when SSEAlgorithm is set to KMS and a specified CMK is used for encryption. In other cases, leave this parameter empty.
    */
    public String kMSMasterKeyID() {
        return this.kMSMasterKeyID;
    }

    /**
    * The algorithm that is used to encrypt objects. If this parameter is not specified, objects are encrypted by using AES256. This parameter is valid only when SSEAlgorithm is set to KMS. Valid value: SM4.
    */
    public String kMSDataEncryption() {
        return this.kMSDataEncryption;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String sSEAlgorithm;
        private String kMSMasterKeyID;
        private String kMSDataEncryption;
        
        /**
        * The default server-side encryption method. Valid values: KMS, AES256, and SM4. You are charged when you call API operations to encrypt or decrypt data by using CMKs managed by KMS. For more information, see [Billing of KMS](~~52608~~). If the default server-side encryption method is configured for the destination bucket and ReplicaCMKID is configured in the CRR rule:*   If objects in the source bucket are not encrypted, they are encrypted by using the default encryption method of the destination bucket after they are replicated.*   If objects in the source bucket are encrypted by using SSE-KMS or SSE-OSS, they are encrypted by using the same method after they are replicated.For more information, see [Use data replication with server-side encryption](~~177216~~).
        */
        public Builder sSEAlgorithm(String value) {
            requireNonNull(value);
            this.sSEAlgorithm = value;
            return this;
        }
        
        /**
        * The CMK ID that is specified when SSEAlgorithm is set to KMS and a specified CMK is used for encryption. In other cases, leave this parameter empty.
        */
        public Builder kMSMasterKeyID(String value) {
            requireNonNull(value);
            this.kMSMasterKeyID = value;
            return this;
        }
        
        /**
        * The algorithm that is used to encrypt objects. If this parameter is not specified, objects are encrypted by using AES256. This parameter is valid only when SSEAlgorithm is set to KMS. Valid value: SM4.
        */
        public Builder kMSDataEncryption(String value) {
            requireNonNull(value);
            this.kMSDataEncryption = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(ApplyServerSideEncryptionByDefault from) { 
            this.sSEAlgorithm = from.sSEAlgorithm; 
            this.kMSMasterKeyID = from.kMSMasterKeyID; 
            this.kMSDataEncryption = from.kMSDataEncryption; 
        }

        public ApplyServerSideEncryptionByDefault build() {
            return new ApplyServerSideEncryptionByDefault(this);
        }
    }
}
