package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.models.VersioningConfiguration;

/**
 * The result for the GetAgenticBucketVersioning operation.
 */
public final class GetAgenticBucketVersioningResult extends ResultModel {
    /**
     * The versioning configuration of the bucket.
     */
    public VersioningConfiguration versioningConfiguration() { return (VersioningConfiguration) innerBody; }
    GetAgenticBucketVersioningResult(Builder builder) { super(builder); }
    public static Builder newBuilder() { return new Builder(); }
    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() { super(); }
        public GetAgenticBucketVersioningResult build() { return new GetAgenticBucketVersioningResult(this); }
    }
}
