package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * XML response body for the GetDataPipelineConfiguration operation.
 */
@JacksonXmlRootElement(localName = "GetDataPipelineConfigurationResult")
public final class GetDataPipelineConfigurationResponseBody {
    @JacksonXmlProperty(localName = "DataPipelineConfiguration")
    private DataPipelineConfiguration dataPipelineConfiguration;

    public GetDataPipelineConfigurationResponseBody() {}

    public DataPipelineConfiguration dataPipelineConfiguration() {
        return this.dataPipelineConfiguration;
    }
}
