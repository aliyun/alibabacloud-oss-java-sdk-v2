package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketEncryption;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class BucketEncryption {


    public static PutBucketEncryptionResult putBucketEncryption(ClientImpl impl, PutBucketEncryptionRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketEncryption.fromPutBucketEncryption(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketEncryption.toPutBucketEncryption(output);
    }

    public static CompletableFuture<PutBucketEncryptionResult> putBucketEncryptionAsync(ClientImpl impl, PutBucketEncryptionRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketEncryption.fromPutBucketEncryption(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketEncryption::toPutBucketEncryption);
    }


    public static GetBucketEncryptionResult getBucketEncryption(ClientImpl impl, GetBucketEncryptionRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketEncryption.fromGetBucketEncryption(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketEncryption.toGetBucketEncryption(output);
    }

    public static CompletableFuture<GetBucketEncryptionResult> getBucketEncryptionAsync(ClientImpl impl, GetBucketEncryptionRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketEncryption.fromGetBucketEncryption(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketEncryption::toGetBucketEncryption);
    }


    public static DeleteBucketEncryptionResult deleteBucketEncryption(ClientImpl impl, DeleteBucketEncryptionRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketEncryption.fromDeleteBucketEncryption(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketEncryption.toDeleteBucketEncryption(output);
    }

    public static CompletableFuture<DeleteBucketEncryptionResult> deleteBucketEncryptionAsync(ClientImpl impl, DeleteBucketEncryptionRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketEncryption.fromDeleteBucketEncryption(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketEncryption::toDeleteBucketEncryption);
    }
}