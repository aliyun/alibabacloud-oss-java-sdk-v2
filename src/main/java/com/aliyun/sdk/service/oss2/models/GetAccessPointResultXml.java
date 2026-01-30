package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The container that stores the information about an access point.
 */
 @JacksonXmlRootElement(localName = "GetAccessPointResult")
public final class GetAccessPointResultXml {  
    @JacksonXmlProperty(localName = "AccessPointName")
    private String accessPointName;
 
    @JacksonXmlProperty(localName = "Bucket")
    private String bucket;
 
    @JacksonXmlProperty(localName = "AccountId")
    private String accountId;
 
    @JacksonXmlProperty(localName = "NetworkOrigin")
    private String networkOrigin;
 
    @JacksonXmlProperty(localName = "Endpoints")
    private AccessPointEndpoints endpoints;
 
    @JacksonXmlProperty(localName = "CreationDate")
    private String creationDate;
 
    @JacksonXmlProperty(localName = "VpcConfiguration")
    private AccessPointVpcConfiguration vpcConfiguration;
 
    @JacksonXmlProperty(localName = "AccessPointArn")
    private String accessPointArn;
 
    @JacksonXmlProperty(localName = "Alias")
    private String alias;
 
    @JacksonXmlProperty(localName = "Status")
    private String status;
 
    @JacksonXmlProperty(localName = "PublicAccessBlockConfiguration")
    private PublicAccessBlockConfiguration publicAccessBlockConfiguration;

    public GetAccessPointResultXml() {}

    private GetAccessPointResultXml(Builder builder) { 
        this.accessPointName = builder.accessPointName; 
        this.bucket = builder.bucket; 
        this.accountId = builder.accountId; 
        this.networkOrigin = builder.networkOrigin; 
        this.endpoints = builder.endpoints; 
        this.creationDate = builder.creationDate; 
        this.vpcConfiguration = builder.vpcConfiguration; 
        this.accessPointArn = builder.accessPointArn; 
        this.alias = builder.alias; 
        this.status = builder.status; 
        this.publicAccessBlockConfiguration = builder.publicAccessBlockConfiguration; 
    }

    /**
    * The name of the access point.
    */
    public String accessPointName() {
        return this.accessPointName;
    }

    /**
    * The name of the bucket for which the access point is configured.
    */
    public String bucket() {
        return this.bucket;
    }

    /**
    * The ID of the Alibaba Cloud account for which the access point is configured.
    */
    public String accountId() {
        return this.accountId;
    }

    /**
    * The network origin of the access point. Valid values: vpc and internet. vpc: You can only use the specified VPC ID to access the access point. internet: You can use public endpoints and internal endpoints to access the access point.
    */
    public String networkOrigin() {
        return this.networkOrigin;
    }

    /**
    * The container that stores the network origin information about the access point.
    */
    public AccessPointEndpoints endpoints() {
        return this.endpoints;
    }

    /**
    * The creation date of the access point.
    */
    public String creationDate() {
        return this.creationDate;
    }

    /**
    * The container that stores the information about the VPC.
    */
    public AccessPointVpcConfiguration vpcConfiguration() {
        return this.vpcConfiguration;
    }

    /**
    * The ARN of the access point.
    */
    public String accessPointArn() {
        return this.accessPointArn;
    }

    /**
    * The alias of the access point.
    */
    public String alias() {
        return this.alias;
    }

    /**
    * The status of the access point.
    */
    public String status() {
        return this.status;
    }

    /**
    * Configuration to block public access for the access point.
    */
    public PublicAccessBlockConfiguration publicAccessBlockConfiguration() {
        return this.publicAccessBlockConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String accessPointName;
        private String bucket;
        private String accountId;
        private String networkOrigin;
        private AccessPointEndpoints endpoints;
        private String creationDate;
        private AccessPointVpcConfiguration vpcConfiguration;
        private String accessPointArn;
        private String alias;
        private String status;
        private PublicAccessBlockConfiguration publicAccessBlockConfiguration;
        
        /**
        * The name of the access point.
        */
        public Builder accessPointName(String value) {
            requireNonNull(value);
            this.accessPointName = value;
            return this;
        }
        
        /**
        * The name of the bucket for which the access point is configured.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }
        
        /**
        * The ID of the Alibaba Cloud account for which the access point is configured.
        */
        public Builder accountId(String value) {
            requireNonNull(value);
            this.accountId = value;
            return this;
        }
        
        /**
        * The network origin of the access point. Valid values: vpc and internet. vpc: You can only use the specified VPC ID to access the access point. internet: You can use public endpoints and internal endpoints to access the access point.
        */
        public Builder networkOrigin(String value) {
            requireNonNull(value);
            this.networkOrigin = value;
            return this;
        }
        
        /**
        * The container that stores the network origin information about the access point.
        */
        public Builder endpoints(AccessPointEndpoints value) {
            requireNonNull(value);
            this.endpoints = value;
            return this;
        }
        
        /**
        * The creation date of the access point.
        */
        public Builder creationDate(String value) {
            requireNonNull(value);
            this.creationDate = value;
            return this;
        }
        
        /**
        * The container that stores the information about the VPC.
        */
        public Builder vpcConfiguration(AccessPointVpcConfiguration value) {
            requireNonNull(value);
            this.vpcConfiguration = value;
            return this;
        }
        
        /**
        * The ARN of the access point.
        */
        public Builder accessPointArn(String value) {
            requireNonNull(value);
            this.accessPointArn = value;
            return this;
        }
        
        /**
        * The alias of the access point.
        */
        public Builder alias(String value) {
            requireNonNull(value);
            this.alias = value;
            return this;
        }
        
        /**
        * The status of the access point.
        */
        public Builder status(String value) {
            requireNonNull(value);
            this.status = value;
            return this;
        }
        
        /**
        * Configuration to block public access for the access point.
        */
        public Builder publicAccessBlockConfiguration(PublicAccessBlockConfiguration value) {
            requireNonNull(value);
            this.publicAccessBlockConfiguration = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(GetAccessPointResultXml from) { 
            this.accessPointName = from.accessPointName; 
            this.bucket = from.bucket; 
            this.accountId = from.accountId; 
            this.networkOrigin = from.networkOrigin; 
            this.endpoints = from.endpoints; 
            this.creationDate = from.creationDate; 
            this.vpcConfiguration = from.vpcConfiguration; 
            this.accessPointArn = from.accessPointArn; 
            this.alias = from.alias; 
            this.status = from.status; 
            this.publicAccessBlockConfiguration = from.publicAccessBlockConfiguration; 
        }

        public GetAccessPointResultXml build() {
            return new GetAccessPointResultXml(this);
        }
    }
}