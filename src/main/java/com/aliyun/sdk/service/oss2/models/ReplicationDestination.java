package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the information about the destination bucket.
 */
 @JacksonXmlRootElement(localName = "ReplicationDestination")
public final class ReplicationDestination {  
    @JacksonXmlProperty(localName = "Bucket")
    private String bucket;
 
    @JacksonXmlProperty(localName = "Location")
    private String location;
 
    @JacksonXmlProperty(localName = "TransferType")
    private String transferType;

    public ReplicationDestination() {}

    private ReplicationDestination(Builder builder) { 
        this.bucket = builder.bucket; 
        this.location = builder.location; 
        this.transferType = builder.transferType; 
    }

    /**
    * The destination bucket to which data is replicated.
    */
    public String bucket() {
        return this.bucket;
    }

    /**
    * The region in which the destination bucket is located.
    */
    public String location() {
        return this.location;
    }

    /**
    * The link that is used to transfer data during data replication. Valid values:*   internal (default): the default data transfer link used in OSS.*   oss_acc: the transfer acceleration link. You can set TransferType to oss_acc only when you create CRR rules.
    */
    public String transferType() {
        return this.transferType;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String bucket;
        private String location;
        private String transferType;
        
        /**
        * The destination bucket to which data is replicated.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }
        
        /**
        * The region in which the destination bucket is located.
        */
        public Builder location(String value) {
            requireNonNull(value);
            this.location = value;
            return this;
        }
        
        /**
        * The link that is used to transfer data during data replication. Valid values:*   internal (default): the default data transfer link used in OSS.*   oss_acc: the transfer acceleration link. You can set TransferType to oss_acc only when you create CRR rules.
        */
        public Builder transferType(String value) {
            requireNonNull(value);
            this.transferType = value;
            return this;
        }
        
        /**
         * The link that is used to transfer data during data replication. Valid values:*   internal (default): the default data transfer link used in OSS.*   oss_acc: the transfer acceleration link. You can set TransferType to oss_acc only when you create CRR rules.
         */
        public Builder transferType(TransferType value) {
            requireNonNull(value);
            this.transferType = value.toString();
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(ReplicationDestination from) { 
            this.bucket = from.bucket; 
            this.location = from.location; 
            this.transferType = from.transferType; 
        }

        public ReplicationDestination build() {
            return new ReplicationDestination(this);
        }
    }
}