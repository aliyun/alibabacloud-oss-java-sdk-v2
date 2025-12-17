package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The container for listed redundancy type conversion tasks.
 */
@JacksonXmlRootElement(localName = "ListBucketDataRedundancyTransition")
public final class ListBucketDataRedundancyTransition {  
    @JacksonXmlProperty(localName = "BucketDataRedundancyTransition")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<BucketDataRedundancyTransition> bucketDataRedundancyTransition;
    
    @JacksonXmlProperty(localName = "IsTruncated")
    private Boolean isTruncated;
    
    @JacksonXmlProperty(localName = "NextContinuationToken")
    private String nextContinuationToken;

    public ListBucketDataRedundancyTransition() {}

    private ListBucketDataRedundancyTransition(Builder builder) { 
        this.bucketDataRedundancyTransition = builder.bucketDataRedundancyTransition;
        this.isTruncated = builder.isTruncated;
        this.nextContinuationToken = builder.nextContinuationToken;
    }

    /**
    * The information about the redundancy type conversion task.
    */
    public List<BucketDataRedundancyTransition> bucketDataRedundancyTransition() {
        return this.bucketDataRedundancyTransition;
    }
    
    /**
    * Indicates whether the returned results are truncated. 
    * true: The number of returned results is smaller than the number of results to be returned. In this case, NextContinuationToken is returned to indicate the position of the next object to be returned.
    * false: The number of returned results is equal to the number of results to be returned. In this case, no NextContinuationToken is returned.
    */
    public Boolean isTruncated() {
        return this.isTruncated;
    }
    
    /**
    * The token that specifies to start the next page. 
    * If the value of IsTruncated is true, you can use the value of NextContinuationToken as the value of continuation-token in the next request to obtain the objects returned in the next page.
    */
    public String nextContinuationToken() {
        return this.nextContinuationToken;
    }

    // Setters for XML deserialization
    public void setBucketDataRedundancyTransition(List<BucketDataRedundancyTransition> bucketDataRedundancyTransition) {
        this.bucketDataRedundancyTransition = bucketDataRedundancyTransition;
    }

    public void setIsTruncated(Boolean isTruncated) {
        this.isTruncated = isTruncated;
    }

    public void setNextContinuationToken(String nextContinuationToken) {
        this.nextContinuationToken = nextContinuationToken;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private List<BucketDataRedundancyTransition> bucketDataRedundancyTransition;
        private Boolean isTruncated;
        private String nextContinuationToken;
        
        /**
        * The information about the redundancy type conversion task.
        */
        public Builder bucketDataRedundancyTransition(List<BucketDataRedundancyTransition> value) {
            requireNonNull(value);
            this.bucketDataRedundancyTransition = value;
            return this;
        }
        
        /**
        * Indicates whether the returned results are truncated. 
        * true: The number of returned results is smaller than the number of results to be returned. In this case, NextContinuationToken is returned to indicate the position of the next object to be returned.
        * false: The number of returned results is equal to the number of results to be returned. In this case, no NextContinuationToken is returned.
        */
        public Builder isTruncated(Boolean value) {
            requireNonNull(value);
            this.isTruncated = value;
            return this;
        }
        
        /**
        * The token that specifies to start the next page. 
        * If the value of IsTruncated is true, you can use the value of NextContinuationToken as the value of continuation-token in the next request to obtain the objects returned in the next page.
        */
        public Builder nextContinuationToken(String value) {
            requireNonNull(value);
            this.nextContinuationToken = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(ListBucketDataRedundancyTransition from) { 
            this.bucketDataRedundancyTransition = from.bucketDataRedundancyTransition;
            this.isTruncated = from.isTruncated;
            this.nextContinuationToken = from.nextContinuationToken;
        }

        public ListBucketDataRedundancyTransition build() {
            return new ListBucketDataRedundancyTransition(this);
        }
    }
}