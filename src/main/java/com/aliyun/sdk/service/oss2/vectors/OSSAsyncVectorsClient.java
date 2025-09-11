package com.aliyun.sdk.service.oss2.vectors;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import java.util.concurrent.CompletableFuture;
import com.aliyun.sdk.service.oss2.vectors.models.*;

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

    // vector bucket api
    //-----------------------------------------------------------------------

    /**
     * Creates a vector bucket.
     *
     * @param request A {@link PutVectorBucketRequest} for PutVectorBucket operation.
     * @return A Java Future containing the {@link PutVectorBucketResult} of the PutVectorBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutVectorBucketResult> putVectorBucketAsync(PutVectorBucketRequest request) {
        return putVectorBucketAsync(request, OperationOptions.defaults());
    }

    /**
     * Creates a vector bucket.
     *
     * @param request A {@link PutVectorBucketRequest} for PutVectorBucket operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link PutVectorBucketResult} of the PutVectorBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutVectorBucketResult> putVectorBucketAsync(PutVectorBucketRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the information of a vector bucket.
     *
     * @param request A {@link GetVectorBucketRequest} for GetVectorBucket operation.
     * @return A Java Future containing the {@link GetVectorBucketResult} of the GetVectorBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetVectorBucketResult> getVectorBucketAsync(GetVectorBucketRequest request) {
        return getVectorBucketAsync(request, OperationOptions.defaults());
    }

    /**
     * Gets the information of a vector bucket.
     *
     * @param request A {@link GetVectorBucketRequest} for GetVectorBucket operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetVectorBucketResult} of the GetVectorBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetVectorBucketResult> getVectorBucketAsync(GetVectorBucketRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes a vector bucket.
     *
     * @param request A {@link DeleteVectorBucketRequest} for DeleteVectorBucket operation.
     * @return A Java Future containing the {@link DeleteVectorBucketResult} of the DeleteVectorBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteVectorBucketResult> deleteVectorBucketAsync(DeleteVectorBucketRequest request) {
        return deleteVectorBucketAsync(request, OperationOptions.defaults());
    }

    /**
     * Deletes a vector bucket.
     *
     * @param request A {@link DeleteVectorBucketRequest} for DeleteVectorBucket operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link DeleteVectorBucketResult} of the DeleteVectorBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteVectorBucketResult> deleteVectorBucketAsync(DeleteVectorBucketRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Lists vector buckets.
     *
     * @param request A {@link ListVectorBucketsRequest} for ListVectorBuckets operation.
     * @return A Java Future containing the {@link ListVectorBucketsResult} of the ListVectorBuckets operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListVectorBucketsResult> listVectorBucketsAsync(ListVectorBucketsRequest request) {
        return listVectorBucketsAsync(request, OperationOptions.defaults());
    }

    /**
     * Lists vector buckets.
     *
     * @param request A {@link ListVectorBucketsRequest} for ListVectorBuckets operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link ListVectorBucketsResult} of the ListVectorBuckets operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListVectorBucketsResult> listVectorBucketsAsync(ListVectorBucketsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------

}
