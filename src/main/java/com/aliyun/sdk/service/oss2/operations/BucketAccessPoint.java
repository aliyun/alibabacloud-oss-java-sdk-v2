package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.*;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class BucketAccessPoint {


    public static ListAccessPointsResult listAccessPoints(ClientImpl impl, ListAccessPointsRequest request, OperationOptions options) {
        

        OperationInput input = SerdeAccessPoint.fromListAccessPoints(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAccessPoint.toListAccessPoints(output);
    }

    public static CompletableFuture<ListAccessPointsResult> listAccessPointsAsync(ClientImpl impl, ListAccessPointsRequest request, OperationOptions options) {
        
        
        OperationInput input = SerdeAccessPoint.fromListAccessPoints(request);
        return impl.executeAsync(input, options).thenApply(SerdeAccessPoint::toListAccessPoints);
    }


    public static GetAccessPointResult getAccessPoint(ClientImpl impl, GetAccessPointRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeAccessPoint.fromGetAccessPoint(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAccessPoint.toGetAccessPoint(output);
    }

    public static CompletableFuture<GetAccessPointResult> getAccessPointAsync(ClientImpl impl, GetAccessPointRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeAccessPoint.fromGetAccessPoint(request);
        return impl.executeAsync(input, options).thenApply(SerdeAccessPoint::toGetAccessPoint);
    }


    public static GetAccessPointPolicyResult getAccessPointPolicy(ClientImpl impl, GetAccessPointPolicyRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeAccessPoint.fromGetAccessPointPolicy(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAccessPoint.toGetAccessPointPolicy(output);
    }

    public static CompletableFuture<GetAccessPointPolicyResult> getAccessPointPolicyAsync(ClientImpl impl, GetAccessPointPolicyRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeAccessPoint.fromGetAccessPointPolicy(request);
        return impl.executeAsync(input, options).thenApply(SerdeAccessPoint::toGetAccessPointPolicy);
    }


    public static DeleteAccessPointPolicyResult deleteAccessPointPolicy(ClientImpl impl, DeleteAccessPointPolicyRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeAccessPoint.fromDeleteAccessPointPolicy(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAccessPoint.toDeleteAccessPointPolicy(output);
    }

    public static CompletableFuture<DeleteAccessPointPolicyResult> deleteAccessPointPolicyAsync(ClientImpl impl, DeleteAccessPointPolicyRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeAccessPoint.fromDeleteAccessPointPolicy(request);
        return impl.executeAsync(input, options).thenApply(SerdeAccessPoint::toDeleteAccessPointPolicy);
    }


    public static PutAccessPointPolicyResult putAccessPointPolicy(ClientImpl impl, PutAccessPointPolicyRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeAccessPoint.fromPutAccessPointPolicy(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAccessPoint.toPutAccessPointPolicy(output);
    }

    public static CompletableFuture<PutAccessPointPolicyResult> putAccessPointPolicyAsync(ClientImpl impl, PutAccessPointPolicyRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeAccessPoint.fromPutAccessPointPolicy(request);
        return impl.executeAsync(input, options).thenApply(SerdeAccessPoint::toPutAccessPointPolicy);
    }


    public static DeleteAccessPointResult deleteAccessPoint(ClientImpl impl, DeleteAccessPointRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeAccessPoint.fromDeleteAccessPoint(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAccessPoint.toDeleteAccessPoint(output);
    }

    public static CompletableFuture<DeleteAccessPointResult> deleteAccessPointAsync(ClientImpl impl, DeleteAccessPointRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeAccessPoint.fromDeleteAccessPoint(request);
        return impl.executeAsync(input, options).thenApply(SerdeAccessPoint::toDeleteAccessPoint);
    }


    public static CreateAccessPointResult createAccessPoint(ClientImpl impl, CreateAccessPointRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeAccessPoint.fromCreateAccessPoint(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAccessPoint.toCreateAccessPoint(output);
    }

    public static CompletableFuture<CreateAccessPointResult> createAccessPointAsync(ClientImpl impl, CreateAccessPointRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeAccessPoint.fromCreateAccessPoint(request);
        return impl.executeAsync(input, options).thenApply(SerdeAccessPoint::toCreateAccessPoint);
    }


}