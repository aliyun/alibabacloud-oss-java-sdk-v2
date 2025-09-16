package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.Base64Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class CallbackHelper {

    public enum CallbackBodyType {
        URL_ENCODED("application/x-www-form-urlencoded"),
        JSON("application/json");

        private final String value;

        CallbackBodyType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }


    private final String callbackUrl;
    private final String callbackHost;
    private final String callbackBody;
    private final String callbackBodyType;
    private final Boolean callbackSNI;
    private final Map<String, String> callbackVar;


    private CallbackHelper(Builder builder) {
        this.callbackUrl = builder.callbackUrl;
        this.callbackHost = builder.callbackHost;
        this.callbackBody = builder.callbackBody;
        this.callbackBodyType = builder.callbackBodyType;
        this.callbackSNI = builder.callbackSNI;
        this.callbackVar = builder.callbackVar;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Gets the callbackUrl.
     */
    public String callbackUrl() {
        return this.callbackUrl;
    }

    /**
     * Gets the callback host.
     */
    public String callbackHost() {
        return this.callbackHost;
    }

    /**
     * Gets the callback body in the request.
     */
    public String callbackBody() {
        return this.callbackBody;
    }

    /**
     * Gets the content-type header in the request.
     */
    public String callbackBodyType() {
        return this.callbackBodyType;
    }

    /**
     * When the client initiates a callback request,
     * does the OSS send a server name indication SNI to the return source address specified through callbackUrl
     */
    public Boolean callbackSNI() {
        return this.callbackSNI;
    }

    /**
     * The custom parameters
     */
    public Map<String, String> callbackVar() {
        return this.callbackVar;
    }

    public String toCallbackParameter() {
        Map<String, Object> callbackMap = new LinkedHashMap<>();
        if (this.callbackUrl != null) {
            callbackMap.put("callbackUrl", this.callbackUrl);
        }
        if (this.callbackHost != null) {
            callbackMap.put("callbackHost", this.callbackHost);
        }
        if (this.callbackBody != null) {
            callbackMap.put("callbackBody", this.callbackBody);
        }
        if (this.callbackBodyType != null) {
            callbackMap.put("callbackBodyType", this.callbackBodyType);
        }
        if (this.callbackSNI != null) {
            callbackMap.put("callbackSNI", this.callbackSNI);
        }
        return toJsonBase64(callbackMap);
    }

    public String toCallbackVarParameter() {
        Map<String, Object> callbackMap = new LinkedHashMap<>();
        if (this.callbackVar != null) {
            callbackMap.putAll(this.callbackVar);
        }
        return toJsonBase64(callbackMap);
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String callbackUrl;
        private String callbackHost;
        private String callbackBody;
        private String callbackBodyType;
        private Boolean callbackSNI;
        private Map<String, String> callbackVar;

        private Builder() {
            super();
        }

        private Builder(CallbackHelper from) {
            this.callbackUrl = from.callbackUrl;
            this.callbackHost = from.callbackHost;
            this.callbackBody = from.callbackBody;
            this.callbackBodyType = from.callbackBodyType;
            this.callbackSNI = from.callbackSNI;
            this.callbackVar = from.callbackVar;
        }

        /**
         * Sets the callbackUrl after a successful upload.
         */
        public Builder callbackUrl(String value) {
            requireNonNull(value);
            this.callbackUrl = value;
            return this;
        }

        /**
         * Sets the callback host.
         */
        public Builder callbackHost(String value) {
            requireNonNull(value);
            this.callbackHost = value;
            return this;
        }


        /**
         * Sets the callback body in the request.
         */
        public Builder callbackBody(String value) {
            requireNonNull(value);
            this.callbackBody = value;
            return this;
        }

        /**
         * Sets the content-type header in the request.
         */
        public Builder callbackBodyType(CallbackBodyType value) {
            requireNonNull(value);
            this.callbackBodyType = value.toString();
            return this;
        }

        /**
         * When the client initiates a callback request,
         * does the OSS send a server name indication SNI to the return source address specified through callbackUrl
         */
        public Builder callbackSNI(boolean value) {
            this.callbackSNI = value;
            return this;
        }

        /**
         * The custom parameters
         */
        public Builder callbackVar(Map<String, String> value) {
            this.callbackVar = value;
            return this;
        }

        public CallbackHelper build() {
            return new CallbackHelper(this);
        }
    }

    static String toJsonBase64(Map<String, Object> value) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(value);
            return Base64Utils.encodeToString(json.getBytes(StandardCharsets.UTF_8));
        } catch (Exception ignore) {
            //
        }
        return null;
    }
}
