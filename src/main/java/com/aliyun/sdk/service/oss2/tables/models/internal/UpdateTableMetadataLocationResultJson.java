package com.aliyun.sdk.service.oss2.tables.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UpdateTableMetadataLocationResultJson {

    @JsonProperty("name")
    public String name;

    @JsonProperty("versionToken")
    public String versionToken;

    @JsonProperty("metadataLocation")
    public String metadataLocation;

    @JsonProperty("namespace")
    public List<String> namespace;

    @JsonProperty("tableARN")
    public String tableARN;
}
