package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * Container for filter conditions, supporting logical operators AND and OR as well as comparison operators.
 * Filter expressions are separated by commas (,) indicating an AND relationship, while individual Filter expressions are connected with OR relationship.
 */
 @JacksonXmlRootElement(localName = "Filters")
public final class MetaQueryFilters {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Filter")
    private List<String> filter;

    public MetaQueryFilters() {}

    private MetaQueryFilters(Builder builder) {
        this.filter = builder.filter; 
    }

    /**
    * Filter condition expressions. Each expression represents filtering criteria, for example:
    * "Size > 1024,FileModifiedTime > 2025-06-03T09:20:47.999Z" means to filter files with size greater than 1024 bytes and modification time after 2025-06-03T09:20:47.999Z.
    * 
    * @return the list of filter condition expressions
    */
    public List<String> filter() {
        return this.filter;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<String> filter;
        
        /**
        * Sets the filter condition expressions. Each expression represents filtering criteria, for example:
        * "Size > 1024,FileModifiedTime > 2025-06-03T09:20:47.999Z" means to filter files with size greater than 1024 bytes and modification time after 2025-06-03T09:20:47.999Z.
        * 
        * @param value the list of filter condition expressions
        * @return the builder instance
        */
        public Builder filter(List<String> value) {
            requireNonNull(value);
            this.filter = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(MetaQueryFilters from) {
            this.filter = from.filter; 
        }

        public MetaQueryFilters build() {
            return new MetaQueryFilters(this);
        }
    }
}
