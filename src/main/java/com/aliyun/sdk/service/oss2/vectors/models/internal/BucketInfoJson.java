package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

/**
 * BucketInfo defines Bucket information.
 */

public final class BucketInfoJson {
    @JsonProperty("Name")
    public String name;

    @JsonProperty("Location")
    public String location;

    @JsonProperty("CreationDate")
    public Instant creationDate;

    @JsonProperty("ExtranetEndpoint")
    public String extranetEndpoint;

    @JsonProperty("IntranetEndpoint")
    public String intranetEndpoint;

    @JsonProperty("ResourceGroupId")
    public String resourceGroupId;

    public BucketInfoJson() {
    }
}
