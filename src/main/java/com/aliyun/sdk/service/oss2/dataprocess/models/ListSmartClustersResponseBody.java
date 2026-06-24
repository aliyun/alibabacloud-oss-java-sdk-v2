package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "ListSmartClustersResponse")
public final class ListSmartClustersResponseBody {

    @JacksonXmlProperty(localName = "NextToken")
    private String nextToken;

    @JacksonXmlElementWrapper(localName = "SmartClusters")
    @JacksonXmlProperty(localName = "SmartCluster")
    private List<SmartClusterInfo> smartClusters;

    public ListSmartClustersResponseBody() {}

    public String nextToken() { return this.nextToken; }
    public List<SmartClusterInfo> smartClusters() { return this.smartClusters; }
}
