package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

public final class DeleteFileMetaResult extends ResultModel {

    DeleteFileMetaResult(Builder builder) { super(builder); }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder extends ResultModel.Builder<Builder> {
        public DeleteFileMetaResult build() { return new DeleteFileMetaResult(this); }
        private Builder() { super(); }
        private Builder(DeleteFileMetaResult result) { super(result); }
    }
}
