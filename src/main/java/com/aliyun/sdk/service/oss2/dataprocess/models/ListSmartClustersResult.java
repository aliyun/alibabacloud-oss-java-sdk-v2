package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

import java.util.List;

public final class ListSmartClustersResult extends ResultModel {

    public String nextToken() {
        ListSmartClustersResponseBody body = (ListSmartClustersResponseBody) innerBody;
        return body != null ? body.nextToken() : null;
    }

    public List<SmartClusterInfo> smartClusters() {
        ListSmartClustersResponseBody body = (ListSmartClustersResponseBody) innerBody;
        return body != null ? body.smartClusters() : null;
    }

    ListSmartClustersResult(Builder builder) { super(builder); }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder extends ResultModel.Builder<Builder> {
        public ListSmartClustersResult build() { return new ListSmartClustersResult(this); }
        private Builder() { super(); }
        private Builder(ListSmartClustersResult result) { super(result); }
    }
}
