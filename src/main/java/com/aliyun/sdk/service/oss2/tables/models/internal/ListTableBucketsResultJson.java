package com.aliyun.sdk.service.oss2.tables.models.internal;

import com.aliyun.sdk.service.oss2.tables.models.TableBucketSummary;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ListTableBucketsResultJson {

    @JsonProperty("tableBuckets")
    public List<TableBucketSummary> tableBuckets;

    @JsonProperty("continuationToken")
    public String continuationToken;
}
