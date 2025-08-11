package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.models.internal.ListAllMyBucketsResultXml;

import java.util.List;
import java.util.Optional;

/**
 * The result for the ListBuckets operation.
 */
public final class ListBucketsResult extends ResultModel {
    private final ListAllMyBucketsResultXml delegate;

    ListBucketsResult(Builder builder) {
        super(builder);
        this.delegate = (ListAllMyBucketsResultXml) Optional
                .ofNullable(innerBody)
                .orElse(new ListAllMyBucketsResultXml());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The marker for the next ListBuckets (GetService) request. You can use the value of this parameter as the value of marker in the next ListBuckets (GetService) request to retrieve the unreturned results.
     */
    public String nextMarker() {
        return delegate.nextMarker;
    }

    /**
     * The container that stores the information about multiple buckets.
     */
    public List<BucketSummary> buckets() {
        return CastUtils.ensureList(delegate.buckets.buckets());
    }

    /**
     * The container that stores the information about the bucket owner.
     */
    public Owner owner() {
        return delegate.owner;
    }

    /**
     * The prefix contained in the names of returned buckets.
     */
    public String prefix() {
        return delegate.prefix;
    }

    /**
     * The name of the bucket from which the buckets are returned.
     */
    public String marker() {
        return delegate.marker;
    }

    /**
     * The maximum number of buckets that can be returned.
     */
    public Long maxKeys() {
        return delegate.maxKeys;
    }

    /**
     * Indicates whether all results are returned. Valid values:- true: All results are not returned in the response. - false: All results are returned in the response.
     */
    public Boolean isTruncated() {
        return delegate.isTruncated;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(ListBucketsResult delegate) {
            super(delegate);
        }

        public ListBucketsResult build() {
            return new ListBucketsResult(this);
        }
    }
}
