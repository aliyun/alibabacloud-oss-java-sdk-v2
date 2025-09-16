package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.models.internal.ListMultipartUploadsResultXml;
import com.aliyun.sdk.service.oss2.vectors.models.internal.ListAllMyBucketsResultJson;
import java.util.List;
import java.util.Optional;

/**
 * The result for the ListVectorBuckets operation.
 */
public final class ListVectorBucketsResult extends ResultModel {
    private final ListAllMyBucketsResultJson delegate;

    private ListVectorBucketsResult(Builder builder) {
        super(builder);
        this.delegate = (ListAllMyBucketsResultJson) innerBody;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The prefix that the names of returned buckets must contain.
     */
    public String prefix() {
        return delegate.listAllMyBucketsResult.prefix;
    }

    /**
     * The name of the bucket from which the list operation begins.
     */
    public String marker() {
        return delegate.listAllMyBucketsResult.marker;
    }

    /**
     * The maximum number of buckets that can be returned in the single query.
     */
    public Integer maxKeys() {
        return delegate.listAllMyBucketsResult.maxKeys;
    }

    /**
     * Indicates whether the list of buckets is truncated.
     */
    public Boolean isTruncated() {
        return delegate.listAllMyBucketsResult.isTruncated;
    }

    /**
     * The marker for the next list operation.
     */
    public String nextMarker() {
        return delegate.listAllMyBucketsResult.nextMarker;
    }

    /**
     * The list of buckets.
     */
    public List<VectorBucketProperties> buckets() {
        return delegate.listAllMyBucketsResult.buckets;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(ListVectorBucketsResult from) {
            super(from);
        }

        public ListVectorBucketsResult build() {
            return new ListVectorBucketsResult(this);
        }
    }
}
