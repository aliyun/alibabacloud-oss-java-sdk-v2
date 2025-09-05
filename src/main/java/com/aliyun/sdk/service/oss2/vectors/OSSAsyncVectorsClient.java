package com.aliyun.sdk.service.oss2.vectors;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;

import java.util.concurrent.CompletableFuture;

/**
 * A client for accessing OSS vectors asynchronously.
 * This can be created using the static {@link #newBuilder()} method.
 */
public interface OSSAsyncVectorsClient extends AutoCloseable {

    static OSSAsyncVectorsClientBuilder newBuilder() {
        return new DefaultOSSAsyncVectorsClientBuilder();
    }

    // common api
    //-----------------------------------------------------------------------
    default CompletableFuture<OperationOutput> invokeOperationAsync(OperationInput input, OperationOptions opts) {
        throw new UnsupportedOperationException();
    }


    //-----------------------------------------------------------------------
}
