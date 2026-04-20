package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * XML response body for the RestartDataPipeline operation.
 */
@JacksonXmlRootElement(localName = "RestartDataPipelineResult")
public final class RestartDataPipelineResponseBody {
    @JacksonXmlProperty(localName = "RequestId")
    private String requestId;

    public RestartDataPipelineResponseBody() {}

    public String requestId() {
        return this.requestId;
    }
}
