package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.GetBucketRefererRequest;
import com.aliyun.sdk.service.oss2.models.GetBucketRefererResult;
import com.aliyun.sdk.service.oss2.models.PutBucketRefererRequest;
import com.aliyun.sdk.service.oss2.models.PutBucketRefererResult;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketReferer;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class BucketReferer {


    public static PutBucketRefererResult putBucketReferer(ClientImpl impl, PutBucketRefererRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketReferer.fromPutBucketReferer(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketReferer.toPutBucketReferer(output);
    }

    public static CompletableFuture<PutBucketRefererResult> putBucketRefererAsync(ClientImpl impl, PutBucketRefererRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketReferer.fromPutBucketReferer(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketReferer::toPutBucketReferer);
    }


    public static GetBucketRefererResult getBucketReferer(ClientImpl impl, GetBucketRefererRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketReferer.fromGetBucketReferer(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketReferer.toGetBucketReferer(output);
    }

    public static CompletableFuture<GetBucketRefererResult> getBucketRefererAsync(ClientImpl impl, GetBucketRefererRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketReferer.fromGetBucketReferer(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketReferer::toGetBucketReferer);
    }


}