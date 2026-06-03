package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * XML response body for the UpdateDataset operation.
 *
 * <p>The UpdateDataset response is wrapped in a {@code <UpdateDatasetResponse>} root element
 * containing the full {@code <Dataset>} information (same field set as GetDataset, with
 * {@code TemplateId} / {@code BindCount} / cluster status fields / {@code WorkflowParametersString}
 * masked by the backend).</p>
 */
@JacksonXmlRootElement(localName = "UpdateDatasetResponse")
public final class UpdateDatasetResponseBody {
    @JacksonXmlProperty(localName = "Dataset")
    private Dataset dataset;

    public UpdateDatasetResponseBody() {}

    public Dataset dataset() {
        return this.dataset;
    }
}
