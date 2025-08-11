package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the RestoreObject operation.
 */
public final class RestoreObjectResult extends ResultModel {

    RestoreObjectResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The restoration priority.
     * This header is displayed only for the Cold Archive or Deep Cold Archive object in the restored state.
     */
    public String objectRestorePriority() {
        String value = headers.get("x-oss-object-restore-priority");
        return value;
    }

    /**
     * Version of the object.
     */
    public String versionId() {
        String value = headers.get("x-oss-version-id");
        return value;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(RestoreObjectResult result) {
            super(result);
        }

        public RestoreObjectResult build() {
            return new RestoreObjectResult(this);
        }
    }
}
