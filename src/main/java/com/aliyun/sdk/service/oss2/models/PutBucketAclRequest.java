package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutBucketAcl operation.
 */
public final class PutBucketAclRequest extends RequestModel { 
    private final String bucket;

    private PutBucketAclRequest(Builder builder) {
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
     * The ACL that you want to configure or modify for the bucket. The x-oss-acl header is included in PutBucketAcl requests to configure or modify the ACL of the bucket. If this header is not included, the ACL configurations do not take effect.Valid values:*   public-read-write: All users can read and write objects in the bucket. Exercise caution when you set the value to public-read-write.*   public-read: Only the owner and authorized users of the bucket can read and write objects in the bucket. Other users can only read objects in the bucket. Exercise caution when you set the value to public-read.*   private: Only the owner and authorized users of this bucket can read and write objects in the bucket. Other users cannot access objects in the bucket.
     */
    public String acl() {
        String value = headers.get("x-oss-acl");
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
        * The ACL that you want to configure or modify for the bucket. The x-oss-acl header is included in PutBucketAcl requests to configure or modify the ACL of the bucket. If this header is not included, the ACL configurations do not take effect.Valid values:*   public-read-write: All users can read and write objects in the bucket. Exercise caution when you set the value to public-read-write.*   public-read: Only the owner and authorized users of the bucket can read and write objects in the bucket. Other users can only read objects in the bucket. Exercise caution when you set the value to public-read.*   private: Only the owner and authorized users of this bucket can read and write objects in the bucket. Other users cannot access objects in the bucket.
        */
        public Builder acl(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-acl", value);
            return this;        
        }

        /**
         * The ACL that you want to configure or modify for the bucket. The x-oss-acl header is included in PutBucketAcl requests to configure or modify the ACL of the bucket. If this header is not included, the ACL configurations do not take effect.Valid values:*   public-read-write: All users can read and write objects in the bucket. Exercise caution when you set the value to public-read-write.*   public-read: Only the owner and authorized users of the bucket can read and write objects in the bucket. Other users can only read objects in the bucket. Exercise caution when you set the value to public-read.*   private: Only the owner and authorized users of this bucket can read and write objects in the bucket. Other users cannot access objects in the bucket.
         */
        public Builder acl(ObjectACLType value) {
            requireNonNull(value);
            this.headers.put("x-oss-acl", value.toString());
            return this;
        }
    
        
        public PutBucketAclRequest build() {
            return new PutBucketAclRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketAclRequest request) {
            super(request);
            this.bucket = request.bucket;
        }        
    }

}
