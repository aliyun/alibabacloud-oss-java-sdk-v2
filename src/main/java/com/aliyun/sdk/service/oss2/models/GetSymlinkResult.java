package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetSymlink operation.
 */
public final class GetSymlinkResult extends ResultModel {

    GetSymlinkResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Indicates the target object that the symbol link directs to.
     */
    public String symlinkTarget() {
        String value = headers.get("x-oss-symlink-target");
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

        private Builder(GetSymlinkResult result) {
            super(result);
        }

        public GetSymlinkResult build() {
            return new GetSymlinkResult(this);
        }
    }
}
