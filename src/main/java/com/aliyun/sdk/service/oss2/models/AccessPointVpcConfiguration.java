package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the information about the VPC.
 */
 @JacksonXmlRootElement(localName = "VpcConfiguration")
public final class AccessPointVpcConfiguration {  
    @JacksonXmlProperty(localName = "VpcId")
    private String vpcId;

    public AccessPointVpcConfiguration() {}

    private AccessPointVpcConfiguration(Builder builder) { 
        this.vpcId = builder.vpcId; 
    }

    /**
    * The ID of the VPC that is required only when the NetworkOrigin parameter is set to vpc.
    */
    public String vpcId() {
        return this.vpcId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String vpcId;
        
        /**
        * The ID of the VPC that is required only when the NetworkOrigin parameter is set to vpc.
        */
        public Builder vpcId(String value) {
            requireNonNull(value);
            this.vpcId = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(AccessPointVpcConfiguration from) { 
            this.vpcId = from.vpcId; 
        }

        public AccessPointVpcConfiguration build() {
            return new AccessPointVpcConfiguration(this);
        }
    }
}
