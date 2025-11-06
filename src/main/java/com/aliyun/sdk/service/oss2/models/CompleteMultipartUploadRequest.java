package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the CompleteMultipartUpload operation.
 */
public final class CompleteMultipartUploadRequest extends RequestModel {
    private final String bucket;
    private final String key;
    private final CompleteMultipartUpload completeMultipartUpload;

    private CompleteMultipartUploadRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.key = builder.key;
        this.completeMultipartUpload = builder.completeMultipartUpload;
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
     * Specifieswhethertheobjectwith the sameobjectname is overwritten when you call the CompleteMultipartUpload operation.- If the value of x-oss-forbid-overwrite is not specified or set to false, the existing object can be overwritten by the object that has the same name. - If the value of x-oss-forbid-overwrite is set to true, the existing object cannot be overwritten by the object that has the same name. - The x-oss-forbid-overwrite request header is invalid if versioning is enabled or suspended for the bucket. In this case, the existing object can be overwritten by the object that has the same name when you call the CompleteMultipartUpload operation. - If you specify the x-oss-forbid-overwrite request header, the queries per second (QPS) performance of OSS may be degraded. If you want to configure the x-oss-forbid-overwrite header in a large number of requests (QPS  1,000), submit a ticket.
     */
    public String forbidOverwrite() {
        String value = headers.get("x-oss-forbid-overwrite");
        return value;
    }

    /**
     * Specifies whether to list all parts that are uploaded by using the current upload ID.Valid value: yes.- If x-oss-complete-all is set to yes in the request, OSS lists all parts that are uploaded by using the current upload ID, sorts the parts by part number, and then performs the CompleteMultipartUpload operation. When OSS performs the CompleteMultipartUpload operation, OSS cannot detect the parts that are not uploaded or currently being uploaded. Before you call the CompleteMultipartUpload operation, make sure that all parts are uploaded.- If x-oss-complete-all is specified in the request, the request body cannot be specified. Otherwise, an error occurs.- If x-oss-complete-all is specified in the request, the format of the response remains unchanged.
     */
    public String completeAll() {
        String value = headers.get("x-oss-complete-all");
        return value;
    }

    /**
     * The identifier of the multipart upload task.
     */
    public String uploadId() {
        String value = parameters.get("uploadId");
        return value;
    }

    /**
     * The encodingtype of the object name in the response. Only URL encoding is supported.The object name can contain characters that are encoded in UTF-8. However, the XML 1.0 standard cannot be used to parse control characters, such as characters with an ASCII value from 0 to 10. You can configure this parameter to encode the object name in the response.
     */
    public String encodingType() {
        String value = parameters.get("encoding-type");
        return value;
    }

    /**
     * The access control list (ACL) of the object.
     * 
     * @deprecated Use {@link #objectAcl()} instead.
     */
    public String acl() {
        String value = headers.get("x-oss-object-acl");
        return value;
    }

    /**
     * The access control list (ACL) of the object.
     */
    public String objectAcl() {
        String value = headers.get("x-oss-object-acl");
        return value;
    }

    /**
     * A callback parameter is a Base64-encoded string that contains multiple fields in the JSON format.
     */
    public String callback() {
        String value = headers.get("x-oss-callback");
        return value;
    }

    /**
     * Configure custom parameters by using the callback-var parameter.
     */
    public String callbackVar() {
        String value = headers.get("x-oss-callback-var");
        return value;
    }

    /**
     * To indicate that the requester is aware that the request and data download will incur costs.
     */
    public String requestPayer() {
        String value = headers.get("x-oss-request-payer");
        return value;
    }

    /**
     * The request body schema.
     */
    public CompleteMultipartUpload completeMultipartUpload() {
        return completeMultipartUpload;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private String key;
        private CompleteMultipartUpload completeMultipartUpload;

        private Builder() {
            super();
        }

        private Builder(CompleteMultipartUploadRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.key = request.key;
            this.completeMultipartUpload = request.completeMultipartUpload;
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
         * Specifieswhethertheobjectwith the sameobjectname is overwritten when you call the CompleteMultipartUpload operation.- If the value of x-oss-forbid-overwrite is not specified or set to false, the existing object can be overwritten by the object that has the same name. - If the value of x-oss-forbid-overwrite is set to true, the existing object cannot be overwritten by the object that has the same name. - The x-oss-forbid-overwrite request header is invalid if versioning is enabled or suspended for the bucket. In this case, the existing object can be overwritten by the object that has the same name when you call the CompleteMultipartUpload operation. - If you specify the x-oss-forbid-overwrite request header, the queries per second (QPS) performance of OSS may be degraded. If you want to configure the x-oss-forbid-overwrite header in a large number of requests (QPS  1,000), submit a ticket.
         */
        public Builder forbidOverwrite(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-forbid-overwrite", value);
            return this;
        }

        /**
         * Specifies whether to list all parts that are uploaded by using the current upload ID.Valid value: yes.- If x-oss-complete-all is set to yes in the request, OSS lists all parts that are uploaded by using the current upload ID, sorts the parts by part number, and then performs the CompleteMultipartUpload operation. When OSS performs the CompleteMultipartUpload operation, OSS cannot detect the parts that are not uploaded or currently being uploaded. Before you call the CompleteMultipartUpload operation, make sure that all parts are uploaded.- If x-oss-complete-all is specified in the request, the request body cannot be specified. Otherwise, an error occurs.- If x-oss-complete-all is specified in the request, the format of the response remains unchanged.
         */
        public Builder completeAll(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-complete-all", value);
            return this;
        }

        /**
         * The identifier of the multipart upload task.
         */
        public Builder uploadId(String value) {
            requireNonNull(value);
            this.parameters.put("uploadId", value);
            return this;
        }

        /**
         * The encodingtype of the object name in the response. Only URL encoding is supported.The object name can contain characters that are encoded in UTF-8. However, the XML 1.0 standard cannot be used to parse control characters, such as characters with an ASCII value from 0 to 10. You can configure this parameter to encode the object name in the response.
         */
        public Builder encodingType(String value) {
            requireNonNull(value);
            this.parameters.put("encoding-type", value);
            return this;
        }

        /**
         * The request body schema.
         */
        public Builder completeMultipartUpload(CompleteMultipartUpload value) {
            requireNonNull(value);
            this.completeMultipartUpload = value;
            return this;
        }

        /**
         * The access control list (ACL) of the object.
         * 
         * @deprecated Use {@link #objectAcl(String)} instead.
         */
        public Builder acl(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-object-acl", value);
            return this;
        }

        /**
         * The access control list (ACL) of the object.
         */
        public Builder objectAcl(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-object-acl", value);
            return this;
        }

        /**
         * A callback parameter is a Base64-encoded string that contains multiple fields in the JSON format.
         */
        public Builder callback(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-callback", value);
            return this;
        }

        /**
         * Configure custom parameters by using the callback-var parameter.
         */
        public Builder callbackVar(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-callback-var", value);
            return this;
        }

        /**
         * Specifies whether the object that is uploaded by calling the PutObject operation overwrites the existing object that has the same name.  When versioning is enabled or suspended for the bucket to which you want to upload the object, the **x-oss-forbid-overwrite** header does not take effect. In this case, the object that is uploaded by calling the PutObject operation overwrites the existing object that has the same name.   - If you do not specify the **x-oss-forbid-overwrite** header or set the **x-oss-forbid-overwrite** header to **false**, the object that is uploaded by calling the PutObject operation overwrites the existing object that has the same name.   - If the value of **x-oss-forbid-overwrite** is set to **true**, existing objects cannot be overwritten by objects that have the same names. If you specify the **x-oss-forbid-overwrite** request header, the queries per second (QPS) performance of OSS is degraded. If you want to use the **x-oss-forbid-overwrite** request header to perform a large number of operations (QPS greater than 1,000), contact technical support. Default value: **false**.
         */
        public Builder forbidOverwrite(Boolean value) {
            requireNonNull(value);
            this.headers.put("x-oss-forbid-overwrite", value.toString());
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

        public CompleteMultipartUploadRequest build() {
            return new CompleteMultipartUploadRequest(this);
        }
    }

}
