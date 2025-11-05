package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the hotlink protection configurations.
 */
 @JacksonXmlRootElement(localName = "RefererConfiguration")
public final class RefererConfiguration {  
    @JacksonXmlProperty(localName = "RefererList")
    private RefererList refererList;
 
    @JacksonXmlProperty(localName = "RefererBlacklist")
    private RefererBlacklist refererBlacklist;
 
    @JacksonXmlProperty(localName = "AllowEmptyReferer")
    private Boolean allowEmptyReferer;
 
    @JacksonXmlProperty(localName = "AllowTruncateQueryString")
    private Boolean allowTruncateQueryString;
 
    @JacksonXmlProperty(localName = "TruncatePath")
    private Boolean truncatePath;

    public RefererConfiguration() {}

    private RefererConfiguration(Builder builder) { 
        this.refererList = builder.refererList; 
        this.refererBlacklist = builder.refererBlacklist; 
        this.allowEmptyReferer = builder.allowEmptyReferer; 
        this.allowTruncateQueryString = builder.allowTruncateQueryString; 
        this.truncatePath = builder.truncatePath; 
    }

    /**
    * The container that stores the Referer whitelist.  ****The PutBucketReferer operation overwrites the existing Referer whitelist with the Referer whitelist specified in RefererList. If RefererList is not specified in the request, which specifies that no Referer elements are included, the operation clears the existing Referer whitelist.
    */
    public RefererList refererList() {
        return this.refererList;
    }

    /**
    * The container that stores the Referer blacklist.
    */
    public RefererBlacklist refererBlacklist() {
        return this.refererBlacklist;
    }

    /**
    * Specifies whether to allow a request whose Referer field is empty. Valid values:*   true (default)*   false
    */
    public Boolean allowEmptyReferer() {
        return this.allowEmptyReferer;
    }

    /**
    * Specifies whether to truncate the query string in the URL when the Referer is matched. Valid values:*   true (default)*   false
    */
    public Boolean allowTruncateQueryString() {
        return this.allowTruncateQueryString;
    }

    /**
    * Specifies whether to truncate the path and parts that follow the path in the URL when the Referer is matched. Valid values:*   true*   false
    */
    public Boolean truncatePath() {
        return this.truncatePath;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private RefererList refererList;
        private RefererBlacklist refererBlacklist;
        private Boolean allowEmptyReferer;
        private Boolean allowTruncateQueryString;
        private Boolean truncatePath;
        
        /**
        * The container that stores the Referer whitelist.  ****The PutBucketReferer operation overwrites the existing Referer whitelist with the Referer whitelist specified in RefererList. If RefererList is not specified in the request, which specifies that no Referer elements are included, the operation clears the existing Referer whitelist.
        */
        public Builder refererList(RefererList value) {
            requireNonNull(value);
            this.refererList = value;
            return this;
        }
        
        /**
        * The container that stores the Referer blacklist.
        */
        public Builder refererBlacklist(RefererBlacklist value) {
            requireNonNull(value);
            this.refererBlacklist = value;
            return this;
        }
        
        /**
        * Specifies whether to allow a request whose Referer field is empty. Valid values:*   true (default)*   false
        */
        public Builder allowEmptyReferer(Boolean value) {
            requireNonNull(value);
            this.allowEmptyReferer = value;
            return this;
        }
        
        /**
        * Specifies whether to truncate the query string in the URL when the Referer is matched. Valid values:*   true (default)*   false
        */
        public Builder allowTruncateQueryString(Boolean value) {
            requireNonNull(value);
            this.allowTruncateQueryString = value;
            return this;
        }
        
        /**
        * Specifies whether to truncate the path and parts that follow the path in the URL when the Referer is matched. Valid values:*   true*   false
        */
        public Builder truncatePath(Boolean value) {
            requireNonNull(value);
            this.truncatePath = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(RefererConfiguration from) { 
            this.refererList = from.refererList; 
            this.refererBlacklist = from.refererBlacklist; 
            this.allowEmptyReferer = from.allowEmptyReferer; 
            this.allowTruncateQueryString = from.allowTruncateQueryString; 
            this.truncatePath = from.truncatePath; 
        }

        public RefererConfiguration build() {
            return new RefererConfiguration(this);
        }
    }
}
