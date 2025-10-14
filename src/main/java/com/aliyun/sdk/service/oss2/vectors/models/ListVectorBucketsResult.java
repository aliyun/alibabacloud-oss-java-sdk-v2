package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.vectors.models.internal.ListVectorBucketsResultJson;
import java.util.Optional;
import java.util.List;

/**
 * The result for the ListVectorBuckets operation.
 */
public final class ListVectorBucketsResult extends ResultModel {
    private final BucketsSummary delegate;

    private ListVectorBucketsResult(Builder builder) {
        super(builder);
        this.delegate = Optional.ofNullable((ListVectorBucketsResultJson) innerBody)
                .map(result -> result.bucketsSummary)
                .orElse(new BucketsSummary());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The prefix in the bucket name.
     */
    public String prefix() {
        return delegate.prefix();
    }

    /**
     * The marker indicating the key from which the current query begins.
     */
    public String marker() {
        return delegate.marker();
    }

    /**
     * The maximum number of buckets returned in the response.
     */
    public Long maxKeys() {
        return delegate.maxKeys();
    }

    /**
     * Indicates whether the returned result is truncated.
     */
    public Boolean isTruncated() {
        return delegate.isTruncated();
    }

    /**
     * The marker indicating where the next query begins.
     */
    public String nextMarker() {
        return delegate.nextMarker();
    }

    /**
     * The list of buckets.
     */
    public List<VectorBucketSummary> buckets() {
        return CastUtils.ensureList(delegate.buckets());
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