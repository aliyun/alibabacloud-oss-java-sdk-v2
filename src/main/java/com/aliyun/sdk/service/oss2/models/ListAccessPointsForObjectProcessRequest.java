package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;
import static java.util.Objects.requireNonNull;

/**
 * The request for the ListAccessPointsForObjectProcess operation.
 */
public final class ListAccessPointsForObjectProcessRequest extends RequestModel { 

    private ListAccessPointsForObjectProcessRequest(Builder builder) {
        super(builder);
    }
    
    /**
     * The maximum number of Object FC Access Points to return.Valid values: 1 to 1000 If the list cannot be complete at a time due to the configurations of the max-keys element, the NextContinuationToken element is included in the response as the token for the next list.
     */
    public Long maxKeys() {
        String value = parameters.get("max-keys");
        return ConvertUtils.toLongOrNull(value);
    }
    
    /**
     * The token from which the list operation must start. You can obtain this token from the NextContinuationToken element in the returned result.
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
        * The maximum number of Object FC Access Points to return.Valid values: 1 to 1000 If the list cannot be complete at a time due to the configurations of the max-keys element, the NextContinuationToken element is included in the response as the token for the next list.
        */
        public Builder maxKeys(Long value) {
            requireNonNull(value);
            this.parameters.put("max-keys", value.toString());
            return this;        
        }
    
        /**
        * The token from which the list operation must start. You can obtain this token from the NextContinuationToken element in the returned result.
        */
        public Builder continuationToken(String value) {
            requireNonNull(value);
            this.parameters.put("continuation-token", value);
            return this;        
        }
    
        
        public ListAccessPointsForObjectProcessRequest build() {
            return new ListAccessPointsForObjectProcessRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(ListAccessPointsForObjectProcessRequest request) {
            super(request);
        }        
    }

}
