package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The container that stores server-side encryption rules.
 */
@JacksonXmlRootElement(localName = "ServerSideEncryptionRule")
public final class BucketInfoServerSideEncryptionRule {
    @JacksonXmlProperty(localName = "SSEAlgorithm")
    private String sseAlgorithm;

    @JacksonXmlProperty(localName = "KMSMasterKeyID")
    private String kmsMasterKeyID;

    @JacksonXmlProperty(localName = "KMSDataEncryption")
    private String kmsDataEncryption;

    public BucketInfoServerSideEncryptionRule() {
    }

    private BucketInfoServerSideEncryptionRule(BucketInfoServerSideEncryptionRule.Builder builder) {
        this.sseAlgorithm = builder.sseAlgorithm;
        this.kmsMasterKeyID = builder.kmsMasterKeyID;
        this.kmsDataEncryption = builder.kmsDataEncryption;
    }

    public static BucketInfoServerSideEncryptionRule.Builder newBuilder() {
        return new BucketInfoServerSideEncryptionRule.Builder();
    }

    /**
     * The default server-side encryption method. Valid values: KMS, AES256, and SM4. You are charged when you call API operations to encrypt or decrypt data by using CMKs managed by KMS. For more information, see [Billing of KMS](~~52608~~). If the default server-side encryption method is configured for the destination bucket and ReplicaCMKID is configured in the CRR rule:*   If objects in the source bucket are not encrypted, they are encrypted by using the default encryption method of the destination bucket after they are replicated.*   If objects in the source bucket are encrypted by using SSE-KMS or SSE-OSS, they are encrypted by using the same method after they are replicated.For more information, see [Use data replication with server-side encryption](~~177216~~).
     */
    public String sseAlgorithm() {
        return this.sseAlgorithm;
    }

    /**
     * The CMK ID that is specified when SSEAlgorithm is set to KMS and a specified CMK is used for encryption. In other cases, leave this parameter empty.
     */
    public String kmsMasterKeyID() {
        return this.kmsMasterKeyID;
    }

    /**
     * The algorithm that is used to encrypt objects. If this parameter is not specified, objects are encrypted by using AES256. This parameter is valid only when SSEAlgorithm is set to KMS. Valid value: SM4.
     */
    public String kmsDataEncryption() {
        return this.kmsDataEncryption;
    }

    public BucketInfoServerSideEncryptionRule.Builder toBuilder() {
        return new BucketInfoServerSideEncryptionRule.Builder(this);
    }

    public static class Builder {
        private String sseAlgorithm;
        private String kmsMasterKeyID;
        private String kmsDataEncryption;

        private Builder() {
            super();
        }

        private Builder(BucketInfoServerSideEncryptionRule from) {
            this.sseAlgorithm = from.sseAlgorithm;
            this.kmsMasterKeyID = from.kmsMasterKeyID;
            this.kmsDataEncryption = from.kmsDataEncryption;
        }

        /**
         * The default server-side encryption method. Valid values: KMS, AES256, and SM4. You are charged when you call API operations to encrypt or decrypt data by using CMKs managed by KMS. For more information, see [Billing of KMS](~~52608~~). If the default server-side encryption method is configured for the destination bucket and ReplicaCMKID is configured in the CRR rule:*   If objects in the source bucket are not encrypted, they are encrypted by using the default encryption method of the destination bucket after they are replicated.*   If objects in the source bucket are encrypted by using SSE-KMS or SSE-OSS, they are encrypted by using the same method after they are replicated.For more information, see [Use data replication with server-side encryption](~~177216~~).
         */
        public BucketInfoServerSideEncryptionRule.Builder sSEAlgorithm(String value) {
            requireNonNull(value);
            this.sseAlgorithm = value;
            return this;
        }

        /**
         * The CMK ID that is specified when SSEAlgorithm is set to KMS and a specified CMK is used for encryption. In other cases, leave this parameter empty.
         */
        public BucketInfoServerSideEncryptionRule.Builder kMSMasterKeyID(String value) {
            requireNonNull(value);
            this.kmsMasterKeyID = value;
            return this;
        }

        /**
         * The algorithm that is used to encrypt objects. If this parameter is not specified, objects are encrypted by using AES256. This parameter is valid only when SSEAlgorithm is set to KMS. Valid value: SM4.
         */
        public BucketInfoServerSideEncryptionRule.Builder kMSDataEncryption(String value) {
            requireNonNull(value);
            this.kmsDataEncryption = value;
            return this;
        }

        public BucketInfoServerSideEncryptionRule build() {
            return new BucketInfoServerSideEncryptionRule(this);
        }
    }
}
