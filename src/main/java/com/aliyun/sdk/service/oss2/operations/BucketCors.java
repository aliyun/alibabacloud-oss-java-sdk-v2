package com.aliyun.sdk.service.oss2.operations;


import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketCors;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

public final class BucketCors {

    public static PutBucketCorsResult putBucketCors(ClientImpl impl, PutBucketCorsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.corsConfiguration(), "request.corsConfiguration is required");

        OperationInput input = SerdeBucketCors.fromPutBucketCors(request);

        OperationOutput output = impl.execute(input, options);

        return SerdeBucketCors.toPutBucketCors(output);
    }

    public static CompletableFuture<PutBucketCorsResult> putBucketCorsAsync(ClientImpl impl, PutBucketCorsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.corsConfiguration(), "request.corsConfiguration is required");

        OperationInput input = SerdeBucketCors.fromPutBucketCors(request);

        return impl.executeAsync(input, options).thenApply(SerdeBucketCors::toPutBucketCors);
    }

    public static GetBucketCorsResult getBucketCors(ClientImpl impl, GetBucketCorsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketCors.fromGetBucketCors(request);

        OperationOutput output = impl.execute(input, options);

        return SerdeBucketCors.toGetBucketCors(output);
    }

    public static CompletableFuture<GetBucketCorsResult> getBucketCorsAsync(ClientImpl impl, GetBucketCorsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketCors.fromGetBucketCors(request);

        return impl.executeAsync(input, options).thenApply(SerdeBucketCors::toGetBucketCors);
    }


    public static DeleteBucketCorsResult deleteBucketCors(ClientImpl impl, DeleteBucketCorsRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketCors.fromDeleteBucketCors(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketCors.toDeleteBucketCors(output);
    }

    public static CompletableFuture<DeleteBucketCorsResult> deleteBucketCorsAsync(ClientImpl impl, DeleteBucketCorsRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketCors.fromDeleteBucketCors(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketCors::toDeleteBucketCors);
    }


    public static OptionObjectResult optionObject(ClientImpl impl, OptionObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeBucketCors.fromOptionObject(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketCors.toOptionObject(output);
    }

    public static CompletableFuture<OptionObjectResult> optionObjectAsync(ClientImpl impl, OptionObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeBucketCors.fromOptionObject(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketCors::toOptionObject);
    }


}