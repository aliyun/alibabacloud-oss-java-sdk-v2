package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.PublicAccessBlockConfiguration;
import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the GetAgenticBucketPublicAccessBlock operation.
 */
public final class GetAgenticBucketPublicAccessBlockResult extends ResultModel {
    /**
     * The public access block configuration of the bucket.
     */
    public PublicAccessBlockConfiguration publicAccessBlockConfiguration() { return (PublicAccessBlockConfiguration) innerBody; }
    GetAgenticBucketPublicAccessBlockResult(Builder builder) { super(builder); }
    public static Builder newBuilder() { return new Builder(); }
    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() { super(); }
        public GetAgenticBucketPublicAccessBlockResult build() { return new GetAgenticBucketPublicAccessBlockResult(this); }
    }
}
