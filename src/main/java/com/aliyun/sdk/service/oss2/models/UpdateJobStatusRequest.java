package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the UpdateJobStatus operation.
 */
public final class UpdateJobStatusRequest extends RequestModel {

    private UpdateJobStatusRequest(Builder builder) {
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

    /**
     * The requested job status.
     */
    public String requestedJobStatus() {
        return parameters.get("requestedJobStatus");
    }

    /**
     * The reason for the status update.
     */
    public String statusUpdateReason() {
        return parameters.get("statusUpdateReason");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(UpdateJobStatusRequest request) {
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

        /**
         * The requested job status. Valid values: Cancelled, Ready.
         */
        public Builder requestedJobStatus(String value) {
            requireNonNull(value);
            this.parameters.put("requestedJobStatus", value);
            return this;
        }

        /**
         * The reason for the status update.
         */
        public Builder statusUpdateReason(String value) {
            requireNonNull(value);
            this.parameters.put("statusUpdateReason", value);
            return this;
        }

        public UpdateJobStatusRequest build() {
            return new UpdateJobStatusRequest(this);
        }
    }
}
