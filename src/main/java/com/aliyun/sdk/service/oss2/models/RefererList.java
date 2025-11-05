package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the Referer whitelist.  ****The PutBucketReferer operation overwrites the existing Referer whitelist with the Referer whitelist specified in RefererList. If RefererList is not specified in the request, which specifies that no Referer elements are included, the operation clears the existing Referer whitelist.
 */
 @JacksonXmlRootElement(localName = "RefererList")
public final class RefererList {  
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Referer")
    private List<String> referers;

    public RefererList() {}

    private RefererList(Builder builder) { 
        this.referers = builder.referers; 
    }

    /**
    * The addresses in the Referer whitelist.
    */
    public List<String> referers() {
        return this.referers;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<String> referers;
        
        /**
        * The addresses in the Referer whitelist.
        */
        public Builder referers(List<String> value) {
            requireNonNull(value);
            this.referers = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(RefererList from) { 
            this.referers = from.referers; 
        }

        public RefererList build() {
            return new RefererList(this);
        }
    }
}
