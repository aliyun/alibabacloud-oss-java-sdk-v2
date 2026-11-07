package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the UpdateJobPriority operation.
 */
public final class UpdateJobPriorityResult extends ResultModel {

    UpdateJobPriorityResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The response body containing the updated priority information.
     */
    public UpdateJobPriorityResultBody updateJobPriorityResult() {
        return (UpdateJobPriorityResultBody) innerBody;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(UpdateJobPriorityResult result) {
            super(result);
        }

        public UpdateJobPriorityResult build() {
            return new UpdateJobPriorityResult(this);
        }
    }
}
