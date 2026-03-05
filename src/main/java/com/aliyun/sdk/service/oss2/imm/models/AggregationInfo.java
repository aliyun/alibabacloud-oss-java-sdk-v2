package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AggregationInfo {

    @JsonProperty("Field")
    private String field;

    @JsonProperty("Operation")
    private String operation;

    @JsonProperty("Value")
    private Double value;

    @JsonProperty("Groups")
    private List<AggregationGroup> groups;

    public AggregationInfo() {
    }

    public String getField() {
        return field;
    }

    public AggregationInfo setField(String field) {
        this.field = field;
        return this;
    }

    public String getOperation() {
        return operation;
    }

    public AggregationInfo setOperation(String operation) {
        this.operation = operation;
        return this;
    }

    public Double getValue() {
        return value;
    }

    public AggregationInfo setValue(Double value) {
        this.value = value;
        return this;
    }

    public List<AggregationGroup> getGroups() {
        return groups;
    }

    public AggregationInfo setGroups(List<AggregationGroup> groups) {
        this.groups = groups;
        return this;
    }
}
