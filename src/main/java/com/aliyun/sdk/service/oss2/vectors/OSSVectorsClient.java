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

}
