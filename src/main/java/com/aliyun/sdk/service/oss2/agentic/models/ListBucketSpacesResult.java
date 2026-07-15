package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.Owner;
import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * The result for the ListBucketSpaces operation.
 */
public final class ListBucketSpacesResult extends ResultModel {

    /**
     * The parsed XML body of the result.
     */
    public ListBucketSpacesResultXml resultBody() {
        return (ListBucketSpacesResultXml) innerBody;
    }

    /**
     * The owner of the bucket spaces.
     */
    public Owner owner() {
        ListBucketSpacesResultXml body = resultBody();
        return body != null ? body.owner : null;
    }

    /**
     * The prefix that the returned names must contain.
     */
    public String prefix() {
        ListBucketSpacesResultXml body = resultBody();
        return body != null ? body.prefix : null;
    }

    /**
     * The maximum number of results to return.
     */
    public Integer maxKeys() {
        ListBucketSpacesResultXml body = resultBody();
        return body != null ? body.maxKeys : null;
    }

    /**
     * The token from which the list operation continues.
     */
    public String continuationToken() {
        ListBucketSpacesResultXml body = resultBody();
        return body != null ? body.continuationToken : null;
    }

    /**
     * The token for the next page of results.
     */
    public String nextContinuationToken() {
        ListBucketSpacesResultXml body = resultBody();
        return body != null ? body.nextContinuationToken : null;
    }

    /**
     * Indicates whether the list is truncated.
     */
    public Boolean isTruncated() {
        ListBucketSpacesResultXml body = resultBody();
        return body != null ? body.isTruncated : null;
    }

    /**
     * The list of bucket spaces.
     */
    public List<BucketSpaceSummary> bucketSpaces() {
        ListBucketSpacesResultXml body = resultBody();
        return body != null ? body.bucketSpaces : null;
    }

    ListBucketSpacesResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() { super(); }

        public ListBucketSpacesResult build() {
            return new ListBucketSpacesResult(this);
        }
    }

    @JacksonXmlRootElement(localName = "ListBucketSpacesResult")
    public static class ListBucketSpacesResultXml {
        @JacksonXmlProperty(localName = "Owner")
        public Owner owner;

        @JacksonXmlProperty(localName = "Prefix")
        public String prefix;

        @JacksonXmlProperty(localName = "MaxKeys")
        public Integer maxKeys;

        @JacksonXmlProperty(localName = "ContinuationToken")
        public String continuationToken;

        @JacksonXmlProperty(localName = "NextContinuationToken")
        public String nextContinuationToken;

        @JacksonXmlProperty(localName = "IsTruncated")
        public Boolean isTruncated;

        @JacksonXmlElementWrapper(localName = "BucketSpaces")
        @JacksonXmlProperty(localName = "BucketSpace")
        public List<BucketSpaceSummary> bucketSpaces;
    }
}
