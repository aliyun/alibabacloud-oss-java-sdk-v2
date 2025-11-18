package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores regions in which the destination bucket can be located with the TransferType information.
 */
 @JacksonXmlRootElement(localName = "LocationTransferType")
public final class LocationTransferType {  
    @JacksonXmlProperty(localName = "Location")
    private String location;
 
    @JacksonXmlProperty(localName = "TransferTypes")
    private TransferTypes transferTypes;

    public LocationTransferType() {}

    private LocationTransferType(Builder builder) { 
        this.location = builder.location; 
        this.transferTypes = builder.transferTypes; 
    }

    /**
    * The regions in which the destination bucket can be located.
    */
    public String location() {
        return this.location;
    }

    /**
    * The container that stores the transfer type.
    */
    public TransferTypes transferTypes() {
        return this.transferTypes;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String location;
        private TransferTypes transferTypes;
        
        /**
        * The regions in which the destination bucket can be located.
        */
        public Builder location(String value) {
            requireNonNull(value);
            this.location = value;
            return this;
        }
        
        /**
        * The container that stores the transfer type.
        */
        public Builder transferTypes(TransferTypes value) {
            requireNonNull(value);
            this.transferTypes = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(LocationTransferType from) { 
            this.location = from.location; 
            this.transferTypes = from.transferTypes; 
        }

        public LocationTransferType build() {
            return new LocationTransferType(this);
        }
    }
}
