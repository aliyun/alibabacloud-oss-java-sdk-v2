package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.*;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class BucketArchiveDirectRead {


    public static GetBucketArchiveDirectReadResult getBucketArchiveDirectRead(ClientImpl impl, GetBucketArchiveDirectReadRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketArchiveDirectRead.fromGetBucketArchiveDirectRead(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketArchiveDirectRead.toGetBucketArchiveDirectRead(output);
    }

    public static CompletableFuture<GetBucketArchiveDirectReadResult> getBucketArchiveDirectReadAsync(ClientImpl impl, GetBucketArchiveDirectReadRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketArchiveDirectRead.fromGetBucketArchiveDirectRead(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketArchiveDirectRead::toGetBucketArchiveDirectRead);
    }


    public static PutBucketArchiveDirectReadResult putBucketArchiveDirectRead(ClientImpl impl, PutBucketArchiveDirectReadRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketArchiveDirectRead.fromPutBucketArchiveDirectRead(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketArchiveDirectRead.toPutBucketArchiveDirectRead(output);
    }

    public static CompletableFuture<PutBucketArchiveDirectReadResult> putBucketArchiveDirectReadAsync(ClientImpl impl, PutBucketArchiveDirectReadRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketArchiveDirectRead.fromPutBucketArchiveDirectRead(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketArchiveDirectRead::toPutBucketArchiveDirectRead);
    }
}