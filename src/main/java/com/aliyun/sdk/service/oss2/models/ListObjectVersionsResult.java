package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.models.internal.ListVersionsResultXml;

import java.util.List;

/**
 * The result for the ListObjectVersions operation.
 */
public final class ListObjectVersionsResult extends ResultModel {
    public final ListVersionsResultXml result;

    /**
     * The container that stores the results of the ListObjectVersions (GetBucketVersions) request.
     */
    private ListObjectVersionsResult(Builder builder) {
        super(builder);
        this.result = (ListVersionsResultXml) innerBody;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The container that stores the versions of objects except for delete markers
     */
    public List<ObjectVersion> versions() {
        return CastUtils.ensureList(result.versions);
    }

    /**
     * The container that stores delete markers
     */
    public List<DeleteMarkerEntry> deleteMarkers() {
        return CastUtils.ensureList(result.deleteMarkers);
    }

    /**
     * The prefix contained in the names of the returned objects.
     */
    public String prefix() {
        return result.prefix;
    }

    /**
     * Indicates whether the returned results are truncated.
     * - true: indicates that not all results are returned for the request.
     * - false: indicates that all results are returned for the request.
     */
    public Boolean isTruncated() {
        return result.isTruncated;
    }

    /**
     * If not all results are returned for the request, the NextKeyMarker parameter is included
     * in the response to indicate the key-marker value of the next ListObjectVersions (GetBucketVersions) request.
     */
    public String nextKeyMarker() {
        return result.nextKeyMarker;
    }

    /**
     * If not all results are returned for the request, the NextVersionIdMarker parameter is included
     * in the response to indicate the version-id-marker value of the next ListObjectVersions (GetBucketVersions) request.
     */
    public String nextVersionIdMarker() {
        return result.nextVersionIdMarker;
    }

    /**
     * The character that is used to group objects by name. The objects whose names contain
     * the same string from the prefix to the next occurrence of the delimiter are grouped
     * as a single result parameter in CommonPrefixes.
     */
    public String delimiter() {
        return result.delimiter;
    }

    /**
     * The encoding type of the content in the response. If you specify encoding-type in the request,
     * the values of Delimiter, Marker, Prefix, NextMarker, and Key are encoded in the response.
     */
    public String encodingType() {
        return result.encodingType;
    }

    /**
     * Objects whose names contain the same string that ranges from the prefix to the next occurrence
     * of the delimiter are grouped as a single result element
     */
    public List<CommonPrefix> commonPrefixes() {
        return CastUtils.ensureList(result.commonPrefixes);
    }

    /**
     * The bucket name
     */
    public String name() {
        return result.name;
    }

    /**
     * Indicates the object from which the ListObjectVersions (GetBucketVersions) operation starts.
     */
    public String keyMarker() {
        return result.keyMarker;
    }

    /**
     * The version from which the ListObjectVersions (GetBucketVersions) operation starts.
     * This parameter is used together with KeyMarker.
     */
    public String versionIdMarker() {
        return result.versionIdMarker;
    }

    /**
     * The maximum number of objects that can be returned in the response.
     */
    public Long maxKeys() {
        return result.maxKeys;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public ListObjectVersionsResult build() {
            return new ListObjectVersionsResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(ListObjectVersionsResult result) {
            super(result);
        }
    }
}
