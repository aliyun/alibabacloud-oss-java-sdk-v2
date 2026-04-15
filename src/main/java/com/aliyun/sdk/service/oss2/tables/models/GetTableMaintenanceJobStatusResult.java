package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.tables.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.tables.models.internal.GetTableMaintenanceJobStatusResultJson;

import java.util.Map;
import java.util.Optional;

public final class GetTableMaintenanceJobStatusResult extends ResultModel {
    private final GetTableMaintenanceJobStatusResultJson delegate;

    private GetTableMaintenanceJobStatusResult(Builder builder) {
        super(builder);
        this.delegate = (GetTableMaintenanceJobStatusResultJson) Optional.ofNullable(innerBody).orElse(new GetTableMaintenanceJobStatusResultJson());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String tableARN() {
        return delegate.tableARN;
    }

    public Map<String, TableMaintenanceJobStatusValue> jobStatus() {
        return CastUtils.ensureMap(delegate.status);
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {
        private Builder() {
            super();
        }

        private Builder(GetTableMaintenanceJobStatusResult from) {
            super(from);
        }

        public GetTableMaintenanceJobStatusResult build() {
            return new GetTableMaintenanceJobStatusResult(this);
        }
    }
}
