package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ElementRelation {

    @JsonProperty("Type")
    private String type;

    @JsonProperty("ObjectId")
    private String objectId;

    public ElementRelation() {
    }

    public String getType() {
        return type;
    }

    public ElementRelation setType(String type) {
        this.type = type;
        return this;
    }

    public String getObjectId() {
        return objectId;
    }

    public ElementRelation setObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }
}
