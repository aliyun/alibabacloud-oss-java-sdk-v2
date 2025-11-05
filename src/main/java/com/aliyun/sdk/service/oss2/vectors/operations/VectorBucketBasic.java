package com.aliyun.sdk.service.oss2.vectors.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.vectors.models.*;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorBucketBasic;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class VectorBucketBasic {

    public static PutVectorBucketResult putVectorBucket(ClientImpl impl, PutVectorBucketRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorBucketBasic.fromPutVectorBucket(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeVectorBucketBasic.toPutVectorBucket(output);
    }

    public static CompletableFuture<PutVectorBucketResult> putVectorBucketAsync(ClientImpl impl, PutVectorBucketRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorBucketBasic.fromPutVectorBucket(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeVectorBucketBasic::toPutVectorBucket);
    }

    public static GetVectorBucketResult getVectorBucket(ClientImpl impl, GetVectorBucketRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorBucketBasic.fromGetVectorBucket(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeVectorBucketBasic.toGetVectorBucket(output);
    }

    public static CompletableFuture<GetVectorBucketResult> getVectorBucketAsync(ClientImpl impl, GetVectorBucketRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorBucketBasic.fromGetVectorBucket(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeVectorBucketBasic::toGetVectorBucket);
    }

    public static DeleteVectorBucketResult deleteVectorBucket(ClientImpl impl, DeleteVectorBucketRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorBucketBasic.fromDeleteVectorBucket(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeVectorBucketBasic.toDeleteVectorBucket(output);
    }

    public static CompletableFuture<DeleteVectorBucketResult> deleteVectorBucketAsync(ClientImpl impl, DeleteVectorBucketRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorBucketBasic.fromDeleteVectorBucket(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeVectorBucketBasic::toDeleteVectorBucket);
    }

    public static ListVectorBucketsResult listVectorBuckets(ClientImpl impl, ListVectorBucketsRequest request, OperationOptions options) {
        OperationInput input = SerdeVectorBucketBasic.fromListVectorBuckets(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeVectorBucketBasic.toListVectorBuckets(output);
    }

    public static CompletableFuture<ListVectorBucketsResult> listVectorBucketsAsync(ClientImpl impl, ListVectorBucketsRequest request, OperationOptions options) {
        OperationInput input = SerdeVectorBucketBasic.fromListVectorBuckets(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeVectorBucketBasic::toListVectorBuckets);
    }

}
