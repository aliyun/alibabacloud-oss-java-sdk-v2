package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleQuery {

    @JsonProperty("Field")
    private String field;

    @JsonProperty("Value")
    private String value;

    @JsonProperty("Operation")
    private String operation;

    @JsonProperty("SubQueries")
    private List<SimpleQuery> subQueries;

    public SimpleQuery() {
    }

    public String getField() {
        return field;
    }

    public SimpleQuery setField(String field) {
        this.field = field;
        return this;
    }

    public String getValue() {
        return value;
    }

    public SimpleQuery setValue(String value) {
        this.value = value;
        return this;
    }

    public String getOperation() {
        return operation;
    }

    public SimpleQuery setOperation(String operation) {
        this.operation = operation;
        return this;
    }

    public List<SimpleQuery> getSubQueries() {
        return subQueries;
    }

    public SimpleQuery setSubQueries(List<SimpleQuery> subQueries) {
        this.subQueries = subQueries;
        return this;
    }
}
