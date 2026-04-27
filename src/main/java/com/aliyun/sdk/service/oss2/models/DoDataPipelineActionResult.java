package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.transport.BinaryData;

/**
 * The result for the DoDataPipelineAction operation.
 */
public final class DoDataPipelineActionResult extends ResultModel {

    private final BinaryData body;

    public DoDataPipelineActionResult(Builder builder) {
        super(builder);
        this.body = builder.body;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Returns the response body as BinaryData.
     */
    public BinaryData body() {
        return body;
    }

    public static class Builder extends ResultModel.Builder<Builder> {
        private BinaryData body;

        public Builder body(BinaryData value) {
            this.body = value;
            return this;
        }

        public DoDataPipelineActionResult build() {
            return new DoDataPipelineActionResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(DoDataPipelineActionResult result) {
            super(result);
            this.body = result.body;
        }
    }
}
