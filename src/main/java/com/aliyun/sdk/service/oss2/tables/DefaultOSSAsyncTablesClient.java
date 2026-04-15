package com.aliyun.sdk.service.oss2.tables;

import com.aliyun.sdk.service.oss2.*;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.tables.models.*;
import com.aliyun.sdk.service.oss2.tables.operations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Internal implementation of {@link OSSAsyncTablesClient}.
 */
public class DefaultOSSAsyncTablesClient implements OSSAsyncTablesClient {

    private final ClientImpl clientImpl;

    public DefaultOSSAsyncTablesClient(ClientConfiguration config) {
        this(config, new ArrayList<>());
    }

    @SafeVarargs
    public DefaultOSSAsyncTablesClient(ClientConfiguration config, Function<ClientOptions, ClientOptions>... optFns) {
        this(config, Arrays.asList(optFns));
    }

    private DefaultOSSAsyncTablesClient(ClientConfiguration config, Collection<Function<ClientOptions, ClientOptions>> optFns) {
        this.clientImpl = new ClientImpl(config, optFns);
    }

    @Override
    public void close() throws Exception {
        this.clientImpl.close();
    }

    @Override
    public CompletableFuture<OperationOutput> invokeOperationAsync(OperationInput input, OperationOptions opts) {
        return this.clientImpl.executeAsync(input, opts);
    }

    // namespace api
    @Override
    public CompletableFuture<CreateNamespaceResult> createNamespaceAsync(CreateNamespaceRequest request, OperationOptions options) {
        return NamespaceBasic.createNamespaceAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<DeleteNamespaceResult> deleteNamespaceAsync(DeleteNamespaceRequest request, OperationOptions options) {
        return NamespaceBasic.deleteNamespaceAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetNamespaceResult> getNamespaceAsync(GetNamespaceRequest request, OperationOptions options) {
        return NamespaceBasic.getNamespaceAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<ListNamespacesResult> listNamespacesAsync(ListNamespacesRequest request, OperationOptions options) {
        return NamespaceBasic.listNamespacesAsync(this.clientImpl, request, options);
    }

    // table bucket api
    @Override
    public CompletableFuture<CreateTableBucketResult> createTableBucketAsync(CreateTableBucketRequest request, OperationOptions options) {
        return TableBucketBasic.createTableBucketAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<DeleteTableBucketResult> deleteTableBucketAsync(DeleteTableBucketRequest request, OperationOptions options) {
        return TableBucketBasic.deleteTableBucketAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetTableBucketResult> getTableBucketAsync(GetTableBucketRequest request, OperationOptions options) {
        return TableBucketBasic.getTableBucketAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<ListTableBucketsResult> listTableBucketsAsync(ListTableBucketsRequest request, OperationOptions options) {
        return TableBucketBasic.listTableBucketsAsync(this.clientImpl, request, options);
    }

    // table bucket config api
    @Override
    public CompletableFuture<DeleteTableBucketEncryptionResult> deleteTableBucketEncryptionAsync(DeleteTableBucketEncryptionRequest request, OperationOptions options) {
        return TableBucketConfigBasic.deleteTableBucketEncryptionAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetTableBucketEncryptionResult> getTableBucketEncryptionAsync(GetTableBucketEncryptionRequest request, OperationOptions options) {
        return TableBucketConfigBasic.getTableBucketEncryptionAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<PutTableBucketEncryptionResult> putTableBucketEncryptionAsync(PutTableBucketEncryptionRequest request, OperationOptions options) {
        return TableBucketConfigBasic.putTableBucketEncryptionAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetTableBucketMaintenanceConfigurationResult> getTableBucketMaintenanceConfigurationAsync(GetTableBucketMaintenanceConfigurationRequest request, OperationOptions options) {
        return TableBucketConfigBasic.getTableBucketMaintenanceConfigurationAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<PutTableBucketMaintenanceConfigurationResult> putTableBucketMaintenanceConfigurationAsync(PutTableBucketMaintenanceConfigurationRequest request, OperationOptions options) {
        return TableBucketConfigBasic.putTableBucketMaintenanceConfigurationAsync(this.clientImpl, request, options);
    }


    @Override
    public CompletableFuture<DeleteTableBucketPolicyResult> deleteTableBucketPolicyAsync(DeleteTableBucketPolicyRequest request, OperationOptions options) {
        return TableBucketConfigBasic.deleteTableBucketPolicyAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetTableBucketPolicyResult> getTableBucketPolicyAsync(GetTableBucketPolicyRequest request, OperationOptions options) {
        return TableBucketConfigBasic.getTableBucketPolicyAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<PutTableBucketPolicyResult> putTableBucketPolicyAsync(PutTableBucketPolicyRequest request, OperationOptions options) {
        return TableBucketConfigBasic.putTableBucketPolicyAsync(this.clientImpl, request, options);
    }

    // table api
    @Override
    public CompletableFuture<CreateTableResult> createTableAsync(CreateTableRequest request, OperationOptions options) {
        return TableBasic.createTableAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<DeleteTableResult> deleteTableAsync(DeleteTableRequest request, OperationOptions options) {
        return TableBasic.deleteTableAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetTableResult> getTableAsync(GetTableRequest request, OperationOptions options) {
        return TableBasic.getTableAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<ListTablesResult> listTablesAsync(ListTablesRequest request, OperationOptions options) {
        return TableBasic.listTablesAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<RenameTableResult> renameTableAsync(RenameTableRequest request, OperationOptions options) {
        return TableBasic.renameTableAsync(this.clientImpl, request, options);
    }

    // table config api
    @Override
    public CompletableFuture<GetTableEncryptionResult> getTableEncryptionAsync(GetTableEncryptionRequest request, OperationOptions options) {
        return TableConfigBasic.getTableEncryptionAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetTableMaintenanceConfigurationResult> getTableMaintenanceConfigurationAsync(GetTableMaintenanceConfigurationRequest request, OperationOptions options) {
        return TableConfigBasic.getTableMaintenanceConfigurationAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<PutTableMaintenanceConfigurationResult> putTableMaintenanceConfigurationAsync(PutTableMaintenanceConfigurationRequest request, OperationOptions options) {
        return TableConfigBasic.putTableMaintenanceConfigurationAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetTableMaintenanceJobStatusResult> getTableMaintenanceJobStatusAsync(GetTableMaintenanceJobStatusRequest request, OperationOptions options) {
        return TableConfigBasic.getTableMaintenanceJobStatusAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetTableMetadataLocationResult> getTableMetadataLocationAsync(GetTableMetadataLocationRequest request, OperationOptions options) {
        return TableConfigBasic.getTableMetadataLocationAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<UpdateTableMetadataLocationResult> updateTableMetadataLocationAsync(UpdateTableMetadataLocationRequest request, OperationOptions options) {
        return TableConfigBasic.updateTableMetadataLocationAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<DeleteTablePolicyResult> deleteTablePolicyAsync(DeleteTablePolicyRequest request, OperationOptions options) {
        return TableConfigBasic.deleteTablePolicyAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetTablePolicyResult> getTablePolicyAsync(GetTablePolicyRequest request, OperationOptions options) {
        return TableConfigBasic.getTablePolicyAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<PutTablePolicyResult> putTablePolicyAsync(PutTablePolicyRequest request, OperationOptions options) {
        return TableConfigBasic.putTablePolicyAsync(this.clientImpl, request, options);
    }

}
