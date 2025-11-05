package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketPublicAccessBlock;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class BucketPublicAccessBlock {


    public static GetBucketPublicAccessBlockResult getBucketPublicAccessBlock(ClientImpl impl, GetBucketPublicAccessBlockRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketPublicAccessBlock.fromGetBucketPublicAccessBlock(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketPublicAccessBlock.toGetBucketPublicAccessBlock(output);
    }

    public static CompletableFuture<GetBucketPublicAccessBlockResult> getBucketPublicAccessBlockAsync(ClientImpl impl, GetBucketPublicAccessBlockRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketPublicAccessBlock.fromGetBucketPublicAccessBlock(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketPublicAccessBlock::toGetBucketPublicAccessBlock);
    }


    public static PutBucketPublicAccessBlockResult putBucketPublicAccessBlock(ClientImpl impl, PutBucketPublicAccessBlockRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketPublicAccessBlock.fromPutBucketPublicAccessBlock(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketPublicAccessBlock.toPutBucketPublicAccessBlock(output);
    }

    public static CompletableFuture<PutBucketPublicAccessBlockResult> putBucketPublicAccessBlockAsync(ClientImpl impl, PutBucketPublicAccessBlockRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketPublicAccessBlock.fromPutBucketPublicAccessBlock(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketPublicAccessBlock::toPutBucketPublicAccessBlock);
    }


    public static DeleteBucketPublicAccessBlockResult deleteBucketPublicAccessBlock(ClientImpl impl, DeleteBucketPublicAccessBlockRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketPublicAccessBlock.fromDeleteBucketPublicAccessBlock(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketPublicAccessBlock.toDeleteBucketPublicAccessBlock(output);
    }

    public static CompletableFuture<DeleteBucketPublicAccessBlockResult> deleteBucketPublicAccessBlockAsync(ClientImpl impl, DeleteBucketPublicAccessBlockRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketPublicAccessBlock.fromDeleteBucketPublicAccessBlock(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketPublicAccessBlock::toDeleteBucketPublicAccessBlock);
    }


}