package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.*;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class BucketTransferAcceleration {


    public static PutBucketTransferAccelerationResult putBucketTransferAcceleration(ClientImpl impl, PutBucketTransferAccelerationRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketTransferAcceleration.fromPutBucketTransferAcceleration(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketTransferAcceleration.toPutBucketTransferAcceleration(output);
    }

    public static CompletableFuture<PutBucketTransferAccelerationResult> putBucketTransferAccelerationAsync(ClientImpl impl, PutBucketTransferAccelerationRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketTransferAcceleration.fromPutBucketTransferAcceleration(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketTransferAcceleration::toPutBucketTransferAcceleration);
    }


    public static GetBucketTransferAccelerationResult getBucketTransferAcceleration(ClientImpl impl, GetBucketTransferAccelerationRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketTransferAcceleration.fromGetBucketTransferAcceleration(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketTransferAcceleration.toGetBucketTransferAcceleration(output);
    }

    public static CompletableFuture<GetBucketTransferAccelerationResult> getBucketTransferAccelerationAsync(ClientImpl impl, GetBucketTransferAccelerationRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketTransferAcceleration.fromGetBucketTransferAcceleration(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketTransferAcceleration::toGetBucketTransferAcceleration);
    }


}