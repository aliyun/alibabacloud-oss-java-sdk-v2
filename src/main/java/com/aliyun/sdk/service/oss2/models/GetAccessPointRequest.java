package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

import java.time.Instant;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * The request for the GetAccessPoint operation.
 */
public final class GetAccessPointRequest extends RequestModel { 
    private final String bucket;

    private GetAccessPointRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }
    
    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }
    
    /**
     * The name of the access point.
     */
    public String accessPointName() {
        String value = headers.get("x-oss-access-point-name");
        return value;
    }
    

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
    
        /**
        * The name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        /**
        * The name of the access point.
        */
        public Builder accessPointName(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-access-point-name", value);
            return this;        
        }
    
        
        public GetAccessPointRequest build() {
            return new GetAccessPointRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetAccessPointRequest request) {
            super(request);
            this.bucket = request.bucket;
        }        
    }

}
