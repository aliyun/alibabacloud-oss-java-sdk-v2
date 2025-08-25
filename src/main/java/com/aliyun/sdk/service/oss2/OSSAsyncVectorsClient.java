package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.exceptions.OperationException;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transport.BinaryDataConsumerSupplier;
import com.aliyun.sdk.service.oss2.utils.IOUtils;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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
