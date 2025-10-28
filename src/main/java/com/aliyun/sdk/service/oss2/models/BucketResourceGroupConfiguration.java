package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The configurations of the resource group to which the bucket belongs.
 */
@JacksonXmlRootElement(localName = "BucketResourceGroupConfiguration")
public final class BucketResourceGroupConfiguration {
    @JacksonXmlProperty(localName = "ResourceGroupId")
    private String resourceGroupId;

    public BucketResourceGroupConfiguration() {}

    private BucketResourceGroupConfiguration(Builder builder) {
        this.resourceGroupId = builder.resourceGroupId;
    }

    /**
     * The ID of the resource group to which the bucket belongs.
     */
    public String resourceGroupId() {
        return this.resourceGroupId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String resourceGroupId;

        /**
         * The ID of the resource group to which the bucket belongs.
         */
        public Builder resourceGroupId(String value) {
            requireNonNull(value);
            this.resourceGroupId = value;
            return this;
        }


        private Builder() {
            super();
        }

        private Builder(BucketResourceGroupConfiguration from) {
            this.resourceGroupId = from.resourceGroupId;
        }

        public BucketResourceGroupConfiguration build() {
            return new BucketResourceGroupConfiguration(this);
        }
    }
}
