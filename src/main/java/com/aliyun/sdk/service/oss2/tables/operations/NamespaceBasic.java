package com.aliyun.sdk.service.oss2.tables.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.tables.models.*;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeNamespaceBasic;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

public final class NamespaceBasic {

    // createNamespace - sync
    public static CreateNamespaceResult createNamespace(ClientImpl impl, CreateNamespaceRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        OperationInput input = SerdeNamespaceBasic.fromCreateNamespace(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeNamespaceBasic.toCreateNamespace(output);
    }

    // createNamespace - async
    public static CompletableFuture<CreateNamespaceResult> createNamespaceAsync(ClientImpl impl, CreateNamespaceRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        OperationInput input = SerdeNamespaceBasic.fromCreateNamespace(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeNamespaceBasic::toCreateNamespace);
    }

    // deleteNamespace - sync
    public static DeleteNamespaceResult deleteNamespace(ClientImpl impl, DeleteNamespaceRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        OperationInput input = SerdeNamespaceBasic.fromDeleteNamespace(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeNamespaceBasic.toDeleteNamespace(output);
    }

    // deleteNamespace - async
    public static CompletableFuture<DeleteNamespaceResult> deleteNamespaceAsync(ClientImpl impl, DeleteNamespaceRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        OperationInput input = SerdeNamespaceBasic.fromDeleteNamespace(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeNamespaceBasic::toDeleteNamespace);
    }

    // getNamespace - sync
    public static GetNamespaceResult getNamespace(ClientImpl impl, GetNamespaceRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        OperationInput input = SerdeNamespaceBasic.fromGetNamespace(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeNamespaceBasic.toGetNamespace(output);
    }

    // getNamespace - async
    public static CompletableFuture<GetNamespaceResult> getNamespaceAsync(ClientImpl impl, GetNamespaceRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        OperationInput input = SerdeNamespaceBasic.fromGetNamespace(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeNamespaceBasic::toGetNamespace);
    }

    // listNamespaces - sync
    public static ListNamespacesResult listNamespaces(ClientImpl impl, ListNamespacesRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        OperationInput input = SerdeNamespaceBasic.fromListNamespaces(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeNamespaceBasic.toListNamespaces(output);
    }

    // listNamespaces - async
    public static CompletableFuture<ListNamespacesResult> listNamespacesAsync(ClientImpl impl, ListNamespacesRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        OperationInput input = SerdeNamespaceBasic.fromListNamespaces(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeNamespaceBasic::toListNamespaces);
    }
}
