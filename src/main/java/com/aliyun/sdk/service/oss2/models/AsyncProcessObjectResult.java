package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.models.internal.AsyncProcessJson;

/**
 * The result for the AsyncProcessObject operation.
 */
public final class AsyncProcessObjectResult extends ResultModel {

    /**
     * The container that stores the async process information.
     */
    public AsyncProcessJson asyncProcessJson() {
        return (AsyncProcessJson)innerBody;
    }

    private AsyncProcessObjectResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(AsyncProcessObjectResult result) {
            super(result);
        }

        public AsyncProcessObjectResult build() {
            return new AsyncProcessObjectResult(this);
        }
    }
}
