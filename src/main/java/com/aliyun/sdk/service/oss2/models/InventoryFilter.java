package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the prefix used to filter objects. Only objects whose names contain the specified prefix are included in the inventory.
 */
 @JacksonXmlRootElement(localName = "InventoryFilter")
public final class InventoryFilter {  
    @JacksonXmlProperty(localName = "LastModifyEndTimeStamp")
    private Long lastModifyEndTimeStamp;
 
    @JacksonXmlProperty(localName = "LowerSizeBound")
    private Long lowerSizeBound;
 
    @JacksonXmlProperty(localName = "UpperSizeBound")
    private Long upperSizeBound;
 
    @JacksonXmlProperty(localName = "StorageClass")
    private String storageClass;
 
    @JacksonXmlProperty(localName = "Prefix")
    private String prefix;
 
    @JacksonXmlProperty(localName = "LastModifyBeginTimeStamp")
    private Long lastModifyBeginTimeStamp;

    public InventoryFilter() {}

    private InventoryFilter(Builder builder) { 
        this.lastModifyEndTimeStamp = builder.lastModifyEndTimeStamp; 
        this.lowerSizeBound = builder.lowerSizeBound; 
        this.upperSizeBound = builder.upperSizeBound; 
        this.storageClass = builder.storageClass; 
        this.prefix = builder.prefix; 
        this.lastModifyBeginTimeStamp = builder.lastModifyBeginTimeStamp; 
    }

    /**
    * The end of the time range during which the object was last modified. Unit: seconds.Valid values: [1262275200, 253402271999]
    */
    public Long lastModifyEndTimeStamp() {
        return this.lastModifyEndTimeStamp;
    }

    /**
    * The minimum size of the specified object.Unit: B.Valid values: [0 B, 48.8 TB]
    */
    public Long lowerSizeBound() {
        return this.lowerSizeBound;
    }

    /**
    * The maximum size of the specified object.Unit: B.Valid values: [0B, 48.8TB]
    */
    public Long upperSizeBound() {
        return this.upperSizeBound;
    }

    /**
    * The storage class of the object. You can specify multiple storage classes.Valid values:- Standard- IA - Archive - ColdArchive - All
    */
    public String storageClass() {
        return this.storageClass;
    }

    /**
    * The prefix that is specified in the inventory.
    */
    public String prefix() {
        return this.prefix;
    }

    /**
    * The beginning of the time range during which the object was last modified. Unit: seconds.Valid values: [1262275200, 253402271999]
    */
    public Long lastModifyBeginTimeStamp() {
        return this.lastModifyBeginTimeStamp;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private Long lastModifyEndTimeStamp;
        private Long lowerSizeBound;
        private Long upperSizeBound;
        private String storageClass;
        private String prefix;
        private Long lastModifyBeginTimeStamp;
        
        /**
        * The end of the time range during which the object was last modified. Unit: seconds.Valid values: [1262275200, 253402271999]
        */
        public Builder lastModifyEndTimeStamp(Long value) {
            requireNonNull(value);
            this.lastModifyEndTimeStamp = value;
            return this;
        }
        
        /**
        * The minimum size of the specified object.Unit: B.Valid values: [0 B, 48.8 TB]
        */
        public Builder lowerSizeBound(Long value) {
            requireNonNull(value);
            this.lowerSizeBound = value;
            return this;
        }
        
        /**
        * The maximum size of the specified object.Unit: B.Valid values: [0B, 48.8TB]
        */
        public Builder upperSizeBound(Long value) {
            requireNonNull(value);
            this.upperSizeBound = value;
            return this;
        }
        
        /**
        * The storage class of the object. You can specify multiple storage classes.Valid values:- Standard- IA - Archive - ColdArchive - All
        */
        public Builder storageClass(String value) {
            requireNonNull(value);
            this.storageClass = value;
            return this;
        }
        
        /**
        * The prefix that is specified in the inventory.
        */
        public Builder prefix(String value) {
            requireNonNull(value);
            this.prefix = value;
            return this;
        }
        
        /**
        * The beginning of the time range during which the object was last modified. Unit: seconds.Valid values: [1262275200, 253402271999]
        */
        public Builder lastModifyBeginTimeStamp(Long value) {
            requireNonNull(value);
            this.lastModifyBeginTimeStamp = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(InventoryFilter from) { 
            this.lastModifyEndTimeStamp = from.lastModifyEndTimeStamp; 
            this.lowerSizeBound = from.lowerSizeBound; 
            this.upperSizeBound = from.upperSizeBound; 
            this.storageClass = from.storageClass; 
            this.prefix = from.prefix; 
            this.lastModifyBeginTimeStamp = from.lastModifyBeginTimeStamp; 
        }

        public InventoryFilter build() {
            return new InventoryFilter(this);
        }
    }
}
