package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * XML response body for the CreateDataset operation.
 *
 * <p>The CreateDataset response is wrapped in a {@code <CreateDatasetResponse>} root element
 * containing the full {@code <Dataset>} information (same field set as GetDataset, with
 * {@code TemplateId} / {@code BindCount} / cluster status fields / {@code WorkflowParametersString}
 * masked by the backend).</p>
 */
@JacksonXmlRootElement(localName = "CreateDatasetResponse")
public final class CreateDatasetResponseBody {
    @JacksonXmlProperty(localName = "Dataset")
    private Dataset dataset;

    public CreateDatasetResponseBody() {}

    public Dataset dataset() {
        return this.dataset;
    }
}
