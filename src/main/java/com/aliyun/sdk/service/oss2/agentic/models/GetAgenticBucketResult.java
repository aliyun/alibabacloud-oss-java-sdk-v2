package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the GetAgenticBucket operation.
 */
public final class GetAgenticBucketResult extends ResultModel {

    /**
     * The information of the agentic bucket.
     */
    public AgenticBucketInfo agenticBucketInfo() {
        return (AgenticBucketInfo) innerBody;
    }

    GetAgenticBucketResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        public GetAgenticBucketResult build() {
            return new GetAgenticBucketResult(this);
        }
    }
}
