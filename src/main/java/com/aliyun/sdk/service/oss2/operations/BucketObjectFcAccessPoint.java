package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.*;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class BucketObjectFcAccessPoint {


    public static CreateAccessPointForObjectProcessResult createAccessPointForObjectProcess(ClientImpl impl, CreateAccessPointForObjectProcessRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketObjectFcAccessPoint.fromCreateAccessPointForObjectProcess(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketObjectFcAccessPoint.toCreateAccessPointForObjectProcess(output);
    }

    public static CompletableFuture<CreateAccessPointForObjectProcessResult> createAccessPointForObjectProcessAsync(ClientImpl impl, CreateAccessPointForObjectProcessRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketObjectFcAccessPoint.fromCreateAccessPointForObjectProcess(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketObjectFcAccessPoint::toCreateAccessPointForObjectProcess);
    }


    public static GetAccessPointForObjectProcessResult getAccessPointForObjectProcess(ClientImpl impl, GetAccessPointForObjectProcessRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketObjectFcAccessPoint.fromGetAccessPointForObjectProcess(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketObjectFcAccessPoint.toGetAccessPointForObjectProcess(output);
    }

    public static CompletableFuture<GetAccessPointForObjectProcessResult> getAccessPointForObjectProcessAsync(ClientImpl impl, GetAccessPointForObjectProcessRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketObjectFcAccessPoint.fromGetAccessPointForObjectProcess(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketObjectFcAccessPoint::toGetAccessPointForObjectProcess);
    }


    public static ListAccessPointsForObjectProcessResult listAccessPointsForObjectProcess(ClientImpl impl, ListAccessPointsForObjectProcessRequest request, OperationOptions options) {
        

        OperationInput input = SerdeBucketObjectFcAccessPoint.fromListAccessPointsForObjectProcess(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketObjectFcAccessPoint.toListAccessPointsForObjectProcess(output);
    }

    public static CompletableFuture<ListAccessPointsForObjectProcessResult> listAccessPointsForObjectProcessAsync(ClientImpl impl, ListAccessPointsForObjectProcessRequest request, OperationOptions options) {
        
        
        OperationInput input = SerdeBucketObjectFcAccessPoint.fromListAccessPointsForObjectProcess(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketObjectFcAccessPoint::toListAccessPointsForObjectProcess);
    }


    public static DeleteAccessPointForObjectProcessResult deleteAccessPointForObjectProcess(ClientImpl impl, DeleteAccessPointForObjectProcessRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketObjectFcAccessPoint.fromDeleteAccessPointForObjectProcess(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketObjectFcAccessPoint.toDeleteAccessPointForObjectProcess(output);
    }

    public static CompletableFuture<DeleteAccessPointForObjectProcessResult> deleteAccessPointForObjectProcessAsync(ClientImpl impl, DeleteAccessPointForObjectProcessRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketObjectFcAccessPoint.fromDeleteAccessPointForObjectProcess(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketObjectFcAccessPoint::toDeleteAccessPointForObjectProcess);
    }


    public static GetAccessPointConfigForObjectProcessResult getAccessPointConfigForObjectProcess(ClientImpl impl, GetAccessPointConfigForObjectProcessRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketObjectFcAccessPoint.fromGetAccessPointConfigForObjectProcess(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketObjectFcAccessPoint.toGetAccessPointConfigForObjectProcess(output);
    }

    public static CompletableFuture<GetAccessPointConfigForObjectProcessResult> getAccessPointConfigForObjectProcessAsync(ClientImpl impl, GetAccessPointConfigForObjectProcessRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketObjectFcAccessPoint.fromGetAccessPointConfigForObjectProcess(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketObjectFcAccessPoint::toGetAccessPointConfigForObjectProcess);
    }


    public static PutAccessPointConfigForObjectProcessResult putAccessPointConfigForObjectProcess(ClientImpl impl, PutAccessPointConfigForObjectProcessRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketObjectFcAccessPoint.fromPutAccessPointConfigForObjectProcess(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketObjectFcAccessPoint.toPutAccessPointConfigForObjectProcess(output);
    }

    public static CompletableFuture<PutAccessPointConfigForObjectProcessResult> putAccessPointConfigForObjectProcessAsync(ClientImpl impl, PutAccessPointConfigForObjectProcessRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketObjectFcAccessPoint.fromPutAccessPointConfigForObjectProcess(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketObjectFcAccessPoint::toPutAccessPointConfigForObjectProcess);
    }


    public static PutAccessPointPolicyForObjectProcessResult putAccessPointPolicyForObjectProcess(ClientImpl impl, PutAccessPointPolicyForObjectProcessRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketObjectFcAccessPoint.fromPutAccessPointPolicyForObjectProcess(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketObjectFcAccessPoint.toPutAccessPointPolicyForObjectProcess(output);
    }

    public static CompletableFuture<PutAccessPointPolicyForObjectProcessResult> putAccessPointPolicyForObjectProcessAsync(ClientImpl impl, PutAccessPointPolicyForObjectProcessRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketObjectFcAccessPoint.fromPutAccessPointPolicyForObjectProcess(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketObjectFcAccessPoint::toPutAccessPointPolicyForObjectProcess);
    }


    public static GetAccessPointPolicyForObjectProcessResult getAccessPointPolicyForObjectProcess(ClientImpl impl, GetAccessPointPolicyForObjectProcessRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketObjectFcAccessPoint.fromGetAccessPointPolicyForObjectProcess(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketObjectFcAccessPoint.toGetAccessPointPolicyForObjectProcess(output);
    }

    public static CompletableFuture<GetAccessPointPolicyForObjectProcessResult> getAccessPointPolicyForObjectProcessAsync(ClientImpl impl, GetAccessPointPolicyForObjectProcessRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketObjectFcAccessPoint.fromGetAccessPointPolicyForObjectProcess(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketObjectFcAccessPoint::toGetAccessPointPolicyForObjectProcess);
    }


    public static DeleteAccessPointPolicyForObjectProcessResult deleteAccessPointPolicyForObjectProcess(ClientImpl impl, DeleteAccessPointPolicyForObjectProcessRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketObjectFcAccessPoint.fromDeleteAccessPointPolicyForObjectProcess(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketObjectFcAccessPoint.toDeleteAccessPointPolicyForObjectProcess(output);
    }

    public static CompletableFuture<DeleteAccessPointPolicyForObjectProcessResult> deleteAccessPointPolicyForObjectProcessAsync(ClientImpl impl, DeleteAccessPointPolicyForObjectProcessRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketObjectFcAccessPoint.fromDeleteAccessPointPolicyForObjectProcess(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketObjectFcAccessPoint::toDeleteAccessPointPolicyForObjectProcess);
    }


    public static WriteGetObjectResponseResult writeGetObjectResponse(ClientImpl impl, WriteGetObjectResponseRequest request, OperationOptions options) {
        
        requireNonNull(request.requestRoute(), "request.requestRoute is required");
        requireNonNull(request.requestToken(), "request.requestToken is required");
        requireNonNull(request.fwdStatus(), "request.fwdStatus is required");

        OperationInput input = SerdeBucketObjectFcAccessPoint.fromWriteGetObjectResponse(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketObjectFcAccessPoint.toWriteGetObjectResponse(output);
    }

    public static CompletableFuture<WriteGetObjectResponseResult> writeGetObjectResponseAsync(ClientImpl impl, WriteGetObjectResponseRequest request, OperationOptions options) {
        
        requireNonNull(request.requestRoute(), "request.requestRoute is required");
        requireNonNull(request.requestToken(), "request.requestToken is required");
        requireNonNull(request.fwdStatus(), "request.fwdStatus is required");
        
        OperationInput input = SerdeBucketObjectFcAccessPoint.fromWriteGetObjectResponse(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketObjectFcAccessPoint::toWriteGetObjectResponse);
    }


}