package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.tables.models.internal.GetTableMetadataLocationResultJson;

import java.util.Optional;

public final class GetTableMetadataLocationResult extends ResultModel {
    private final GetTableMetadataLocationResultJson delegate;

    private GetTableMetadataLocationResult(Builder builder) {
        super(builder);
        this.delegate = (GetTableMetadataLocationResultJson) Optional.ofNullable(innerBody).orElse(new GetTableMetadataLocationResultJson());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String versionToken() {
        return delegate.versionToken;
    }

    public String metadataLocation() {
        return delegate.metadataLocation;
    }

    public String warehouseLocation() {
        return delegate.warehouseLocation;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() {
            super();
        }

        private Builder(GetTableMetadataLocationResult from) {
            super(from);
        }

        public GetTableMetadataLocationResult build() {
            return new GetTableMetadataLocationResult(this);
        }
    }
}
