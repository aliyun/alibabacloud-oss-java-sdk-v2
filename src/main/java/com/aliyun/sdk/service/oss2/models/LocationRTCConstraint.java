package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores regions in which the RTC can be enabled.
 */
 @JacksonXmlRootElement(localName = "LocationRTCConstraint")
public final class LocationRTCConstraint {  
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Location")
    private List<String> locations;

    public LocationRTCConstraint() {}

    private LocationRTCConstraint(Builder builder) { 
        this.locations = builder.locations; 
    }

    /**
    * The regions where RTC is supported.
    */
    public List<String> locations() {
        return this.locations;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<String> locations;
        
        /**
        * The regions where RTC is supported.
        */
        public Builder locations(List<String> value) {
            requireNonNull(value);
            this.locations = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(LocationRTCConstraint from) { 
            this.locations = from.locations; 
        }

        public LocationRTCConstraint build() {
            return new LocationRTCConstraint(this);
        }
    }
}
