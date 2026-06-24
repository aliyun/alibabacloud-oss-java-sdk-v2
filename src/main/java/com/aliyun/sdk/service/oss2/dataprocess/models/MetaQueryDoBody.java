package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "MetaQuery")
public final class MetaQueryDoBody {

    @JsonProperty("Query")
    @JacksonXmlProperty(localName = "Query")
    private String query;

    @JsonProperty("Sort")
    @JacksonXmlProperty(localName = "Sort")
    private String sort;

    @JsonProperty("Order")
    @JacksonXmlProperty(localName = "Order")
    private String order;

    @JsonProperty("Aggregations")
    @JacksonXmlElementWrapper(localName = "Aggregations")
    @JacksonXmlProperty(localName = "Aggregation")
    private List<Aggregation> aggregations;

    @JsonProperty("MaxResults")
    @JacksonXmlProperty(localName = "MaxResults")
    private Integer maxResults;

    @JsonProperty("NextToken")
    @JacksonXmlProperty(localName = "NextToken")
    private String nextToken;

    @JsonProperty("WithFields")
    @JacksonXmlElementWrapper(localName = "WithFields")
    @JacksonXmlProperty(localName = "WithField")
    private List<String> withFields;

    @JsonProperty("WithoutTotalHits")
    @JacksonXmlProperty(localName = "WithoutTotalHits")
    private String withoutTotalHits;

    @JsonProperty("SourceURI")
    @JacksonXmlProperty(localName = "SourceURI")
    private String sourceURI;

    @JsonProperty("MediaTypes")
    @JacksonXmlElementWrapper(localName = "MediaTypes")
    @JacksonXmlProperty(localName = "MediaType")
    private List<String> mediaTypes;

    @JsonProperty("SimpleQuery")
    @JacksonXmlProperty(localName = "SimpleQuery")
    private String simpleQuery;

    @JsonProperty("SmartClusterIds")
    @JacksonXmlElementWrapper(localName = "SmartClusterIds")
    @JacksonXmlProperty(localName = "SmartClusterId")
    private List<String> smartClusterIds;

    public MetaQueryDoBody() {}

    private MetaQueryDoBody(Builder builder) {
        this.query = builder.query;
        this.sort = builder.sort;
        this.order = builder.order;
        this.aggregations = builder.aggregations;
        this.maxResults = builder.maxResults;
        this.nextToken = builder.nextToken;
        this.withFields = builder.withFields;
        this.withoutTotalHits = builder.withoutTotalHits;
        this.sourceURI = builder.sourceURI;
        this.mediaTypes = builder.mediaTypes;
        this.simpleQuery = builder.simpleQuery;
        this.smartClusterIds = builder.smartClusterIds;
    }

    public String query() { return this.query; }
    public String sort() { return this.sort; }
    public String order() { return this.order; }
    public List<Aggregation> aggregations() { return this.aggregations; }
    public Integer maxResults() { return this.maxResults; }
    public String nextToken() { return this.nextToken; }
    public List<String> withFields() { return this.withFields; }
    public String withoutTotalHits() { return this.withoutTotalHits; }
    public String sourceURI() { return this.sourceURI; }
    public List<String> mediaTypes() { return this.mediaTypes; }
    public String simpleQuery() { return this.simpleQuery; }
    public List<String> smartClusterIds() { return this.smartClusterIds; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private String query;
        private String sort;
        private String order;
        private List<Aggregation> aggregations;
        private Integer maxResults;
        private String nextToken;
        private List<String> withFields;
        private String withoutTotalHits;
        private String sourceURI;
        private List<String> mediaTypes;
        private String simpleQuery;
        private List<String> smartClusterIds;

        public Builder query(String value) { this.query = value; return this; }
        public Builder sort(String value) { this.sort = value; return this; }
        public Builder order(String value) { this.order = value; return this; }
        public Builder aggregations(List<Aggregation> value) { this.aggregations = value; return this; }
        public Builder maxResults(Integer value) { this.maxResults = value; return this; }
        public Builder nextToken(String value) { this.nextToken = value; return this; }
        public Builder withFields(List<String> value) { this.withFields = value; return this; }
        public Builder withoutTotalHits(String value) { this.withoutTotalHits = value; return this; }
        public Builder sourceURI(String value) { this.sourceURI = value; return this; }
        public Builder mediaTypes(List<String> value) { this.mediaTypes = value; return this; }
        public Builder simpleQuery(String value) { this.simpleQuery = value; return this; }
        public Builder smartClusterIds(List<String> value) { this.smartClusterIds = value; return this; }

        private Builder() {}
        private Builder(MetaQueryDoBody from) {
            this.query = from.query;
            this.sort = from.sort;
            this.order = from.order;
            this.aggregations = from.aggregations;
            this.maxResults = from.maxResults;
            this.nextToken = from.nextToken;
            this.withFields = from.withFields;
            this.withoutTotalHits = from.withoutTotalHits;
            this.sourceURI = from.sourceURI;
            this.mediaTypes = from.mediaTypes;
            this.simpleQuery = from.simpleQuery;
            this.smartClusterIds = from.smartClusterIds;
        }

        public MetaQueryDoBody build() { return new MetaQueryDoBody(this); }
    }
}
