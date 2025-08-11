package com.aliyun.sdk.service.oss2.models.internal;

import com.aliyun.sdk.service.oss2.models.CommonPrefix;
import com.aliyun.sdk.service.oss2.models.DeleteMarkerEntry;
import com.aliyun.sdk.service.oss2.models.ObjectVersion;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.ArrayList;
import java.util.List;


/**
 * The container that stores the results of the ListObjectVersions (GetBucketVersions) request.
 */
 @JacksonXmlRootElement(localName = "ListVersionsResult")
public final class ListVersionsResultXml {

    public List<ObjectVersion> versions;

    public List<DeleteMarkerEntry> deleteMarkers;

    @JacksonXmlProperty(localName = "Prefix")
    public String prefix;
 
    @JacksonXmlProperty(localName = "IsTruncated")
    public Boolean isTruncated;
 
    @JacksonXmlProperty(localName = "NextKeyMarker")
    public String nextKeyMarker;
 
    @JacksonXmlProperty(localName = "NextVersionIdMarker")
    public String nextVersionIdMarker;
 
    @JacksonXmlProperty(localName = "Delimiter")
    public String delimiter;
 
    @JacksonXmlProperty(localName = "EncodingType")
    public String encodingType;

    public List<CommonPrefix> commonPrefixes;
 
    @JacksonXmlProperty(localName = "Name")
    public String name;
 
    @JacksonXmlProperty(localName = "KeyMarker")
    public String keyMarker;
 
    @JacksonXmlProperty(localName = "VersionIdMarker")
    public String versionIdMarker;
 
    @JacksonXmlProperty(localName = "MaxKeys")
    public Long maxKeys;

    public ListVersionsResultXml() {}


    /**
     * Add a version to the versions list.
     *
     * @param version the ObjectVersion to add
     * @return this ListVersionsResultXml instance
     */
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Version")
    public ListVersionsResultXml addVersion(ObjectVersion version) {
        if (this.versions == null) {
            this.versions = new ArrayList<>();
        }
        this.versions.add(version);
        return this;
    }

    /**
     * Add a delete marker to the deleteMarkers list.
     *
     * @param deleteMarker the DeleteMarkerEntry to add
     * @return this ListVersionsResultXml instance
     */
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "DeleteMarker")
    public ListVersionsResultXml addDeleteMarker(DeleteMarkerEntry deleteMarker) {
        if (this.deleteMarkers == null) {
            this.deleteMarkers = new ArrayList<>();
        }
        this.deleteMarkers.add(deleteMarker);
        return this;
    }

    /**
     * Add a common prefix to the commonPrefixes list.
     *
     * @param commonPrefix the CommonPrefix to add
     * @return this ListVersionsResultXml instance
     */
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "CommonPrefixes")
    public ListVersionsResultXml addCommonPrefix(CommonPrefix commonPrefix) {
        if (this.commonPrefixes == null) {
            this.commonPrefixes = new ArrayList<>();
        }
        this.commonPrefixes.add(commonPrefix);
        return this;
    }
}
