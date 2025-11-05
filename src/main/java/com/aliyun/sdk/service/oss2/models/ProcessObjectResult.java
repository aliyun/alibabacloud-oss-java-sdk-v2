package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.models.internal.ImageProcessJson;

/**
 * The result for the ProcessObject operation.
 */
public final class ProcessObjectResult extends ResultModel {

    /**
     * The container that stores the ACL information.
     */
    public ImageProcessJson imageProcessResult() {
        return (ImageProcessJson)innerBody;
    }


    private ProcessObjectResult(Builder builder) {
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

        private Builder(ProcessObjectResult result) {
            super(result);
        }

        public ProcessObjectResult build() {
            return new ProcessObjectResult(this);
        }
    }
}
