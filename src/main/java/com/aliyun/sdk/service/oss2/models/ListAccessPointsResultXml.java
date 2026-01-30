package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the information about access points.
 */
 @JacksonXmlRootElement(localName = "ListAccessPointsResult")
public final class ListAccessPointsResultXml {  
    @JacksonXmlProperty(localName = "IsTruncated")
    private String isTruncated;
 
    @JacksonXmlProperty(localName = "NextContinuationToken")
    private String nextContinuationToken;
 
    @JacksonXmlProperty(localName = "AccountId")
    private String accountId;
 
    @JacksonXmlProperty(localName = "AccessPoints")
    private AccessPoints accessPoints;
 
    @JacksonXmlProperty(localName = "MaxKeys")
    private Integer maxKeys;

    public ListAccessPointsResultXml() {}

    private ListAccessPointsResultXml(Builder builder) { 
        this.isTruncated = builder.isTruncated; 
        this.nextContinuationToken = builder.nextContinuationToken; 
        this.accountId = builder.accountId; 
        this.accessPoints = builder.accessPoints; 
        this.maxKeys = builder.maxKeys; 
    }

    /**
    * Indicates whether the returned list is truncated. Valid values: * true: indicates that not all results are returned. * false: indicates that all results are returned.
    */
    public String isTruncated() {
        return this.isTruncated;
    }

    /**
    * Indicates that this ListAccessPoints request does not return all results that can be listed. You can use NextContinuationToken to continue obtaining list results.
    */
    public String nextContinuationToken() {
        return this.nextContinuationToken;
    }

    /**
    * The ID of the Alibaba Cloud account to which the access point belongs.
    */
    public String accountId() {
        return this.accountId;
    }

    /**
    * The container that stores the information about all access points.
    */
    public AccessPoints accessPoints() {
        return this.accessPoints;
    }

    /**
    * The maximum number of results set for this enumeration operation.
    */
    public Integer maxKeys() {
        return this.maxKeys;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String isTruncated;
        private String nextContinuationToken;
        private String accountId;
        private AccessPoints accessPoints;
        private Integer maxKeys;
        
        /**
        * Indicates whether the returned list is truncated. Valid values: * true: indicates that not all results are returned. * false: indicates that all results are returned.
        */
        public Builder isTruncated(String value) {
            requireNonNull(value);
            this.isTruncated = value;
            return this;
        }
        
        /**
        * Indicates that this ListAccessPoints request does not return all results that can be listed. You can use NextContinuationToken to continue obtaining list results.
        */
        public Builder nextContinuationToken(String value) {
            requireNonNull(value);
            this.nextContinuationToken = value;
            return this;
        }
        
        /**
        * The ID of the Alibaba Cloud account to which the access point belongs.
        */
        public Builder accountId(String value) {
            requireNonNull(value);
            this.accountId = value;
            return this;
        }
        
        /**
        * The container that stores the information about all access points.
        */
        public Builder accessPoints(AccessPoints value) {
            requireNonNull(value);
            this.accessPoints = value;
            return this;
        }
        
        /**
        * The maximum number of results set for this enumeration operation.
        */
        public Builder maxKeys(Integer value) {
            requireNonNull(value);
            this.maxKeys = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(ListAccessPointsResultXml from) { 
            this.isTruncated = from.isTruncated; 
            this.nextContinuationToken = from.nextContinuationToken; 
            this.accountId = from.accountId; 
            this.accessPoints = from.accessPoints; 
            this.maxKeys = from.maxKeys; 
        }

        public ListAccessPointsResultXml build() {
            return new ListAccessPointsResultXml(this);
        }
    }
}