package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * Addresses information in the vector retrieval results of data indexing
 */
 @JacksonXmlRootElement(localName = "Addresses")
public final class MetaQueryAddresses {  
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Address")
    private List<MetaQueryAddress> address;

    public MetaQueryAddresses() {}

    private MetaQueryAddresses(Builder builder) { 
        this.address = builder.address; 
    }

    /**
     * Address information in the vector retrieval results of data indexing
     */
    public List<MetaQueryAddress> address() {
        return this.address;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<MetaQueryAddress> address;
        
        /**
         * Address information in the vector retrieval results of data indexing
         */
        public Builder address(List<MetaQueryAddress> value) {
            requireNonNull(value);
            this.address = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(MetaQueryAddresses from) { 
            this.address = from.address; 
        }

        public MetaQueryAddresses build() {
            return new MetaQueryAddresses(this);
        }
    }
}