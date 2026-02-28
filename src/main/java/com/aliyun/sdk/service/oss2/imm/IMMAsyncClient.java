package com.aliyun.sdk.service.oss2.imm;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.imm.models.*;

import java.util.concurrent.CompletableFuture;

/**
 * A client for accessing IMM asynchronously.
 * This can be created using the static {@link #newBuilder()} method.
 */
public interface IMMAsyncClient extends AutoCloseable {

    static IMMAsyncClientBuilder newBuilder() {
        return new DefaultIMMAsyncClientBuilder();
    }

    // common api
    //-----------------------------------------------------------------------
    default CompletableFuture<OperationOutput> invokeOperationAsync(OperationInput input, OperationOptions opts) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------

    // Dataset api
    //-----------------------------------------------------------------------
    /**
     * Creates a dataset.
     *
     * @param request A {@link CreateDatasetRequest} for CreateDataset operation.
     * @return A Java Future containing the {@link CreateDatasetResult} of the CreateDataset operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<CreateDatasetResult> createDatasetAsync(CreateDatasetRequest request) {
        return createDatasetAsync(request, OperationOptions.defaults());
    }

    /**
     * Creates a vector bucket.
     *
     * @param request A {@link CreateDatasetRequest} for CreateDataset operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link CreateDatasetResult} of the CreateDataset operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<CreateDatasetResult> createDatasetAsync(CreateDatasetRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------

}
