package com.aliyun.sdk.service.oss2.operations;


import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.*;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

public final class BucketVersioning {


    public static PutBucketVersioningResult putBucketVersioning(ClientImpl impl, PutBucketVersioningRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketVersioning.fromPutBucketVersioning(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketVersioning.toPutBucketVersioning(output);
    }

    public static CompletableFuture<PutBucketVersioningResult> putBucketVersioningAsync(ClientImpl impl, PutBucketVersioningRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketVersioning.fromPutBucketVersioning(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketVersioning::toPutBucketVersioning);
    }


    public static GetBucketVersioningResult getBucketVersioning(ClientImpl impl, GetBucketVersioningRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketVersioning.fromGetBucketVersioning(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketVersioning.toGetBucketVersioning(output);
    }

    public static CompletableFuture<GetBucketVersioningResult> getBucketVersioningAsync(ClientImpl impl, GetBucketVersioningRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketVersioning.fromGetBucketVersioning(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketVersioning::toGetBucketVersioning);
    }


    public static ListObjectVersionsResult listObjectVersions(ClientImpl impl, ListObjectVersionsRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketVersioning.fromListObjectVersions(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketVersioning.toListObjectVersions(output);
    }

    public static CompletableFuture<ListObjectVersionsResult> listObjectVersionsAsync(ClientImpl impl, ListObjectVersionsRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketVersioning.fromListObjectVersions(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketVersioning::toListObjectVersions);
    }


}