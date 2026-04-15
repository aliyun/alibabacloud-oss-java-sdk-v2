package com.aliyun.sdk.service.oss2.tables.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.tables.models.*;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableBasic;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

public final class TableBasic {

    // createTable - sync
    public static CreateTableResult createTable(ClientImpl impl, CreateTableRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        OperationInput input = SerdeTableBasic.fromCreateTable(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableBasic.toCreateTable(output);
    }

    // createTable - async
    public static CompletableFuture<CreateTableResult> createTableAsync(ClientImpl impl, CreateTableRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        OperationInput input = SerdeTableBasic.fromCreateTable(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableBasic::toCreateTable);
    }

    // deleteTable - sync
    public static DeleteTableResult deleteTable(ClientImpl impl, DeleteTableRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        requireNonNull(request.name(), "request.name is required");
        OperationInput input = SerdeTableBasic.fromDeleteTable(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableBasic.toDeleteTable(output);
    }

    // deleteTable - async
    public static CompletableFuture<DeleteTableResult> deleteTableAsync(ClientImpl impl, DeleteTableRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        requireNonNull(request.name(), "request.name is required");
        OperationInput input = SerdeTableBasic.fromDeleteTable(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableBasic::toDeleteTable);
    }

    // getTable - sync
    public static GetTableResult getTable(ClientImpl impl, GetTableRequest request, OperationOptions options) {
        if (request.tableBucketARN() != null) {
            requireNonNull(request.namespace(), "request.namespace is required");
            requireNonNull(request.name(), "request.name is required");
        } else {
            requireNonNull(request.tableArn(), "request.tableArn is required");
        }
        OperationInput input = SerdeTableBasic.fromGetTable(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableBasic.toGetTable(output);
    }

    // getTable - async
    public static CompletableFuture<GetTableResult> getTableAsync(ClientImpl impl, GetTableRequest request, OperationOptions options) {
        if (request.tableBucketARN() != null) {
            requireNonNull(request.namespace(), "request.namespace is required");
            requireNonNull(request.name(), "request.name is required");
        } else {
            requireNonNull(request.tableArn(), "request.tableArn is required");
        }
        OperationInput input = SerdeTableBasic.fromGetTable(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableBasic::toGetTable);
    }

    // listTables - sync
    public static ListTablesResult listTables(ClientImpl impl, ListTablesRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        OperationInput input = SerdeTableBasic.fromListTables(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableBasic.toListTables(output);
    }

    // listTables - async
    public static CompletableFuture<ListTablesResult> listTablesAsync(ClientImpl impl, ListTablesRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        OperationInput input = SerdeTableBasic.fromListTables(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableBasic::toListTables);
    }

    // renameTable - sync
    public static RenameTableResult renameTable(ClientImpl impl, RenameTableRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        requireNonNull(request.name(), "request.name is required");
        OperationInput input = SerdeTableBasic.fromRenameTable(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableBasic.toRenameTable(output);
    }

    // renameTable - async
    public static CompletableFuture<RenameTableResult> renameTableAsync(ClientImpl impl, RenameTableRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        requireNonNull(request.name(), "request.name is required");
        OperationInput input = SerdeTableBasic.fromRenameTable(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableBasic::toRenameTable);
    }
}
