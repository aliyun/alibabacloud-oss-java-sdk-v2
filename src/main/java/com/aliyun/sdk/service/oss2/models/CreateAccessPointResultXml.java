package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The container that stores the information about an access point.
 */
 @JacksonXmlRootElement(localName = "CreateAccessPointResult")
public final class CreateAccessPointResultXml {  
    @JacksonXmlProperty(localName = "Alias")
    private String alias;
 
    @JacksonXmlProperty(localName = "AccessPointArn")
    private String accessPointArn;

    public CreateAccessPointResultXml() {}

    private CreateAccessPointResultXml(Builder builder) { 
        this.alias = builder.alias; 
        this.accessPointArn = builder.accessPointArn; 
    }

    /**
    * The alias of the access point.
    */
    public String alias() {
        return this.alias;
    }

    /**
    * The Alibaba Cloud Resource Name (ARN) of the access point.
    */
    public String accessPointArn() {
        return this.accessPointArn;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String alias;
        private String accessPointArn;
        
        /**
        * The alias of the access point.
        */
        public Builder alias(String value) {
            requireNonNull(value);
            this.alias = value;
            return this;
        }
        
        /**
        * The Alibaba Cloud Resource Name (ARN) of the access point.
        */
        public Builder accessPointArn(String value) {
            requireNonNull(value);
            this.accessPointArn = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(CreateAccessPointResultXml from) { 
            this.alias = from.alias; 
            this.accessPointArn = from.accessPointArn; 
        }

        public CreateAccessPointResultXml build() {
            return new CreateAccessPointResultXml(this);
        }
    }
}