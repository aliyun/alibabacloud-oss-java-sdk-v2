package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the UpdateJobStatus operation.
 */
public final class UpdateJobStatusResult extends ResultModel {

    UpdateJobStatusResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The response body containing the updated status information.
     */
    public UpdateJobStatusResultBody updateJobStatusResult() {
        return (UpdateJobStatusResultBody) innerBody;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(UpdateJobStatusResult result) {
            super(result);
        }

        public UpdateJobStatusResult build() {
            return new UpdateJobStatusResult(this);
        }
    }
}
