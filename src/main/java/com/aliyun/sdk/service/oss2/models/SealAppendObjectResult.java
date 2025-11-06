package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the SealAppendObject operation.
 */
public final class SealAppendObjectResult extends ResultModel {

    SealAppendObjectResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The GMT time when the SealAppendable operation was executed on the object.
     * If the object has not been sealed, the current operation time is returned.
     * If the object has been sealed, the corresponding operation time is returned.
     */
    public String sealedTime() {
        String value = headers.get("x-oss-sealed-time");
        return value;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(SealAppendObjectResult result) {
            super(result);
        }

        public SealAppendObjectResult build() {
            return new SealAppendObjectResult(this);
        }
    }
}
