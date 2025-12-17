package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketTags;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class BucketTags {


    public static PutBucketTagsResult putBucketTags(ClientImpl impl, PutBucketTagsRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketTags.fromPutBucketTags(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketTags.toPutBucketTags(output);
    }

    public static CompletableFuture<PutBucketTagsResult> putBucketTagsAsync(ClientImpl impl, PutBucketTagsRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketTags.fromPutBucketTags(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketTags::toPutBucketTags);
    }


    public static GetBucketTagsResult getBucketTags(ClientImpl impl, GetBucketTagsRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketTags.fromGetBucketTags(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketTags.toGetBucketTags(output);
    }

    public static CompletableFuture<GetBucketTagsResult> getBucketTagsAsync(ClientImpl impl, GetBucketTagsRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketTags.fromGetBucketTags(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketTags::toGetBucketTags);
    }


    public static DeleteBucketTagsResult deleteBucketTags(ClientImpl impl, DeleteBucketTagsRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketTags.fromDeleteBucketTags(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketTags.toDeleteBucketTags(output);
    }

    public static CompletableFuture<DeleteBucketTagsResult> deleteBucketTagsAsync(ClientImpl impl, DeleteBucketTagsRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketTags.fromDeleteBucketTags(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketTags::toDeleteBucketTags);
    }


}