package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutBucketRtc operation.
 */
public final class PutBucketRtcRequest extends RequestModel { 
    private final String bucket;
    private final RtcConfiguration rtcConfiguration;

    private PutBucketRtcRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.rtcConfiguration = builder.rtcConfiguration;
    }
    
    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }
    
    /**
     * The container of the request body.
     */
    public RtcConfiguration rtcConfiguration() {
        return rtcConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private RtcConfiguration rtcConfiguration;
    
        /**
        * The name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        /**
        * The container of the request body.
        */
        public Builder rtcConfiguration(RtcConfiguration value) {
            requireNonNull(value);
            this.rtcConfiguration = value;
            return this;        
        }
        
        public PutBucketRtcRequest build() {
            return new PutBucketRtcRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketRtcRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.rtcConfiguration = request.rtcConfiguration;
        }        
    }

}
