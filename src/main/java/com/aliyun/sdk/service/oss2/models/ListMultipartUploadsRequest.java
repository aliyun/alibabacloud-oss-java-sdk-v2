package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

import static java.util.Objects.requireNonNull;

/**
 * The request for the ListMultipartUploads operation.
 */
public final class ListMultipartUploadsRequest extends RequestModel {
    private final String bucket;

    private ListMultipartUploadsRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }

    /**
     * The character used to group objects by name. Objects whose names contain the same string that ranges from the specified prefix to the delimiter that appears for the first time are grouped as a CommonPrefixes element.
     */
    public String delimiter() {
        String value = parameters.get("delimiter");
        return value;
    }

    /**
     * The maximumnumber of multipart upload tasks that can be returned for the current request. Default value: 1000. Maximum value: 1000.
     */
    public Long maxUploads() {
        String value = parameters.get("max-uploads");
        return ConvertUtils.toLongOrNull(value);
    }

    /**
     * This parameter is used together with the upload-id-marker parameter to specify the position from which the next list begins.- If the upload-id-marker parameter is not set, Object Storage Service (OSS) returns all multipart upload tasks in which object names are alphabetically after the key-marker value.- If the upload-id-marker parameter is set, the response includes the following tasks:  - Multipart upload tasks in which object names are alphabetically after the key-marker value in alphabetical order  - Multipart upload tasks in which object names are the same as the key-marker parameter value but whose upload IDs are greater than the upload-id-marker parameter value
     */
    public String keyMarker() {
        String value = parameters.get("key-marker");
        return value;
    }

    /**
     * The prefix that the returned object names must contain. If you specify a prefix in the request, the specified prefix is included in the response.You can use prefixes to group and manage objects in buckets in the same way you manage a folder in a file system.
     */
    public String prefix() {
        String value = parameters.get("prefix");
        return value;
    }

    /**
     * The upload ID of the multipart upload task after which the list begins. This parameter is used together with the key-marker parameter.- If the key-marker parameter is not set, OSS ignores the upload-id-marker parameter.- If the key-marker parameter is configured, the query result includes:  - Multipart upload tasks in which object names are alphabetically after the key-marker value in alphabetical order  - Multipart upload tasks in which object names are the same as the key-marker parameter value but whose upload IDs are greater than the upload-id-marker parameter value
     */
    public String uploadIdMarker() {
        String value = parameters.get("upload-id-marker");
        return value;
    }

    /**
     * The encoding type of the object name in the response. Values of Delimiter, KeyMarker, Prefix, NextKeyMarker, and Key can be encoded in UTF-8. However, the XML 1.0 standard cannot be used to parse control characters such as characters with an American Standard Code for Information Interchange (ASCII) value from 0 to 10. You can set the encoding-type parameter to encode values of Delimiter, KeyMarker, Prefix, NextKeyMarker, and Key in the response.Default value: null
     */
    public String encodingType() {
        String value = parameters.get("encoding-type");
        return value;
    }

    /**
     * To indicate that the requester is aware that the request and data download will incur costs.
     */
    public String requestPayer() {
        String value = headers.get("x-oss-request-payer");
        return value;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;

        private Builder() {
            super();
        }

        private Builder(ListMultipartUploadsRequest request) {
            super(request);
            this.bucket = request.bucket;
        }

        /**
         * The name of the bucket.
         */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        /**
         * The character used to group objects by name. Objects whose names contain the same string that ranges from the specified prefix to the delimiter that appears for the first time are grouped as a CommonPrefixes element.
         */
        public Builder delimiter(String value) {
            requireNonNull(value);
            this.parameters.put("delimiter", value);
            return this;
        }

        /**
         * The maximumnumber of multipart upload tasks that can be returned for the current request. Default value: 1000. Maximum value: 1000.
         */
        public Builder maxUploads(Long value) {
            requireNonNull(value);
            this.parameters.put("max-uploads", value.toString());
            return this;
        }

        /**
         * This parameter is used together with the upload-id-marker parameter to specify the position from which the next list begins.- If the upload-id-marker parameter is not set, Object Storage Service (OSS) returns all multipart upload tasks in which object names are alphabetically after the key-marker value.- If the upload-id-marker parameter is set, the response includes the following tasks:  - Multipart upload tasks in which object names are alphabetically after the key-marker value in alphabetical order  - Multipart upload tasks in which object names are the same as the key-marker parameter value but whose upload IDs are greater than the upload-id-marker parameter value
         */
        public Builder keyMarker(String value) {
            requireNonNull(value);
            this.parameters.put("key-marker", value);
            return this;
        }

        /**
         * The prefix that the returned object names must contain. If you specify a prefix in the request, the specified prefix is included in the response.You can use prefixes to group and manage objects in buckets in the same way you manage a folder in a file system.
         */
        public Builder prefix(String value) {
            requireNonNull(value);
            this.parameters.put("prefix", value);
            return this;
        }

        /**
         * The upload ID of the multipart upload task after which the list begins. This parameter is used together with the key-marker parameter.- If the key-marker parameter is not set, OSS ignores the upload-id-marker parameter.- If the key-marker parameter is configured, the query result includes:  - Multipart upload tasks in which object names are alphabetically after the key-marker value in alphabetical order  - Multipart upload tasks in which object names are the same as the key-marker parameter value but whose upload IDs are greater than the upload-id-marker parameter value
         */
        public Builder uploadIdMarker(String value) {
            requireNonNull(value);
            this.parameters.put("upload-id-marker", value);
            return this;
        }

        /**
         * The encoding type of the object name in the response. Values of Delimiter, KeyMarker, Prefix, NextKeyMarker, and Key can be encoded in UTF-8. However, the XML 1.0 standard cannot be used to parse control characters such as characters with an American Standard Code for Information Interchange (ASCII) value from 0 to 10. You can set the encoding-type parameter to encode values of Delimiter, KeyMarker, Prefix, NextKeyMarker, and Key in the response.Default value: null
         */
        public Builder encodingType(String value) {
            requireNonNull(value);
            this.parameters.put("encoding-type", value);
            return this;
        }

        /**
         * To indicate that the requester is aware that the request and data download will incur costs.
         */
        public Builder requestPayer(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-request-payer", value);
            return this;
        }

        public ListMultipartUploadsRequest build() {
            return new ListMultipartUploadsRequest(this);
        }
    }

}
