package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * JSON model for list vector indexes result
 */
public class ListVectorIndexesResultJson {

    @JsonProperty("indexes")
    public List<Map<String, Object>> indexes;

    @JsonProperty("nextToken")
    public String nextToken;

    public List<Map<String, Object>> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<Map<String, Object>> indexes) {
        this.indexes = indexes;
    }

    public String getNextToken() {
        return nextToken;
    }

    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }
}
