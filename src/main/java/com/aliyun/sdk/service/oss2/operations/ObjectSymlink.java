package com.aliyun.sdk.service.oss2.operations;


import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.GetSymlinkRequest;
import com.aliyun.sdk.service.oss2.models.GetSymlinkResult;
import com.aliyun.sdk.service.oss2.models.PutSymlinkRequest;
import com.aliyun.sdk.service.oss2.models.PutSymlinkResult;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectSymlink;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

public final class ObjectSymlink {


    public static PutSymlinkResult putSymlink(ClientImpl impl, PutSymlinkRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectSymlink.fromPutSymlink(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectSymlink.toPutSymlink(output);
    }

    public static CompletableFuture<PutSymlinkResult> putSymlinkAsync(ClientImpl impl, PutSymlinkRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectSymlink.fromPutSymlink(request);
        return impl.executeAsync(input, options).thenApply(SerdeObjectSymlink::toPutSymlink);
    }


    public static GetSymlinkResult getSymlink(ClientImpl impl, GetSymlinkRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectSymlink.fromGetSymlink(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectSymlink.toGetSymlink(output);
    }

    public static CompletableFuture<GetSymlinkResult> getSymlinkAsync(ClientImpl impl, GetSymlinkRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectSymlink.fromGetSymlink(request);
        return impl.executeAsync(input, options).thenApply(SerdeObjectSymlink::toGetSymlink);
    }


}