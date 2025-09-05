package com.aliyun.sdk.service.oss2.vectors;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;

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
