package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the DescribeJob operation.
 */
public final class DescribeJobRequest extends RequestModel {

    private DescribeJobRequest(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The ID of the batch operation job.
     */
    public String batchJobId() {
        return parameters.get("batchJobId");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(DescribeJobRequest request) {
            super(request);
        }

        /**
         * The ID of the batch operation job.
         */
        public Builder batchJobId(String value) {
            requireNonNull(value);
            this.parameters.put("batchJobId", value);
            return this;
        }

        public DescribeJobRequest build() {
            return new DescribeJobRequest(this);
        }
    }
}
