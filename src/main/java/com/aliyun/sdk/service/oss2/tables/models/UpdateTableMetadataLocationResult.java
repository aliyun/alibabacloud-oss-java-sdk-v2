package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.tables.models.internal.UpdateTableMetadataLocationResultJson;

import java.util.List;
import java.util.Optional;

public final class UpdateTableMetadataLocationResult extends ResultModel {
    private final UpdateTableMetadataLocationResultJson delegate;

    private UpdateTableMetadataLocationResult(Builder builder) {
        super(builder);
        this.delegate = (UpdateTableMetadataLocationResultJson) Optional.ofNullable(innerBody).orElse(new UpdateTableMetadataLocationResultJson());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String name() {
        return delegate.name;
    }

    public String versionToken() {
        return delegate.versionToken;
    }

    public String metadataLocation() {
        return delegate.metadataLocation;
    }

    public List<String> namespace() {
        return delegate.namespace;
    }

    public String tableARN() {
        return delegate.tableARN;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() {
            super();
        }

        private Builder(UpdateTableMetadataLocationResult from) {
            super(from);
        }

        public UpdateTableMetadataLocationResult build() {
            return new UpdateTableMetadataLocationResult(this);
        }
    }
}
