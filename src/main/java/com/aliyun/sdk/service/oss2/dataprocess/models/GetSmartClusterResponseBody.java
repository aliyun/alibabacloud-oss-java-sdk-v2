package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "GetSmartClusterResponse")
public final class GetSmartClusterResponseBody {

    @JacksonXmlProperty(localName = "SmartCluster")
    private SmartClusterInfo smartCluster;

    public GetSmartClusterResponseBody() {}

    public SmartClusterInfo smartCluster() { return this.smartCluster; }
}
