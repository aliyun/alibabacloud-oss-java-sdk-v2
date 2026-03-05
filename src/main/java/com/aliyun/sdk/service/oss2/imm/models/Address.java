package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

    @JsonProperty("Language")
    private String language;

    @JsonProperty("AddressLine")
    private String addressLine;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Province")
    private String province;

    @JsonProperty("City")
    private String city;

    @JsonProperty("District")
    private String district;

    @JsonProperty("Township")
    private String township;

    public Address() {
    }

    public String getLanguage() {
        return language;
    }

    public Address setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public Address setAddressLine(String addressLine) {
        this.addressLine = addressLine;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Address setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getProvince() {
        return province;
    }

    public Address setProvince(String province) {
        this.province = province;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Address setCity(String city) {
        this.city = city;
        return this;
    }

    public String getDistrict() {
        return district;
    }

    public Address setDistrict(String district) {
        this.district = district;
        return this;
    }

    public String getTownship() {
        return township;
    }

    public Address setTownship(String township) {
        this.township = township;
        return this;
    }
}
