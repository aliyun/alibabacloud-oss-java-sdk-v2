package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores information about a single Object FC Access Point.
 */
 @JacksonXmlRootElement(localName = "AccessPointForObjectProcess")
public final class AccessPointForObjectProcess {  
    @JacksonXmlProperty(localName = "AllowAnonymousAccessForObjectProcess")
    private String allowAnonymousAccessForObjectProcess;
 
    @JacksonXmlProperty(localName = "AccessPointNameForObjectProcess")
    private String accessPointNameForObjectProcess;
 
    @JacksonXmlProperty(localName = "AccessPointForObjectProcessAlias")
    private String accessPointForObjectProcessAlias;
 
    @JacksonXmlProperty(localName = "AccessPointName")
    private String accessPointName;
 
    @JacksonXmlProperty(localName = "Status")
    private String status;

    public AccessPointForObjectProcess() {}

    private AccessPointForObjectProcess(Builder builder) { 
        this.allowAnonymousAccessForObjectProcess = builder.allowAnonymousAccessForObjectProcess; 
        this.accessPointNameForObjectProcess = builder.accessPointNameForObjectProcess; 
        this.accessPointForObjectProcessAlias = builder.accessPointForObjectProcessAlias; 
        this.accessPointName = builder.accessPointName; 
        this.status = builder.status; 
    }

    /**
    * Whether allow anonymous user access this FC Access Point.
    */
    public String allowAnonymousAccessForObjectProcess() {
        return this.allowAnonymousAccessForObjectProcess;
    }

    /**
    * The name of the Object FC Access Point.
    */
    public String accessPointNameForObjectProcess() {
        return this.accessPointNameForObjectProcess;
    }

    /**
    * The alias of the Object FC Access Point.
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

    /**
    * The status of the Object FC Access Point. Valid values:enable: The Object FC Access Point is created.disable: The Object FC Access Point is disabled.creating: The Object FC Access Point is being created.deleting: The Object FC Access Point is deleted.
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
        private String allowAnonymousAccessForObjectProcess;
        private String accessPointNameForObjectProcess;
        private String accessPointForObjectProcessAlias;
        private String accessPointName;
        private String status;
        
        /**
        * Whether allow anonymous user access this FC Access Point.
        */
        public Builder allowAnonymousAccessForObjectProcess(String value) {
            requireNonNull(value);
            this.allowAnonymousAccessForObjectProcess = value;
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
        * The alias of the Object FC Access Point.
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
        
        /**
        * The status of the Object FC Access Point. Valid values:enable: The Object FC Access Point is created.disable: The Object FC Access Point is disabled.creating: The Object FC Access Point is being created.deleting: The Object FC Access Point is deleted.
        */
        public Builder status(String value) {
            requireNonNull(value);
            this.status = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(AccessPointForObjectProcess from) { 
            this.allowAnonymousAccessForObjectProcess = from.allowAnonymousAccessForObjectProcess; 
            this.accessPointNameForObjectProcess = from.accessPointNameForObjectProcess; 
            this.accessPointForObjectProcessAlias = from.accessPointForObjectProcessAlias; 
            this.accessPointName = from.accessPointName; 
            this.status = from.status; 
        }

        public AccessPointForObjectProcess build() {
            return new AccessPointForObjectProcess(this);
        }
    }
}
