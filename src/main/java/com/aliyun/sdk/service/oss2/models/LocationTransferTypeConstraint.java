package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores regions in which the destination bucket can be located with TransferType specified.
 */
 @JacksonXmlRootElement(localName = "LocationTransferTypeConstraint")
public final class LocationTransferTypeConstraint {  
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "LocationTransferType")
    private List<LocationTransferType> locationTransferTypes;

    public LocationTransferTypeConstraint() {}

    private LocationTransferTypeConstraint(Builder builder) { 
        this.locationTransferTypes = builder.locationTransferTypes; 
    }

    /**
    * The container that stores regions in which the destination bucket can be located with the TransferType information.
    */
    public List<LocationTransferType> locationTransferTypes() {
        return this.locationTransferTypes;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<LocationTransferType> locationTransferTypes;
        
        /**
        * The container that stores regions in which the destination bucket can be located with the TransferType information.
        */
        public Builder locationTransferTypes(List<LocationTransferType> value) {
            requireNonNull(value);
            this.locationTransferTypes = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(LocationTransferTypeConstraint from) { 
            this.locationTransferTypes = from.locationTransferTypes; 
        }

        public LocationTransferTypeConstraint build() {
            return new LocationTransferTypeConstraint(this);
        }
    }
}
