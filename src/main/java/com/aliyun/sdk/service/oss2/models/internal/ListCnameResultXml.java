package com.aliyun.sdk.service.oss2.models.internal;

import com.aliyun.sdk.service.oss2.models.CnameInfo;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;

/**
 * The container that stores the results of the ListCname request.
 */
@JacksonXmlRootElement(localName = "ListCnameResult")
public final class ListCnameResultXml {

    @JacksonXmlProperty(localName = "Bucket")
    public String bucket;

    @JacksonXmlProperty(localName = "Owner")
    public String owner;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Cname")
    public List<CnameInfo> cnames;

    public ListCnameResultXml() {}

}