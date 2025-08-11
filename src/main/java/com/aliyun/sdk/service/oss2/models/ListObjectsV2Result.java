package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.models.internal.ListBucketV2ResultXml;

import java.util.List;

/**
 * The result for the ListObjectsV2 operation.
 */
public final class ListObjectsV2Result extends ResultModel {
    public final ListBucketV2ResultXml delegate;

    private ListObjectsV2Result(Builder builder) {
        super(builder);
        this.delegate = (ListBucketV2ResultXml) innerBody;

    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Gets the name of the bucket.
     *
     * @return The name of the bucket.
     */
    public String name() {
        return delegate.name;
    }

    /**
     * Gets the prefix used to filter objects returned in the result.
     *
     * @return The prefix used in the request.
     */
    public String prefix() {
        return delegate.prefix;
    }

    /**
     * Gets the maximum number of keys returned in the response.
     *
     * @return The maximum number of keys returned in the response.
     */
    public Integer maxKeys() {
        return delegate.maxKeys;
    }

    /**
     * Gets the total number of keys returned in the response.
     *
     * @return The number of keys returned in the response.
     */
    public Integer keyCount() {
        return delegate.keyCount;
    }

    /**
     * Gets the list of Contents objects representing the objects found in the bucket.
     *
     * @return A list of Contents objects.
     */
    public List<ObjectSummary> contents() {
        return CastUtils.ensureList(delegate.contents);
    }

    /**
     * Gets the list of CommonPrefixes objects representing the common prefixes found in the bucket.
     *
     * @return A list of CommonPrefixes objects.
     */
    public List<CommonPrefix> commonPrefixes() {
        return CastUtils.ensureList(delegate.commonPrefixes);
    }

    /**
     * Gets the key to start from for the next list operation.
     *
     * @return The key to start from for the next list operation.
     */
    public String startAfter() {
        return delegate.startAfter;
    }

    /**
     * Gets the delimiter used to group keys (if specified in the request).
     *
     * @return The delimiter used in the request.
     */
    public String delimiter() {
        return delegate.delimiter;
    }

    /**
     * Indicates whether the response was truncated.
     *
     * @return true if the response was truncated, false otherwise.
     */
    public Boolean isTruncated() {
        return delegate.isTruncated;
    }

    /**
     * Gets the encoding type used to encode object key names in the XML response.
     *
     * @return The encoding type used in the response.
     */
    public String encodingType() {
        return delegate.encodingType;
    }

    /**
     * Gets the continuation token used for pagination in subsequent requests.
     *
     * @return The continuation token used in the request.
     */
    public String continuationToken() {
        return delegate.continuationToken;
    }

    /**
     * Gets the next continuation token used for pagination in subsequent requests.
     *
     * @return The next continuation token used in the response.
     */
    public String nextContinuationToken() {
        return delegate.nextContinuationToken;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(ListObjectsV2Result delegate) {
            super(delegate);
        }

        public ListObjectsV2Result build() {
            return new ListObjectsV2Result(this);
        }
    }
}
