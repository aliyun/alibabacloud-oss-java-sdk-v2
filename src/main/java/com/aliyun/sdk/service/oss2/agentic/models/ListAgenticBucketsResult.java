package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * The result for the ListAgenticBuckets operation.
 */
public final class ListAgenticBucketsResult extends ResultModel {

    /**
     * The parsed XML body of the result.
     */
    public ListAgenticBucketsResultXml resultBody() {
        return (ListAgenticBucketsResultXml) innerBody;
    }

    /**
     * The region in which the buckets are located.
     */
    public String region() {
        ListAgenticBucketsResultXml body = resultBody();
        return body != null ? body.region : null;
    }

    /**
     * The owner of the buckets.
     */
    public String owner() {
        ListAgenticBucketsResultXml body = resultBody();
        return body != null ? body.owner : null;
    }

    /**
     * The token from which the list operation continues.
     */
    public String continuationToken() {
        ListAgenticBucketsResultXml body = resultBody();
        return body != null ? body.continuationToken : null;
    }

    /**
     * The token for the next page of results.
     */
    public String nextContinuationToken() {
        ListAgenticBucketsResultXml body = resultBody();
        return body != null ? body.nextContinuationToken : null;
    }

    /**
     * Indicates whether the list is truncated.
     */
    public Boolean isTruncated() {
        ListAgenticBucketsResultXml body = resultBody();
        return body != null ? body.isTruncated : null;
    }

    /**
     * The list of agentic buckets.
     */
    public List<AgenticBucketSummary> agenticBuckets() {
        ListAgenticBucketsResultXml body = resultBody();
        return body != null ? body.agenticBuckets : null;
    }

    ListAgenticBucketsResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        public ListAgenticBucketsResult build() {
            return new ListAgenticBucketsResult(this);
        }
    }

    @JacksonXmlRootElement(localName = "ListAgenticBucketsResult")
    public static class ListAgenticBucketsResultXml {
        @JacksonXmlProperty(localName = "Region")
        public String region;

        @JacksonXmlProperty(localName = "Owner")
        public String owner;

        @JacksonXmlProperty(localName = "ContinuationToken")
        public String continuationToken;

        @JacksonXmlProperty(localName = "NextContinuationToken")
        public String nextContinuationToken;

        @JacksonXmlProperty(localName = "IsTruncated")
        public Boolean isTruncated;

        @JacksonXmlElementWrapper(localName = "AgenticBuckets")
        @JacksonXmlProperty(localName = "AgenticBucket")
        public List<AgenticBucketSummary> agenticBuckets;
    }
}
