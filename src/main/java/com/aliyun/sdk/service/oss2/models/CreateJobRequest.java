package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the CreateJob operation.
 */
public final class CreateJobRequest extends RequestModel {

    private final CreateJobRequestBody createJobBody;

    private CreateJobRequest(Builder builder) {
        super(builder);
        this.createJobBody = builder.createJobBody;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The request body for CreateJob.
     */
    public CreateJobRequestBody createJobBody() {
        return createJobBody;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private CreateJobRequestBody createJobBody;

        private Builder() {
            super();
        }

        private Builder(CreateJobRequest request) {
            super(request);
            this.createJobBody = request.createJobBody;
        }

        /**
         * The request body for CreateJob.
         */
        public Builder createJobBody(CreateJobRequestBody value) {
            requireNonNull(value);
            this.createJobBody = value;
            return this;
        }

        public CreateJobRequest build() {
            return new CreateJobRequest(this);
        }
    }
}
