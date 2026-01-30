package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * Address information in the vector retrieval results of data indexing
 */
 @JacksonXmlRootElement(localName = "Address")
public final class MetaQueryAddress {  
    @JacksonXmlProperty(localName = "AddressLine")
    private String addressLine;
 
    @JacksonXmlProperty(localName = "City")
    private String city;
 
    @JacksonXmlProperty(localName = "Country")
    private String country;
 
    @JacksonXmlProperty(localName = "District")
    private String district;
 
    @JacksonXmlProperty(localName = "Language")
    private String language;
 
    @JacksonXmlProperty(localName = "Province")
    private String province;
 
    @JacksonXmlProperty(localName = "Township")
    private String township;

    public MetaQueryAddress() {}

    private MetaQueryAddress(Builder builder) { 
        this.addressLine = builder.addressLine; 
        this.city = builder.city; 
        this.country = builder.country; 
        this.district = builder.district; 
        this.language = builder.language; 
        this.province = builder.province; 
        this.township = builder.township; 
    }

    /**
     * Full address line
     */
    public String addressLine() {
        return this.addressLine;
    }

    /**
     * City name
     */
    public String city() {
        return this.city;
    }

    /**
     * Country name
     */
    public String country() {
        return this.country;
    }

    /**
     * District name
     */
    public String district() {
        return this.district;
    }

    /**
     * Language used in the address, in BCP 47 format
     */
    public String language() {
        return this.language;
    }

    /**
     * Province name
     */
    public String province() {
        return this.province;
    }

    /**
     * Township name
     */
    public String township() {
        return this.township;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String addressLine;
        private String city;
        private String country;
        private String district;
        private String language;
        private String province;
        private String township;
        
        /**
         * Full address line
         */
        public Builder addressLine(String value) {
            requireNonNull(value);
            this.addressLine = value;
            return this;
        }
        
        /**
         * City name
         */
        public Builder city(String value) {
            requireNonNull(value);
            this.city = value;
            return this;
        }
        
        /**
         * Country name
         */
        public Builder country(String value) {
            requireNonNull(value);
            this.country = value;
            return this;
        }
        
        /**
         * District name
         */
        public Builder district(String value) {
            requireNonNull(value);
            this.district = value;
            return this;
        }
        
        /**
         * Language used in the address, in BCP 47 format
         */
        public Builder language(String value) {
            requireNonNull(value);
            this.language = value;
            return this;
        }
        
        /**
         * Province name
         */
        public Builder province(String value) {
            requireNonNull(value);
            this.province = value;
            return this;
        }
        
        /**
         * Township name
         */
        public Builder township(String value) {
            requireNonNull(value);
            this.township = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(MetaQueryAddress from) { 
            this.addressLine = from.addressLine; 
            this.city = from.city; 
            this.country = from.country; 
            this.district = from.district; 
            this.language = from.language; 
            this.province = from.province; 
            this.township = from.township; 
        }

        public MetaQueryAddress build() {
            return new MetaQueryAddress(this);
        }
    }
}