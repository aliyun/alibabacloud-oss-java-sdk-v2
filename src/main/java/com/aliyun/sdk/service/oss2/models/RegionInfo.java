package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The information about the region.
 */
@JacksonXmlRootElement(localName = "RegionInfo")
public final class RegionInfo {
    @JacksonXmlProperty(localName = "Region")
    private String region;

    @JacksonXmlProperty(localName = "InternetEndpoint")
    private String internetEndpoint;

    @JacksonXmlProperty(localName = "InternalEndpoint")
    private String internalEndpoint;

    @JacksonXmlProperty(localName = "AccelerateEndpoint")
    private String accelerateEndpoint;

    public RegionInfo() {
    }

    private RegionInfo(Builder builder) {
        this.region = builder.region;
        this.internetEndpoint = builder.internetEndpoint;
        this.internalEndpoint = builder.internalEndpoint;
        this.accelerateEndpoint = builder.accelerateEndpoint;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The region ID.
     */
    public String region() {
        return this.region;
    }

    /**
     * The public endpoint of the region.
     */
    public String internetEndpoint() {
        return this.internetEndpoint;
    }

    /**
     * The internal endpoint of the region.
     */
    public String internalEndpoint() {
        return this.internalEndpoint;
    }

    /**
     * The acceleration endpoint of the region. The value is always oss-accelerate.aliyuncs.com.
     */
    public String accelerateEndpoint() {
        return this.accelerateEndpoint;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String region;
        private String internetEndpoint;
        private String internalEndpoint;
        private String accelerateEndpoint;

        private Builder() {
            super();
        }

        private Builder(RegionInfo from) {
            this.region = from.region;
            this.internetEndpoint = from.internetEndpoint;
            this.internalEndpoint = from.internalEndpoint;
            this.accelerateEndpoint = from.accelerateEndpoint;
        }

        /**
         * The region ID.
         */
        public Builder region(String value) {
            requireNonNull(value);
            this.region = value;
            return this;
        }

        /**
         * The public endpoint of the region.
         */
        public Builder internetEndpoint(String value) {
            requireNonNull(value);
            this.internetEndpoint = value;
            return this;
        }

        /**
         * The internal endpoint of the region.
         */
        public Builder internalEndpoint(String value) {
            requireNonNull(value);
            this.internalEndpoint = value;
            return this;
        }

        /**
         * The acceleration endpoint of the region. The value is always oss-accelerate.aliyuncs.com.
         */
        public Builder accelerateEndpoint(String value) {
            requireNonNull(value);
            this.accelerateEndpoint = value;
            return this;
        }

        public RegionInfo build() {
            return new RegionInfo(this);
        }
    }
}
