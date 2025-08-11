package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.models.internal.ListPartResultXml;

import java.util.List;
import java.util.Optional;

/**
 * The result for the ListParts operation.
 */
public final class ListPartsResult extends ResultModel {
    public final ListPartResultXml delegate;


    ListPartsResult(Builder builder) {
        super(builder);
        this.delegate = (ListPartResultXml) Optional
                .ofNullable(innerBody)
                .orElse(new ListPartResultXml());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The name of the object.
     */
    public String key() {
        return delegate.key;
    }

    /**
     * The ID of the upload task.
     */
    public String uploadId() {
        return delegate.uploadId;
    }

    /**
     * The position from which the list starts. All parts whose part numbers are greater than the value of this parameter are listed.
     */
    public Long partNumberMarker() {
        return delegate.partNumberMarker;
    }

    /**
     * The NextPartNumberMarker value that is used for the PartNumberMarker value in a subsequent request when the response does not contain all required results.
     */
    public Long nextPartNumberMarker() {
        return delegate.nextPartNumberMarker;
    }

    /**
     * The maximum number of parts in the response.
     */
    public Long maxParts() {
        return delegate.maxParts;
    }

    /**
     * Indicates whether the list of parts returned in the response has been truncated. A value of true indicates that the response does not contain all required results. A value of false indicates that the response contains all required results.Valid values: true and false.
     */
    public Boolean isTruncated() {
        return delegate.isTruncated;
    }

    /**
     * The list of all parts.
     */
    public List<Part> parts() {
        return CastUtils.ensureList(delegate.parts);
    }

    /**
     * The name of the bucket.
     */
    public String bucket() {
        return delegate.bucket;
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

        private Builder(ListPartsResult delegate) {
            super(delegate);
        }

        public ListPartsResult build() {
            return new ListPartsResult(this);
        }
    }
}
