package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the information about Function Compute.
 */
 @JacksonXmlRootElement(localName = "FunctionCompute")
public final class ObjectProcessFunctionCompute {
    @JacksonXmlProperty(localName = "FunctionAssumeRoleArn")
    private String functionAssumeRoleArn;
 
    @JacksonXmlProperty(localName = "FunctionArn")
    private String functionArn;

    public ObjectProcessFunctionCompute() {}

    private ObjectProcessFunctionCompute(Builder builder) {
        this.functionAssumeRoleArn = builder.functionAssumeRoleArn; 
        this.functionArn = builder.functionArn; 
    }

    /**
    * The Alibaba Cloud Resource Name (ARN) of the role that Function Compute uses to access your resources in other cloud services. The default role is AliyunFCDefaultRole.
    */
    public String functionAssumeRoleArn() {
        return this.functionAssumeRoleArn;
    }

    /**
    * The ARN of the function. For more information,
    */
    public String functionArn() {
        return this.functionArn;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String functionAssumeRoleArn;
        private String functionArn;
        
        /**
        * The Alibaba Cloud Resource Name (ARN) of the role that Function Compute uses to access your resources in other cloud services. The default role is AliyunFCDefaultRole.
        */
        public Builder functionAssumeRoleArn(String value) {
            requireNonNull(value);
            this.functionAssumeRoleArn = value;
            return this;
        }
        
        /**
        * The ARN of the function. For more information,
        */
        public Builder functionArn(String value) {
            requireNonNull(value);
            this.functionArn = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(ObjectProcessFunctionCompute from) {
            this.functionAssumeRoleArn = from.functionAssumeRoleArn; 
            this.functionArn = from.functionArn; 
        }

        public ObjectProcessFunctionCompute build() {
            return new ObjectProcessFunctionCompute(this);
        }
    }
}
