package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutSymlink operation.
 */
public final class PutSymlinkRequest extends RequestModel {
    private final String bucket;
    private final String key;

    private PutSymlinkRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.key = builder.key;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }

    /**
     * The full path of the object.
     */
    public String key() {
        return key;
    }

    /**
     * The target object to which the symbolic link points. The naming conventions for target objects are the same as those for objects.  - Similar to ObjectName, TargetObjectName must be URL-encoded.   - The target object to which a symbolic link points cannot be a symbolic link.
     */
    public String symlinkTarget() {
        String value = headers.get("x-oss-symlink-target");
        return value;
    }

    /**
     * The access control list (ACL) of the object. Default value: default. Valid values:- default: The ACL of the object is the same as that of the bucket in which the object is stored. - private: The ACL of the object is private. Only the owner of the object and authorized users can read and write this object. - public-read: The ACL of the object is public-read. Only the owner of the object and authorized users can read and write this object. Other users can only read the object. Exercise caution when you set the object ACL to this value. - public-read-write: The ACL of the object is public-read-write. All users can read and write this object. Exercise caution when you set the object ACL to this value. For more information about the ACL, see **[ACL](~~100676~~)**.
     */
    public String objectAcl() {
        String value = headers.get("x-oss-object-acl");
        return value;
    }

    /**
     * The storage class of the bucket. Default value: Standard.  Valid values:- Standard- IA- Archive- ColdArchive
     */
    public String storageClass() {
        String value = headers.get("x-oss-storage-class");
        return value;
    }

    /**
     * Specifies whether the PutSymlink operation overwrites the object that has the same name as that of the symbolic link you want to create.   - If the value of **x-oss-forbid-overwrite** is not specified or set to **false**, existing objects can be overwritten by objects that have the same names.   - If the value of **x-oss-forbid-overwrite** is set to **true**, existing objects cannot be overwritten by objects that have the same names. If you specify the **x-oss-forbid-overwrite** request header, the queries per second (QPS) performance of OSS is degraded. If you want to use the **x-oss-forbid-overwrite** request header to perform a large number of operations (QPS greater than 1,000), contact technical support.  The **x-oss-forbid-overwrite** request header is invalid when versioning is enabled or suspended for the destination bucket. In this case, the object with the same name can be overwritten.
     */
    public String forbidOverwrite() {
        String value = headers.get("x-oss-forbid-overwrite");
        return value;
    }

    /**
     * To indicate that the requester is aware that the request and data download will incur costs.
     */
    public String requestPayer() {
        String value = headers.get("x-oss-request-payer");
        return value;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private String key;

        private Builder() {
            super();
        }

        private Builder(PutSymlinkRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.key = request.key;
        }

        /**
         * The name of the bucket.
         */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        /**
         * The full path of the object.
         */
        public Builder key(String value) {
            requireNonNull(value);
            this.key = value;
            return this;
        }

        /**
         * The target object to which the symbolic link points. The naming conventions for target objects are the same as those for objects.  - Similar to ObjectName, TargetObjectName must be URL-encoded.   - The target object to which a symbolic link points cannot be a symbolic link.
         */
        public Builder symlinkTarget(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-symlink-target", value);
            return this;
        }

        /**
         * The access control list (ACL) of the object. Default value: default. Valid values:- default: The ACL of the object is the same as that of the bucket in which the object is stored. - private: The ACL of the object is private. Only the owner of the object and authorized users can read and write this object. - public-read: The ACL of the object is public-read. Only the owner of the object and authorized users can read and write this object. Other users can only read the object. Exercise caution when you set the object ACL to this value. - public-read-write: The ACL of the object is public-read-write. All users can read and write this object. Exercise caution when you set the object ACL to this value. For more information about the ACL, see **[ACL](~~100676~~)**.
         */
        public Builder objectAcl(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-object-acl", value);
            return this;
        }

        /**
         * The storage class of the bucket. Default value: Standard.  Valid values:- Standard- IA- Archive- ColdArchive
         */
        public Builder storageClass(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-storage-class", value);
            return this;
        }

        /**
         * Specifies whether the PutSymlink operation overwrites the object that has the same name as that of the symbolic link you want to create.   - If the value of **x-oss-forbid-overwrite** is not specified or set to **false**, existing objects can be overwritten by objects that have the same names.   - If the value of **x-oss-forbid-overwrite** is set to **true**, existing objects cannot be overwritten by objects that have the same names. If you specify the **x-oss-forbid-overwrite** request header, the queries per second (QPS) performance of OSS is degraded. If you want to use the **x-oss-forbid-overwrite** request header to perform a large number of operations (QPS greater than 1,000), contact technical support.  The **x-oss-forbid-overwrite** request header is invalid when versioning is enabled or suspended for the destination bucket. In this case, the object with the same name can be overwritten.
         */
        public Builder forbidOverwrite(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-forbid-overwrite", value);
            return this;
        }

        /**
         * To indicate that the requester is aware that the request and data download will incur costs.
         */
        public Builder requestPayer(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-request-payer", value);
            return this;
        }

        public PutSymlinkRequest build() {
            return new PutSymlinkRequest(this);
        }
    }

}
