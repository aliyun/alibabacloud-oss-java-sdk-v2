package com.aliyun.sdk.service.oss2.tables.models.internal;

import com.aliyun.sdk.service.oss2.tables.models.TableMaintenanceJobStatusValue;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class GetTableMaintenanceJobStatusResultJson {

    @JsonProperty("tableARN")
    public String tableARN;

    @JsonProperty("status")
    public Map<String, TableMaintenanceJobStatusValue> status;
}
