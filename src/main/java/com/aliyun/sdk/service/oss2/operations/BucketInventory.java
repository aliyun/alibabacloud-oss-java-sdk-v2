package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.*;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class BucketInventory {


    public static PutBucketInventoryResult putBucketInventory(ClientImpl impl, PutBucketInventoryRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketInventory.fromPutBucketInventory(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketInventory.toPutBucketInventory(output);
    }

    public static CompletableFuture<PutBucketInventoryResult> putBucketInventoryAsync(ClientImpl impl, PutBucketInventoryRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketInventory.fromPutBucketInventory(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketInventory::toPutBucketInventory);
    }


    public static GetBucketInventoryResult getBucketInventory(ClientImpl impl, GetBucketInventoryRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketInventory.fromGetBucketInventory(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketInventory.toGetBucketInventory(output);
    }

    public static CompletableFuture<GetBucketInventoryResult> getBucketInventoryAsync(ClientImpl impl, GetBucketInventoryRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketInventory.fromGetBucketInventory(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketInventory::toGetBucketInventory);
    }


    public static ListBucketInventoryResult listBucketInventory(ClientImpl impl, ListBucketInventoryRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketInventory.fromListBucketInventory(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketInventory.toListBucketInventory(output);
    }

    public static CompletableFuture<ListBucketInventoryResult> listBucketInventoryAsync(ClientImpl impl, ListBucketInventoryRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketInventory.fromListBucketInventory(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketInventory::toListBucketInventory);
    }


    public static DeleteBucketInventoryResult deleteBucketInventory(ClientImpl impl, DeleteBucketInventoryRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketInventory.fromDeleteBucketInventory(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketInventory.toDeleteBucketInventory(output);
    }

    public static CompletableFuture<DeleteBucketInventoryResult> deleteBucketInventoryAsync(ClientImpl impl, DeleteBucketInventoryRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketInventory.fromDeleteBucketInventory(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketInventory::toDeleteBucketInventory);
    }


}