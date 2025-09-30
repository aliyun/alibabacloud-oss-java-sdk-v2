package com.aliyun.sdk.service.oss2.vectors;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.vectors.models.*;


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
    //-----------------------------------------------------------------------


    // vector bucket api
    //-----------------------------------------------------------------------

    /**
     * Creates a vector bucket.
     *
     * @param request A {@link PutVectorBucketRequest} for PutVectorBucket operation.
     * @return A {@link PutVectorBucketResult} for PutVectorBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default PutVectorBucketResult putVectorBucket(PutVectorBucketRequest request) {
        return putVectorBucket(request, OperationOptions.defaults());
    }

    /**
     * Creates a vector bucket.
     *
     * @param request A {@link PutVectorBucketRequest} for PutVectorBucket operation.
     * @param options The operation options.
     * @return A {@link PutVectorBucketResult} for PutVectorBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default PutVectorBucketResult putVectorBucket(PutVectorBucketRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the information of a vector bucket.
     *
     * @param request A {@link GetVectorBucketRequest} for GetVectorBucket operation.
     * @return A {@link GetVectorBucketResult} for GetVectorBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default GetVectorBucketResult getVectorBucket(GetVectorBucketRequest request) {
        return getVectorBucket(request, OperationOptions.defaults());
    }

    /**
     * Gets the information of a vector bucket.
     *
     * @param request A {@link GetVectorBucketRequest} for GetVectorBucket operation.
     * @param options The operation options.
     * @return A {@link GetVectorBucketResult} for GetVectorBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default GetVectorBucketResult getVectorBucket(GetVectorBucketRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes a vector bucket.
     *
     * @param request A {@link DeleteVectorBucketRequest} for DeleteVectorBucket operation.
     * @return A {@link DeleteVectorBucketResult} for DeleteVectorBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteVectorBucketResult deleteVectorBucket(DeleteVectorBucketRequest request) {
        return deleteVectorBucket(request, OperationOptions.defaults());
    }

    /**
     * Deletes a vector bucket.
     *
     * @param request A {@link DeleteVectorBucketRequest} for DeleteVectorBucket operation.
     * @param options The operation options.
     * @return A {@link DeleteVectorBucketResult} for DeleteVectorBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteVectorBucketResult deleteVectorBucket(DeleteVectorBucketRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Lists vector buckets.
     *
     * @param request A {@link ListVectorBucketsRequest} for ListVectorBuckets operation.
     * @return A {@link ListVectorBucketsResult} for ListVectorBuckets operation.
     * @throws RuntimeException If an error occurs
     */
    default ListVectorBucketsResult listVectorBuckets(ListVectorBucketsRequest request) {
        return listVectorBuckets(request, OperationOptions.defaults());
    }

    /**
     * Lists vector buckets.
     *
     * @param request A {@link ListVectorBucketsRequest} for ListVectorBuckets operation.
     * @param options The operation options.
     * @return A {@link ListVectorBucketsResult} for ListVectorBuckets operation.
     * @throws RuntimeException If an error occurs
     */
    default ListVectorBucketsResult listVectorBuckets(ListVectorBucketsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------

    // vector index api
    /**
     * Creates a vector index.
     *
     * @param request A {@link PutVectorIndexRequest} for PutVectorIndex operation.
     * @return A {@link PutVectorIndexResult} for PutVectorIndex operation.
     * @throws RuntimeException If an error occurs
     */
    default PutVectorIndexResult putVectorIndex(PutVectorIndexRequest request) {
        return putVectorIndex(request, OperationOptions.defaults());
    }

    /**
     * Creates a vector index.
     *
     * @param request A {@link PutVectorIndexRequest} for PutVectorIndex operation.
     * @param options The operation options.
     * @return A {@link PutVectorIndexResult} for PutVectorIndex operation.
     * @throws RuntimeException If an error occurs
     */
    default PutVectorIndexResult putVectorIndex(PutVectorIndexRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the information of a vector index.
     *
     * @param request A {@link GetVectorIndexRequest} for GetVectorIndex operation.
     * @return A {@link GetVectorIndexResult} for GetVectorIndex operation.
     * @throws RuntimeException If an error occurs
     */
    default GetVectorIndexResult getVectorIndex(GetVectorIndexRequest request) {
        return getVectorIndex(request, OperationOptions.defaults());
    }

    /**
     * Gets the information of a vector index.
     *
     * @param request A {@link GetVectorIndexRequest} for GetVectorIndex operation.
     * @param options The operation options.
     * @return A {@link GetVectorIndexResult} for GetVectorIndex operation.
     * @throws RuntimeException If an error occurs
     */
    default GetVectorIndexResult getVectorIndex(GetVectorIndexRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Lists vector indexes.
     *
     * @param request A {@link ListVectorIndexesRequest} for ListVectorIndexes operation.
     * @return A {@link ListVectorIndexesResult} for ListVectorIndexes operation.
     * @throws RuntimeException If an error occurs
     */
    default ListVectorIndexesResult listVectorIndexes(ListVectorIndexesRequest request) {
        return listVectorIndexes(request, OperationOptions.defaults());
    }

    /**
     * Lists vector indexes.
     *
     * @param request A {@link ListVectorIndexesRequest} for ListVectorIndexes operation.
     * @param options The operation options.
     * @return A {@link ListVectorIndexesResult} for ListVectorIndexes operation.
     * @throws RuntimeException If an error occurs
     */
    default ListVectorIndexesResult listVectorIndexes(ListVectorIndexesRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes a vector index.
     *
     * @param request A {@link DeleteVectorIndexRequest} for DeleteVectorIndex operation.
     * @return A {@link DeleteVectorIndexResult} for DeleteVectorIndex operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteVectorIndexResult deleteVectorIndex(DeleteVectorIndexRequest request) {
        return deleteVectorIndex(request, OperationOptions.defaults());
    }

    /**
     * Deletes a vector index.
     *
     * @param request A {@link DeleteVectorIndexRequest} for DeleteVectorIndex operation.
     * @param options The operation options.
     * @return A {@link DeleteVectorIndexResult} for DeleteVectorIndex operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteVectorIndexResult deleteVectorIndex(DeleteVectorIndexRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------


    // vectors api
    /**
     * Inserts vectors into an index.
     *
     * @param request A {@link PutVectorsRequest} for PutVectors operation.
     * @return A {@link PutVectorsResult} for PutVectors operation.
     * @throws RuntimeException If an error occurs
     */
    default PutVectorsResult putVectors(PutVectorsRequest request) {
        return putVectors(request, OperationOptions.defaults());
    }

    /**
     * Inserts vectors into an index.
     *
     * @param request A {@link PutVectorsRequest} for PutVectors operation.
     * @param options The operation options.
     * @return A {@link PutVectorsResult} for PutVectors operation.
     * @throws RuntimeException If an error occurs
     */
    default PutVectorsResult putVectors(PutVectorsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets vectors by their keys.
     *
     * @param request A {@link GetVectorsRequest} for GetVectors operation.
     * @return A {@link GetVectorsResult} for GetVectors operation.
     * @throws RuntimeException If an error occurs
     */
    default GetVectorsResult getVectors(GetVectorsRequest request) {
        return getVectors(request, OperationOptions.defaults());
    }

    /**
     * Gets vectors by their keys.
     *
     * @param request A {@link GetVectorsRequest} for GetVectors operation.
     * @param options The operation options.
     * @return A {@link GetVectorsResult} for GetVectors operation.
     * @throws RuntimeException If an error occurs
     */
    default GetVectorsResult getVectors(GetVectorsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Lists vectors in an index.
     *
     * @param request A {@link ListVectorsRequest} for ListVectors operation.
     * @return A {@link ListVectorsResult} for ListVectors operation.
     * @throws RuntimeException If an error occurs
     */
    default ListVectorsResult listVectors(ListVectorsRequest request) {
        return listVectors(request, OperationOptions.defaults());
    }

    /**
     * Lists vectors in an index.
     *
     * @param request A {@link ListVectorsRequest} for ListVectors operation.
     * @param options The operation options.
     * @return A {@link ListVectorsResult} for ListVectors operation.
     * @throws RuntimeException If an error occurs
     */
    default ListVectorsResult listVectors(ListVectorsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes vectors by their keys.
     *
     * @param request A {@link DeleteVectorsRequest} for DeleteVectors operation.
     * @return A {@link DeleteVectorsResult} for DeleteVectors operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteVectorsResult deleteVectors(DeleteVectorsRequest request) {
        return deleteVectors(request, OperationOptions.defaults());
    }

    /**
     * Deletes vectors by their keys.
     *
     * @param request A {@link DeleteVectorsRequest} for DeleteVectors operation.
     * @param options The operation options.
     * @return A {@link DeleteVectorsResult} for DeleteVectors operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteVectorsResult deleteVectors(DeleteVectorsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Queries vectors in an index based on similarity.
     *
     * @param request A {@link QueryVectorsRequest} for QueryVectors operation.
     * @return A {@link QueryVectorsResult} for QueryVectors operation.
     * @throws RuntimeException If an error occurs
     */
    default QueryVectorsResult queryVectors(QueryVectorsRequest request) {
        return queryVectors(request, OperationOptions.defaults());
    }

    /**
     * Queries vectors in an index based on similarity.
     *
     * @param request A {@link QueryVectorsRequest} for QueryVectors operation.
     * @param options The operation options.
     * @return A {@link QueryVectorsResult} for QueryVectors operation.
     * @throws RuntimeException If an error occurs
     */
    default QueryVectorsResult queryVectors(QueryVectorsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------

}
