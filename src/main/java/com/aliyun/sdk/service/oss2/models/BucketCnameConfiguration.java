package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the CNAME record.
 */
 @JacksonXmlRootElement(localName = "BucketCnameConfiguration")
public final class BucketCnameConfiguration {  
    @JacksonXmlProperty(localName = "Cname")
    private Cname cname;

    public BucketCnameConfiguration() {}

    private BucketCnameConfiguration(Builder builder) { 
        this.cname = builder.cname; 
    }

    /**
    * The container that stores the CNAME information.
    */
    public Cname cname() {
        return this.cname;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private Cname cname;
        
        /**
        * The container that stores the CNAME information.
        */
        public Builder cname(Cname value) {
            requireNonNull(value);
            this.cname = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(BucketCnameConfiguration from) { 
            this.cname = from.cname; 
        }

        public BucketCnameConfiguration build() {
            return new BucketCnameConfiguration(this);
        }
    }
}
