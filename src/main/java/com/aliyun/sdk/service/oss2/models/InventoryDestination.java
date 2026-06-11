package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores information about exported inventory lists.
 */
 @JacksonXmlRootElement(localName = "InventoryDestination")
public final class InventoryDestination {  
    @JacksonXmlProperty(localName = "OSSBucketDestination")
    private InventoryOSSBucketDestination ossBucketDestination;

    public InventoryDestination() {}

    private InventoryDestination(Builder builder) {
        this.ossBucketDestination = builder.ossBucketDestination;
    }

    /**
    * The container that stores information about the bucket in which exported inventory lists are stored.
    */
    public InventoryOSSBucketDestination ossBucketDestination() {
        return this.ossBucketDestination;
    }

    /**
     * @deprecated use {@link #ossBucketDestination()} instead
     */
    @Deprecated
    public InventoryOSSBucketDestination oSSBucketDestination() {
        return this.ossBucketDestination;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private InventoryOSSBucketDestination ossBucketDestination;

        /**
        * The container that stores information about the bucket in which exported inventory lists are stored.
        */
        public Builder ossBucketDestination(InventoryOSSBucketDestination value) {
            requireNonNull(value);
            this.ossBucketDestination = value;
            return this;
        }

        /**
         * @deprecated use {@link #ossBucketDestination(InventoryOSSBucketDestination)} instead
         */
        @Deprecated
        public Builder oSSBucketDestination(InventoryOSSBucketDestination value) {
            return ossBucketDestination(value);
        }


        private Builder() {
            super();
        }

        private Builder(InventoryDestination from) {
            this.ossBucketDestination = from.ossBucketDestination;
        }

        public InventoryDestination build() {
            return new InventoryDestination(this);
        }
    }
}
