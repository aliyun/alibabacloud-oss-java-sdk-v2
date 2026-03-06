package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * XML response body for the CreateDataset operation.
 */
@JacksonXmlRootElement(localName = "CreateDatasetResult")
public final class CreateDatasetResponseBody {
    @JacksonXmlProperty(localName = "Dataset")
    private Dataset dataset;

    public CreateDatasetResponseBody() {}

    public Dataset dataset() {
        return this.dataset;
    }
}
