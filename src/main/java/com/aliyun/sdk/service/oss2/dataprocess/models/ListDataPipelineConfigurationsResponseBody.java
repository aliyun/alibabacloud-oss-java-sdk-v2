package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * XML response body for the ListDataPipelineConfigurations operation.
 */
@JacksonXmlRootElement(localName = "ListDataPipelineConfigurationsResult")
public final class ListDataPipelineConfigurationsResponseBody {
    @JacksonXmlProperty(localName = "NextToken")
    private String nextToken;

    @JacksonXmlElementWrapper(localName = "DataPipelineConfigurations")
    @JacksonXmlProperty(localName = "DataPipelineConfiguration")
    private List<DataPipelineConfiguration> dataPipelineConfigurations;

    public ListDataPipelineConfigurationsResponseBody() {}

    public String nextToken() {
        return this.nextToken;
    }

    public List<DataPipelineConfiguration> dataPipelineConfigurations() {
        return this.dataPipelineConfigurations;
    }
}
