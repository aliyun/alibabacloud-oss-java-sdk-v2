package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

import static java.util.Objects.requireNonNull;

/**
 * The request for the ListBuckets operation.
 */
public final class ListBucketsRequest extends RequestModel {

    private ListBucketsRequest(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The ID of the resource group to which the bucket belongs.
     */
    public String resourceGroupId() {
        String value = headers.get("x-oss-resource-group-id");
        return value;
    }

    /**
     * The prefix that the names of returned buckets must contain. If this parameter is not specified, prefixes are not used to filter returned buckets. By default, this parameter is left empty.
     */
    public String prefix() {
        String value = parameters.get("prefix");
        return value;
    }

    /**
     * The name of the bucket from which the buckets start to return. The buckets whose names are alphabetically after the value of marker are returned. If this parameter is not specified, all results are returned. By default, this parameter is left empty.
     */
    public String marker() {
        String value = parameters.get("marker");
        return value;
    }

    /**
     * The maximum number of buckets that can be returned. Valid values: 1 to 1000. Default value: 100
     */
    public Long maxKeys() {
        String value = parameters.get("max-keys");
        return ConvertUtils.toLongOrNull(value);
    }

    /**
     * A tag key of target buckets. The listing results will only include Buckets that have been tagged with this key.
     */
    public String tagKey() {
        String value = parameters.get("tag-key");
        return value;
    }

    /**
     * A tag value for the target buckets. If this parameter is specified in the request, the tag-key must also be specified. The listing results will only include Buckets that have been tagged with this key-value pair.
     */
    public String tagValue() {
        String value = parameters.get("tag-value");
        return value;
    }

    /**
     * Tag list of target buckets. Only Buckets that match all the key-value pairs in the list will added into the listing results. The tagging parameter cannot be used with the tag-key and tag-value parameters in a request.
     */
    public String tagging() {
        String value = parameters.get("tagging");
        return value;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(ListBucketsRequest request) {
            super(request);
        }

        /**
         * The ID of the resource group to which the bucket belongs.
         */
        public Builder resourceGroupId(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-resource-group-id", value);
            return this;
        }

        /**
         * The prefix that the names of returned buckets must contain. If this parameter is not specified, prefixes are not used to filter returned buckets. By default, this parameter is left empty.
         */
        public Builder prefix(String value) {
            requireNonNull(value);
            this.parameters.put("prefix", value);
            return this;
        }

        /**
         * The name of the bucket from which the buckets start to return. The buckets whose names are alphabetically after the value of marker are returned. If this parameter is not specified, all results are returned. By default, this parameter is left empty.
         */
        public Builder marker(String value) {
            requireNonNull(value);
            this.parameters.put("marker", value);
            return this;
        }

        /**
         * The maximum number of buckets that can be returned. Valid values: 1 to 1000. Default value: 100
         */
        public Builder maxKeys(Long value) {
            requireNonNull(value);
            this.parameters.put("max-keys", value.toString());
            return this;
        }

        /**
         * A tag key of target buckets. The listing results will only include Buckets that have been tagged with this key.
         */
        public Builder tagKey(String value) {
            requireNonNull(value);
            this.parameters.put("tag-key", value);
            return this;
        }

        /**
         * A tag value for the target buckets. If this parameter is specified in the request, the tag-key must also be specified. The listing results will only include Buckets that have been tagged with this key-value pair.
         */
        public Builder tagValue(String value) {
            requireNonNull(value);
            this.parameters.put("tag-value", value);
            return this;
        }

        /**
         * Tag list of target buckets. Only Buckets that match all the key-value pairs in the list will added into the listing results. The tagging parameter cannot be used with the tag-key and tag-value parameters in a request.
         */
        public Builder tagging(String value) {
            requireNonNull(value);
            this.parameters.put("tagging", value);
            return this;
        }

        public ListBucketsRequest build() {
            return new ListBucketsRequest(this);
        }
    }

}
