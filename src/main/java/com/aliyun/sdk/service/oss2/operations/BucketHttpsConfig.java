package com.aliyun.sdk.service.oss2.operations;


import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.*;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

public final class BucketHttpsConfig {


    public static GetBucketHttpsConfigResult getBucketHttpsConfig(ClientImpl impl, GetBucketHttpsConfigRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketHttpsConfig.fromGetBucketHttpsConfig(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketHttpsConfig.toGetBucketHttpsConfig(output);
    }

    public static CompletableFuture<GetBucketHttpsConfigResult> getBucketHttpsConfigAsync(ClientImpl impl, GetBucketHttpsConfigRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketHttpsConfig.fromGetBucketHttpsConfig(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketHttpsConfig::toGetBucketHttpsConfig);
    }


    public static PutBucketHttpsConfigResult putBucketHttpsConfig(ClientImpl impl, PutBucketHttpsConfigRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketHttpsConfig.fromPutBucketHttpsConfig(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketHttpsConfig.toPutBucketHttpsConfig(output);
    }

    public static CompletableFuture<PutBucketHttpsConfigResult> putBucketHttpsConfigAsync(ClientImpl impl, PutBucketHttpsConfigRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketHttpsConfig.fromPutBucketHttpsConfig(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketHttpsConfig::toPutBucketHttpsConfig);
    }


}