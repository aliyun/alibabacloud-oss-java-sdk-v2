package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.models.internal.DeleteResultXml;

import java.util.List;

/**
 * The result for the DeleteMultipleObjects operation.
 */
public final class DeleteMultipleObjectsResult extends ResultModel {
    private final DeleteResultXml delegate;


    DeleteMultipleObjectsResult(Builder builder) {
        super(builder);
        this.delegate = (DeleteResultXml) innerBody;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The container that stores information about the deleted objects.
     */
    public List<DeletedInfo> deletedObjects() {
        return CastUtils.ensureList(this.delegate.deleted);
    }

    /**
     * The encoding type of the object name in the response. If this parameter is specified in the request, the object name is encoded in the response.
     */
    public String encodingType() {
        return this.delegate.encodingType;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(DeleteMultipleObjectsResult result) {
            super(result);
        }

        public DeleteMultipleObjectsResult build() {
            return new DeleteMultipleObjectsResult(this);
        }
    }
}
