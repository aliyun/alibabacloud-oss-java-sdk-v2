package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "CreateSmartClusterResponse")
public final class CreateSmartClusterResponseBody {

    @JacksonXmlProperty(localName = "ObjectId")
    private String objectId;

    public CreateSmartClusterResponseBody() {}

    public String objectId() { return this.objectId; }
}
