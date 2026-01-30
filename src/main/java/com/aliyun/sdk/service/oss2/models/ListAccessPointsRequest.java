package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;
import static java.util.Objects.requireNonNull;

/**
 * The request for the ListAccessPoints operation.
 */
public final class ListAccessPointsRequest extends RequestModel { 

    private ListAccessPointsRequest(Builder builder) {
        super(builder);
    }
    
    /**
     * The maximum number of access points that can be returned. Valid values:*   For user-level access points: (0,1000].*   For bucket-level access points: (0,100].
     */
    public Long maxKeys() {
        String value = parameters.get("max-keys");
        return ConvertUtils.toLongOrNull(value);
    }
    
    /**
     * The token from which the listing operation starts. You must specify the value of NextContinuationToken that is obtained from the previous query as the value of continuation-token.
     */
    public String continuationToken() {
        String value = parameters.get("continuation-token");
        return value;
    }
    

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
    
        /**
        * The maximum number of access points that can be returned. Valid values:*   For user-level access points: (0,1000].*   For bucket-level access points: (0,100].
        */
        public Builder maxKeys(Long value) {
            requireNonNull(value);
            this.parameters.put("max-keys", value.toString());
            return this;        
        }
    
        /**
        * The token from which the listing operation starts. You must specify the value of NextContinuationToken that is obtained from the previous query as the value of continuation-token.
        */
        public Builder continuationToken(String value) {
            requireNonNull(value);
            this.parameters.put("continuation-token", value);
            return this;        
        }
    
        
        public ListAccessPointsRequest build() {
            return new ListAccessPointsRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(ListAccessPointsRequest request) {
            super(request);
        }        
    }

}
