package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.*;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class BucketMetaquery {


    public static GetMetaQueryStatusResult getMetaQueryStatus(ClientImpl impl, GetMetaQueryStatusRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketMetaquery.fromGetMetaQueryStatus(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketMetaquery.toGetMetaQueryStatus(output);
    }

    public static CompletableFuture<GetMetaQueryStatusResult> getMetaQueryStatusAsync(ClientImpl impl, GetMetaQueryStatusRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketMetaquery.fromGetMetaQueryStatus(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketMetaquery::toGetMetaQueryStatus);
    }


    public static CloseMetaQueryResult closeMetaQuery(ClientImpl impl, CloseMetaQueryRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketMetaquery.fromCloseMetaQuery(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketMetaquery.toCloseMetaQuery(output);
    }

    public static CompletableFuture<CloseMetaQueryResult> closeMetaQueryAsync(ClientImpl impl, CloseMetaQueryRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketMetaquery.fromCloseMetaQuery(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketMetaquery::toCloseMetaQuery);
    }


    public static DoMetaQueryResult doMetaQuery(ClientImpl impl, DoMetaQueryRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketMetaquery.fromDoMetaQuery(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketMetaquery.toDoMetaQuery(output);
    }

    public static CompletableFuture<DoMetaQueryResult> doMetaQueryAsync(ClientImpl impl, DoMetaQueryRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketMetaquery.fromDoMetaQuery(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketMetaquery::toDoMetaQuery);
    }


    public static OpenMetaQueryResult openMetaQuery(ClientImpl impl, OpenMetaQueryRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketMetaquery.fromOpenMetaQuery(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketMetaquery.toOpenMetaQuery(output);
    }

    public static CompletableFuture<OpenMetaQueryResult> openMetaQueryAsync(ClientImpl impl, OpenMetaQueryRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketMetaquery.fromOpenMetaQuery(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketMetaquery::toOpenMetaQuery);
    }


}