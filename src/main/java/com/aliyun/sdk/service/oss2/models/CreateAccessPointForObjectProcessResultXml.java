package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the information about an Object FC Access Point.
 */
@JacksonXmlRootElement(localName = "CreateAccessPointForObjectProcessResult")
public final class CreateAccessPointForObjectProcessResultXml {  
    @JacksonXmlProperty(localName = "AccessPointForObjectProcessArn")
    private String accessPointForObjectProcessArn;
 
    @JacksonXmlProperty(localName = "AccessPointForObjectProcessAlias")
    private String accessPointForObjectProcessAlias;

    public CreateAccessPointForObjectProcessResultXml() {}

    private CreateAccessPointForObjectProcessResultXml(Builder builder) { 
        this.accessPointForObjectProcessArn = builder.accessPointForObjectProcessArn; 
        this.accessPointForObjectProcessAlias = builder.accessPointForObjectProcessAlias; 
    }

    /**
    * The ARN of the Object FC Access Point.
    */
    public String accessPointForObjectProcessArn() {
        return this.accessPointForObjectProcessArn;
    }

    /**
    * The alias of the Object FC Access Point.
    */
    public String accessPointForObjectProcessAlias() {
        return this.accessPointForObjectProcessAlias;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String accessPointForObjectProcessArn;
        private String accessPointForObjectProcessAlias;
        
        /**
        * The ARN of the Object FC Access Point.
        */
        public Builder accessPointForObjectProcessArn(String value) {
            requireNonNull(value);
            this.accessPointForObjectProcessArn = value;
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
        

        private Builder() {
            super();
        }

        private Builder(CreateAccessPointForObjectProcessResultXml from) { 
            this.accessPointForObjectProcessArn = from.accessPointForObjectProcessArn; 
            this.accessPointForObjectProcessAlias = from.accessPointForObjectProcessAlias; 
        }

        public CreateAccessPointForObjectProcessResultXml build() {
            return new CreateAccessPointForObjectProcessResultXml(this);
        }
    }
}