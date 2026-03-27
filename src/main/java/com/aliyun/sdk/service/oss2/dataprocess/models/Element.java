package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "Element")
public final class Element {

    @JacksonXmlElementWrapper(localName = "ElementContents")
    @JacksonXmlProperty(localName = "ElementContent")
    private List<ElementContent> elementContents;

    @JacksonXmlProperty(localName = "ObjectId")
    private String objectId;

    @JacksonXmlProperty(localName = "ElementType")
    private String elementType;

    @JacksonXmlProperty(localName = "SemanticSimilarity")
    private Float semanticSimilarity;

    @JacksonXmlElementWrapper(localName = "ElementRelations")
    @JacksonXmlProperty(localName = "ElementRelation")
    private List<ElementRelation> elementRelations;

    public Element() {}

    private Element(Builder builder) {
        this.elementContents = builder.elementContents;
        this.objectId = builder.objectId;
        this.elementType = builder.elementType;
        this.semanticSimilarity = builder.semanticSimilarity;
        this.elementRelations = builder.elementRelations;
    }

    public List<ElementContent> elementContents() { return this.elementContents; }
    public String objectId() { return this.objectId; }
    public String elementType() { return this.elementType; }
    public Float semanticSimilarity() { return this.semanticSimilarity; }
    public List<ElementRelation> elementRelations() { return this.elementRelations; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private List<ElementContent> elementContents;
        private String objectId;
        private String elementType;
        private Float semanticSimilarity;
        private List<ElementRelation> elementRelations;

        public Builder elementContents(List<ElementContent> value) { this.elementContents = value; return this; }
        public Builder objectId(String value) { this.objectId = value; return this; }
        public Builder elementType(String value) { this.elementType = value; return this; }
        public Builder semanticSimilarity(Float value) { this.semanticSimilarity = value; return this; }
        public Builder elementRelations(List<ElementRelation> value) { this.elementRelations = value; return this; }

        private Builder() { super(); }

        private Builder(Element from) {
            this.elementContents = from.elementContents;
            this.objectId = from.objectId;
            this.elementType = from.elementType;
            this.semanticSimilarity = from.semanticSimilarity;
            this.elementRelations = from.elementRelations;
        }

        public Element build() { return new Element(this); }
    }
}
