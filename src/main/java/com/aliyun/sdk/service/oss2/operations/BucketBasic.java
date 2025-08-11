package com.aliyun.sdk.service.oss2.operations;


import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketBasic;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

public final class BucketBasic {


    public static GetBucketStatResult getBucketStat(ClientImpl impl, GetBucketStatRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketBasic.fromGetBucketStat(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketBasic.toGetBucketStat(output);
    }

    public static CompletableFuture<GetBucketStatResult> getBucketStatAsync(ClientImpl impl, GetBucketStatRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketBasic.fromGetBucketStat(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketBasic::toGetBucketStat);
    }


    public static PutBucketResult putBucket(ClientImpl impl, PutBucketRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketBasic.fromPutBucket(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketBasic.toPutBucket(output);
    }

    public static CompletableFuture<PutBucketResult> putBucketAsync(ClientImpl impl, PutBucketRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketBasic.fromPutBucket(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketBasic::toPutBucket);
    }


    public static DeleteBucketResult deleteBucket(ClientImpl impl, DeleteBucketRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketBasic.fromDeleteBucket(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketBasic.toDeleteBucket(output);
    }

    public static CompletableFuture<DeleteBucketResult> deleteBucketAsync(ClientImpl impl, DeleteBucketRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketBasic.fromDeleteBucket(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketBasic::toDeleteBucket);
    }


    public static ListObjectsResult listObjects(ClientImpl impl, ListObjectsRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketBasic.fromListObjects(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketBasic.toListObjects(output);
    }

    public static CompletableFuture<ListObjectsResult> listObjectsAsync(ClientImpl impl, ListObjectsRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketBasic.fromListObjects(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketBasic::toListObjects);
    }


    public static ListObjectsV2Result listObjectsV2(ClientImpl impl, ListObjectsV2Request request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketBasic.fromListObjectsV2(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketBasic.toListObjectsV2(output);
    }

    public static CompletableFuture<ListObjectsV2Result> listObjectsV2Async(ClientImpl impl, ListObjectsV2Request request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketBasic.fromListObjectsV2(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketBasic::toListObjectsV2);
    }


    public static GetBucketInfoResult getBucketInfo(ClientImpl impl, GetBucketInfoRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketBasic.fromGetBucketInfo(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketBasic.toGetBucketInfo(output);
    }

    public static CompletableFuture<GetBucketInfoResult> getBucketInfoAsync(ClientImpl impl, GetBucketInfoRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketBasic.fromGetBucketInfo(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketBasic::toGetBucketInfo);
    }


    public static GetBucketLocationResult getBucketLocation(ClientImpl impl, GetBucketLocationRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketBasic.fromGetBucketLocation(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketBasic.toGetBucketLocation(output);
    }

    public static CompletableFuture<GetBucketLocationResult> getBucketLocationAsync(ClientImpl impl, GetBucketLocationRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketBasic.fromGetBucketLocation(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketBasic::toGetBucketLocation);
    }


}