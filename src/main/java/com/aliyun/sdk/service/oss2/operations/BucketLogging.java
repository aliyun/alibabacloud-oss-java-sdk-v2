package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketLogging;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class BucketLogging {


    public static PutBucketLoggingResult putBucketLogging(ClientImpl impl, PutBucketLoggingRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketLogging.fromPutBucketLogging(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketLogging.toPutBucketLogging(output);
    }

    public static CompletableFuture<PutBucketLoggingResult> putBucketLoggingAsync(ClientImpl impl, PutBucketLoggingRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketLogging.fromPutBucketLogging(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketLogging::toPutBucketLogging);
    }


    public static GetBucketLoggingResult getBucketLogging(ClientImpl impl, GetBucketLoggingRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketLogging.fromGetBucketLogging(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketLogging.toGetBucketLogging(output);
    }

    public static CompletableFuture<GetBucketLoggingResult> getBucketLoggingAsync(ClientImpl impl, GetBucketLoggingRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketLogging.fromGetBucketLogging(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketLogging::toGetBucketLogging);
    }


    public static DeleteBucketLoggingResult deleteBucketLogging(ClientImpl impl, DeleteBucketLoggingRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketLogging.fromDeleteBucketLogging(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketLogging.toDeleteBucketLogging(output);
    }

    public static CompletableFuture<DeleteBucketLoggingResult> deleteBucketLoggingAsync(ClientImpl impl, DeleteBucketLoggingRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketLogging.fromDeleteBucketLogging(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketLogging::toDeleteBucketLogging);
    }


    public static PutUserDefinedLogFieldsConfigResult putUserDefinedLogFieldsConfig(ClientImpl impl, PutUserDefinedLogFieldsConfigRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketLogging.fromPutUserDefinedLogFieldsConfig(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketLogging.toPutUserDefinedLogFieldsConfig(output);
    }

    public static CompletableFuture<PutUserDefinedLogFieldsConfigResult> putUserDefinedLogFieldsConfigAsync(ClientImpl impl, PutUserDefinedLogFieldsConfigRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketLogging.fromPutUserDefinedLogFieldsConfig(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketLogging::toPutUserDefinedLogFieldsConfig);
    }


    public static GetUserDefinedLogFieldsConfigResult getUserDefinedLogFieldsConfig(ClientImpl impl, GetUserDefinedLogFieldsConfigRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketLogging.fromGetUserDefinedLogFieldsConfig(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketLogging.toGetUserDefinedLogFieldsConfig(output);
    }

    public static CompletableFuture<GetUserDefinedLogFieldsConfigResult> getUserDefinedLogFieldsConfigAsync(ClientImpl impl, GetUserDefinedLogFieldsConfigRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketLogging.fromGetUserDefinedLogFieldsConfig(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketLogging::toGetUserDefinedLogFieldsConfig);
    }


    public static DeleteUserDefinedLogFieldsConfigResult deleteUserDefinedLogFieldsConfig(ClientImpl impl, DeleteUserDefinedLogFieldsConfigRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketLogging.fromDeleteUserDefinedLogFieldsConfig(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketLogging.toDeleteUserDefinedLogFieldsConfig(output);
    }

    public static CompletableFuture<DeleteUserDefinedLogFieldsConfigResult> deleteUserDefinedLogFieldsConfigAsync(ClientImpl impl, DeleteUserDefinedLogFieldsConfigRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketLogging.fromDeleteUserDefinedLogFieldsConfig(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketLogging::toDeleteUserDefinedLogFieldsConfig);
    }


}