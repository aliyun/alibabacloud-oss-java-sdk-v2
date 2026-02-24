package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the list of Object FC Access Points.
 */
@JacksonXmlRootElement(localName = "ListAccessPointsForObjectProcessResult")
public final class ListAccessPointsForObjectProcessResultXml {  
    @JacksonXmlProperty(localName = "IsTruncated")
    private Boolean isTruncated;
 
    @JacksonXmlProperty(localName = "NextContinuationToken")
    private String nextContinuationToken;
 
    @JacksonXmlProperty(localName = "AccountId")
    private String accountId;
 
    @JacksonXmlProperty(localName = "AccessPointsForObjectProcess")
    private AccessPointsForObjectProcess accessPointsForObjectProcess;

    public ListAccessPointsForObjectProcessResultXml() {}

    private ListAccessPointsForObjectProcessResultXml(Builder builder) { 
        this.isTruncated = builder.isTruncated; 
        this.nextContinuationToken = builder.nextContinuationToken; 
        this.accountId = builder.accountId; 
        this.accessPointsForObjectProcess = builder.accessPointsForObjectProcess; 
    }

    /**
    * Indicates whether the returned results are truncated. Valid values:true: indicates that not all results are returned for the request.false: indicates that all results are returned for the request.
    */
    public Boolean isTruncated() {
        return this.isTruncated;
    }

    /**
    * Indicates that this ListAccessPointsForObjectProcess request contains subsequent results. You need to set the NextContinuationToken element to continuation-token for subsequent results.
    */
    public String nextContinuationToken() {
        return this.nextContinuationToken;
    }

    /**
    * The UID of the Alibaba Cloud account to which the Object FC Access Points belong.
    */
    public String accountId() {
        return this.accountId;
    }

    /**
    * The container that stores the list of Object FC Access Points.
    */
    public AccessPointsForObjectProcess accessPointsForObjectProcess() {
        return this.accessPointsForObjectProcess;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private Boolean isTruncated;
        private String nextContinuationToken;
        private String accountId;
        private AccessPointsForObjectProcess accessPointsForObjectProcess;
        
        /**
        * Indicates whether the returned results are truncated. Valid values:true: indicates that not all results are returned for the request.false: indicates that all results are returned for the request.
        */
        public Builder isTruncated(Boolean value) {
            requireNonNull(value);
            this.isTruncated = value;
            return this;
        }
        
        /**
        * Indicates that this ListAccessPointsForObjectProcess request contains subsequent results. You need to set the NextContinuationToken element to continuation-token for subsequent results.
        */
        public Builder nextContinuationToken(String value) {
            requireNonNull(value);
            this.nextContinuationToken = value;
            return this;
        }
        
        /**
        * The UID of the Alibaba Cloud account to which the Object FC Access Points belong.
        */
        public Builder accountId(String value) {
            requireNonNull(value);
            this.accountId = value;
            return this;
        }
        
        /**
        * Sets the container that stores the list of Object FC Access Points.
        *
        * @param value The container that stores the list of Object FC Access Points.
        * @return This builder instance.
        */
        public Builder accessPointsForObjectProcess(AccessPointsForObjectProcess value) {
            requireNonNull(value);
            this.accessPointsForObjectProcess = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(ListAccessPointsForObjectProcessResultXml from) { 
            this.isTruncated = from.isTruncated; 
            this.nextContinuationToken = from.nextContinuationToken; 
            this.accountId = from.accountId; 
            this.accessPointsForObjectProcess = from.accessPointsForObjectProcess; 
        }

        public ListAccessPointsForObjectProcessResultXml build() {
            return new ListAccessPointsForObjectProcessResultXml(this);
        }
    }
}