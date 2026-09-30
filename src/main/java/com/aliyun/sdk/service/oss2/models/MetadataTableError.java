package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * The error information of a metadata table configuration.
 */
public final class MetadataTableError {
    @JacksonXmlProperty(localName = "ErrorCode")
    private String errorCode;

    @JacksonXmlProperty(localName = "ErrorMessage")
    private String errorMessage;

    public MetadataTableError() {
    }

    public String errorCode() {
        return errorCode;
    }

    public String errorMessage() {
        return errorMessage;
    }
}
