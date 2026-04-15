package com.aliyun.sdk.service.oss2.tables.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetTableMetadataLocationResultJson {

    @JsonProperty("versionToken")
    public String versionToken;

    @JsonProperty("metadataLocation")
    public String metadataLocation;

    @JsonProperty("warehouseLocation")
    public String warehouseLocation;
}
