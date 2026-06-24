package com.aliyun.sdk.service.oss2.models.internal;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * The wrapper for the CopyObject response XML parsing.
 */
@JacksonXmlRootElement(localName = "CopyObjectResult")
public final class CopyObjectResultXml {
    @JacksonXmlProperty(localName = "ETag")
    public String eTag;

    @JacksonXmlProperty(localName = "LastModified")
    public String lastModified;

    public CopyObjectResultXml() {
    }
}
