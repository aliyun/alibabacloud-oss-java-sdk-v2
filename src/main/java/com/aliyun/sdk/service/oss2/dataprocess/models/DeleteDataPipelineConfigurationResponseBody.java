package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * XML response body for the DeleteDataPipelineConfiguration operation.
 */
@JacksonXmlRootElement(localName = "DeleteDataPipelineConfigurationResult")
public final class DeleteDataPipelineConfigurationResponseBody {
    @JacksonXmlProperty(localName = "RequestId")
    private String requestId;

    public DeleteDataPipelineConfigurationResponseBody() {}

    public String requestId() {
        return this.requestId;
    }
}
