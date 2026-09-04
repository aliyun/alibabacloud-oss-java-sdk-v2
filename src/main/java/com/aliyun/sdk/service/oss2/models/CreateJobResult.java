package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the CreateJob operation.
 */
public final class CreateJobResult extends ResultModel {

    CreateJobResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The response body containing the job ID.
     */
    public CreateJobResultBody createJobResult() {
        return (CreateJobResultBody) innerBody;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(CreateJobResult result) {
            super(result);
        }

        public CreateJobResult build() {
            return new CreateJobResult(this);
        }
    }
}
