package com.aliyun.sdk.service.oss2.operations;


import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.GetObjectAclRequest;
import com.aliyun.sdk.service.oss2.models.GetObjectAclResult;
import com.aliyun.sdk.service.oss2.models.PutObjectAclRequest;
import com.aliyun.sdk.service.oss2.models.PutObjectAclResult;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectAcl;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

public final class ObjectAcl {


    public static PutObjectAclResult putObjectAcl(ClientImpl impl, PutObjectAclRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.objectAcl(), "request.objectAcl is required");

        OperationInput input = SerdeObjectAcl.fromPutObjectAcl(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectAcl.toPutObjectAcl(output);
    }

    public static CompletableFuture<PutObjectAclResult> putObjectAclAsync(ClientImpl impl, PutObjectAclRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.objectAcl(), "request.objectAcl is required");

        OperationInput input = SerdeObjectAcl.fromPutObjectAcl(request);
        return impl.executeAsync(input, options).thenApply(SerdeObjectAcl::toPutObjectAcl);
    }


    public static GetObjectAclResult getObjectAcl(ClientImpl impl, GetObjectAclRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectAcl.fromGetObjectAcl(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectAcl.toGetObjectAcl(output);
    }

    public static CompletableFuture<GetObjectAclResult> getObjectAclAsync(ClientImpl impl, GetObjectAclRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectAcl.fromGetObjectAcl(request);
        return impl.executeAsync(input, options).thenApply(SerdeObjectAcl::toGetObjectAcl);
    }


}