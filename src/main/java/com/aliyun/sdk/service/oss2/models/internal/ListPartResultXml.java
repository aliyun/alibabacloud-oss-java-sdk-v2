package com.aliyun.sdk.service.oss2.models.internal;

import com.aliyun.sdk.service.oss2.models.Part;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * The container that stores the response of the ListParts request.
 */
@JacksonXmlRootElement(localName = "ListPartResult")
public final class ListPartResultXml {
    @JacksonXmlProperty(localName = "Key")
    public String key;

    @JacksonXmlProperty(localName = "UploadId")
    public String uploadId;

    @JacksonXmlProperty(localName = "PartNumberMarker")
    public Long partNumberMarker;

    @JacksonXmlProperty(localName = "NextPartNumberMarker")
    public Long nextPartNumberMarker;

    @JacksonXmlProperty(localName = "MaxParts")
    public Long maxParts;

    @JacksonXmlProperty(localName = "IsTruncated")
    public Boolean isTruncated;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Part")
    public List<Part> parts;

    @JacksonXmlProperty(localName = "Bucket")
    public String bucket;

    @JacksonXmlProperty(localName = "EncodingType")
    public String encodingType;


    public ListPartResultXml() {
    }


}
