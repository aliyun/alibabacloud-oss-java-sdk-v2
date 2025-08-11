package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

/**
 * The result for the DeleteObject operation.
 */
public final class DeleteObjectResult extends ResultModel {

    DeleteObjectResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Indicates whether the deleted version is a delete marker.
     */
    public Boolean deleteMarker() {
        String value = headers.get("x-oss-delete-marker");
        return ConvertUtils.toBoolOrNull(value);
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

        private Builder(DeleteObjectResult result) {
            super(result);
        }

        public DeleteObjectResult build() {
            return new DeleteObjectResult(this);
        }
    }
}
