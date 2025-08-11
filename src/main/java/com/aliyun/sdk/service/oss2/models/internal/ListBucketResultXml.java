package com.aliyun.sdk.service.oss2.models.internal;

import com.aliyun.sdk.service.oss2.models.CommonPrefix;
import com.aliyun.sdk.service.oss2.models.ObjectSummary;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * The container that stores the metadata of the returned objects.
 */
@JacksonXmlRootElement(localName = "ListBucketResult")
public final class ListBucketResultXml {
    @JacksonXmlProperty(localName = "Name")
    public String name;

    @JacksonXmlProperty(localName = "Prefix")
    public String prefix;

    @JacksonXmlProperty(localName = "MaxKeys")
    public Integer maxKeys;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Contents")
    public List<ObjectSummary> contents;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "CommonPrefixes")
    public List<CommonPrefix> commonPrefixes;


    @JacksonXmlProperty(localName = "Delimiter")
    public String delimiter;

    @JacksonXmlProperty(localName = "IsTruncated")
    public Boolean isTruncated;

    @JacksonXmlProperty(localName = "EncodingType")
    public String encodingType;

    @JacksonXmlProperty(localName = "Marker")
    public String marker;

    @JacksonXmlProperty(localName = "NextMarker")
    public String nextMarker;

    public ListBucketResultXml() {
    }

}
