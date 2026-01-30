package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores information about the bucket in which exported inventory lists are stored.
 */
 @JacksonXmlRootElement(localName = "InventoryOSSBucketDestination")
public final class InventoryOSSBucketDestination {  
    @JacksonXmlProperty(localName = "Encryption")
    private InventoryEncryption encryption;
 
    @JacksonXmlProperty(localName = "Format")
    private String format;
 
    @JacksonXmlProperty(localName = "AccountId")
    private String accountId;
 
    @JacksonXmlProperty(localName = "RoleArn")
    private String roleArn;
 
    @JacksonXmlProperty(localName = "Bucket")
    private String bucket;
 
    @JacksonXmlProperty(localName = "Prefix")
    private String prefix;

    public InventoryOSSBucketDestination() {}

    private InventoryOSSBucketDestination(Builder builder) { 
        this.encryption = builder.encryption; 
        this.format = builder.format; 
        this.accountId = builder.accountId; 
        this.roleArn = builder.roleArn; 
        this.bucket = builder.bucket; 
        this.prefix = builder.prefix; 
    }

    /**
    * The container that stores the encryption method of the exported inventory lists.
    */
    public InventoryEncryption encryption() {
        return this.encryption;
    }

    /**
    * The format of exported inventory lists. The exported inventory lists are CSV objects compressed by using GZIP.
    */
    public String format() {
        return this.format;
    }

    /**
    * The ID of the account to which permissions are granted by the bucket owner.
    */
    public String accountId() {
        return this.accountId;
    }

    /**
    * The Alibaba Cloud Resource Name (ARN) of the role that has the permissions to read all objects from the source bucket and write objects to the destination bucket. Format: `acs:ram::uid:role/rolename`.
    */
    public String roleArn() {
        return this.roleArn;
    }

    /**
    * The name of the bucket in which exported inventory lists are stored.
    */
    public String bucket() {
        return this.bucket;
    }

    /**
    * The prefix of the path in which the exported inventory lists are stored.
    */
    public String prefix() {
        return this.prefix;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private InventoryEncryption encryption;
        private String format;
        private String accountId;
        private String roleArn;
        private String bucket;
        private String prefix;
        
        /**
        * The container that stores the encryption method of the exported inventory lists.
        */
        public Builder encryption(InventoryEncryption value) {
            requireNonNull(value);
            this.encryption = value;
            return this;
        }
        
        /**
        * The format of exported inventory lists. The exported inventory lists are CSV objects compressed by using GZIP.
        */
        public Builder format(String value) {
            requireNonNull(value);
            this.format = value;
            return this;
        }
        
        /**
        * The ID of the account to which permissions are granted by the bucket owner.
        */
        public Builder accountId(String value) {
            requireNonNull(value);
            this.accountId = value;
            return this;
        }
        
        /**
        * The Alibaba Cloud Resource Name (ARN) of the role that has the permissions to read all objects from the source bucket and write objects to the destination bucket. Format: `acs:ram::uid:role/rolename`.
        */
        public Builder roleArn(String value) {
            requireNonNull(value);
            this.roleArn = value;
            return this;
        }
        
        /**
        * The name of the bucket in which exported inventory lists are stored.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }
        
        /**
        * The prefix of the path in which the exported inventory lists are stored.
        */
        public Builder prefix(String value) {
            requireNonNull(value);
            this.prefix = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(InventoryOSSBucketDestination from) { 
            this.encryption = from.encryption; 
            this.format = from.format; 
            this.accountId = from.accountId; 
            this.roleArn = from.roleArn; 
            this.bucket = from.bucket; 
            this.prefix = from.prefix; 
        }

        public InventoryOSSBucketDestination build() {
            return new InventoryOSSBucketDestination(this);
        }
    }
}
