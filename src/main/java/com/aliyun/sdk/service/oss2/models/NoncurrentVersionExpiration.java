package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The delete operation that you want OSS to perform on the previous versions of the objects that match the lifecycle rule when the previous versions expire.
 */
 @JacksonXmlRootElement(localName = "NoncurrentVersionExpiration")
public final class NoncurrentVersionExpiration {  
    @JacksonXmlProperty(localName = "NoncurrentDays")
    private Integer noncurrentDays;

    public NoncurrentVersionExpiration() {}

    private NoncurrentVersionExpiration(Builder builder) { 
        this.noncurrentDays = builder.noncurrentDays; 
    }

    /**
    * The number of days from when the objects became previous versions to when the lifecycle rule takes effect.
    */
    public Integer noncurrentDays() {
        return this.noncurrentDays;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private Integer noncurrentDays;
        
        /**
        * The number of days from when the objects became previous versions to when the lifecycle rule takes effect.
        */
        public Builder noncurrentDays(Integer value) {
            requireNonNull(value);
            this.noncurrentDays = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(NoncurrentVersionExpiration from) { 
            this.noncurrentDays = from.noncurrentDays; 
        }

        public NoncurrentVersionExpiration build() {
            return new NoncurrentVersionExpiration(this);
        }
    }
}
