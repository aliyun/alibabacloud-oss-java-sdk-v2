package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.tables.models.internal.GetTablePolicyResultJson;

import java.util.Optional;

public final class GetTablePolicyResult extends ResultModel {
    private final GetTablePolicyResultJson delegate;

    private GetTablePolicyResult(Builder builder) {
        super(builder);
        this.delegate = (GetTablePolicyResultJson) Optional.ofNullable(innerBody).orElse(new GetTablePolicyResultJson());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String resourcePolicy() {
        return delegate.resourcePolicy;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() {
            super();
        }

        private Builder(GetTablePolicyResult from) {
            super(from);
        }

        public GetTablePolicyResult build() {
            return new GetTablePolicyResult(this);
        }
    }
}
