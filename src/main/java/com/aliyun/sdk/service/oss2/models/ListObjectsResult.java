package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.models.internal.ListBucketResultXml;

import java.util.List;

/**
 * The result for the ListObjects operation.
 */
public final class ListObjectsResult extends ResultModel {
    public final ListBucketResultXml delegate;

    private ListObjectsResult(Builder builder) {
        super(builder);
        this.delegate = (ListBucketResultXml) innerBody;
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
     * Gets the list of Contents objects representing the objects found in the bucket.
     *
     * @return A list of Contents objects.
     */
    public List<ObjectSummary> contents() {
        return  CastUtils.ensureList(delegate.contents);
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
     * Gets the marker used for pagination in subsequent requests.
     *
     * @return The marker used in the request.
     */
    public String marker() {
        return delegate.marker;
    }

    /**
     * Gets the next marker used for pagination in subsequent requests.
     *
     * @return The next marker used in the response.
     */
    public String nextMarker() {
        return delegate.nextMarker;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(ListObjectsResult delegate) {
            super(delegate);
        }

        public ListObjectsResult build() {
            return new ListObjectsResult(this);
        }
    }
}
