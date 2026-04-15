package com.aliyun.sdk.service.oss2.tables.models.internal;

import com.aliyun.sdk.service.oss2.tables.models.TableBucketMaintenanceConfigurationValue;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class GetTableBucketMaintenanceConfigurationResultJson {

    @JsonProperty("tableBucketARN")
    public String tableBucketARN;

    @JsonProperty("configuration")
    public Map<String, TableBucketMaintenanceConfigurationValue> configuration;
}
