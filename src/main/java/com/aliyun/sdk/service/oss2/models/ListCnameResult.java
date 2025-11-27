package com.aliyun.sdk.service.oss2.models;

import java.util.List;

/**
 * The result for the ListCname operation.
 */
public final class ListCnameResult extends ResultModel { 

    /**
     * The name of the bucket owner.
     */
    public String owner() {
        ListCnameInfo info = (ListCnameInfo) innerBody;
        return info != null ? info.getOwner() : null;
    } 
    
    /**
     * The container that is used to store the information about all CNAME records.
     */
    public List<CnameInfo> cnames() {
        ListCnameInfo info = (ListCnameInfo) innerBody;
        return info != null ? info.getCnames() : null;
    } 
    
    /**
     * The name of the bucket to which the CNAME records you want to query are mapped.
     */
    public String bucket() {
        ListCnameInfo info = (ListCnameInfo) innerBody;
        return info != null ? info.getBucket() : null;
    } 
     

    ListCnameResult(Builder builder) {
        super(builder);
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