package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the configurations of the inventory.
 */
 @JacksonXmlRootElement(localName = "InventoryConfiguration")
public final class InventoryConfiguration {  
    @JacksonXmlProperty(localName = "Schedule")
    private InventorySchedule schedule;
 
    @JacksonXmlProperty(localName = "Filter")
    private InventoryFilter filter;
 
    @JacksonXmlProperty(localName = "IncludedObjectVersions")
    private String includedObjectVersions;
 
    @JacksonXmlProperty(localName = "OptionalFields")
    private OptionalFields optionalFields;
 
    @JacksonXmlProperty(localName = "Id")
    private String id;
 
    @JacksonXmlProperty(localName = "IsEnabled")
    private Boolean isEnabled;
 
    @JacksonXmlProperty(localName = "Destination")
    private InventoryDestination destination;

    @JacksonXmlProperty(localName = "IncrementalInventory")
    private IncrementalInventory incrementalInventory;

    public InventoryConfiguration() {}

    private InventoryConfiguration(Builder builder) { 
        this.schedule = builder.schedule; 
        this.filter = builder.filter; 
        this.includedObjectVersions = builder.includedObjectVersions; 
        this.optionalFields = builder.optionalFields; 
        this.id = builder.id; 
        this.isEnabled = builder.isEnabled; 
        this.destination = builder.destination;
        this.incrementalInventory = builder.incrementalInventory;
    }

    /**
    * The container that stores information about the frequency at which inventory lists are exported.
    */
    public InventorySchedule schedule() {
        return this.schedule;
    }

    /**
    * The container that stores the prefix used to filter objects. Only objects whose names contain the specified prefix are included in the inventory.
    */
    public InventoryFilter filter() {
        return this.filter;
    }

    /**
    * Specifies whether to include the version information about the objects in inventory lists. Valid values:*   All: The information about all versions of the objects is exported.*   Current: Only the information about the current versions of the objects is exported.
    */
    public String includedObjectVersions() {
        return this.includedObjectVersions;
    }

    /**
    * The container that stores the configuration fields in inventory lists.
    */
    public OptionalFields optionalFields() {
        return this.optionalFields;
    }

    /**
    * The name of the inventory. The name must be unique in the bucket.
    */
    public String id() {
        return this.id;
    }

    /**
    * Specifies whether to enable the bucket inventory feature. Valid values:*   true*   false
    */
    public Boolean isEnabled() {
        return this.isEnabled;
    }

    /**
    * The container that stores the exported inventory lists.
    */
    public InventoryDestination destination() {
        return this.destination;
    }

    /**
     *
     */
    public IncrementalInventory incrementalInventory() {
        return this.incrementalInventory;
    }


    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private InventorySchedule schedule;
        private InventoryFilter filter;
        private String includedObjectVersions;
        private OptionalFields optionalFields;
        private String id;
        private Boolean isEnabled;
        private InventoryDestination destination;
        private IncrementalInventory incrementalInventory;
        
        /**
        * The container that stores information about the frequency at which inventory lists are exported.
        */
        public Builder schedule(InventorySchedule value) {
            requireNonNull(value);
            this.schedule = value;
            return this;
        }
        
        /**
        * The container that stores the prefix used to filter objects. Only objects whose names contain the specified prefix are included in the inventory.
        */
        public Builder filter(InventoryFilter value) {
            requireNonNull(value);
            this.filter = value;
            return this;
        }
        
        /**
        * Specifies whether to include the version information about the objects in inventory lists. Valid values:*   All: The information about all versions of the objects is exported.*   Current: Only the information about the current versions of the objects is exported.
        */
        public Builder includedObjectVersions(String value) {
            requireNonNull(value);
            this.includedObjectVersions = value;
            return this;
        }
        
        /**
        * The container that stores the configuration fields in inventory lists.
        */
        public Builder optionalFields(OptionalFields value) {
            requireNonNull(value);
            this.optionalFields = value;
            return this;
        }
        
        /**
        * The name of the inventory. The name must be unique in the bucket.
        */
        public Builder id(String value) {
            requireNonNull(value);
            this.id = value;
            return this;
        }
        
        /**
        * Specifies whether to enable the bucket inventory feature. Valid values:*   true*   false
        */
        public Builder isEnabled(Boolean value) {
            requireNonNull(value);
            this.isEnabled = value;
            return this;
        }
        
        /**
        * The container that stores the exported inventory lists.
        */
        public Builder destination(InventoryDestination value) {
            requireNonNull(value);
            this.destination = value;
            return this;
        }

        /**
         *
         */
        public Builder incrementalInventory(IncrementalInventory value) {
            requireNonNull(value);
            this.incrementalInventory = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(InventoryConfiguration from) { 
            this.schedule = from.schedule; 
            this.filter = from.filter; 
            this.includedObjectVersions = from.includedObjectVersions; 
            this.optionalFields = from.optionalFields; 
            this.id = from.id; 
            this.isEnabled = from.isEnabled; 
            this.destination = from.destination;
            this.incrementalInventory = from.incrementalInventory;
        }

        public InventoryConfiguration build() {
            return new InventoryConfiguration(this);
        }
    }
}
