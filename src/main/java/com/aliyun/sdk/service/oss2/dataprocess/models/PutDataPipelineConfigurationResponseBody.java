package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * XML response body for the PutDataPipelineConfiguration operation.
 */
@JacksonXmlRootElement(localName = "PutDataPipelineConfigurationResult")
public final class PutDataPipelineConfigurationResponseBody {
    @JacksonXmlProperty(localName = "RequestId")
    private String requestId;

    public PutDataPipelineConfigurationResponseBody() {}

    public String requestId() {
        return this.requestId;
    }
}
