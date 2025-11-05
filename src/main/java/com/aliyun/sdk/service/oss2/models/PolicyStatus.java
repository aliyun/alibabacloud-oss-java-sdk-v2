package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores public access information.
 */
 @JacksonXmlRootElement(localName = "PolicyStatus")
public final class PolicyStatus {  
    @JacksonXmlProperty(localName = "IsPublic")
    private Boolean isPublic;

    public PolicyStatus() {}

    private PolicyStatus(Builder builder) { 
        this.isPublic = builder.isPublic; 
    }

    /**
    * Indicates whether the current bucket policy allows public access.truefalse
    */
    public Boolean isPublic() {
        return this.isPublic;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private Boolean isPublic;
        
        /**
        * Indicates whether the current bucket policy allows public access.truefalse
        */
        public Builder isPublic(Boolean value) {
            requireNonNull(value);
            this.isPublic = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(PolicyStatus from) { 
            this.isPublic = from.isPublic; 
        }

        public PolicyStatus build() {
            return new PolicyStatus(this);
        }
    }
}
