package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketCname;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class BucketCname {


    public static PutCnameResult putCname(ClientImpl impl, PutCnameRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketCname.fromPutCname(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketCname.toPutCname(output);
    }

    public static CompletableFuture<PutCnameResult> putCnameAsync(ClientImpl impl, PutCnameRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketCname.fromPutCname(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketCname::toPutCname);
    }


    public static ListCnameResult listCname(ClientImpl impl, ListCnameRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketCname.fromListCname(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketCname.toListCname(output);
    }

    public static CompletableFuture<ListCnameResult> listCnameAsync(ClientImpl impl, ListCnameRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketCname.fromListCname(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketCname::toListCname);
    }


    public static DeleteCnameResult deleteCname(ClientImpl impl, DeleteCnameRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketCname.fromDeleteCname(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketCname.toDeleteCname(output);
    }

    public static CompletableFuture<DeleteCnameResult> deleteCnameAsync(ClientImpl impl, DeleteCnameRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketCname.fromDeleteCname(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketCname::toDeleteCname);
    }


    public static GetCnameTokenResult getCnameToken(ClientImpl impl, GetCnameTokenRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketCname.fromGetCnameToken(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketCname.toGetCnameToken(output);
    }

    public static CompletableFuture<GetCnameTokenResult> getCnameTokenAsync(ClientImpl impl, GetCnameTokenRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketCname.fromGetCnameToken(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketCname::toGetCnameToken);
    }


    public static CreateCnameTokenResult createCnameToken(ClientImpl impl, CreateCnameTokenRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketCname.fromCreateCnameToken(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketCname.toCreateCnameToken(output);
    }

    public static CompletableFuture<CreateCnameTokenResult> createCnameTokenAsync(ClientImpl impl, CreateCnameTokenRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketCname.fromCreateCnameToken(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketCname::toCreateCnameToken);
    }

}