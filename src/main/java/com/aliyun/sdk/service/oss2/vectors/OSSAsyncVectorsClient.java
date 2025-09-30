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


    // vector index api
    /**
     * Creates a vector index.
     *
     * @param request A {@link PutVectorIndexRequest} for PutVectorIndex operation.
     * @return A Java Future containing the {@link PutVectorIndexResult} of the PutVectorIndex operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutVectorIndexResult> putVectorIndexAsync(PutVectorIndexRequest request) {
        return putVectorIndexAsync(request, OperationOptions.defaults());
    }

    /**
     * Creates a vector index.
     *
     * @param request A {@link PutVectorIndexRequest} for PutVectorIndex operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link PutVectorIndexResult} of the PutVectorIndex operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutVectorIndexResult> putVectorIndexAsync(PutVectorIndexRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the information of a vector index.
     *
     * @param request A {@link GetVectorIndexRequest} for GetVectorIndex operation.
     * @return A Java Future containing the {@link GetVectorIndexResult} of the GetVectorIndex operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetVectorIndexResult> getVectorIndexAsync(GetVectorIndexRequest request) {
        return getVectorIndexAsync(request, OperationOptions.defaults());
    }

    /**
     * Gets the information of a vector index.
     *
     * @param request A {@link GetVectorIndexRequest} for GetVectorIndex operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetVectorIndexResult} of the GetVectorIndex operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetVectorIndexResult> getVectorIndexAsync(GetVectorIndexRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Lists vector indexes.
     *
     * @param request A {@link ListVectorIndexesRequest} for ListVectorIndexes operation.
     * @return A Java Future containing the {@link ListVectorIndexesResult} of the ListVectorIndexes operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListVectorIndexesResult> listVectorIndexesAsync(ListVectorIndexesRequest request) {
        return listVectorIndexesAsync(request, OperationOptions.defaults());
    }

    /**
     * Lists vector indexes.
     *
     * @param request A {@link ListVectorIndexesRequest} for ListVectorIndexes operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link ListVectorIndexesResult} of the ListVectorIndexes operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListVectorIndexesResult> listVectorIndexesAsync(ListVectorIndexesRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes a vector index.
     *
     * @param request A {@link DeleteVectorIndexRequest} for DeleteVectorIndex operation.
     * @return A Java Future containing the {@link DeleteVectorIndexResult} of the DeleteVectorIndex operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteVectorIndexResult> deleteVectorIndexAsync(DeleteVectorIndexRequest request) {
        return deleteVectorIndexAsync(request, OperationOptions.defaults());
    }

    /**
     * Deletes a vector index.
     *
     * @param request A {@link DeleteVectorIndexRequest} for DeleteVectorIndex operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link DeleteVectorIndexResult} of the DeleteVectorIndex operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteVectorIndexResult> deleteVectorIndexAsync(DeleteVectorIndexRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------


    // vectors api
    /**
     * Inserts vectors into an index.
     *
     * @param request A {@link PutVectorsRequest} for PutVectors operation.
     * @return A Java Future containing the {@link PutVectorsResult} of the PutVectors operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutVectorsResult> putVectorsAsync(PutVectorsRequest request) {
        return putVectorsAsync(request, OperationOptions.defaults());
    }

    /**
     * Inserts vectors into an index.
     *
     * @param request A {@link PutVectorsRequest} for PutVectors operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link PutVectorsResult} of the PutVectors operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutVectorsResult> putVectorsAsync(PutVectorsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets vectors by their keys.
     *
     * @param request A {@link GetVectorsRequest} for GetVectors operation.
     * @return A Java Future containing the {@link GetVectorsResult} of the GetVectors operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetVectorsResult> getVectorsAsync(GetVectorsRequest request) {
        return getVectorsAsync(request, OperationOptions.defaults());
    }

    /**
     * Gets vectors by their keys.
     *
     * @param request A {@link GetVectorsRequest} for GetVectors operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetVectorsResult} of the GetVectors operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetVectorsResult> getVectorsAsync(GetVectorsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Lists vectors in an index.
     *
     * @param request A {@link ListVectorsRequest} for ListVectors operation.
     * @return A Java Future containing the {@link ListVectorsResult} of the ListVectors operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListVectorsResult> listVectorsAsync(ListVectorsRequest request) {
        return listVectorsAsync(request, OperationOptions.defaults());
    }

    /**
     * Lists vectors in an index.
     *
     * @param request A {@link ListVectorsRequest} for ListVectors operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link ListVectorsResult} of the ListVectors operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListVectorsResult> listVectorsAsync(ListVectorsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes vectors by their keys.
     *
     * @param request A {@link DeleteVectorsRequest} for DeleteVectors operation.
     * @return A Java Future containing the {@link DeleteVectorsResult} of the DeleteVectors operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteVectorsResult> deleteVectorsAsync(DeleteVectorsRequest request) {
        return deleteVectorsAsync(request, OperationOptions.defaults());
    }

    /**
     * Deletes vectors by their keys.
     *
     * @param request A {@link DeleteVectorsRequest} for DeleteVectors operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link DeleteVectorsResult} of the DeleteVectors operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteVectorsResult> deleteVectorsAsync(DeleteVectorsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Queries vectors in an index based on similarity.
     *
     * @param request A {@link QueryVectorsRequest} for QueryVectors operation.
     * @return A Java Future containing the {@link QueryVectorsResult} of the QueryVectors operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<QueryVectorsResult> queryVectorsAsync(QueryVectorsRequest request) {
        return queryVectorsAsync(request, OperationOptions.defaults());
    }

    /**
     * Queries vectors in an index based on similarity.
     *
     * @param request A {@link QueryVectorsRequest} for QueryVectors operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link QueryVectorsResult} of the QueryVectors operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<QueryVectorsResult> queryVectorsAsync(QueryVectorsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------

}
