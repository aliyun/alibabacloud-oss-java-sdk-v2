package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.*;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class BucketWorm {


    public static InitiateBucketWormResult initiateBucketWorm(ClientImpl impl, InitiateBucketWormRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketWorm.fromInitiateBucketWorm(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketWorm.toInitiateBucketWorm(output);
    }

    public static CompletableFuture<InitiateBucketWormResult> initiateBucketWormAsync(ClientImpl impl, InitiateBucketWormRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketWorm.fromInitiateBucketWorm(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketWorm::toInitiateBucketWorm);
    }


    public static AbortBucketWormResult abortBucketWorm(ClientImpl impl, AbortBucketWormRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketWorm.fromAbortBucketWorm(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketWorm.toAbortBucketWorm(output);
    }

    public static CompletableFuture<AbortBucketWormResult> abortBucketWormAsync(ClientImpl impl, AbortBucketWormRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketWorm.fromAbortBucketWorm(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketWorm::toAbortBucketWorm);
    }


    public static CompleteBucketWormResult completeBucketWorm(ClientImpl impl, CompleteBucketWormRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketWorm.fromCompleteBucketWorm(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketWorm.toCompleteBucketWorm(output);
    }

    public static CompletableFuture<CompleteBucketWormResult> completeBucketWormAsync(ClientImpl impl, CompleteBucketWormRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketWorm.fromCompleteBucketWorm(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketWorm::toCompleteBucketWorm);
    }


    public static ExtendBucketWormResult extendBucketWorm(ClientImpl impl, ExtendBucketWormRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketWorm.fromExtendBucketWorm(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketWorm.toExtendBucketWorm(output);
    }

    public static CompletableFuture<ExtendBucketWormResult> extendBucketWormAsync(ClientImpl impl, ExtendBucketWormRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketWorm.fromExtendBucketWorm(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketWorm::toExtendBucketWorm);
    }


    public static GetBucketWormResult getBucketWorm(ClientImpl impl, GetBucketWormRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketWorm.fromGetBucketWorm(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketWorm.toGetBucketWorm(output);
    }

    public static CompletableFuture<GetBucketWormResult> getBucketWormAsync(ClientImpl impl, GetBucketWormRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketWorm.fromGetBucketWorm(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketWorm::toGetBucketWorm);
    }


}