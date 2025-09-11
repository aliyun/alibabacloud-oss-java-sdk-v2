package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.vectors.models.internal.BucketProperties;
import com.aliyun.sdk.service.oss2.vectors.models.internal.VectorBucketsJson;

import java.util.List;
import java.util.Optional;

/**
 * The result for the ListVectorBuckets operation.
 */
public final class ListVectorBucketsResult extends ResultModel {
    private final VectorBucketsJson delegate;

    private ListVectorBucketsResult(Builder builder) {
        super(builder);
        this.delegate = Optional.ofNullable((VectorBucketsResponse) innerBody)
                .map(VectorBucketsResponse::getVectorBuckets)
                .orElse(new VectorBucketsJson());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The prefix that the names of returned buckets must contain.
     */
    public String prefix() {
        return delegate.prefix();
    }

    /**
     * The name of the bucket from which the list operation begins.
     */
    public String marker() {
        return delegate.marker();
    }

    /**
     * The maximum number of buckets that can be returned in the single query.
     */
    public Integer maxKeys() {
        return delegate.maxKeys();
    }

    /**
     * Indicates whether the list of buckets is truncated.
     */
    public Boolean isTruncated() {
        return delegate.isTruncated();
    }

    /**
     * The marker for the next list operation.
     */
    public String nextMarker() {
        return delegate.nextMarker();
    }

    /**
     * The list of buckets.
     */
    public List<BucketProperties> buckets() {
        return delegate.buckets();
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
