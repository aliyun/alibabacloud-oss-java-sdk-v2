package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * Encryption configuration for the replication rule.
 */
 @JacksonXmlRootElement(localName = "ReplicationEncryptionConfiguration")
public final class ReplicationEncryptionConfiguration {  
    @JacksonXmlProperty(localName = "ReplicaKmsKeyID")
    private String replicaKmsKeyID;

    public ReplicationEncryptionConfiguration() {}

    private ReplicationEncryptionConfiguration(Builder builder) { 
        this.replicaKmsKeyID = builder.replicaKmsKeyID; 
    }

    /**
    * The KMS key ID used for replication.
    */
    public String replicaKmsKeyID() {
        return this.replicaKmsKeyID;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String replicaKmsKeyID;
        
        /**
        * The KMS key ID used for replication.
        */
        public Builder replicaKmsKeyID(String value) {
            requireNonNull(value);
            this.replicaKmsKeyID = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(ReplicationEncryptionConfiguration from) { 
            this.replicaKmsKeyID = from.replicaKmsKeyID; 
        }

        public ReplicationEncryptionConfiguration build() {
            return new ReplicationEncryptionConfiguration(this);
        }
    }
}
