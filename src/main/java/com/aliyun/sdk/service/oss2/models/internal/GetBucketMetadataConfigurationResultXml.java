package com.aliyun.sdk.service.oss2.models.internal;

import com.aliyun.sdk.service.oss2.models.MetadataConfigurationResult;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * The XML body returned by GetBucketMetadataConfiguration.
 */
@JacksonXmlRootElement(localName = "GetBucketMetadataConfigurationResult")
public final class GetBucketMetadataConfigurationResultXml {
    @JacksonXmlProperty(localName = "MetadataConfigurationResult")
    public MetadataConfigurationResult metadataConfigurationResult;

    public GetBucketMetadataConfigurationResultXml() {
    }
}
