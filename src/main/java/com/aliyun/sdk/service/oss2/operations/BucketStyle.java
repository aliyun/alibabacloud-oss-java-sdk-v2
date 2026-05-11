package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.*;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class BucketStyle {


    public static PutStyleResult putStyle(ClientImpl impl, PutStyleRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketStyle.fromPutStyle(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketStyle.toPutStyle(output);
    }

    public static CompletableFuture<PutStyleResult> putStyleAsync(ClientImpl impl, PutStyleRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketStyle.fromPutStyle(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketStyle::toPutStyle);
    }


    public static ListStyleResult listStyle(ClientImpl impl, ListStyleRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketStyle.fromListStyle(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketStyle.toListStyle(output);
    }

    public static CompletableFuture<ListStyleResult> listStyleAsync(ClientImpl impl, ListStyleRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketStyle.fromListStyle(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketStyle::toListStyle);
    }


    public static GetStyleResult getStyle(ClientImpl impl, GetStyleRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketStyle.fromGetStyle(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketStyle.toGetStyle(output);
    }

    public static CompletableFuture<GetStyleResult> getStyleAsync(ClientImpl impl, GetStyleRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketStyle.fromGetStyle(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketStyle::toGetStyle);
    }


    public static DeleteStyleResult deleteStyle(ClientImpl impl, DeleteStyleRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketStyle.fromDeleteStyle(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketStyle.toDeleteStyle(output);
    }

    public static CompletableFuture<DeleteStyleResult> deleteStyleAsync(ClientImpl impl, DeleteStyleRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketStyle.fromDeleteStyle(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketStyle::toDeleteStyle);
    }


}