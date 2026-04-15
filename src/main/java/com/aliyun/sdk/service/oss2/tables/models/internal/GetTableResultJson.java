package com.aliyun.sdk.service.oss2.tables.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GetTableResultJson {

    @JsonProperty("name")
    public String name;

    @JsonProperty("type")
    public String type;

    @JsonProperty("tableARN")
    public String tableARN;

    @JsonProperty("namespace")
    public List<String> namespace;

    @JsonProperty("namespaceId")
    public String namespaceId;

    @JsonProperty("versionToken")
    public String versionToken;

    @JsonProperty("metadataLocation")
    public String metadataLocation;

    @JsonProperty("warehouseLocation")
    public String warehouseLocation;

    @JsonProperty("createdAt")
    public String createdAt;

    @JsonProperty("createdBy")
    public String createdBy;

    @JsonProperty("modifiedAt")
    public String modifiedAt;

    @JsonProperty("modifiedBy")
    public String modifiedBy;

    @JsonProperty("ownerAccountId")
    public String ownerAccountId;

    @JsonProperty("format")
    public String format;

    @JsonProperty("tableBucketId")
    public String tableBucketId;

}
