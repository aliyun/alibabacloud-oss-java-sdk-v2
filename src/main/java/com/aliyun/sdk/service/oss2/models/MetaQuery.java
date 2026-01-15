package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the meta query parameters for querying inventory data.
 */
@JacksonXmlRootElement(localName = "MetaQuery")
public final class MetaQuery {

    @JacksonXmlProperty(localName = "NextToken")
    private String nextToken;

    @JacksonXmlProperty(localName = "MaxResults")
    private Integer maxResults;

    @JacksonXmlProperty(localName = "Query")
    private String query;

    @JacksonXmlProperty(localName = "Sort")
    private String sort;

    @JacksonXmlProperty(localName = "Order")
    private SortOrder order;

    @JacksonXmlProperty(localName = "Aggregations")
    private MetaQueryAggregations aggregations;

    @JacksonXmlElementWrapper(localName = "MediaTypes")
    @JacksonXmlProperty(localName = "MediaType")
    private List<String> mediaTypes;

    @JacksonXmlProperty(localName = "SimpleQuery")
    private String simpleQuery;

    public MetaQuery() {}

    private MetaQuery(Builder builder) {
        this.nextToken = builder.nextToken;
        this.maxResults = builder.maxResults;
        this.query = builder.query;
        this.sort = builder.sort;
        this.order = builder.order;
       this.aggregations = builder.aggregations; 
        this.mediaTypes = builder.mediaTypes;
        this.simpleQuery = builder.simpleQuery;
    }

    /**
     * The token used for pagination.
     */
    public String nextToken() {
        return nextToken;
    }

    /**
     * The maximum number of results to return per page.
     */
    public Integer maxResults() {
        return maxResults;
    }

    /**
     * The query string for filtering results.
     */
    public String query() {
        return query;
    }

    /**
     * The field to sort by.
     */
    public String sort() {
        return sort;
    }

    /**
     * The order of sorting (asc or desc).
     */
    public SortOrder order() {
        return order;
    }

    /**
     * The aggregation operations container.
     */
    public MetaQueryAggregations aggregations() {
        return aggregations;
    }

    /**
     * The list of media types to filter by.
     */
    public List<String> mediaTypes() {
        return mediaTypes;
    }

    /**
     * A simplified query string.
     */
    public String simpleQuery() {
        return simpleQuery;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }


    /**
     * Supported operations in query and aggregation.
     */
    public enum Operation {
        eq, ne, gt, ge, lt, le, like, in, sum, avg, max, min
    }

    /**
     * Sorting order: asc or desc.
     */
    public enum SortOrder {
        asc, desc
    }

    public static class Builder {
        private String nextToken;
        private Integer maxResults;
        private String query;
        private String sort;
        private SortOrder order;
        private MetaQueryAggregations aggregations;
        private List<String> mediaTypes;
        private String simpleQuery;

        public Builder nextToken(String value) {
            requireNonNull(value);
            this.nextToken = value;
            return this;
        }

        public Builder maxResults(Integer value) {
            requireNonNull(value);
            this.maxResults = value;
            return this;
        }

        public Builder query(String value) {
            requireNonNull(value);
            this.query = value;
            return this;
        }

        public Builder sort(String value) {
            requireNonNull(value);
            this.sort = value;
            return this;
        }

        public Builder order(SortOrder value) {
            requireNonNull(value);
            this.order = value;
            return this;
        }

        public Builder aggregations(MetaQueryAggregations value) {
            requireNonNull(value);
            this.aggregations = value;
            return this;
        }

        public Builder mediaTypes(List<String> value) {
            requireNonNull(value);
            this.mediaTypes = value;
            return this;
        }

        public Builder simpleQuery(String value) {
            requireNonNull(value);
            this.simpleQuery = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(MetaQuery from) {
            this.nextToken = from.nextToken;
            this.maxResults = from.maxResults;
            this.query = from.query;
            this.sort = from.sort;
            this.order = from.order;
            this.aggregations = from.aggregations;
            this.mediaTypes = from.mediaTypes;
            this.simpleQuery = from.simpleQuery;
        }

        public MetaQuery build() {
            return new MetaQuery(this);
        }
    }
}
