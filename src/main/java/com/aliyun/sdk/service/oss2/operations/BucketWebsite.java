package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.*;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class BucketWebsite {


    public static GetBucketWebsiteResult getBucketWebsite(ClientImpl impl, GetBucketWebsiteRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketWebsite.fromGetBucketWebsite(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketWebsite.toGetBucketWebsite(output);
    }

    public static CompletableFuture<GetBucketWebsiteResult> getBucketWebsiteAsync(ClientImpl impl, GetBucketWebsiteRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketWebsite.fromGetBucketWebsite(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketWebsite::toGetBucketWebsite);
    }


    public static PutBucketWebsiteResult putBucketWebsite(ClientImpl impl, PutBucketWebsiteRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketWebsite.fromPutBucketWebsite(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketWebsite.toPutBucketWebsite(output);
    }

    public static CompletableFuture<PutBucketWebsiteResult> putBucketWebsiteAsync(ClientImpl impl, PutBucketWebsiteRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketWebsite.fromPutBucketWebsite(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketWebsite::toPutBucketWebsite);
    }


    public static DeleteBucketWebsiteResult deleteBucketWebsite(ClientImpl impl, DeleteBucketWebsiteRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketWebsite.fromDeleteBucketWebsite(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketWebsite.toDeleteBucketWebsite(output);
    }

    public static CompletableFuture<DeleteBucketWebsiteResult> deleteBucketWebsiteAsync(ClientImpl impl, DeleteBucketWebsiteRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketWebsite.fromDeleteBucketWebsite(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketWebsite::toDeleteBucketWebsite);
    }


}