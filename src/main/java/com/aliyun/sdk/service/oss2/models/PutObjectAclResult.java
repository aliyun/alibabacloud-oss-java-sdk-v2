package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutObjectAcl operation.
 */
public final class PutObjectAclResult extends ResultModel {

    PutObjectAclResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
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

        private Builder(PutObjectAclResult result) {
            super(result);
        }

        public PutObjectAclResult build() {
            return new PutObjectAclResult(this);
        }
    }
}
