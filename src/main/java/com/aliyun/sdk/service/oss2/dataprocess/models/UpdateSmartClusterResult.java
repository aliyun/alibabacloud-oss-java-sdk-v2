package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

public final class UpdateSmartClusterResult extends ResultModel {

    public String objectId() {
        UpdateSmartClusterResponseBody body = (UpdateSmartClusterResponseBody) innerBody;
        return body != null ? body.objectId() : null;
    }

    UpdateSmartClusterResult(Builder builder) { super(builder); }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder extends ResultModel.Builder<Builder> {
        public UpdateSmartClusterResult build() { return new UpdateSmartClusterResult(this); }
        private Builder() { super(); }
        private Builder(UpdateSmartClusterResult result) { super(result); }
    }
}
