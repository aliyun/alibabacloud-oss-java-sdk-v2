package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

public final class PutTableBucketMaintenanceConfigurationResult extends ResultModel {
    PutTableBucketMaintenanceConfigurationResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() {
            super();
        }

        private Builder(PutTableBucketMaintenanceConfigurationResult from) {
            super(from);
        }

        public PutTableBucketMaintenanceConfigurationResult build() {
            return new PutTableBucketMaintenanceConfigurationResult(this);
        }
    }
}
