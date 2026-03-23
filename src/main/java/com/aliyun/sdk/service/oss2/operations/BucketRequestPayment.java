package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.*;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class BucketRequestPayment {


    public static PutBucketRequestPaymentResult putBucketRequestPayment(ClientImpl impl, PutBucketRequestPaymentRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketRequestPayment.fromPutBucketRequestPayment(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketRequestPayment.toPutBucketRequestPayment(output);
    }

    public static CompletableFuture<PutBucketRequestPaymentResult> putBucketRequestPaymentAsync(ClientImpl impl, PutBucketRequestPaymentRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketRequestPayment.fromPutBucketRequestPayment(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketRequestPayment::toPutBucketRequestPayment);
    }


    public static GetBucketRequestPaymentResult getBucketRequestPayment(ClientImpl impl, GetBucketRequestPaymentRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketRequestPayment.fromGetBucketRequestPayment(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketRequestPayment.toGetBucketRequestPayment(output);
    }

    public static CompletableFuture<GetBucketRequestPaymentResult> getBucketRequestPaymentAsync(ClientImpl impl, GetBucketRequestPaymentRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketRequestPayment.fromGetBucketRequestPayment(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketRequestPayment::toGetBucketRequestPayment);
    }


}