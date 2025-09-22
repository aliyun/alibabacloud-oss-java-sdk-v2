package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * JSON model for index info
 */
public class IndexInfoJson {

    @JsonProperty("index")
    public Map<String, Object> index;

    public IndexInfoJson() {
    }
}

