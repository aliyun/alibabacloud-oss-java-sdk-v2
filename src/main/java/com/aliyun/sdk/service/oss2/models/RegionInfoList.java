package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The information about the regions.
 */
@JacksonXmlRootElement(localName = "RegionInfoList")
public final class RegionInfoList {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "RegionInfo")
    private List<RegionInfo> regionInfos;

    public RegionInfoList() {
    }

    private RegionInfoList(Builder builder) {
        this.regionInfos = builder.regionInfos;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The information about the regions.
     */
    public List<RegionInfo> regionInfos() {
        return this.regionInfos;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private List<RegionInfo> regionInfos;

        private Builder() {
            super();
        }


        private Builder(RegionInfoList from) {
            this.regionInfos = from.regionInfos;
        }

        /**
         * The information about the regions.
         */
        public Builder regionInfos(List<RegionInfo> value) {
            requireNonNull(value);
            this.regionInfos = value;
            return this;
        }

        public RegionInfoList build() {
            return new RegionInfoList(this);
        }
    }
}
