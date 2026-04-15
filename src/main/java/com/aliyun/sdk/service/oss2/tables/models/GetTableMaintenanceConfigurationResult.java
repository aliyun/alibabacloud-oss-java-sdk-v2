package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.tables.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.tables.models.internal.GetTableMaintenanceConfigurationResultJson;

import java.util.Map;
import java.util.Optional;

public final class GetTableMaintenanceConfigurationResult extends ResultModel {
    private final GetTableMaintenanceConfigurationResultJson delegate;

    private GetTableMaintenanceConfigurationResult(Builder builder) {
        super(builder);
        this.delegate = (GetTableMaintenanceConfigurationResultJson) Optional.ofNullable(innerBody).orElse(new GetTableMaintenanceConfigurationResultJson());
    }

    public static Builder newBuilder() {
        return new Builder();
    }
    
    public String tableARN() {
        return delegate.tableARN;
    }
    
    public Map<String, TableMaintenanceConfigurationValue> configuration() {
        return CastUtils.ensureMap(delegate.configuration);
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() {
            super();
        }

        private Builder(GetTableMaintenanceConfigurationResult from) {
            super(from);
        }

        public GetTableMaintenanceConfigurationResult build() {
            return new GetTableMaintenanceConfigurationResult(this);
        }
    }
}
