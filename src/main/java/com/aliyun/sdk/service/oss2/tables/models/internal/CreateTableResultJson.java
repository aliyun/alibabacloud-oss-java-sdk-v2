package com.aliyun.sdk.service.oss2.tables.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateTableResultJson {

    @JsonProperty("tableARN")
    public String tableARN;

    @JsonProperty("versionToken")
    public String versionToken;
}