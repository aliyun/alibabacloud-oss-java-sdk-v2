package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

import java.time.Instant;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * The request for the ListObjectVersions operation.
 */
public final class ListObjectVersionsRequest extends RequestModel { 
    private final String bucket;

    private ListObjectVersionsRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }
    
    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }
    
    /**
     * The character that is used to group objects by name. If you specify prefix and delimiter in the request, the response contains CommonPrefixes. The objects whose name contains the same string from the prefix to the next occurrence of the delimiter are grouped as a single result element in CommonPrefixes. If you specify prefix and set delimiter to a forward slash (/), only the objects in the directory are listed. The subdirectories in the directory are returned in CommonPrefixes. Objects and subdirectories in the subdirectories are not listed.By default, this parameter is left empty.
     */
    public String delimiter() {
        String value = parameters.get("delimiter");
        return value;
    }
    
    /**
     * The name of the object after which the GetBucketVersions (ListObjectVersions) operation begins. If this parameter is specified, objects whose name is alphabetically after the value of key-marker are returned. Use key-marker and version-id-marker in combination. The value of key-marker must be less than 1,024 bytes in length.By default, this parameter is left empty.  You must also specify key-marker if you specify version-id-marker.
     */
    public String keyMarker() {
        String value = parameters.get("key-marker");
        return value;
    }
    
    /**
     * The version ID of the object specified in key-marker after which the GetBucketVersions (ListObjectVersions) operation begins. The versions are returned from the latest version to the earliest version. If version-id-marker is not specified, the GetBucketVersions (ListObjectVersions) operation starts from the latest version of the object whose name is alphabetically after the value of key-marker by default.By default, this parameter is left empty.Valid values: version IDs.
     */
    public String versionIdMarker() {
        String value = parameters.get("version-id-marker");
        return value;
    }
    
    /**
     * The maximum number of objects to be returned. If the number of returned objects exceeds the value of max-keys, the response contains `NextKeyMarker` and `NextVersionIdMarker`. Specify the values of `NextKeyMarker` and `NextVersionIdMarker` as the markers for the next request. Valid values: 1 to 999. Default value: 100.
     */
    public Long maxKeys() {
        String value = parameters.get("max-keys");
        return ConvertUtils.toLongOrNull(value);
    }
    
    /**
     * The prefix that the names of returned objects must contain.*   The value of prefix must be less than 1,024 bytes in length.*   If you specify prefix, the names of the returned objects contain the prefix.If you set prefix to a directory name, the objects whose name starts with the prefix are listed. The returned objects consist of all objects and subdirectories in the directory.By default, this parameter is left empty.
     */
    public String prefix() {
        String value = parameters.get("prefix");
        return value;
    }
    
    /**
     * The encoding type of the content in the response. By default, this parameter is left empty. Set the value to URL.  The values of Delimiter, Marker, Prefix, NextMarker, and Key are UTF-8 encoded. If the value of Delimiter, Marker, Prefix, NextMarker, or Key contains a control character that is not supported by Extensible Markup Language (XML) 1.0, you can specify encoding-type to encode the value in the response.
     */
    public String encodingType() {
        String value = parameters.get("encoding-type");
        return value;
    }
    

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
    
        /**
        * The name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        /**
        * The character that is used to group objects by name. If you specify prefix and delimiter in the request, the response contains CommonPrefixes. The objects whose name contains the same string from the prefix to the next occurrence of the delimiter are grouped as a single result element in CommonPrefixes. If you specify prefix and set delimiter to a forward slash (/), only the objects in the directory are listed. The subdirectories in the directory are returned in CommonPrefixes. Objects and subdirectories in the subdirectories are not listed.By default, this parameter is left empty.
        */
        public Builder delimiter(String value) {
            requireNonNull(value);
            this.parameters.put("delimiter", value);
            return this;        
        }
    
        /**
        * The name of the object after which the GetBucketVersions (ListObjectVersions) operation begins. If this parameter is specified, objects whose name is alphabetically after the value of key-marker are returned. Use key-marker and version-id-marker in combination. The value of key-marker must be less than 1,024 bytes in length.By default, this parameter is left empty.  You must also specify key-marker if you specify version-id-marker.
        */
        public Builder keyMarker(String value) {
            requireNonNull(value);
            this.parameters.put("key-marker", value);
            return this;        
        }
    
        /**
        * The version ID of the object specified in key-marker after which the GetBucketVersions (ListObjectVersions) operation begins. The versions are returned from the latest version to the earliest version. If version-id-marker is not specified, the GetBucketVersions (ListObjectVersions) operation starts from the latest version of the object whose name is alphabetically after the value of key-marker by default.By default, this parameter is left empty.Valid values: version IDs.
        */
        public Builder versionIdMarker(String value) {
            requireNonNull(value);
            this.parameters.put("version-id-marker", value);
            return this;        
        }
    
        /**
        * The maximum number of objects to be returned. If the number of returned objects exceeds the value of max-keys, the response contains `NextKeyMarker` and `NextVersionIdMarker`. Specify the values of `NextKeyMarker` and `NextVersionIdMarker` as the markers for the next request. Valid values: 1 to 999. Default value: 100.
        */
        public Builder maxKeys(Long value) {
            requireNonNull(value);
            this.parameters.put("max-keys", value.toString());
            return this;        
        }
    
        /**
        * The prefix that the names of returned objects must contain.*   The value of prefix must be less than 1,024 bytes in length.*   If you specify prefix, the names of the returned objects contain the prefix.If you set prefix to a directory name, the objects whose name starts with the prefix are listed. The returned objects consist of all objects and subdirectories in the directory.By default, this parameter is left empty.
        */
        public Builder prefix(String value) {
            requireNonNull(value);
            this.parameters.put("prefix", value);
            return this;        
        }
    
        /**
        * The encoding type of the content in the response. By default, this parameter is left empty. Set the value to URL.  The values of Delimiter, Marker, Prefix, NextMarker, and Key are UTF-8 encoded. If the value of Delimiter, Marker, Prefix, NextMarker, or Key contains a control character that is not supported by Extensible Markup Language (XML) 1.0, you can specify encoding-type to encode the value in the response.
        */
        public Builder encodingType(String value) {
            requireNonNull(value);
            this.parameters.put("encoding-type", value);
            return this;        
        }
    
        
        public ListObjectVersionsRequest build() {
            return new ListObjectVersionsRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(ListObjectVersionsRequest request) {
            super(request);
            this.bucket = request.bucket;
        }        
    }

}
