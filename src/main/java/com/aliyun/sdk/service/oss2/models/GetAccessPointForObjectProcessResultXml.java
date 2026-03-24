package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the information about an Object FC Access Point.
 */
@JacksonXmlRootElement(localName = "GetAccessPointForObjectProcessResult")
public final class GetAccessPointForObjectProcessResultXml {  
    @JacksonXmlProperty(localName = "CreationDate")
    private String creationDate;
 
    @JacksonXmlProperty(localName = "Endpoints")
    private AccessPointEndpoints endpoints;
 
    @JacksonXmlProperty(localName = "AllowAnonymousAccessForObjectProcess")
    private String allowAnonymousAccessForObjectProcess;
 
    @JacksonXmlProperty(localName = "PublicAccessBlockConfiguration")
    private PublicAccessBlockConfiguration publicAccessBlockConfiguration;
 
    @JacksonXmlProperty(localName = "AccessPointNameForObjectProcess")
    private String accessPointNameForObjectProcess;
 
    @JacksonXmlProperty(localName = "AccountId")
    private String accountId;
 
    @JacksonXmlProperty(localName = "AccessPointForObjectProcessArn")
    private String accessPointForObjectProcessArn;
 
    @JacksonXmlProperty(localName = "Status")
    private String status;
 
    @JacksonXmlProperty(localName = "AccessPointForObjectProcessAlias")
    private String accessPointForObjectProcessAlias;
 
    @JacksonXmlProperty(localName = "AccessPointName")
    private String accessPointName;

    public GetAccessPointForObjectProcessResultXml() {}

    private GetAccessPointForObjectProcessResultXml(Builder builder) { 
        this.creationDate = builder.creationDate; 
        this.endpoints = builder.endpoints; 
        this.allowAnonymousAccessForObjectProcess = builder.allowAnonymousAccessForObjectProcess; 
        this.publicAccessBlockConfiguration = builder.publicAccessBlockConfiguration; 
        this.accessPointNameForObjectProcess = builder.accessPointNameForObjectProcess; 
        this.accountId = builder.accountId; 
        this.accessPointForObjectProcessArn = builder.accessPointForObjectProcessArn; 
        this.status = builder.status; 
        this.accessPointForObjectProcessAlias = builder.accessPointForObjectProcessAlias; 
        this.accessPointName = builder.accessPointName; 
    }

    /**
    * The time when the Object FC Access Point was created. The value is a timestamp.
    */
    public String creationDate() {
        return this.creationDate;
    }

    /**
    * The container that stores the endpoints of the Object FC Access Point.
    */
    public AccessPointEndpoints endpoints() {
        return this.endpoints;
    }

    /**
    * Whether allow anonymous users to access this FC Access Point.
    */
    public String allowAnonymousAccessForObjectProcess() {
        return this.allowAnonymousAccessForObjectProcess;
    }

    /**
    * The public endpoint of the Object FC Access Point.
    */
    public PublicAccessBlockConfiguration publicAccessBlockConfiguration() {
        return this.publicAccessBlockConfiguration;
    }

    /**
    * The name of the Object FC Access Point.
    */
    public String accessPointNameForObjectProcess() {
        return this.accessPointNameForObjectProcess;
    }

    /**
    * The UID of the Alibaba Cloud account to which the Object FC Access Point belongs.
    */
    public String accountId() {
        return this.accountId;
    }

    /**
    * The public endpoint of the Object FC Access Point.
    */
    public String accessPointForObjectProcessArn() {
        return this.accessPointForObjectProcessArn;
    }

    /**
    * The status of the Object FC Access Point. Valid values:enable: The Object FC Access Point is created.disable: The Object FC Access Point is disabled.creating: The Object FC Access Point is being created.deleting: The Object FC Access Point is deleted.
    */
    public String status() {
        return this.status;
    }

    /**
    * The public endpoint of the Object FC Access Point.
    */
    public String accessPointForObjectProcessAlias() {
        return this.accessPointForObjectProcessAlias;
    }

    /**
    * The name of the access point.
    */
    public String accessPointName() {
        return this.accessPointName;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String creationDate;
        private AccessPointEndpoints endpoints;
        private String allowAnonymousAccessForObjectProcess;
        private PublicAccessBlockConfiguration publicAccessBlockConfiguration;
        private String accessPointNameForObjectProcess;
        private String accountId;
        private String accessPointForObjectProcessArn;
        private String status;
        private String accessPointForObjectProcessAlias;
        private String accessPointName;
        
        /**
        * The time when the Object FC Access Point was created. The value is a timestamp.
        */
        public Builder creationDate(String value) {
            requireNonNull(value);
            this.creationDate = value;
            return this;
        }
        
        /**
        * The container that stores the endpoints of the Object FC Access Point.
        */
        public Builder endpoints(AccessPointEndpoints value) {
            requireNonNull(value);
            this.endpoints = value;
            return this;
        }
        
        /**
        * Whether allow anonymous users to access this FC Access Point.
        */
        public Builder allowAnonymousAccessForObjectProcess(String value) {
            requireNonNull(value);
            this.allowAnonymousAccessForObjectProcess = value;
            return this;
        }
        
        /**
        * The public endpoint of the Object FC Access Point.
        */
        public Builder publicAccessBlockConfiguration(PublicAccessBlockConfiguration value) {
            requireNonNull(value);
            this.publicAccessBlockConfiguration = value;
            return this;
        }
        
        /**
        * The name of the Object FC Access Point.
        */
        public Builder accessPointNameForObjectProcess(String value) {
            requireNonNull(value);
            this.accessPointNameForObjectProcess = value;
            return this;
        }
        
        /**
        * The UID of the Alibaba Cloud account to which the Object FC Access Point belongs.
        */
        public Builder accountId(String value) {
            requireNonNull(value);
            this.accountId = value;
            return this;
        }
        
        /**
        * The public endpoint of the Object FC Access Point.
        */
        public Builder accessPointForObjectProcessArn(String value) {
            requireNonNull(value);
            this.accessPointForObjectProcessArn = value;
            return this;
        }
        
        /**
        * The status of the Object FC Access Point. Valid values:enable: The Object FC Access Point is created.disable: The Object FC Access Point is disabled.creating: The Object FC Access Point is being created.deleting: The Object FC Access Point is deleted.
        */
        public Builder status(String value) {
            requireNonNull(value);
            this.status = value;
            return this;
        }
        
        /**
        * The public endpoint of the Object FC Access Point.
        */
        public Builder accessPointForObjectProcessAlias(String value) {
            requireNonNull(value);
            this.accessPointForObjectProcessAlias = value;
            return this;
        }
        
        /**
        * The name of the access point.
        */
        public Builder accessPointName(String value) {
            requireNonNull(value);
            this.accessPointName = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(GetAccessPointForObjectProcessResultXml from) { 
            this.creationDate = from.creationDate; 
            this.endpoints = from.endpoints; 
            this.allowAnonymousAccessForObjectProcess = from.allowAnonymousAccessForObjectProcess; 
            this.publicAccessBlockConfiguration = from.publicAccessBlockConfiguration; 
            this.accessPointNameForObjectProcess = from.accessPointNameForObjectProcess; 
            this.accountId = from.accountId; 
            this.accessPointForObjectProcessArn = from.accessPointForObjectProcessArn; 
            this.status = from.status; 
            this.accessPointForObjectProcessAlias = from.accessPointForObjectProcessAlias; 
            this.accessPointName = from.accessPointName; 
        }

        public GetAccessPointForObjectProcessResultXml build() {
            return new GetAccessPointForObjectProcessResultXml(this);
        }
    }
}