package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

/**
 * The result for the GetObjectMeta operation.
 */
public final class GetObjectMetaResult extends ResultModel {

    GetObjectMetaResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The time when the storage class of the object is converted to Cold Archive or Deep Cold Archive based on lifecycle rules.
     */
    public String transitionTime() {
        String value = headers.get("x-oss-transition-time");
        return value;
    }

    /**
     * Version of the object.
     */
    public String versionId() {
        String value = headers.get("x-oss-version-id");
        return value;
    }

    /**
     * The entity tag (ETag).
     */
    public String eTag() {
        String value = headers.get("ETag");
        return value;
    }

    /**
     * Size of the body in bytes.
     */
    public Long contentLength() {
        String value = headers.get("Content-Length");
        return ConvertUtils.toLongOrNull(value);
    }

    /**
     * The time when the object was last accessed.
     */
    public String lastAccessTime() {
        String value = headers.get("x-oss-last-access-time");
        return value;
    }

    /**
     * The time when the returned objects were last modified.
     */
    public String lastModified() {
        String value = headers.get("Last-Modified");
        return value;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(GetObjectMetaResult result) {
            super(result);
        }

        public GetObjectMetaResult build() {
            return new GetObjectMetaResult(this);
        }
    }
}
