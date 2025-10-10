package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.vectors.models.internal.ListVectorBucketsResultJson;
import java.util.Optional;
import java.util.List;

/**
 * The result for the ListVectorBuckets operation.
 */
public final class ListVectorBucketsResult extends ResultModel {
    private final ListVectorBucketsResultJson delegate;

    private ListVectorBucketsResult(Builder builder) {
        super(builder);
        this.delegate = (ListVectorBucketsResultJson) Optional
                .ofNullable(innerBody)
                .orElse(new ListVectorBucketsResultJson());;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The prefix in the bucket name.
     */
    public String prefix() {
        return delegate.bucketsSummary != null ? delegate.bucketsSummary.prefix() : null;
    }

    /**
     * The marker indicating the key from which the current query begins.
     */
    public String marker() {
        return delegate.bucketsSummary != null ? delegate.bucketsSummary.marker() : null;
    }

    /**
     * The maximum number of buckets returned in the response.
     */
    public Long maxKeys() {
        return delegate.bucketsSummary != null ? delegate.bucketsSummary.maxKeys() : null;
    }

    /**
     * Indicates whether the returned result is truncated.
     */
    public Boolean isTruncated() {
        return delegate.bucketsSummary != null ? delegate.bucketsSummary.isTruncated() : null;
    }

    /**
     * The marker indicating where the next query begins.
     */
    public String nextMarker() {
        return delegate.bucketsSummary != null ? delegate.bucketsSummary.nextMarker() : null;
    }

    /**
     * The list of buckets.
     */
    public List<VectorBucketSummary> buckets() {
        return delegate.bucketsSummary != null ? delegate.bucketsSummary.buckets() : null;
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