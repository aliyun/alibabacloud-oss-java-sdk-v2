package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.transport.BinaryDataConsumerSupplier;
import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

import static java.util.Objects.requireNonNull;

/**
 * The request for the GetObject operation.
 */
public final class GetObjectRequest extends RequestModel {
    private final String bucket;
    private final String key;
    private final BinaryDataConsumerSupplier dataConsumerSupplier;

    private GetObjectRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.key = builder.key;
        this.dataConsumerSupplier = builder.dataConsumerSupplier;
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
     * The full path of the object.
     */
    public String key() {
        return key;
    }

    /**
     * The range of data of the object to be returned.   - If the value of Range is valid, OSS returns the response that includes the total size of the object and the range of data returned. For example, Content-Range: bytes 0~9/44 indicates that the total size of the object is 44 bytes, and the range of data returned is the first 10 bytes.   - However, if the value of Range is invalid, the entire object is returned, and the response returned by OSS excludes Content-Range. Default value: null
     */
    public String range() {
        String value = headers.get("Range");
        return value;
    }

    /**
     * If the time specified in this header is earlier than the object modified time or is invalid, OSS returns the object and 200 OK. If the time specified in this header is later than or the same as the object modified time, OSS returns 304 Not Modified. The time must be in GMT. Example: `Fri, 13 Nov 2015 14:47:53 GMT`.Default value: null
     */
    public String ifModifiedSince() {
        String value = headers.get("If-Modified-Since");
        return value;
    }

    /**
     * If the time specified in this header is the same as or later than the object modified time, OSS returns the object and 200 OK. If the time specified in this header is earlier than the object modified time, OSS returns 412 Precondition Failed.                               The time must be in GMT. Example: `Fri, 13 Nov 2015 14:47:53 GMT`.You can specify both the **If-Modified-Since** and **If-Unmodified-Since** headers in a request. Default value: null
     */
    public String ifUnmodifiedSince() {
        String value = headers.get("If-Unmodified-Since");
        return value;
    }

    /**
     * If the ETag specified in the request matches the ETag value of the object, OSS transmits the object and returns 200 OK. If the ETag specified in the request does not match the ETag value of the object, OSS returns 412 Precondition Failed. The ETag value of an object is used to check whether the content of the object has changed. You can check data integrity by using the ETag value. Default value: null
     */
    public String ifMatch() {
        String value = headers.get("If-Match");
        return value;
    }

    /**
     * If the ETag specified in the request does not match the ETag value of the object, OSS transmits the object and returns 200 OK. If the ETag specified in the request matches the ETag value of the object, OSS returns 304 Not Modified. You can specify both the **If-Match** and **If-None-Match** headers in a request. Default value: null
     */
    public String ifNoneMatch() {
        String value = headers.get("If-None-Match");
        return value;
    }

    /**
     * The encoding type at the client side. If you want an object to be returned in the GZIP format, you must include the Accept-Encoding:gzip header in your request. OSS determines whether to return the object compressed in the GZip format based on the Content-Type header and whether the size of the object is larger than or equal to 1 KB.                                   If an object is compressed in the GZip format, the response OSS returns does not include the ETag value of the object.    - OSS supports the following Content-Type values to compress the object in the GZip format: text/cache-manifest, text/xml, text/plain, text/css, application/javascript, application/x-javascript, application/rss+xml, application/json, and text/json. Default value: null
     */
    public String acceptEncoding() {
        String value = headers.get("Accept-Encoding");
        return value;
    }

    /**
     * The content-type header in the response that OSS returns.
     */
    public String responseContentType() {
        String value = parameters.get("response-content-type");
        return value;
    }

    /**
     * The content-language header in the response that OSS returns.
     */
    public String responseContentLanguage() {
        String value = parameters.get("response-content-language");
        return value;
    }

    /**
     * The expires header in the response that OSS returns.
     */
    public String responseExpires() {
        String value = parameters.get("response-expires");
        return value;
    }

    /**
     * The cache-control header in the response that OSS returns.
     */
    public String responseCacheControl() {
        String value = parameters.get("response-cache-control");
        return value;
    }

    /**
     * The content-disposition header in the response that OSS returns.
     */
    public String responseContentDisposition() {
        String value = parameters.get("response-content-disposition");
        return value;
    }

    /**
     * The content-encoding header in the response that OSS returns.
     */
    public String responseContentEncoding() {
        String value = parameters.get("response-content-encoding");
        return value;
    }

    /**
     * The version ID of the object that you want to query.
     */
    public String versionId() {
        String value = parameters.get("versionId");
        return value;
    }

    /**
     * The speed limit value. The speed limit value ranges from 245760 to 838860800, with a unit of bit/s. Default value: null
     */
    public Integer trafficLimit() {
        String value = headers.get("x-oss-traffic-limit");
        return ConvertUtils.toIntegerOrNull(value);
    }

    /**
     * Image processing parameters. Default value: null
     */
    public String process() {
        String value = parameters.get("x-oss-process");
        return value;
    }

    /**
     * To indicate that the requester is aware that the request and data download will incur costs. Default value: null
     */
    public String requestPayer() {
        String value = headers.get("x-oss-request-payer");
        return value;
    }

    /**
     * Standard behaviors to download data by range. Default value: null
     */
    public String rangeBehavior() {
        String value = headers.get("x-oss-range-behavior");
        return value;
    }

    /**
     * A data consumer supplier to save the response body.
     * Valid only on GetObjectAsync api.
     */
    public BinaryDataConsumerSupplier dataConsumerSupplier() {
        return this.dataConsumerSupplier;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private String key;
        private BinaryDataConsumerSupplier dataConsumerSupplier;

        private Builder() {
            super();
        }

        private Builder(GetObjectRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.key = request.key;
            this.dataConsumerSupplier = request.dataConsumerSupplier;
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
         * The full path of the object.
         */
        public Builder key(String value) {
            requireNonNull(value);
            this.key = value;
            return this;
        }

        /**
         * The range of data of the object to be returned.   - If the value of Range is valid, OSS returns the response that includes the total size of the object and the range of data returned. For example, Content-Range: bytes 0~9/44 indicates that the total size of the object is 44 bytes, and the range of data returned is the first 10 bytes.   - However, if the value of Range is invalid, the entire object is returned, and the response returned by OSS excludes Content-Range. Default value: null
         */
        public Builder range(String value) {
            requireNonNull(value);
            this.headers.put("Range", value);
            return this;
        }

        /**
         * If the time specified in this header is earlier than the object modified time or is invalid, OSS returns the object and 200 OK. If the time specified in this header is later than or the same as the object modified time, OSS returns 304 Not Modified. The time must be in GMT. Example: `Fri, 13 Nov 2015 14:47:53 GMT`.Default value: null
         */
        public Builder ifModifiedSince(String value) {
            requireNonNull(value);
            this.headers.put("If-Modified-Since", value);
            return this;
        }

        /**
         * If the time specified in this header is the same as or later than the object modified time, OSS returns the object and 200 OK. If the time specified in this header is earlier than the object modified time, OSS returns 412 Precondition Failed.                               The time must be in GMT. Example: `Fri, 13 Nov 2015 14:47:53 GMT`.You can specify both the **If-Modified-Since** and **If-Unmodified-Since** headers in a request. Default value: null
         */
        public Builder ifUnmodifiedSince(String value) {
            requireNonNull(value);
            this.headers.put("If-Unmodified-Since", value);
            return this;
        }

        /**
         * If the ETag specified in the request matches the ETag value of the object, OSS transmits the object and returns 200 OK. If the ETag specified in the request does not match the ETag value of the object, OSS returns 412 Precondition Failed. The ETag value of an object is used to check whether the content of the object has changed. You can check data integrity by using the ETag value. Default value: null
         */
        public Builder ifMatch(String value) {
            requireNonNull(value);
            this.headers.put("If-Match", value);
            return this;
        }

        /**
         * If the ETag specified in the request does not match the ETag value of the object, OSS transmits the object and returns 200 OK. If the ETag specified in the request matches the ETag value of the object, OSS returns 304 Not Modified. You can specify both the **If-Match** and **If-None-Match** headers in a request. Default value: null
         */
        public Builder ifNoneMatch(String value) {
            requireNonNull(value);
            this.headers.put("If-None-Match", value);
            return this;
        }

        /**
         * The encoding type at the client side. If you want an object to be returned in the GZIP format, you must include the Accept-Encoding:gzip header in your request. OSS determines whether to return the object compressed in the GZip format based on the Content-Type header and whether the size of the object is larger than or equal to 1 KB.                                   If an object is compressed in the GZip format, the response OSS returns does not include the ETag value of the object.    - OSS supports the following Content-Type values to compress the object in the GZip format: text/cache-manifest, text/xml, text/plain, text/css, application/javascript, application/x-javascript, application/rss+xml, application/json, and text/json. Default value: null
         */
        public Builder acceptEncoding(String value) {
            requireNonNull(value);
            this.headers.put("Accept-Encoding", value);
            return this;
        }

        /**
         * The content-type header in the response that OSS returns.
         */
        public Builder responseContentType(String value) {
            requireNonNull(value);
            this.parameters.put("response-content-type", value);
            return this;
        }

        /**
         * The content-language header in the response that OSS returns.
         */
        public Builder responseContentLanguage(String value) {
            requireNonNull(value);
            this.parameters.put("response-content-language", value);
            return this;
        }

        /**
         * The expires header in the response that OSS returns.
         */
        public Builder responseExpires(String value) {
            requireNonNull(value);
            this.parameters.put("response-expires", value);
            return this;
        }

        /**
         * The cache-control header in the response that OSS returns.
         */
        public Builder responseCacheControl(String value) {
            requireNonNull(value);
            this.parameters.put("response-cache-control", value);
            return this;
        }

        /**
         * The content-disposition header in the response that OSS returns.
         */
        public Builder responseContentDisposition(String value) {
            requireNonNull(value);
            this.parameters.put("response-content-disposition", value);
            return this;
        }

        /**
         * The content-encoding header in the response that OSS returns.
         */
        public Builder responseContentEncoding(String value) {
            requireNonNull(value);
            this.parameters.put("response-content-encoding", value);
            return this;
        }

        /**
         * The version ID of the object that you want to query.
         */
        public Builder versionId(String value) {
            requireNonNull(value);
            this.parameters.put("versionId", value);
            return this;
        }

        /**
         * The speed limit value. The speed limit value ranges from 245760 to 838860800, with a unit of bit/s. Default value: null
         */
        public Builder trafficLimit(Integer value) {
            requireNonNull(value);
            this.headers.put("x-oss-traffic-limit", value.toString());
            return this;
        }

        /**
         * Image processing parameters. Default value: null
         */
        public Builder process(String value) {
            requireNonNull(value);
            this.parameters.put("x-oss-process", value);
            return this;
        }

        /**
         * To indicate that the requester is aware that the request and data download will incur costs. Default value: null
         */
        public Builder requestPayer(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-request-payer", value);
            return this;
        }

        /**
         * Standard behaviors to download data by range. Default value: null
         */
        public Builder rangeBehavior(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-range-behavior", value);
            return this;
        }

        /**
         * A data consumer supplier to save the response body.
         */
        public Builder dataConsumerSupplier(BinaryDataConsumerSupplier value) {
            requireNonNull(value);
            this.dataConsumerSupplier = value;
            return this;
        }

        public GetObjectRequest build() {
            return new GetObjectRequest(this);
        }
    }

}
