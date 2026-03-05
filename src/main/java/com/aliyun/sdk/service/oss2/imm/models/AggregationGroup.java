package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AggregationGroup {

    @JsonProperty("Value")
    private String value;

    @JsonProperty("Count")
    private Long count;

    public AggregationGroup() {
    }

    public String getValue() {
        return value;
    }

    public AggregationGroup setValue(String value) {
        this.value = value;
        return this;
    }

    public Long getCount() {
        return count;
    }

    public AggregationGroup setCount(Long count) {
        this.count = count;
        return this;
    }
}
