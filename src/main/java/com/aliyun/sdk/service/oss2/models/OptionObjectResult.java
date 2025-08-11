package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

/**
 * The result for the OptionObject operation.
 */
public final class OptionObjectResult extends ResultModel {

    OptionObjectResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The HTTP method of the request. If the request is denied, the response does not contain the header.
     */
    public String accessControlAllowMethods() {
        String value = headers.get("Access-Control-Allow-Methods");
        return value;
    }

    /**
     * The list of headers included in the request. If the request includes headers that are not allowed, the response does not contain the headers and the request is denied.
     */
    public String accessControlAllowHeaders() {
        String value = headers.get("Access-Control-Allow-Headers");
        return value;
    }

    /**
     * The list of headers that can be accessed by JavaScript applications on a client.
     */
    public String accessControlExposeHeaders() {
        String value = headers.get("Access-Control-Expose-Headers");
        return value;
    }

    /**
     * The maximum duration for the browser to cache preflight results. Unit: seconds.
     */
    public Long accessControlMaxAge() {
        String value = headers.get("Access-Control-Max-Age");
        return ConvertUtils.toLongOrNull(value);
    }

    /**
     * The origin that is included in the request. If the request is denied, the response does not contain the header.
     */
    public String accessControlAllowOrigin() {
        String value = headers.get("Access-Control-Allow-Origin");
        return value;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(OptionObjectResult result) {
            super(result);
        }

        public OptionObjectResult build() {
            return new OptionObjectResult(this);
        }
    }
}
