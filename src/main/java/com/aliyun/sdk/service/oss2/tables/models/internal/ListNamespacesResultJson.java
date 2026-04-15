package com.aliyun.sdk.service.oss2.tables.models.internal;

import com.aliyun.sdk.service.oss2.tables.models.NamespaceSummary;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ListNamespacesResultJson {

    @JsonProperty("namespaces")
    public List<NamespaceSummary> namespaces;

    @JsonProperty("continuationToken")
    public String continuationToken;
}
