package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutObjectTagging operation.
 */
public final class PutObjectTaggingResult extends ResultModel {

    PutObjectTaggingResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * VersionId used to reference a specific version of the object.
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

        private Builder(PutObjectTaggingResult result) {
            super(result);
        }

        public PutObjectTaggingResult build() {
            return new PutObjectTaggingResult(this);
        }
    }
}
