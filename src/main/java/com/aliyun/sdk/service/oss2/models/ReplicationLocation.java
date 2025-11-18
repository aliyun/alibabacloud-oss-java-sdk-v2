package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the region in which the destination bucket can be located.
 */
 @JacksonXmlRootElement(localName = "ReplicationLocation")
public final class ReplicationLocation {  
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Location")
    private List<String> locations;
 
    @JacksonXmlProperty(localName = "LocationTransferTypeConstraint")
    private LocationTransferTypeConstraint locationTransferTypeConstraint;
 
    @JacksonXmlProperty(localName = "LocationRTCConstraint")
    private LocationRTCConstraint locationRTCConstraint;

    public ReplicationLocation() {}

    private ReplicationLocation(Builder builder) { 
        this.locations = builder.locations; 
        this.locationTransferTypeConstraint = builder.locationTransferTypeConstraint; 
        this.locationRTCConstraint = builder.locationRTCConstraint; 
    }

    /**
    * The regions in which the destination bucket can be located.
    */
    public List<String> locations() {
        return this.locations;
    }

    /**
    * The container that stores regions in which the destination bucket can be located with TransferType specified.
    */
    public LocationTransferTypeConstraint locationTransferTypeConstraint() {
        return this.locationTransferTypeConstraint;
    }

    /**
    * The container that stores regions in which the RTC can be enabled.
    */
    public LocationRTCConstraint locationRTCConstraint() {
        return this.locationRTCConstraint;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<String> locations;
        private LocationTransferTypeConstraint locationTransferTypeConstraint;
        private LocationRTCConstraint locationRTCConstraint;
        
        /**
        * The regions in which the destination bucket can be located.
        */
        public Builder locations(List<String> value) {
            requireNonNull(value);
            this.locations = value;
            return this;
        }
        
        /**
        * The container that stores regions in which the destination bucket can be located with TransferType specified.
        */
        public Builder locationTransferTypeConstraint(LocationTransferTypeConstraint value) {
            requireNonNull(value);
            this.locationTransferTypeConstraint = value;
            return this;
        }
        
        /**
        * The container that stores regions in which the RTC can be enabled.
        */
        public Builder locationRTCConstraint(LocationRTCConstraint value) {
            requireNonNull(value);
            this.locationRTCConstraint = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(ReplicationLocation from) { 
            this.locations = from.locations; 
            this.locationTransferTypeConstraint = from.locationTransferTypeConstraint; 
            this.locationRTCConstraint = from.locationRTCConstraint; 
        }

        public ReplicationLocation build() {
            return new ReplicationLocation(this);
        }
    }
}
