package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetObjectAcl operation.
 */
public final class GetObjectAclResult extends ResultModel {

    GetObjectAclResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The container that stores the results of the GetObjectACL request.
     */
    public AccessControlPolicy accessControlPolicy() {
        return (AccessControlPolicy) innerBody;
    }

    /**
     * The version ID of the object for which you want to query metadata.
     */
    public String versionId() {
        String value = headers.get("versionId");
        return value;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(GetObjectAclResult result) {
            super(result);
        }

        public GetObjectAclResult build() {
            return new GetObjectAclResult(this);
        }
    }
}
