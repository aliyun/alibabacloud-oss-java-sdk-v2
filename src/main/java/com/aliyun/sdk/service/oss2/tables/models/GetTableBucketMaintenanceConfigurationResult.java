package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.tables.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.tables.models.internal.GetTableBucketMaintenanceConfigurationResultJson;

import java.util.Map;
import java.util.Optional;

public final class GetTableBucketMaintenanceConfigurationResult extends ResultModel {
    private final GetTableBucketMaintenanceConfigurationResultJson delegate;

    private GetTableBucketMaintenanceConfigurationResult(Builder builder) {
        super(builder);
        this.delegate = (GetTableBucketMaintenanceConfigurationResultJson) Optional
                .ofNullable(innerBody)
                .orElse(new GetTableBucketMaintenanceConfigurationResultJson());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String tableBucketARN() {
        return delegate.tableBucketARN;
    }

    public Map<String, TableBucketMaintenanceConfigurationValue> configuration() {
        return CastUtils.ensureMap(delegate.configuration);
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() {
            super();
        }

        private Builder(GetTableBucketMaintenanceConfigurationResult from) {
            super(from);
        }

        public GetTableBucketMaintenanceConfigurationResult build() {
            return new GetTableBucketMaintenanceConfigurationResult(this);
        }
    }
}
