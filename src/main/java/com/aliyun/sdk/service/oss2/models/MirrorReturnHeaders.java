package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * Container to store the rules for setting response headers in mirror-based back-to-origin.
 */
 @JacksonXmlRootElement(localName = "MirrorReturnHeaders")
public final class MirrorReturnHeaders {  
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ReturnHeader")
    private List<ReturnHeader> returnHeaders;

    public MirrorReturnHeaders() {}

    private MirrorReturnHeaders(Builder builder) { 
        this.returnHeaders = builder.returnHeaders; 
    }

    /**
    * The rule list for setting response headers in mirror-based back-to-origin.
    */
    public List<ReturnHeader> returnHeaders() {
        return this.returnHeaders;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<ReturnHeader> returnHeaders;
        
        /**
        * The rule list for setting response headers in mirror-based back-to-origin.
        */
        public Builder returnHeaders(List<ReturnHeader> value) {
            requireNonNull(value);
            this.returnHeaders = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(MirrorReturnHeaders from) { 
            this.returnHeaders = from.returnHeaders; 
        }

        public MirrorReturnHeaders build() {
            return new MirrorReturnHeaders(this);
        }
    }
}
