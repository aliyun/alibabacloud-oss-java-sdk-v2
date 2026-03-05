package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Aggregation {

    @JsonProperty("Field")
    private String field;

    @JsonProperty("Operation")
    private String operation;

    public Aggregation() {
    }

    public String getField() {
        return field;
    }

    public Aggregation setField(String field) {
        this.field = field;
        return this;
    }

    public String getOperation() {
        return operation;
    }

    public Aggregation setOperation(String operation) {
        this.operation = operation;
        return this;
    }
}
