package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

public final class PutTableMaintenanceConfigurationResult extends ResultModel {
    PutTableMaintenanceConfigurationResult(Builder builder) {
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

        private Builder(PutTableMaintenanceConfigurationResult from) {
            super(from);
        }

        public PutTableMaintenanceConfigurationResult build() {
            return new PutTableMaintenanceConfigurationResult(this);
        }
    }
}
