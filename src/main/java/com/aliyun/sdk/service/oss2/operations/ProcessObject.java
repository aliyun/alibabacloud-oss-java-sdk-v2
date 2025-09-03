package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.SerdeProcessObject;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class ProcessObject {

    public static ProcessObjectResult processObject(ClientImpl impl, ProcessObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.process(), "request.process is required");

        OperationInput input = SerdeProcessObject.fromProcessObject(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeProcessObject.toProcessObject(output);
    }

    public static CompletableFuture<ProcessObjectResult> processObjectAsync(ClientImpl impl, ProcessObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.process(), "request.process is required");

        OperationInput input = SerdeProcessObject.fromProcessObject(request);
        return impl.executeAsync(input, options).thenApply(SerdeProcessObject::toProcessObject);
    }


    public static AsyncProcessObjectResult asyncProcessObject(ClientImpl impl, AsyncProcessObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.process(), "request.process is required");

        OperationInput input = SerdeProcessObject.fromAsyncProcessObject(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeProcessObject.toAsyncProcessObject(output);
    }

    public static CompletableFuture<AsyncProcessObjectResult> asyncProcessObjectAsync(ClientImpl impl, AsyncProcessObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.process(), "request.process is required");

        OperationInput input = SerdeProcessObject.fromAsyncProcessObject(request);
        return impl.executeAsync(input, options).thenApply(SerdeProcessObject::toAsyncProcessObject);
    }

}