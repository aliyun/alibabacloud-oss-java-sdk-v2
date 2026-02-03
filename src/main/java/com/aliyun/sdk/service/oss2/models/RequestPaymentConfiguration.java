package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores payment configurations.
 */
 @JacksonXmlRootElement(localName = "RequestPaymentConfiguration")
public final class RequestPaymentConfiguration {  
    @JacksonXmlProperty(localName = "Payer")
    private String payer;

    public RequestPaymentConfiguration() {}

    private RequestPaymentConfiguration(Builder builder) { 
        this.payer = builder.payer; 
    }

    /**
    * The payer of the request and traffic fees. Valid values:*   BucketOwner: The bucket owner is charged request fees and traffic fees.*   Requester: The requester is charged request fees and traffic fees.
    */
    public String payer() {
        return this.payer;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String payer;
        
        /**
        * The payer of the request and traffic fees. Valid values:*   BucketOwner: The bucket owner is charged request fees and traffic fees.*   Requester: The requester is charged request fees and traffic fees.
        */
        public Builder payer(String value) {
            requireNonNull(value);
            this.payer = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(RequestPaymentConfiguration from) { 
            this.payer = from.payer; 
        }

        public RequestPaymentConfiguration build() {
            return new RequestPaymentConfiguration(this);
        }
    }
}
