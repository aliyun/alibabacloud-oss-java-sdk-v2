package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores inventory configuration list.
 */
 @JacksonXmlRootElement(localName = "ListInventoryConfigurationsResult")
public final class ListInventoryConfigurationsResult {  
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "InventoryConfiguration")
    private List<InventoryConfiguration> inventoryConfigurations;
 
    @JacksonXmlProperty(localName = "IsTruncated")
    private Boolean isTruncated;
 
    @JacksonXmlProperty(localName = "NextContinuationToken")
    private String nextContinuationToken;

    public ListInventoryConfigurationsResult() {}

    private ListInventoryConfigurationsResult(Builder builder) { 
        this.inventoryConfigurations = builder.inventoryConfigurations; 
        this.isTruncated = builder.isTruncated; 
        this.nextContinuationToken = builder.nextContinuationToken; 
    }

    /**
    * The container that stores inventory configurations.
    */
    public List<InventoryConfiguration> inventoryConfigurations() {
        return this.inventoryConfigurations;
    }

    /**
    * Specifies whether to list all inventory tasks configured for the bucket.Valid values: true and false- The value of false indicates that all inventory tasks configured for the bucket are listed.- The value of true indicates that not all inventory tasks configured for the bucket are listed. To list the next page of inventory configurations, set the continuation-token parameter in the next request to the value of the NextContinuationToken header in the response to the current request.
    */
    public Boolean isTruncated() {
        return this.isTruncated;
    }

    /**
    * If the value of IsTruncated in the response is true and value of this header is not null, set the continuation-token parameter in the next request to the value of this header.
    */
    public String nextContinuationToken() {
        return this.nextContinuationToken;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<InventoryConfiguration> inventoryConfigurations;
        private Boolean isTruncated;
        private String nextContinuationToken;
        
        /**
        * The container that stores inventory configurations.
        */
        public Builder inventoryConfigurations(List<InventoryConfiguration> value) {
            requireNonNull(value);
            this.inventoryConfigurations = value;
            return this;
        }
        
        /**
        * Specifies whether to list all inventory tasks configured for the bucket.Valid values: true and false- The value of false indicates that all inventory tasks configured for the bucket are listed.- The value of true indicates that not all inventory tasks configured for the bucket are listed. To list the next page of inventory configurations, set the continuation-token parameter in the next request to the value of the NextContinuationToken header in the response to the current request.
        */
        public Builder isTruncated(Boolean value) {
            requireNonNull(value);
            this.isTruncated = value;
            return this;
        }
        
        /**
        * If the value of IsTruncated in the response is true and value of this header is not null, set the continuation-token parameter in the next request to the value of this header.
        */
        public Builder nextContinuationToken(String value) {
            requireNonNull(value);
            this.nextContinuationToken = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(ListInventoryConfigurationsResult from) { 
            this.inventoryConfigurations = from.inventoryConfigurations; 
            this.isTruncated = from.isTruncated; 
            this.nextContinuationToken = from.nextContinuationToken; 
        }

        public ListInventoryConfigurationsResult build() {
            return new ListInventoryConfigurationsResult(this);
        }
    }
}
