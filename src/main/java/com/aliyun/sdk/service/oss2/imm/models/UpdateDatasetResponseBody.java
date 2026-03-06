package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * XML response body for the UpdateDataset operation.
 */
@JacksonXmlRootElement(localName = "UpdateDatasetResult")
public final class UpdateDatasetResponseBody {
    @JacksonXmlProperty(localName = "Dataset")
    private Dataset dataset;

    public UpdateDatasetResponseBody() {}

    public Dataset dataset() {
        return this.dataset;
    }
}
