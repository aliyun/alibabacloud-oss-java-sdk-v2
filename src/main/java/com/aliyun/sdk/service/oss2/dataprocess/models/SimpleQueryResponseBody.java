package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * XML response body for the SimpleQuery operation.
 */
@JacksonXmlRootElement(localName = "SimpleQueryResult")
public final class SimpleQueryResponseBody {
    @JacksonXmlProperty(localName = "NextToken")
    private String nextToken;

    @JacksonXmlElementWrapper(localName = "Files")
    @JacksonXmlProperty(localName = "File")
    private List<File> files;

    @JacksonXmlElementWrapper(localName = "Aggregations")
    @JacksonXmlProperty(localName = "Aggregation")
    private List<AggregationInfo> aggregations;

    @JacksonXmlProperty(localName = "TotalHits")
    private Long totalHits;

    public SimpleQueryResponseBody() {}

    public String nextToken() {
        return this.nextToken;
    }

    public List<File> files() {
        return this.files;
    }

    public List<AggregationInfo> aggregations() {
        return this.aggregations;
    }

    public Long totalHits() {
        return this.totalHits;
    }
}
