package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 * JSON model for vector list
 */
public class VectorListJson {

    @JsonProperty("nextToken")
    public String nextToken;

    @JsonProperty("vectors")
    public List<Map<String, Object>> vectors;

    public String getNextToken() {
        return nextToken;
    }

    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }

    public List<Map<String, Object>> getVectors() {
        return vectors;
    }

    public void setVectors(List<Map<String, Object>> vectors) {
        this.vectors = vectors;
    }
}

