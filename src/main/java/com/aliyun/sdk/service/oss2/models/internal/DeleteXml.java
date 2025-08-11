package com.aliyun.sdk.service.oss2.models.internal;


import com.aliyun.sdk.service.oss2.models.DeleteObject;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * The XML representation of DeleteMultipleObjects request for serialization.
 */
@JacksonXmlRootElement(localName = "Delete")
public class DeleteXml {
    @JacksonXmlProperty(localName = "Quiet")
    public Boolean quiet;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Object")
    public List<DeleteObject> objects;

    public DeleteXml() {
    }

    public DeleteXml(Boolean quiet, List<DeleteObject> objects) {
        this.quiet = quiet;
        this.objects = objects;
    }
}