package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketObjectWormConfiguration;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

/**
 * Bucket Object Worm Configuration operations for managing object-level retention policy configuration.
 */
public final class BucketObjectWormConfiguration {

    public static PutBucketObjectWormConfigurationResult putBucketObjectWormConfiguration(ClientImpl impl, PutBucketObjectWormConfigurationRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketObjectWormConfiguration.fromPutBucketObjectWormConfiguration(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketObjectWormConfiguration.toPutBucketObjectWormConfiguration(output);
    }

    public static CompletableFuture<PutBucketObjectWormConfigurationResult> putBucketObjectWormConfigurationAsync(ClientImpl impl, PutBucketObjectWormConfigurationRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketObjectWormConfiguration.fromPutBucketObjectWormConfiguration(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketObjectWormConfiguration::toPutBucketObjectWormConfiguration);
    }


    public static GetBucketObjectWormConfigurationResult getBucketObjectWormConfiguration(ClientImpl impl, GetBucketObjectWormConfigurationRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketObjectWormConfiguration.fromGetBucketObjectWormConfiguration(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketObjectWormConfiguration.toGetBucketObjectWormConfiguration(output);
    }

    public static CompletableFuture<GetBucketObjectWormConfigurationResult> getBucketObjectWormConfigurationAsync(ClientImpl impl, GetBucketObjectWormConfigurationRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketObjectWormConfiguration.fromGetBucketObjectWormConfiguration(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketObjectWormConfiguration::toGetBucketObjectWormConfiguration);
    }
}
