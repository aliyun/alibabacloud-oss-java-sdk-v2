package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.models.internal.ListCnameResultXml;

import java.util.List;
import java.util.Optional;

/**
 * The result for the ListCname operation.
 */
public final class ListCnameResult extends ResultModel {
    public final ListCnameResultXml delegate;

    /**
     * The name of the bucket owner.
     */
    public String owner() {
        return delegate.owner;
    } 
    
    /**
     * The container that is used to store the information about all CNAME records.
     */
    public List<CnameInfo> cnames() {
        return CastUtils.ensureList(delegate.cnames);
    }
    
    /**
     * The name of the bucket to which the CNAME records you want to query are mapped.
     */
    public String bucket() {
        return delegate.bucket;
    }

    ListCnameResult(Builder builder) {
        super(builder);
        this.delegate = (ListCnameResultXml) Optional
                .ofNullable(innerBody)
                .orElse(new ListCnameResultXml());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public ListCnameResult build() {
            return new ListCnameResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(ListCnameResult result) {
            super(result);
        }
    }
}