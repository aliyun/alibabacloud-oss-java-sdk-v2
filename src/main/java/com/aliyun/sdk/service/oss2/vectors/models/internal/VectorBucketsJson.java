package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The container that stores the vector buckets information.
 */
public final class VectorBucketsJson {
    @JsonProperty("Prefix")
    public String prefix;

    @JsonProperty("Marker")
    public String marker;

    @JsonProperty("MaxKeys")
    public Integer maxKeys;

    @JsonProperty("IsTruncated")
    public Boolean isTruncated;

    @JsonProperty("NextMarker")
    public String nextMarker;

    @JsonProperty("Buckets")
    public List<BucketPropertiesJson> buckets;

    public VectorBucketsJson() {
    }

}
