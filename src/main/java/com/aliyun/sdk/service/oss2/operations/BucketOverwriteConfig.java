package com.aliyun.sdk.service.oss2.operations;


import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.*;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

public final class BucketOverwriteConfig {


    public static PutBucketOverwriteConfigResult putBucketOverwriteConfig(ClientImpl impl, PutBucketOverwriteConfigRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketOverwriteConfig.fromPutBucketOverwriteConfig(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketOverwriteConfig.toPutBucketOverwriteConfig(output);
    }

    public static CompletableFuture<PutBucketOverwriteConfigResult> putBucketOverwriteConfigAsync(ClientImpl impl, PutBucketOverwriteConfigRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketOverwriteConfig.fromPutBucketOverwriteConfig(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketOverwriteConfig::toPutBucketOverwriteConfig);
    }


    public static GetBucketOverwriteConfigResult getBucketOverwriteConfig(ClientImpl impl, GetBucketOverwriteConfigRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketOverwriteConfig.fromGetBucketOverwriteConfig(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketOverwriteConfig.toGetBucketOverwriteConfig(output);
    }

    public static CompletableFuture<GetBucketOverwriteConfigResult> getBucketOverwriteConfigAsync(ClientImpl impl, GetBucketOverwriteConfigRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketOverwriteConfig.fromGetBucketOverwriteConfig(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketOverwriteConfig::toGetBucketOverwriteConfig);
    }


    public static DeleteBucketOverwriteConfigResult deleteBucketOverwriteConfig(ClientImpl impl, DeleteBucketOverwriteConfigRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketOverwriteConfig.fromDeleteBucketOverwriteConfig(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketOverwriteConfig.toDeleteBucketOverwriteConfig(output);
    }

    public static CompletableFuture<DeleteBucketOverwriteConfigResult> deleteBucketOverwriteConfigAsync(ClientImpl impl, DeleteBucketOverwriteConfigRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketOverwriteConfig.fromDeleteBucketOverwriteConfig(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketOverwriteConfig::toDeleteBucketOverwriteConfig);
    }


}