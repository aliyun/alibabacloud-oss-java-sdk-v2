package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the DescribeRegions operation.
 */
public final class DescribeRegionsRequest extends RequestModel {

    private DescribeRegionsRequest(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The region ID of the request.
     */
    public String regions() {
        String value = parameters.get("regions");
        return value;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {

        private Builder() {
            super();
        }


        private Builder(DescribeRegionsRequest request) {
            super(request);
        }

        /**
         * The region ID of the request.
         */
        public Builder regions(String value) {
            requireNonNull(value);
            this.parameters.put("regions", value);
            return this;
        }

        public DescribeRegionsRequest build() {
            return new DescribeRegionsRequest(this);
        }
    }

}
