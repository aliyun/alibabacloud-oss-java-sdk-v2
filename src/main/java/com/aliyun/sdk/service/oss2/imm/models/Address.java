package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Address")
public final class Address {

    @JacksonXmlProperty(localName = "Language")
    private String language;

    @JacksonXmlProperty(localName = "AddressLine")
    private String addressLine;

    @JacksonXmlProperty(localName = "Country")
    private String country;

    @JacksonXmlProperty(localName = "Province")
    private String province;

    @JacksonXmlProperty(localName = "City")
    private String city;

    @JacksonXmlProperty(localName = "District")
    private String district;

    @JacksonXmlProperty(localName = "Township")
    private String township;

    public Address() {
    }

    private Address(Builder builder) {
        this.language = builder.language;
        this.addressLine = builder.addressLine;
        this.country = builder.country;
        this.province = builder.province;
        this.city = builder.city;
        this.district = builder.district;
        this.township = builder.township;
    }

    public String language() {
        return this.language;
    }

    public String addressLine() {
        return this.addressLine;
    }

    public String country() {
        return this.country;
    }

    public String province() {
        return this.province;
    }

    public String city() {
        return this.city;
    }

    public String district() {
        return this.district;
    }

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
        private String language;
        private String addressLine;
        private String country;
        private String province;
        private String city;
        private String district;
        private String township;

        public Builder language(String value) {
            this.language = value;
            return this;
        }

        public Builder addressLine(String value) {
            this.addressLine = value;
            return this;
        }

        public Builder country(String value) {
            this.country = value;
            return this;
        }

        public Builder province(String value) {
            this.province = value;
            return this;
        }

        public Builder city(String value) {
            this.city = value;
            return this;
        }

        public Builder district(String value) {
            this.district = value;
            return this;
        }

        public Builder township(String value) {
            this.township = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(Address from) {
            this.language = from.language;
            this.addressLine = from.addressLine;
            this.country = from.country;
            this.province = from.province;
            this.city = from.city;
            this.district = from.district;
            this.township = from.township;
        }

        public Address build() {
            return new Address(this);
        }
    }
}
