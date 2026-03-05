package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowParameter {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Value")
    private String value;

    public WorkflowParameter() {
    }

    public String getName() {
        return name;
    }

    public WorkflowParameter setName(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public WorkflowParameter setValue(String value) {
        this.value = value;
        return this;
    }
}
