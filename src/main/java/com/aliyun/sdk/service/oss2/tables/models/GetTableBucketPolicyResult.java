package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.tables.models.internal.GetTableBucketPolicyResultJson;

import java.util.Optional;

public final class GetTableBucketPolicyResult extends ResultModel {
    private final GetTableBucketPolicyResultJson delegate;

    private GetTableBucketPolicyResult(Builder builder) {
        super(builder);
        this.delegate = (GetTableBucketPolicyResultJson) Optional
                .ofNullable(innerBody)
                .orElse(new GetTableBucketPolicyResultJson());
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

        private Builder(GetTableBucketPolicyResult from) {
            super(from);
        }

        public GetTableBucketPolicyResult build() {
            return new GetTableBucketPolicyResult(this);
        }
    }
}
