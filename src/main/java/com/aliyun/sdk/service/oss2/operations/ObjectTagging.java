package com.aliyun.sdk.service.oss2.operations;


import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectTagging;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

public final class ObjectTagging {


    public static PutObjectTaggingResult putObjectTagging(ClientImpl impl, PutObjectTaggingRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.tagging(), "request.tagging is required");

        OperationInput input = SerdeObjectTagging.fromPutObjectTagging(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectTagging.toPutObjectTagging(output);
    }

    public static CompletableFuture<PutObjectTaggingResult> putObjectTaggingAsync(ClientImpl impl, PutObjectTaggingRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.tagging(), "request.tagging is required");

        OperationInput input = SerdeObjectTagging.fromPutObjectTagging(request);
        return impl.executeAsync(input, options).thenApply(SerdeObjectTagging::toPutObjectTagging);
    }


    public static GetObjectTaggingResult getObjectTagging(ClientImpl impl, GetObjectTaggingRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectTagging.fromGetObjectTagging(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectTagging.toGetObjectTagging(output);
    }

    public static CompletableFuture<GetObjectTaggingResult> getObjectTaggingAsync(ClientImpl impl, GetObjectTaggingRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectTagging.fromGetObjectTagging(request);
        return impl.executeAsync(input, options).thenApply(SerdeObjectTagging::toGetObjectTagging);
    }


    public static DeleteObjectTaggingResult deleteObjectTagging(ClientImpl impl, DeleteObjectTaggingRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectTagging.fromDeleteObjectTagging(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectTagging.toDeleteObjectTagging(output);
    }

    public static CompletableFuture<DeleteObjectTaggingResult> deleteObjectTaggingAsync(ClientImpl impl, DeleteObjectTaggingRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectTagging.fromDeleteObjectTagging(request);
        return impl.executeAsync(input, options).thenApply(SerdeObjectTagging::toDeleteObjectTagging);
    }


}