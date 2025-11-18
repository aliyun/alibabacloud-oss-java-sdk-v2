package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketReplication;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class BucketReplication {


    public static PutBucketRtcResult putBucketRtc(ClientImpl impl, PutBucketRtcRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketReplication.fromPutBucketRtc(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketReplication.toPutBucketRtc(output);
    }

    public static CompletableFuture<PutBucketRtcResult> putBucketRtcAsync(ClientImpl impl, PutBucketRtcRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketReplication.fromPutBucketRtc(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketReplication::toPutBucketRtc);
    }


    public static PutBucketReplicationResult putBucketReplication(ClientImpl impl, PutBucketReplicationRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketReplication.fromPutBucketReplication(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketReplication.toPutBucketReplication(output);
    }

    public static CompletableFuture<PutBucketReplicationResult> putBucketReplicationAsync(ClientImpl impl, PutBucketReplicationRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketReplication.fromPutBucketReplication(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketReplication::toPutBucketReplication);
    }


    public static GetBucketReplicationResult getBucketReplication(ClientImpl impl, GetBucketReplicationRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketReplication.fromGetBucketReplication(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketReplication.toGetBucketReplication(output);
    }

    public static CompletableFuture<GetBucketReplicationResult> getBucketReplicationAsync(ClientImpl impl, GetBucketReplicationRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketReplication.fromGetBucketReplication(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketReplication::toGetBucketReplication);
    }


    public static GetBucketReplicationLocationResult getBucketReplicationLocation(ClientImpl impl, GetBucketReplicationLocationRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketReplication.fromGetBucketReplicationLocation(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketReplication.toGetBucketReplicationLocation(output);
    }

    public static CompletableFuture<GetBucketReplicationLocationResult> getBucketReplicationLocationAsync(ClientImpl impl, GetBucketReplicationLocationRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketReplication.fromGetBucketReplicationLocation(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketReplication::toGetBucketReplicationLocation);
    }


    public static GetBucketReplicationProgressResult getBucketReplicationProgress(ClientImpl impl, GetBucketReplicationProgressRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketReplication.fromGetBucketReplicationProgress(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketReplication.toGetBucketReplicationProgress(output);
    }

    public static CompletableFuture<GetBucketReplicationProgressResult> getBucketReplicationProgressAsync(ClientImpl impl, GetBucketReplicationProgressRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketReplication.fromGetBucketReplicationProgress(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketReplication::toGetBucketReplicationProgress);
    }


    public static DeleteBucketReplicationResult deleteBucketReplication(ClientImpl impl, DeleteBucketReplicationRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketReplication.fromDeleteBucketReplication(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketReplication.toDeleteBucketReplication(output);
    }

    public static CompletableFuture<DeleteBucketReplicationResult> deleteBucketReplicationAsync(ClientImpl impl, DeleteBucketReplicationRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketReplication.fromDeleteBucketReplication(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketReplication::toDeleteBucketReplication);
    }


}