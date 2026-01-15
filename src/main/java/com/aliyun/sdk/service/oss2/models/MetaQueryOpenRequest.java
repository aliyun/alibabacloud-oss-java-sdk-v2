package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * Request body for enabling metadata management feature for a Bucket.
 */
 @JacksonXmlRootElement(localName = "MetaQuery")
public final class MetaQueryOpenRequest {  
    @JacksonXmlProperty(localName = "Filters")
    private MetaQueryFilters filters;

    public MetaQueryOpenRequest() {}

    private MetaQueryOpenRequest(Builder builder) { 
        this.filters = builder.filters; 
    }

    /**
    * Container for filter conditions, supporting logical operators AND and OR as well as comparison operators.
    * Filter expressions are separated by commas (,) indicating an AND relationship, while individual Filter expressions are connected with OR relationship.
    * 
    * @return the metadata query filters
    */
    public MetaQueryFilters filters() {
        return this.filters;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private MetaQueryFilters filters;
        
        /**
        * Sets the filters for the metadata query.
        * 
        * @param value the metadata query filters
        * @return the builder instance
        */
        public Builder filters(MetaQueryFilters value) {
            requireNonNull(value);
            this.filters = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(MetaQueryOpenRequest from) { 
            this.filters = from.filters; 
        }

        public MetaQueryOpenRequest build() {
            return new MetaQueryOpenRequest(this);
        }
    }
}
