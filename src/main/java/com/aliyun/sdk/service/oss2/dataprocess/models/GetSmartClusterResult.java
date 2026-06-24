package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

public final class GetSmartClusterResult extends ResultModel {

    public SmartClusterInfo smartCluster() {
        GetSmartClusterResponseBody body = (GetSmartClusterResponseBody) innerBody;
        return body != null ? body.smartCluster() : null;
    }

    GetSmartClusterResult(Builder builder) { super(builder); }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder extends ResultModel.Builder<Builder> {
        public GetSmartClusterResult build() { return new GetSmartClusterResult(this); }
        private Builder() { super(); }
        private Builder(GetSmartClusterResult result) { super(result); }
    }
}
