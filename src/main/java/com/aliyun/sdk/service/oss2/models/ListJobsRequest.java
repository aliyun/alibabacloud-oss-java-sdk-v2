package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the ListJobs operation.
 */
public final class ListJobsRequest extends RequestModel {

    private ListJobsRequest(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The job status filter.
     */
    public String batchJobStatuses() {
        return parameters.get("batchJobStatuses");
    }

    /**
     * The maximum number of jobs to return.
     */
    public String maxKeys() {
        return parameters.get("max-keys");
    }

    /**
     * The continuation token for pagination.
     */
    public String continuationToken() {
        return parameters.get("continuation-token");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(ListJobsRequest request) {
            super(request);
        }

        /**
         * The job status filter.
         */
        public Builder batchJobStatuses(String value) {
            requireNonNull(value);
            this.parameters.put("batchJobStatuses", value);
            return this;
        }

        /**
         * The maximum number of jobs to return.
         */
        public Builder maxKeys(Integer value) {
            requireNonNull(value);
            this.parameters.put("max-keys", String.valueOf(value));
            return this;
        }

        /**
         * The continuation token for pagination.
         */
        public Builder continuationToken(String value) {
            requireNonNull(value);
            this.parameters.put("continuation-token", value);
            return this;
        }

        public ListJobsRequest build() {
            return new ListJobsRequest(this);
        }
    }
}
