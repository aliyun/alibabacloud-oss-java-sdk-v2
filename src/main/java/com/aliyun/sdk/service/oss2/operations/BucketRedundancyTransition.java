package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketRedundancyTransition;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class BucketRedundancyTransition {


    public static ListBucketDataRedundancyTransitionResult listBucketDataRedundancyTransition(ClientImpl impl, ListBucketDataRedundancyTransitionRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketRedundancyTransition.fromListBucketDataRedundancyTransition(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketRedundancyTransition.toListBucketDataRedundancyTransition(output);
    }

    public static CompletableFuture<ListBucketDataRedundancyTransitionResult> listBucketDataRedundancyTransitionAsync(ClientImpl impl, ListBucketDataRedundancyTransitionRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketRedundancyTransition.fromListBucketDataRedundancyTransition(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketRedundancyTransition::toListBucketDataRedundancyTransition);
    }


    public static ListUserDataRedundancyTransitionResult listUserDataRedundancyTransition(ClientImpl impl, ListUserDataRedundancyTransitionRequest request, OperationOptions options) {
        OperationInput input = SerdeBucketRedundancyTransition.fromListUserDataRedundancyTransition(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketRedundancyTransition.toListUserDataRedundancyTransition(output);
    }

    public static CompletableFuture<ListUserDataRedundancyTransitionResult> listUserDataRedundancyTransitionAsync(ClientImpl impl, ListUserDataRedundancyTransitionRequest request, OperationOptions options) {
        OperationInput input = SerdeBucketRedundancyTransition.fromListUserDataRedundancyTransition(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketRedundancyTransition::toListUserDataRedundancyTransition);
    }


    public static GetBucketDataRedundancyTransitionResult getBucketDataRedundancyTransition(ClientImpl impl, GetBucketDataRedundancyTransitionRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketRedundancyTransition.fromGetBucketDataRedundancyTransition(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketRedundancyTransition.toGetBucketDataRedundancyTransition(output);
    }

    public static CompletableFuture<GetBucketDataRedundancyTransitionResult> getBucketDataRedundancyTransitionAsync(ClientImpl impl, GetBucketDataRedundancyTransitionRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketRedundancyTransition.fromGetBucketDataRedundancyTransition(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketRedundancyTransition::toGetBucketDataRedundancyTransition);
    }


    public static CreateBucketDataRedundancyTransitionResult createBucketDataRedundancyTransition(ClientImpl impl, CreateBucketDataRedundancyTransitionRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketRedundancyTransition.fromCreateBucketDataRedundancyTransition(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketRedundancyTransition.toCreateBucketDataRedundancyTransition(output);
    }

    public static CompletableFuture<CreateBucketDataRedundancyTransitionResult> createBucketDataRedundancyTransitionAsync(ClientImpl impl, CreateBucketDataRedundancyTransitionRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketRedundancyTransition.fromCreateBucketDataRedundancyTransition(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketRedundancyTransition::toCreateBucketDataRedundancyTransition);
    }


    public static DeleteBucketDataRedundancyTransitionResult deleteBucketDataRedundancyTransition(ClientImpl impl, DeleteBucketDataRedundancyTransitionRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketRedundancyTransition.fromDeleteBucketDataRedundancyTransition(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketRedundancyTransition.toDeleteBucketDataRedundancyTransition(output);
    }

    public static CompletableFuture<DeleteBucketDataRedundancyTransitionResult> deleteBucketDataRedundancyTransitionAsync(ClientImpl impl, DeleteBucketDataRedundancyTransitionRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketRedundancyTransition.fromDeleteBucketDataRedundancyTransition(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketRedundancyTransition::toDeleteBucketDataRedundancyTransition);
    }


}