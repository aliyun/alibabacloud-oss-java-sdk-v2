package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * XML response body for the SemanticQuery operation.
 */
@JacksonXmlRootElement(localName = "SemanticQueryResult")
public final class SemanticQueryResponseBody {
    @JacksonXmlElementWrapper(localName = "Files")
    @JacksonXmlProperty(localName = "File")
    private List<File> files;

    public SemanticQueryResponseBody() {}

    public List<File> files() {
        return this.files;
    }
}
