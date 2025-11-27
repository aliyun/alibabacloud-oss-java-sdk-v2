package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that specifies other conditions used to filter the source objects that you want to replicate. Filter conditions can be specified only for source objects encrypted by using SSE-KMS.
 */
 @JacksonXmlRootElement(localName = "ReplicationSourceSelectionCriteria")
public final class ReplicationSourceSelectionCriteria {  
    @JacksonXmlProperty(localName = "SseKmsEncryptedObjects")
    private SseKmsEncryptedObjects sseKmsEncryptedObjects;

    public ReplicationSourceSelectionCriteria() {}

    private ReplicationSourceSelectionCriteria(Builder builder) { 
        this.sseKmsEncryptedObjects = builder.sseKmsEncryptedObjects; 
    }

    /**
    * The container that is used to filter the source objects that are encrypted by using SSE-KMS. This parameter must be specified if the SourceSelectionCriteria parameter is specified in the data replication rule.
    */
    public SseKmsEncryptedObjects sseKmsEncryptedObjects() {
        return this.sseKmsEncryptedObjects;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private SseKmsEncryptedObjects sseKmsEncryptedObjects;
        
        /**
        * The container that is used to filter the source objects that are encrypted by using SSE-KMS. This parameter must be specified if the SourceSelectionCriteria parameter is specified in the data replication rule.
        */
        public Builder sseKmsEncryptedObjects(SseKmsEncryptedObjects value) {
            requireNonNull(value);
            this.sseKmsEncryptedObjects = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(ReplicationSourceSelectionCriteria from) { 
            this.sseKmsEncryptedObjects = from.sseKmsEncryptedObjects; 
        }

        public ReplicationSourceSelectionCriteria build() {
            return new ReplicationSourceSelectionCriteria(this);
        }
    }
}
