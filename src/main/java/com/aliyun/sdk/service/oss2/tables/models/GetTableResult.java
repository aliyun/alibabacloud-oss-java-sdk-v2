package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.tables.models.internal.GetTableResultJson;

import java.util.List;
import java.util.Optional;

public final class GetTableResult extends ResultModel {
    private final GetTableResultJson delegate;

    private GetTableResult(Builder builder) {
        super(builder);
        this.delegate = (GetTableResultJson) Optional.ofNullable(innerBody).orElse(new GetTableResultJson());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String name() {
        return delegate.name;
    }

    public String type() {
        return delegate.type;
    }

    public String tableARN() {
        return delegate.tableARN;
    }

    public List<String> namespace() {
        return delegate.namespace;
    }

    public String namespaceId() {
        return delegate.namespaceId;
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

    public String createdAt() {
        return delegate.createdAt != null ? delegate.createdAt.toString() : null;
    }

    public String createdBy() {
        return delegate.createdBy;
    }

    public String modifiedAt() {
        return delegate.modifiedAt != null ? delegate.modifiedAt.toString() : null;
    }

    public String modifiedBy() {
        return delegate.modifiedBy;
    }

    public String ownerAccountId() {
        return delegate.ownerAccountId;
    }

    public String format() {
        return delegate.format;
    }

    public String tableBucketId() {
        return delegate.tableBucketId;
    }


    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() {
            super();
        }

        private Builder(GetTableResult from) {
            super(from);
        }

        public GetTableResult build() {
            return new GetTableResult(this);
        }
    }
}