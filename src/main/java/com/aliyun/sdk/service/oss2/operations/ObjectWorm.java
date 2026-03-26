package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectWorm;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;
import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;

/**
 * Object Worm operations for managing object retention and legal hold.
 */
public final class ObjectWorm {

    public static PutObjectRetentionResult putObjectRetention(ClientImpl impl, PutObjectRetentionRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectWorm.fromPutObjectRetention(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectWorm.toPutObjectRetention(output);
    }

    public static CompletableFuture<PutObjectRetentionResult> putObjectRetentionAsync(ClientImpl impl, PutObjectRetentionRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectWorm.fromPutObjectRetention(request);
        return impl.executeAsync(input, options).thenApply(SerdeObjectWorm::toPutObjectRetention);
    }


    public static GetObjectRetentionResult getObjectRetention(ClientImpl impl, GetObjectRetentionRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectWorm.fromGetObjectRetention(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectWorm.toGetObjectRetention(output);
    }

    public static CompletableFuture<GetObjectRetentionResult> getObjectRetentionAsync(ClientImpl impl, GetObjectRetentionRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectWorm.fromGetObjectRetention(request);
        return impl.executeAsync(input, options).thenApply(SerdeObjectWorm::toGetObjectRetention);
    }


    public static PutObjectLegalHoldResult putObjectLegalHold(ClientImpl impl, PutObjectLegalHoldRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectWorm.fromPutObjectLegalHold(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectWorm.toPutObjectLegalHold(output);
    }

    public static CompletableFuture<PutObjectLegalHoldResult> putObjectLegalHoldAsync(ClientImpl impl, PutObjectLegalHoldRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectWorm.fromPutObjectLegalHold(request);
        return impl.executeAsync(input, options).thenApply(SerdeObjectWorm::toPutObjectLegalHold);
    }


    public static GetObjectLegalHoldResult getObjectLegalHold(ClientImpl impl, GetObjectLegalHoldRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectWorm.fromGetObjectLegalHold(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectWorm.toGetObjectLegalHold(output);
    }

    public static CompletableFuture<GetObjectLegalHoldResult> getObjectLegalHoldAsync(ClientImpl impl, GetObjectLegalHoldRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectWorm.fromGetObjectLegalHold(request);
        return impl.executeAsync(input, options).thenApply(SerdeObjectWorm::toGetObjectLegalHold);
    }
}
