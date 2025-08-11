package com.aliyun.sdk.service.oss2.models.internal;

import com.aliyun.sdk.service.oss2.models.CommonPrefix;
import com.aliyun.sdk.service.oss2.models.ObjectSummary;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The container that stores the metadata of the returned objects.
 */
@JacksonXmlRootElement(localName = "ListBucketResult")
public final class ListBucketV2ResultXml {
    @JacksonXmlProperty(localName = "Name")
    public String name;

    @JacksonXmlProperty(localName = "Prefix")
    public String prefix;

    @JacksonXmlProperty(localName = "MaxKeys")
    public Integer maxKeys;

    @JacksonXmlProperty(localName = "KeyCount")
    public Integer keyCount;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Contents")
    public List<ObjectSummary> contents;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "CommonPrefixes")
    public List<CommonPrefix> commonPrefixes;

    @JacksonXmlProperty(localName = "StartAfter")
    public String startAfter;

    @JacksonXmlProperty(localName = "Delimiter")
    public String delimiter;

    @JacksonXmlProperty(localName = "IsTruncated")
    public Boolean isTruncated;

    @JacksonXmlProperty(localName = "EncodingType")
    public String encodingType;

    @JacksonXmlProperty(localName = "ContinuationToken")
    public String continuationToken;

    @JacksonXmlProperty(localName = "NextContinuationToken")
    public String nextContinuationToken;

    public ListBucketV2ResultXml() {
    }

}
