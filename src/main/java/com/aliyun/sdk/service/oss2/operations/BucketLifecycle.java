package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketLifecycle;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class BucketLifecycle {


    public static PutBucketLifecycleResult putBucketLifecycle(ClientImpl impl, PutBucketLifecycleRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketLifecycle.fromPutBucketLifecycle(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketLifecycle.toPutBucketLifecycle(output);
    }

    public static CompletableFuture<PutBucketLifecycleResult> putBucketLifecycleAsync(ClientImpl impl, PutBucketLifecycleRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketLifecycle.fromPutBucketLifecycle(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketLifecycle::toPutBucketLifecycle);
    }


    public static GetBucketLifecycleResult getBucketLifecycle(ClientImpl impl, GetBucketLifecycleRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketLifecycle.fromGetBucketLifecycle(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketLifecycle.toGetBucketLifecycle(output);
    }

    public static CompletableFuture<GetBucketLifecycleResult> getBucketLifecycleAsync(ClientImpl impl, GetBucketLifecycleRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketLifecycle.fromGetBucketLifecycle(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketLifecycle::toGetBucketLifecycle);
    }


    public static DeleteBucketLifecycleResult deleteBucketLifecycle(ClientImpl impl, DeleteBucketLifecycleRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketLifecycle.fromDeleteBucketLifecycle(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketLifecycle.toDeleteBucketLifecycle(output);
    }

    public static CompletableFuture<DeleteBucketLifecycleResult> deleteBucketLifecycleAsync(ClientImpl impl, DeleteBucketLifecycleRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketLifecycle.fromDeleteBucketLifecycle(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketLifecycle::toDeleteBucketLifecycle);
    }


}