package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "MetaQuery")
public final class DoMetaQueryResponseBody {

    @JacksonXmlProperty(localName = "NextToken")
    private String nextToken;

    @JacksonXmlProperty(localName = "TotalHits")
    private Long totalHits;

    @JacksonXmlElementWrapper(localName = "Files")
    @JacksonXmlProperty(localName = "File")
    private List<File> files;

    @JacksonXmlElementWrapper(localName = "Aggregations")
    @JacksonXmlProperty(localName = "Aggregation")
    private List<AggregationInfo> aggregations;

    public DoMetaQueryResponseBody() {}

    public String nextToken() { return this.nextToken; }
    public Long totalHits() { return this.totalHits; }
    public List<File> files() { return this.files; }
    public List<AggregationInfo> aggregations() { return this.aggregations; }
}
