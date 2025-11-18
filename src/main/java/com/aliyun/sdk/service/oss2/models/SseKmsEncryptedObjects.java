package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that is used to filter the source objects that are encrypted by using SSE-KMS. This parameter must be specified if the SourceSelectionCriteria parameter is specified in the data replication rule.
 */
 @JacksonXmlRootElement(localName = "SseKmsEncryptedObjects")
public final class SseKmsEncryptedObjects {  
    @JacksonXmlProperty(localName = "Status")
    private String status;

    public SseKmsEncryptedObjects() {}

    private SseKmsEncryptedObjects(Builder builder) { 
        this.status = builder.status; 
    }

    /**
    * Specifies whether to replicate objects that are encrypted by using SSE-KMS. Valid values:*   Enabled*   Disabled
    */
    public String status() {
        return this.status;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String status;
        
        /**
        * Specifies whether to replicate objects that are encrypted by using SSE-KMS. Valid values:*   Enabled*   Disabled
        */
        public Builder status(String value) {
            requireNonNull(value);
            this.status = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(SseKmsEncryptedObjects from) { 
            this.status = from.status; 
        }

        public SseKmsEncryptedObjects build() {
            return new SseKmsEncryptedObjects(this);
        }
    }
}
