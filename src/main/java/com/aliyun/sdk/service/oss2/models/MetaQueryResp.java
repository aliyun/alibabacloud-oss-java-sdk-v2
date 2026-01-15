package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * Container for data index query results.
 * Data index query response body.
 */
 @JacksonXmlRootElement(localName = "MetaQuery")
public final class MetaQueryResp {  
    @JacksonXmlProperty(localName = "Files")
    private MetaQueryFiles files;
    
    @JacksonXmlProperty(localName = "NextToken")
    private String nextToken;
    
    @JacksonXmlProperty(localName = "Aggregations")
    private MetaQueryAggregations aggregations;

    public MetaQueryResp() {}

    private MetaQueryResp(Builder builder) { 
        this.files = builder.files; 
        this.nextToken = builder.nextToken; 
        this.aggregations = builder.aggregations; 
    }

    /**
    * Container for Object information.
    * 
    * @return the container for Object information
    */
    public MetaQueryFiles files() {
        return this.files;
    }

    /**
    * Pagination token used when the total number of Objects exceeds the set MaxResults.
    * For the next Object listing, use this value as NextToken to return remaining results.
    * This parameter has a value only when not all Objects have been returned.
    * 
    * @return the pagination token
    */
    public String nextToken() {
        return this.nextToken;
    }

    /**
    * Container for aggregation operation result information.
    * 
    * @return the container for aggregation operation results
    */
    public MetaQueryAggregations aggregations() {
        return this.aggregations;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private MetaQueryFiles files;
        private String nextToken;
        private MetaQueryAggregations aggregations;
        
        /**
        * Sets the container for Object information.
        * 
        * @param value the container for Object information
        * @return the builder instance
        */
        public Builder files(MetaQueryFiles value) {
            requireNonNull(value);
            this.files = value;
            return this;
        }
        
        /**
        * Sets the pagination token used when the total number of Objects exceeds the set MaxResults.
        * For the next Object listing, use this value as NextToken to return remaining results.
        * This parameter has a value only when not all Objects have been returned.
        * 
        * @param value the pagination token
        * @return the builder instance
        */
        public Builder nextToken(String value) {
            this.nextToken = value;
            return this;
        }
        
        /**
        * Sets the container for aggregation operation result information.
        * 
        * @param value the container for aggregation operation results
        * @return the builder instance
        */
        public Builder aggregations(MetaQueryAggregations value) {
            this.aggregations = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(MetaQueryResp from) { 
            this.files = from.files; 
            this.nextToken = from.nextToken; 
            this.aggregations = from.aggregations; 
        }

        public MetaQueryResp build() {
            return new MetaQueryResp(this);
        }
    }
}