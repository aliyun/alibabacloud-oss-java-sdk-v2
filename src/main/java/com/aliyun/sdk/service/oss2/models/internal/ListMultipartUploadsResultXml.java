package com.aliyun.sdk.service.oss2.models.internal;

import com.aliyun.sdk.service.oss2.models.Upload;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * The container that stores list multipart uploads result for XML serialization.
 */
@JacksonXmlRootElement(localName = "ListMultipartUploadsResult")
public final class ListMultipartUploadsResultXml {
    @JacksonXmlProperty(localName = "Bucket")
    public String bucket;

    @JacksonXmlProperty(localName = "KeyMarker")
    public String keyMarker;

    @JacksonXmlProperty(localName = "UploadIdMarker")
    public String uploadIdMarker;

    @JacksonXmlProperty(localName = "NextKeyMarker")
    public String nextKeyMarker;

    @JacksonXmlProperty(localName = "NextUploadIdMarker")
    public String nextUploadIdMarker;

    @JacksonXmlProperty(localName = "Delimiter")
    public String delimiter;

    @JacksonXmlProperty(localName = "Prefix")
    public String prefix;

    @JacksonXmlProperty(localName = "MaxUploads")
    public Long maxUploads;

    @JacksonXmlProperty(localName = "IsTruncated")
    public Boolean isTruncated;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Upload")
    public List<Upload> uploads;

    @JacksonXmlProperty(localName = "EncodingType")
    public String encodingType;


    public ListMultipartUploadsResultXml() {
    }

}
