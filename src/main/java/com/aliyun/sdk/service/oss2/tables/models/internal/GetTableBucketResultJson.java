package com.aliyun.sdk.service.oss2.tables.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetTableBucketResultJson {

    @JsonProperty("arn")
    public String arn;

    @JsonProperty("name")
    public String name;

    @JsonProperty("ownerAccountId")
    public String ownerAccountId;

    @JsonProperty("createdAt")
    public String createdAt;

    @JsonProperty("tableBucketId")
    public String tableBucketId;

    @JsonProperty("type")
    public String type;

}
