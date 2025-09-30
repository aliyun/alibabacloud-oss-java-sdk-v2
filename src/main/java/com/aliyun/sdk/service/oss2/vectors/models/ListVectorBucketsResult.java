package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.vectors.models.internal.ListAllMyBucketsResultJson;
import java.util.List;

/**
 * The result for the ListVectorBuckets operation.
 */
public final class ListVectorBucketsResult extends ResultModel {
    private final ListAllMyBucketsResultJson.VectorBucketSummary delegate;

    private ListVectorBucketsResult(Builder builder) {
        super(builder);
        this.delegate = (ListAllMyBucketsResultJson.VectorBucketSummary) innerBody;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The prefix that the names of returned buckets must contain.
     */
    public String prefix() {
        return delegate != null ? delegate.prefix : null;
    }

    /**
     * The name of the bucket from which the list operation begins.
     */
    public String marker() {
        return delegate != null ? delegate.marker : null;
    }

    /**
     * The maximum number of buckets that can be returned in the single query.
     */
    public Integer maxKeys() {
        return delegate != null ? delegate.maxKeys : null;
    }

    /**
     * Indicates whether the list of buckets is truncated.
     */
    public Boolean isTruncated() {
        return delegate != null ? delegate.isTruncated : null;
    }

    /**
     * The marker for the next list operation.
     */
    public String nextMarker() {
        return delegate != null ? delegate.nextMarker : null;
    }

    /**
     * The list of buckets.
     */
    public List<VectorBucketProperties> buckets() {
        return delegate != null ? delegate.buckets : null;
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