package com.aliyun.sdk.service.oss2.tables.models.internal;

import com.aliyun.sdk.service.oss2.tables.models.TableSummary;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ListTablesResultJson {

    @JsonProperty("tables")
    public List<TableSummary> tables;

    @JsonProperty("continuationToken")
    public String continuationToken;
}
