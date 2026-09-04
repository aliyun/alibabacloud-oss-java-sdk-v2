package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the UpdateJobPriority operation.
 */
public final class UpdateJobPriorityRequest extends RequestModel {

    private UpdateJobPriorityRequest(Builder builder) {
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
     * The target priority for the job.
     */
    public String targetPriority() {
        return parameters.get("targetPriority");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(UpdateJobPriorityRequest request) {
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
         * The target priority for the job.
         */
        public Builder targetPriority(Integer value) {
            requireNonNull(value);
            this.parameters.put("targetPriority", String.valueOf(value));
            return this;
        }

        public UpdateJobPriorityRequest build() {
            return new UpdateJobPriorityRequest(this);
        }
    }
}
