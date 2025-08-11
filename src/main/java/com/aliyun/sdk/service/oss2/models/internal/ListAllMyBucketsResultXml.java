package com.aliyun.sdk.service.oss2.models.internal;

import com.aliyun.sdk.service.oss2.models.Owner;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * The container that stores the result of ListBuckets(GetService) request.
 */
@JacksonXmlRootElement(localName = "ListAllMyBucketsResult")
public final class ListAllMyBucketsResultXml {
    @JacksonXmlProperty(localName = "NextMarker")
    public String nextMarker;

    @JacksonXmlProperty(localName = "Buckets")
    public BucketsXml buckets;

    @JacksonXmlProperty(localName = "Owner")
    public Owner owner;

    @JacksonXmlProperty(localName = "Prefix")
    public String prefix;

    @JacksonXmlProperty(localName = "Marker")
    public String marker;

    @JacksonXmlProperty(localName = "MaxKeys")
    public Long maxKeys;

    @JacksonXmlProperty(localName = "IsTruncated")
    public Boolean isTruncated;

    public ListAllMyBucketsResultXml() {
    }
}
