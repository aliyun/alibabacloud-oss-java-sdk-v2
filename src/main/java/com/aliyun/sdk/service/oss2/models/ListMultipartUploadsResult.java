package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.models.internal.ListMultipartUploadsResultXml;

import java.util.List;
import java.util.Optional;

/**
 * The result for the ListMultipartUploads operation.
 */
public final class ListMultipartUploadsResult extends ResultModel {
    public final ListMultipartUploadsResultXml delegate;

    ListMultipartUploadsResult(Builder builder) {
        super(builder);
        this.delegate = (ListMultipartUploadsResultXml) Optional
                .ofNullable(innerBody)
                .orElse(new ListMultipartUploadsResultXml());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The name of the bucket.
     */
    public String bucket() {
        return delegate.bucket;
    }

    /**
     * The name of the object that corresponds to the multipart upload task after which the list begins.
     */
    public String keyMarker() {
        return delegate.keyMarker;
    }

    /**
     * The upload ID of the multipart upload task after which the list begins.
     */
    public String uploadIdMarker() {
        return delegate.uploadIdMarker;
    }

    /**
     * The object name marker in the response for the next request to return the remaining results.
     */
    public String nextKeyMarker() {
        return delegate.nextKeyMarker;
    }

    /**
     * The NextUploadMarker value that is used for the UploadMarker value in the next request if the response does not contain all required results.
     */
    public String nextUploadIdMarker() {
        return delegate.nextUploadIdMarker;
    }

    /**
     * The character used to group objects by name. If you specify the Delimiter parameter in the request, the response contains the CommonPrefixes element. Objects whose names contain the same string from the prefix to the next occurrence of the delimiter are grouped as a single result element in
     */
    public String delimiter() {
        return delegate.delimiter;
    }

    /**
     * The prefix that the returned object names must contain. If you specify a prefix in the request, the specified prefix is included in the response.
     */
    public String prefix() {
        return delegate.prefix;
    }

    /**
     * The maximum number of multipart upload tasks returned by OSS.
     */
    public Long maxUploads() {
        return delegate.maxUploads;
    }

    /**
     * Indicates whether the list of multipart upload tasks returned in the response is truncated. Default value: false. Valid values:- true: Only part of the results are returned this time.- false: All results are returned.
     */
    public Boolean isTruncated() {
        return delegate.isTruncated;
    }

    /**
     * The ID list of the multipart upload tasks.
     */
    public List<Upload> uploads() {
        return CastUtils.ensureList(delegate.uploads);
    }

    /**
     * The encoding type of the content in the response.
     * If encoding-type is specified in the request, the field will be  encoded in the returned result.
     */
    public String encodingType() {
        return delegate.encodingType;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(ListMultipartUploadsResult delegate) {
            super(delegate);
        }

        public ListMultipartUploadsResult build() {
            return new ListMultipartUploadsResult(this);
        }
    }
}
