package com.aliyun.sdk.service.oss2.tables.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CreateNamespaceResultJson {

    @JsonProperty("tableBucketARN")
    public String tableBucketARN;

    @JsonProperty("namespace")
    public List<String> namespace;
}
