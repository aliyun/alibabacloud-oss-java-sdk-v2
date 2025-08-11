package com.aliyun.sdk.service.oss2.models.internal;

import com.aliyun.sdk.service.oss2.models.DeletedInfo;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * The wrapper for the DeleteMultipleObjects response XML parsing.
 */
@JacksonXmlRootElement(localName = "DeleteResult")
public final class DeleteResultXml {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Deleted")
    public List<DeletedInfo> deleted;

    @JacksonXmlProperty(localName = "EncodingType")
    public String encodingType;

    public DeleteResultXml() {
    }
}