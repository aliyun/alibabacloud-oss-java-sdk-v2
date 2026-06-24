package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

public final class GetMetaQueryStatusResult extends ResultModel {

    public MetaQueryStatus metaQueryStatus() {
        return (MetaQueryStatus) innerBody;
    }

    GetMetaQueryStatusResult(Builder builder) { super(builder); }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder extends ResultModel.Builder<Builder> {
        public GetMetaQueryStatusResult build() { return new GetMetaQueryStatusResult(this); }
        private Builder() { super(); }
        private Builder(GetMetaQueryStatusResult result) { super(result); }
    }
}
