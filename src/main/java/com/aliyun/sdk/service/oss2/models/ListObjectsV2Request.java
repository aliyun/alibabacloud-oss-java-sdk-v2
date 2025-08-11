package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

import static java.util.Objects.requireNonNull;

/**
 * The request for the ListObjectsV2 operation.
 */
public final class ListObjectsV2Request extends RequestModel {
    private final String bucket;

    private ListObjectsV2Request(Builder builder) {
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
     * The maximum number of objects to be returned.Valid values: 1 to 999.Default value: 100.  If the number of returned objects exceeds the value of max-keys, the response contains NextContinuationToken.Use the value of NextContinuationToken as the value of continuation-token in the next request.
     */
    public Long maxKeys() {
        String value = parameters.get("max-keys");
        return ConvertUtils.toLongOrNull(value);
    }

    /**
     * The prefix that must be contained in names of the returned objects.*   The value of prefix can be up to 1,024 bytes in length.*   If you specify prefix, the names of the returned objects contain the prefix.If you set prefix to a directory name, the objects whose names start with this prefix are listed. The objects consist of all objects and subdirectories in this directory.If you set prefix to a directory name and set delimiter to a forward slash (/), only the objects in the directory are listed. The subdirectories in the directory are returned in CommonPrefixes. Objects and subdirectories in the subdirectories are not listed.For example, a bucket contains the following three objects: fun/test.jpg, fun/movie/001.avi, and fun/movie/007.avi. If prefix is set to fun/, the three objects are returned. If prefix is set to fun/ and delimiter is set to a forward slash (/), fun/test.jpg and fun/movie/ are returned.
     */
    public String prefix() {
        String value = parameters.get("prefix");
        return value;
    }

    /**
     * The encoding format of the returned objects in the response.  The values of Delimiter, StartAfter, Prefix, NextContinuationToken, and Key are UTF-8 encoded. If the value of Delimiter, StartAfter, Prefix, NextContinuationToken, or Key contains a control character that is not supported by Extensible Markup Language (XML) 1.0, you can specify encoding-type to encode the value in the response.
     */
    public String encodingType() {
        String value = parameters.get("encoding-type");
        return value;
    }

    /**
     * Specifies whether to include the information about the bucket owner in the response. Valid values:*   true*   false
     */
    public Boolean fetchOwner() {
        String value = parameters.get("fetch-owner");
        return ConvertUtils.toBoolOrNull(value);
    }

    /**
     * The name of the object after which the list operation begins. If this parameter is specified, objects whose names are alphabetically after the value of start-after are returned.The objects are returned by page based on start-after. The value of start-after can be up to 1,024 bytes in length.If the value of start-after does not exist when you perform a conditional query, the list starts from the object whose name is alphabetically after the value of start-after.
     */
    public String startAfter() {
        String value = parameters.get("start-after");
        return value;
    }

    /**
     * The token from which the list operation starts. You can obtain the token from NextContinuationToken in the response of the ListObjectsV2 request.
     */
    public String continuationToken() {
        String value = parameters.get("continuation-token");
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

        private Builder(ListObjectsV2Request request) {
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
         * The maximum number of objects to be returned.Valid values: 1 to 999.Default value: 100.  If the number of returned objects exceeds the value of max-keys, the response contains NextContinuationToken.Use the value of NextContinuationToken as the value of continuation-token in the next request.
         */
        public Builder maxKeys(Long value) {
            requireNonNull(value);
            this.parameters.put("max-keys", value.toString());
            return this;
        }

        /**
         * The prefix that must be contained in names of the returned objects.*   The value of prefix can be up to 1,024 bytes in length.*   If you specify prefix, the names of the returned objects contain the prefix.If you set prefix to a directory name, the objects whose names start with this prefix are listed. The objects consist of all objects and subdirectories in this directory.If you set prefix to a directory name and set delimiter to a forward slash (/), only the objects in the directory are listed. The subdirectories in the directory are returned in CommonPrefixes. Objects and subdirectories in the subdirectories are not listed.For example, a bucket contains the following three objects: fun/test.jpg, fun/movie/001.avi, and fun/movie/007.avi. If prefix is set to fun/, the three objects are returned. If prefix is set to fun/ and delimiter is set to a forward slash (/), fun/test.jpg and fun/movie/ are returned.
         */
        public Builder prefix(String value) {
            requireNonNull(value);
            this.parameters.put("prefix", value);
            return this;
        }

        /**
         * The encoding format of the returned objects in the response.  The values of Delimiter, StartAfter, Prefix, NextContinuationToken, and Key are UTF-8 encoded. If the value of Delimiter, StartAfter, Prefix, NextContinuationToken, or Key contains a control character that is not supported by Extensible Markup Language (XML) 1.0, you can specify encoding-type to encode the value in the response.
         */
        public Builder encodingType(String value) {
            requireNonNull(value);
            this.parameters.put("encoding-type", value);
            return this;
        }

        /**
         * Specifies whether to include the information about the bucket owner in the response. Valid values:*   true*   false
         */
        public Builder fetchOwner(Boolean value) {
            requireNonNull(value);
            this.parameters.put("fetch-owner", value.toString());
            return this;
        }

        /**
         * The name of the object after which the list operation begins. If this parameter is specified, objects whose names are alphabetically after the value of start-after are returned.The objects are returned by page based on start-after. The value of start-after can be up to 1,024 bytes in length.If the value of start-after does not exist when you perform a conditional query, the list starts from the object whose name is alphabetically after the value of start-after.
         */
        public Builder startAfter(String value) {
            requireNonNull(value);
            this.parameters.put("start-after", value);
            return this;
        }

        /**
         * The token from which the list operation starts. You can obtain the token from NextContinuationToken in the response of the ListObjectsV2 request.
         */
        public Builder continuationToken(String value) {
            requireNonNull(value);
            this.parameters.put("continuation-token", value);
            return this;
        }

        public ListObjectsV2Request build() {
            return new ListObjectsV2Request(this);
        }
    }

}
