package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores server-side encryption rules.
 */
 @JacksonXmlRootElement(localName = "ServerSideEncryptionRule")
public final class ServerSideEncryptionRule {  
    @JacksonXmlProperty(localName = "ApplyServerSideEncryptionByDefault")
    private ApplyServerSideEncryptionByDefault applyServerSideEncryptionByDefault;

    public ServerSideEncryptionRule() {}

    private ServerSideEncryptionRule(Builder builder) { 
        this.applyServerSideEncryptionByDefault = builder.applyServerSideEncryptionByDefault; 
    }

    /**
    * The container that stores the default server-side encryption method.
    */
    public ApplyServerSideEncryptionByDefault applyServerSideEncryptionByDefault() {
        return this.applyServerSideEncryptionByDefault;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private ApplyServerSideEncryptionByDefault applyServerSideEncryptionByDefault;
        
        /**
        * The container that stores the default server-side encryption method.
        */
        public Builder applyServerSideEncryptionByDefault(ApplyServerSideEncryptionByDefault value) {
            requireNonNull(value);
            this.applyServerSideEncryptionByDefault = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(ServerSideEncryptionRule from) { 
            this.applyServerSideEncryptionByDefault = from.applyServerSideEncryptionByDefault; 
        }

        public ServerSideEncryptionRule build() {
            return new ServerSideEncryptionRule(this);
        }
    }
}
