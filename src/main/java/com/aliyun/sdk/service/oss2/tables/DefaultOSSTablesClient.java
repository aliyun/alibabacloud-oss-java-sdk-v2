package com.aliyun.sdk.service.oss2.tables;

import com.aliyun.sdk.service.oss2.*;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.tables.models.*;
import com.aliyun.sdk.service.oss2.tables.operations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

/**
 * Internal implementation of {@link OSSTablesClient}.
 */
public class DefaultOSSTablesClient implements OSSTablesClient {
    final ClientImpl clientImpl;

    public DefaultOSSTablesClient(ClientConfiguration config) {
        this(config, new ArrayList<>());
    }

    @SafeVarargs
    public DefaultOSSTablesClient(ClientConfiguration config, Function<ClientOptions, ClientOptions>... optFns) {
        this(config, Arrays.asList(optFns));
    }

    private DefaultOSSTablesClient(ClientConfiguration config, Collection<Function<ClientOptions, ClientOptions>> optFns) {
        this.clientImpl = new ClientImpl(config, optFns);
    }

    @Override
    public OperationOutput invokeOperation(OperationInput input, OperationOptions opts) {
        return this.clientImpl.execute(input, opts);
    }

    // namespace api
    @Override
    public CreateNamespaceResult createNamespace(CreateNamespaceRequest request, OperationOptions options) {
        return NamespaceBasic.createNamespace(this.clientImpl, request, options);
    }

    @Override
    public DeleteNamespaceResult deleteNamespace(DeleteNamespaceRequest request, OperationOptions options) {
        return NamespaceBasic.deleteNamespace(this.clientImpl, request, options);
    }

    @Override
    public GetNamespaceResult getNamespace(GetNamespaceRequest request, OperationOptions options) {
        return NamespaceBasic.getNamespace(this.clientImpl, request, options);
    }

    @Override
    public ListNamespacesResult listNamespaces(ListNamespacesRequest request, OperationOptions options) {
        return NamespaceBasic.listNamespaces(this.clientImpl, request, options);
    }

    // table bucket api
    @Override
    public CreateTableBucketResult createTableBucket(CreateTableBucketRequest request, OperationOptions options) {
        return TableBucketBasic.createTableBucket(this.clientImpl, request, options);
    }

    @Override
    public DeleteTableBucketResult deleteTableBucket(DeleteTableBucketRequest request, OperationOptions options) {
        return TableBucketBasic.deleteTableBucket(this.clientImpl, request, options);
    }

    @Override
    public GetTableBucketResult getTableBucket(GetTableBucketRequest request, OperationOptions options) {
        return TableBucketBasic.getTableBucket(this.clientImpl, request, options);
    }

    @Override
    public ListTableBucketsResult listTableBuckets(ListTableBucketsRequest request, OperationOptions options) {
        return TableBucketBasic.listTableBuckets(this.clientImpl, request, options);
    }

    // table bucket config api
    @Override
    public DeleteTableBucketEncryptionResult deleteTableBucketEncryption(DeleteTableBucketEncryptionRequest request, OperationOptions options) {
        return TableBucketConfigBasic.deleteTableBucketEncryption(this.clientImpl, request, options);
    }

    @Override
    public GetTableBucketEncryptionResult getTableBucketEncryption(GetTableBucketEncryptionRequest request, OperationOptions options) {
        return TableBucketConfigBasic.getTableBucketEncryption(this.clientImpl, request, options);
    }

    @Override
    public PutTableBucketEncryptionResult putTableBucketEncryption(PutTableBucketEncryptionRequest request, OperationOptions options) {
        return TableBucketConfigBasic.putTableBucketEncryption(this.clientImpl, request, options);
    }

    @Override
    public GetTableBucketMaintenanceConfigurationResult getTableBucketMaintenanceConfiguration(GetTableBucketMaintenanceConfigurationRequest request, OperationOptions options) {
        return TableBucketConfigBasic.getTableBucketMaintenanceConfiguration(this.clientImpl, request, options);
    }

    @Override
    public PutTableBucketMaintenanceConfigurationResult putTableBucketMaintenanceConfiguration(PutTableBucketMaintenanceConfigurationRequest request, OperationOptions options) {
        return TableBucketConfigBasic.putTableBucketMaintenanceConfiguration(this.clientImpl, request, options);
    }

    @Override
    public DeleteTableBucketPolicyResult deleteTableBucketPolicy(DeleteTableBucketPolicyRequest request, OperationOptions options) {
        return TableBucketConfigBasic.deleteTableBucketPolicy(this.clientImpl, request, options);
    }

    @Override
    public GetTableBucketPolicyResult getTableBucketPolicy(GetTableBucketPolicyRequest request, OperationOptions options) {
        return TableBucketConfigBasic.getTableBucketPolicy(this.clientImpl, request, options);
    }

    @Override
    public PutTableBucketPolicyResult putTableBucketPolicy(PutTableBucketPolicyRequest request, OperationOptions options) {
        return TableBucketConfigBasic.putTableBucketPolicy(this.clientImpl, request, options);
    }

    // table api
    @Override
    public CreateTableResult createTable(CreateTableRequest request, OperationOptions options) {
        return TableBasic.createTable(this.clientImpl, request, options);
    }

    @Override
    public DeleteTableResult deleteTable(DeleteTableRequest request, OperationOptions options) {
        return TableBasic.deleteTable(this.clientImpl, request, options);
    }

    @Override
    public GetTableResult getTable(GetTableRequest request, OperationOptions options) {
        return TableBasic.getTable(this.clientImpl, request, options);
    }

    @Override
    public ListTablesResult listTables(ListTablesRequest request, OperationOptions options) {
        return TableBasic.listTables(this.clientImpl, request, options);
    }

    @Override
    public RenameTableResult renameTable(RenameTableRequest request, OperationOptions options) {
        return TableBasic.renameTable(this.clientImpl, request, options);
    }

    // table config api
    @Override
    public GetTableEncryptionResult getTableEncryption(GetTableEncryptionRequest request, OperationOptions options) {
        return TableConfigBasic.getTableEncryption(this.clientImpl, request, options);
    }

    @Override
    public GetTableMaintenanceConfigurationResult getTableMaintenanceConfiguration(GetTableMaintenanceConfigurationRequest request, OperationOptions options) {
        return TableConfigBasic.getTableMaintenanceConfiguration(this.clientImpl, request, options);
    }

    @Override
    public PutTableMaintenanceConfigurationResult putTableMaintenanceConfiguration(PutTableMaintenanceConfigurationRequest request, OperationOptions options) {
        return TableConfigBasic.putTableMaintenanceConfiguration(this.clientImpl, request, options);
    }

    @Override
    public GetTableMaintenanceJobStatusResult getTableMaintenanceJobStatus(GetTableMaintenanceJobStatusRequest request, OperationOptions options) {
        return TableConfigBasic.getTableMaintenanceJobStatus(this.clientImpl, request, options);
    }

    @Override
    public GetTableMetadataLocationResult getTableMetadataLocation(GetTableMetadataLocationRequest request, OperationOptions options) {
        return TableConfigBasic.getTableMetadataLocation(this.clientImpl, request, options);
    }

    @Override
    public UpdateTableMetadataLocationResult updateTableMetadataLocation(UpdateTableMetadataLocationRequest request, OperationOptions options) {
        return TableConfigBasic.updateTableMetadataLocation(this.clientImpl, request, options);
    }

    @Override
    public DeleteTablePolicyResult deleteTablePolicy(DeleteTablePolicyRequest request, OperationOptions options) {
        return TableConfigBasic.deleteTablePolicy(this.clientImpl, request, options);
    }

    @Override
    public GetTablePolicyResult getTablePolicy(GetTablePolicyRequest request, OperationOptions options) {
        return TableConfigBasic.getTablePolicy(this.clientImpl, request, options);
    }

    @Override
    public PutTablePolicyResult putTablePolicy(PutTablePolicyRequest request, OperationOptions options) {
        return TableConfigBasic.putTablePolicy(this.clientImpl, request, options);
    }

    @Override
    public void close() throws Exception {
        this.clientImpl.close();
    }
}
