package com.aliyun.sdk.service.oss2;

/**
 * A client for accessing OSS Vectors synchronously.
 * This can be created using the static {@link #newBuilder()} method.
 */
public interface OSSVectorsClient extends AutoCloseable {

    static OSSVectorsClientBuilder newBuilder() {
        return new DefaultOSSVectorsClientBuilder();
    }

    // common api
    //-----------------------------------------------------------------------
    default OperationOutput invokeOperation(OperationInput input, OperationOptions opts) {
        throw new UnsupportedOperationException();
    }

}
