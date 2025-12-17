package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;
import static java.util.Objects.requireNonNull;

/**
 * The request for the ListUserDataRedundancyTransition operation.
 */
public final class ListUserDataRedundancyTransitionRequest extends RequestModel { 

    private ListUserDataRedundancyTransitionRequest(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The token from which the list operation starts. You can obtain the token from NextContinuationToken in the response of the ListUserDataRedundancyTransition request.
     */
    public String continuationToken() {
        String value = parameters.get("continuation-token");
        return value;
    }

    /**
     * The maximum number of tasks to be returned. Valid values: 1 to 999. Default value: 100.
     * If the number of returned tasks exceeds the value of max-keys, the response contains NextContinuationToken.
     * Use the value of NextContinuationToken as the value of continuation-token in the next request.
     */
    public Long maxKeys() {
        String value = parameters.get("max-keys");
        return ConvertUtils.toLongOrNull(value);
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 

        /**
         * The token from which the list operation starts. You can obtain the token from NextContinuationToken in the response of the ListUserDataRedundancyTransition request.
         */
        public Builder continuationToken(String value) {
            requireNonNull(value);
            this.parameters.put("continuation-token", value);
            return this;
        }

        /**
         * The maximum number of tasks to be returned. Valid values: 1 to 999. Default value: 100.
         * If the number of returned tasks exceeds the value of max-keys, the response contains NextContinuationToken.
         * Use the value of NextContinuationToken as the value of continuation-token in the next request.
         */
        public Builder maxKeys(Long value) {
            requireNonNull(value);
            this.parameters.put("max-keys", value.toString());
            return this;
        }

        public ListUserDataRedundancyTransitionRequest build() {
            return new ListUserDataRedundancyTransitionRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(ListUserDataRedundancyTransitionRequest request) {
            super(request);
        }        
    }

}