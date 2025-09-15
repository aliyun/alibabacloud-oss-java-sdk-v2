package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

/**
 * Stores the metadata of the bucket.
 */
public final class BucketPropertiesJson {
    @JsonProperty("ExtranetEndpoint")
    public String extranetEndpoint;

    @JsonProperty("ResourceGroupId")
    public String resourceGroupId;

    @JsonProperty("Location")
    public String location;

    @JsonProperty("Name")
    public String name;

    @JsonProperty("CreationDate")
    public Instant creationDate;

    @JsonProperty("IntranetEndpoint")
    public String intranetEndpoint;

    @JsonProperty("Region")
    public String region;

    public BucketPropertiesJson() {
    }
}

