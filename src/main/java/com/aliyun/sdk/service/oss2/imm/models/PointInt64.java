package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PointInt64 {

    @JsonProperty("X")
    private Long x;

    @JsonProperty("Y")
    private Long y;

    public PointInt64() {
    }

    public Long getX() {
        return x;
    }

    public PointInt64 setX(Long x) {
        this.x = x;
        return this;
    }

    public Long getY() {
        return y;
    }

    public PointInt64 setY(Long y) {
        this.y = y;
        return this;
    }
}
