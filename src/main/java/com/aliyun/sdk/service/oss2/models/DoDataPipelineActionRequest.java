package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.transport.BinaryData;

import static java.util.Objects.requireNonNull;

/**
 * The request for the DoDataPipelineAction operation.
 */
public final class DoDataPipelineActionRequest extends RequestModel {
    private final BinaryData body;

    private DoDataPipelineActionRequest(Builder builder) {
        super(builder);
        this.body = builder.body;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The action parameter.
     */
    public String action() {
        String value = parameters.get("action");
        return value;
    }

    public BinaryData body() {
        return body;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private BinaryData body;

        private Builder() {
            super();
        }

        private Builder(DoDataPipelineActionRequest request) {
            super(request);
            this.body = request.body;
        }

        /**
         * The action parameter.
         */
        public Builder action(String value) {
            requireNonNull(value);
            this.parameters.put("action", value);
            return this;
        }

        public Builder body(BinaryData value) {
            this.body = value;
            return this;
        }

        public DoDataPipelineActionRequest build() {
            return new DoDataPipelineActionRequest(this);
        }
    }
}
