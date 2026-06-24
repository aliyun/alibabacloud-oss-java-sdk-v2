package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "UpdateSmartClusterResponse")
public final class UpdateSmartClusterResponseBody {

    @JacksonXmlProperty(localName = "ObjectId")
    private String objectId;

    public UpdateSmartClusterResponseBody() {}

    public String objectId() { return this.objectId; }
}
