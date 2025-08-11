package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

import static java.util.Objects.requireNonNull;

/**
 * The request for the ListObjects operation.
 */
public final class ListObjectsRequest extends RequestModel {
    private final String bucket;

    private ListObjectsRequest(Builder builder) {
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
     * The character that is used to group objects by name. If you specify delimiter in the request, the response contains CommonPrefixes. The objects whose names contain the same string from the prefix to the next occurrence of the delimiter are grouped as a single result element in CommonPrefixes.
     */
    public String delimiter() {
        String value = parameters.get("delimiter");
        return value;
    }

    /**
     * The name of the object after which the GetBucket (ListObjects) operation begins. If this parameter is specified, objects whose names are alphabetically after the value of marker are returned.The objects are returned by page based on marker. The value of marker can be up to 1,024 bytes.If the value of marker does not exist in the list when you perform a conditional query, the GetBucket (ListObjects) operation starts from the object whose name is alphabetically after the value of marker.
     */
    public String marker() {
        String value = parameters.get("marker");
        return value;
    }

    /**
     * The maximum number of objects that can be returned. If the number of objects to be returned exceeds the value of max-keys specified in the request, NextMarker is included in the returned response. The value of NextMarker is used as the value of marker for the next request.Valid values: 1 to 999.Default value: 100.
     */
    public Long maxKeys() {
        String value = parameters.get("max-keys");
        return ConvertUtils.toLongOrNull(value);
    }

    /**
     * The prefix that must be contained in names of the returned objects.*   The value of prefix can be up to 1,024 bytes in length.*   If you specify prefix, the names of the returned objects contain the prefix.If you set prefix to a directory name, the object whose names start with this prefix are listed. The objects consist of all recursive objects and subdirectories in this directory.If you set prefix to a directory name and set delimiter to a forward slash (/), only the objects in the directory are listed. The subdirectories in the directory are listed in CommonPrefixes. Recursive objects and subdirectories in the subdirectories are not listed.For example, a bucket contains the following three objects: fun/test.jpg, fun/movie/001.avi, and fun/movie/007.avi. If prefix is set to fun/, the three objects are returned. If prefix is set to fun/ and delimiter is set to a forward slash (/), fun/test.jpg and fun/movie/ are returned.
     */
    public String prefix() {
        String value = parameters.get("prefix");
        return value;
    }

    /**
     * The encoding format of the content in the response.  The value of Delimiter, Marker, Prefix, NextMarker, and Key are UTF-8 encoded. If the values of Delimiter, Marker, Prefix, NextMarker, and Key contain a control character that is not supported by Extensible Markup Language (XML) 1.0, you can specify encoding-type to encode the value in the response.
     */
    public String encodingType() {
        String value = parameters.get("encoding-type");
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

        private Builder(ListObjectsRequest request) {
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
         * The character that is used to group objects by name. If you specify delimiter in the request, the response contains CommonPrefixes. The objects whose names contain the same string from the prefix to the next occurrence of the delimiter are grouped as a single result element in CommonPrefixes.
         */
        public Builder delimiter(String value) {
            requireNonNull(value);
            this.parameters.put("delimiter", value);
            return this;
        }

        /**
         * The name of the object after which the GetBucket (ListObjects) operation begins. If this parameter is specified, objects whose names are alphabetically after the value of marker are returned.The objects are returned by page based on marker. The value of marker can be up to 1,024 bytes.If the value of marker does not exist in the list when you perform a conditional query, the GetBucket (ListObjects) operation starts from the object whose name is alphabetically after the value of marker.
         */
        public Builder marker(String value) {
            requireNonNull(value);
            this.parameters.put("marker", value);
            return this;
        }

        /**
         * The maximum number of objects that can be returned. If the number of objects to be returned exceeds the value of max-keys specified in the request, NextMarker is included in the returned response. The value of NextMarker is used as the value of marker for the next request.Valid values: 1 to 999.Default value: 100.
         */
        public Builder maxKeys(Long value) {
            requireNonNull(value);
            this.parameters.put("max-keys", value.toString());
            return this;
        }

        /**
         * The prefix that must be contained in names of the returned objects.*   The value of prefix can be up to 1,024 bytes in length.*   If you specify prefix, the names of the returned objects contain the prefix.If you set prefix to a directory name, the object whose names start with this prefix are listed. The objects consist of all recursive objects and subdirectories in this directory.If you set prefix to a directory name and set delimiter to a forward slash (/), only the objects in the directory are listed. The subdirectories in the directory are listed in CommonPrefixes. Recursive objects and subdirectories in the subdirectories are not listed.For example, a bucket contains the following three objects: fun/test.jpg, fun/movie/001.avi, and fun/movie/007.avi. If prefix is set to fun/, the three objects are returned. If prefix is set to fun/ and delimiter is set to a forward slash (/), fun/test.jpg and fun/movie/ are returned.
         */
        public Builder prefix(String value) {
            requireNonNull(value);
            this.parameters.put("prefix", value);
            return this;
        }

        /**
         * The encoding format of the content in the response.  The value of Delimiter, Marker, Prefix, NextMarker, and Key are UTF-8 encoded. If the values of Delimiter, Marker, Prefix, NextMarker, and Key contain a control character that is not supported by Extensible Markup Language (XML) 1.0, you can specify encoding-type to encode the value in the response.
         */
        public Builder encodingType(String value) {
            requireNonNull(value);
            this.parameters.put("encoding-type", value);
            return this;
        }

        public ListObjectsRequest build() {
            return new ListObjectsRequest(this);
        }
    }

}
