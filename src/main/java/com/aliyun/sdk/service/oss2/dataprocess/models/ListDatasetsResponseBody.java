package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * XML response body for the ListDatasets operation.
 */
@JacksonXmlRootElement(localName = "ListDatasetsResponse")
public final class ListDatasetsResponseBody {
    @JacksonXmlProperty(localName = "NextToken")
    private String nextToken;

    @JacksonXmlElementWrapper(localName = "Datasets")
    @JacksonXmlProperty(localName = "Dataset")
    private List<Dataset> datasets;

    public ListDatasetsResponseBody() {}

    public String nextToken() {
        return this.nextToken;
    }

    public List<Dataset> datasets() {
        return this.datasets;
    }
}
