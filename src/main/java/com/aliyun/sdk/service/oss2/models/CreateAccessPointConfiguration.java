package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the information about an access point.
 */
 @JacksonXmlRootElement(localName = "CreateAccessPointConfiguration")
public final class CreateAccessPointConfiguration {  
    @JacksonXmlProperty(localName = "AccessPointName")
    private String accessPointName;
 
    @JacksonXmlProperty(localName = "NetworkOrigin")
    private String networkOrigin;
 
    @JacksonXmlProperty(localName = "VpcConfiguration")
    private AccessPointVpcConfiguration vpcConfiguration;

    public CreateAccessPointConfiguration() {}

    private CreateAccessPointConfiguration(Builder builder) { 
        this.accessPointName = builder.accessPointName; 
        this.networkOrigin = builder.networkOrigin; 
        this.vpcConfiguration = builder.vpcConfiguration; 
    }

    /**
    * The name of the access point. The name of the access point must meet the following naming rules:*   The name must be unique in a region of your Alibaba Cloud account.*   The name cannot end with -ossalias.*   The name can contain only lowercase letters, digits, and hyphens (-). It cannot start or end with a hyphen (-).*   The name must be 3 to 19 characters in length.
    */
    public String accessPointName() {
        return this.accessPointName;
    }

    /**
    * The network origin of the access point.
    */
    public String networkOrigin() {
        return this.networkOrigin;
    }

    /**
    * The container that stores the information about the VPC.
    */
    public AccessPointVpcConfiguration vpcConfiguration() {
        return this.vpcConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String accessPointName;
        private String networkOrigin;
        private AccessPointVpcConfiguration vpcConfiguration;
        
        /**
        * The name of the access point. The name of the access point must meet the following naming rules:*   The name must be unique in a region of your Alibaba Cloud account.*   The name cannot end with -ossalias.*   The name can contain only lowercase letters, digits, and hyphens (-). It cannot start or end with a hyphen (-).*   The name must be 3 to 19 characters in length.
        */
        public Builder accessPointName(String value) {
            requireNonNull(value);
            this.accessPointName = value;
            return this;
        }
        
        /**
        * The network origin of the access point.
        */
        public Builder networkOrigin(String value) {
            requireNonNull(value);
            this.networkOrigin = value;
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
        

        private Builder() {
            super();
        }

        private Builder(CreateAccessPointConfiguration from) { 
            this.accessPointName = from.accessPointName; 
            this.networkOrigin = from.networkOrigin; 
            this.vpcConfiguration = from.vpcConfiguration; 
        }

        public CreateAccessPointConfiguration build() {
            return new CreateAccessPointConfiguration(this);
        }
    }
}
