package com.aliyun.sdk.service.oss2.tables.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.tables.models.*;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableBucketBasic;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

public final class TableBucketBasic {

    // createTableBucket - sync
    public static CreateTableBucketResult createTableBucket(ClientImpl impl, CreateTableBucketRequest request, OperationOptions options) {
        OperationInput input = SerdeTableBucketBasic.fromCreateTableBucket(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableBucketBasic.toCreateTableBucket(output);
    }

    // createTableBucket - async
    public static CompletableFuture<CreateTableBucketResult> createTableBucketAsync(ClientImpl impl, CreateTableBucketRequest request, OperationOptions options) {
        OperationInput input = SerdeTableBucketBasic.fromCreateTableBucket(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableBucketBasic::toCreateTableBucket);
    }

    // deleteTableBucket - sync
    public static DeleteTableBucketResult deleteTableBucket(ClientImpl impl, DeleteTableBucketRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        OperationInput input = SerdeTableBucketBasic.fromDeleteTableBucket(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableBucketBasic.toDeleteTableBucket(output);
    }

    // deleteTableBucket - async
    public static CompletableFuture<DeleteTableBucketResult> deleteTableBucketAsync(ClientImpl impl, DeleteTableBucketRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        OperationInput input = SerdeTableBucketBasic.fromDeleteTableBucket(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableBucketBasic::toDeleteTableBucket);
    }

    // getTableBucket - sync
    public static GetTableBucketResult getTableBucket(ClientImpl impl, GetTableBucketRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        OperationInput input = SerdeTableBucketBasic.fromGetTableBucket(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableBucketBasic.toGetTableBucket(output);
    }

    // getTableBucket - async
    public static CompletableFuture<GetTableBucketResult> getTableBucketAsync(ClientImpl impl, GetTableBucketRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        OperationInput input = SerdeTableBucketBasic.fromGetTableBucket(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableBucketBasic::toGetTableBucket);
    }

    // listTableBuckets - sync
    public static ListTableBucketsResult listTableBuckets(ClientImpl impl, ListTableBucketsRequest request, OperationOptions options) {
        OperationInput input = SerdeTableBucketBasic.fromListTableBuckets(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableBucketBasic.toListTableBuckets(output);
    }

    // listTableBuckets - async
    public static CompletableFuture<ListTableBucketsResult> listTableBucketsAsync(ClientImpl impl, ListTableBucketsRequest request, OperationOptions options) {
        OperationInput input = SerdeTableBucketBasic.fromListTableBuckets(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableBucketBasic::toListTableBuckets);
    }
}
