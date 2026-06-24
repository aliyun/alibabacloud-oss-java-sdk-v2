package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

public final class DeleteSmartClusterResult extends ResultModel {

    DeleteSmartClusterResult(Builder builder) { super(builder); }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder extends ResultModel.Builder<Builder> {
        public DeleteSmartClusterResult build() { return new DeleteSmartClusterResult(this); }
        private Builder() { super(); }
        private Builder(DeleteSmartClusterResult result) { super(result); }
    }
}
