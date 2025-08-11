package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

import static java.util.Objects.requireNonNull;

/**
 * The request for the ListParts operation.
 */
public final class ListPartsRequest extends RequestModel {
    private final String bucket;
    private final String key;

    private ListPartsRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.key = builder.key;
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
     * The name of the object.
     */
    public String key() {
        return key;
    }

    /**
     * The ID of the multipart upload task.By default, this parameter is left empty.
     */
    public String uploadId() {
        String value = parameters.get("uploadId");
        return value;
    }

    /**
     * The maximum number of parts that can be returned by OSS.Default value: 1000.Maximum value: 1000.
     */
    public Long maxParts() {
        String value = parameters.get("max-parts");
        return ConvertUtils.toLongOrNull(value);
    }

    /**
     * The position from which the list starts. All parts whose part numbers are greater than the value of this parameter are listed.By default, this parameter is left empty.
     */
    public Long partNumberMarker() {
        String value = parameters.get("part-number-marker");
        return ConvertUtils.toLongOrNull(value);
    }

    /**
     * The maximum number of parts that can be returned by OSS. Default value: 1000.Maximum value: 1000.
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
        private String key;

        private Builder() {
            super();
        }

        private Builder(ListPartsRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.key = request.key;
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
         * The name of the object.
         */
        public Builder key(String value) {
            requireNonNull(value);
            this.key = value;
            return this;
        }

        /**
         * The ID of the multipart upload task.By default, this parameter is left empty.
         */
        public Builder uploadId(String value) {
            requireNonNull(value);
            this.parameters.put("uploadId", value);
            return this;
        }

        /**
         * The maximum number of parts that can be returned by OSS.Default value: 1000.Maximum value: 1000.
         */
        public Builder maxParts(Long value) {
            requireNonNull(value);
            this.parameters.put("max-parts", value.toString());
            return this;
        }

        /**
         * The position from which the list starts. All parts whose part numbers are greater than the value of this parameter are listed.By default, this parameter is left empty.
         */
        public Builder partNumberMarker(Long value) {
            requireNonNull(value);
            this.parameters.put("part-number-marker", value.toString());
            return this;
        }

        /**
         * The maximum number of parts that can be returned by OSS. Default value: 1000.Maximum value: 1000.
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

        public ListPartsRequest build() {
            return new ListPartsRequest(this);
        }
    }

}
