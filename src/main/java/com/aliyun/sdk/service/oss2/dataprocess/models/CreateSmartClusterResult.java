package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

public final class CreateSmartClusterResult extends ResultModel {

    public String objectId() {
        CreateSmartClusterResponseBody body = (CreateSmartClusterResponseBody) innerBody;
        return body != null ? body.objectId() : null;
    }

    CreateSmartClusterResult(Builder builder) { super(builder); }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder extends ResultModel.Builder<Builder> {
        public CreateSmartClusterResult build() { return new CreateSmartClusterResult(this); }
        private Builder() { super(); }
        private Builder(CreateSmartClusterResult result) { super(result); }
    }
}
