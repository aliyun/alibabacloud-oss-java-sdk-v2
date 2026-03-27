package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * XML response body for the GetDataset operation.
 */
@JacksonXmlRootElement(localName = "GetDatasetResponse")
public final class GetDatasetResponseBody {
    @JacksonXmlProperty(localName = "Dataset")
    private Dataset dataset;

    public GetDatasetResponseBody() {}

    public Dataset dataset() {
        return this.dataset;
    }
}
