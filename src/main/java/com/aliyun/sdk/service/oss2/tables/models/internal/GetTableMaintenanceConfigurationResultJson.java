package com.aliyun.sdk.service.oss2.tables.models.internal;

import com.aliyun.sdk.service.oss2.tables.models.TableMaintenanceConfigurationValue;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class GetTableMaintenanceConfigurationResultJson {

    @JsonProperty("tableARN")
    public String tableARN;

    @JsonProperty("configuration")
    public Map<String, TableMaintenanceConfigurationValue> configuration;
}
