package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Clip {

    @JsonProperty("TimeRange")
    private List<Long> timeRange;

    public Clip() {
    }

    public List<Long> getTimeRange() {
        return timeRange;
    }

    public Clip setTimeRange(List<Long> timeRange) {
        this.timeRange = timeRange;
        return this;
    }
}
