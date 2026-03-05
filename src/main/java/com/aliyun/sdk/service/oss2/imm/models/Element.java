package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Element {

    @JsonProperty("ElementContents")
    private List<ElementContent> elementContents;

    @JsonProperty("ObjectId")
    private String objectId;

    @JsonProperty("ElementType")
    private String elementType;

    @JsonProperty("SemanticSimilarity")
    private Float semanticSimilarity;

    @JsonProperty("ElementRelations")
    private List<ElementRelation> elementRelations;

    public Element() {
    }

    public List<ElementContent> getElementContents() {
        return elementContents;
    }

    public Element setElementContents(List<ElementContent> elementContents) {
        this.elementContents = elementContents;
        return this;
    }

    public String getObjectId() {
        return objectId;
    }

    public Element setObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public String getElementType() {
        return elementType;
    }

    public Element setElementType(String elementType) {
        this.elementType = elementType;
        return this;
    }

    public Float getSemanticSimilarity() {
        return semanticSimilarity;
    }

    public Element setSemanticSimilarity(Float semanticSimilarity) {
        this.semanticSimilarity = semanticSimilarity;
        return this;
    }

    public List<ElementRelation> getElementRelations() {
        return elementRelations;
    }

    public Element setElementRelations(List<ElementRelation> elementRelations) {
        this.elementRelations = elementRelations;
        return this;
    }
}
