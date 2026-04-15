package com.aliyun.sdk.service.oss2.tables.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GetNamespaceResultJson {

    @JsonProperty("namespace")
    public List<String> namespace;

    @JsonProperty("namespaceId")
    public String namespaceId;

    @JsonProperty("tableBucketId")
    public String tableBucketId;

    @JsonProperty("ownerAccountId")
    public String ownerAccountId;

    @JsonProperty("createdAt")
    public String createdAt;

    @JsonProperty("createdBy")
    public String createdBy;
}