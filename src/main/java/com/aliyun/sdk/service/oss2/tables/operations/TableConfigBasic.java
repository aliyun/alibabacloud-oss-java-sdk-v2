package com.aliyun.sdk.service.oss2.tables.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.tables.models.*;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableConfigBasic;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

public final class TableConfigBasic {

    // getTableEncryption - sync
    public static GetTableEncryptionResult getTableEncryption(ClientImpl impl, GetTableEncryptionRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        requireNonNull(request.name(), "request.name is required");
        OperationInput input = SerdeTableConfigBasic.fromGetTableEncryption(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableConfigBasic.toGetTableEncryption(output);
    }

    // getTableEncryption - async
    public static CompletableFuture<GetTableEncryptionResult> getTableEncryptionAsync(ClientImpl impl, GetTableEncryptionRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        requireNonNull(request.name(), "request.name is required");
        OperationInput input = SerdeTableConfigBasic.fromGetTableEncryption(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableConfigBasic::toGetTableEncryption);
    }

    // getTableMaintenanceConfiguration - sync
    public static GetTableMaintenanceConfigurationResult getTableMaintenanceConfiguration(ClientImpl impl, GetTableMaintenanceConfigurationRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        requireNonNull(request.name(), "request.name is required");
        OperationInput input = SerdeTableConfigBasic.fromGetTableMaintenanceConfiguration(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableConfigBasic.toGetTableMaintenanceConfiguration(output);
    }

    // getTableMaintenanceConfiguration - async
    public static CompletableFuture<GetTableMaintenanceConfigurationResult> getTableMaintenanceConfigurationAsync(ClientImpl impl, GetTableMaintenanceConfigurationRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        requireNonNull(request.name(), "request.name is required");
        OperationInput input = SerdeTableConfigBasic.fromGetTableMaintenanceConfiguration(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableConfigBasic::toGetTableMaintenanceConfiguration);
    }

    // putTableMaintenanceConfiguration - sync
    public static PutTableMaintenanceConfigurationResult putTableMaintenanceConfiguration(ClientImpl impl, PutTableMaintenanceConfigurationRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        requireNonNull(request.name(), "request.name is required");
        OperationInput input = SerdeTableConfigBasic.fromPutTableMaintenanceConfiguration(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableConfigBasic.toPutTableMaintenanceConfiguration(output);
    }

    // putTableMaintenanceConfiguration - async
    public static CompletableFuture<PutTableMaintenanceConfigurationResult> putTableMaintenanceConfigurationAsync(ClientImpl impl, PutTableMaintenanceConfigurationRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        requireNonNull(request.name(), "request.name is required");
        OperationInput input = SerdeTableConfigBasic.fromPutTableMaintenanceConfiguration(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableConfigBasic::toPutTableMaintenanceConfiguration);
    }

    // getTableMaintenanceJobStatus - sync
    public static GetTableMaintenanceJobStatusResult getTableMaintenanceJobStatus(ClientImpl impl, GetTableMaintenanceJobStatusRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        requireNonNull(request.name(), "request.name is required");
        OperationInput input = SerdeTableConfigBasic.fromGetTableMaintenanceJobStatus(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableConfigBasic.toGetTableMaintenanceJobStatus(output);
    }

    // getTableMaintenanceJobStatus - async
    public static CompletableFuture<GetTableMaintenanceJobStatusResult> getTableMaintenanceJobStatusAsync(ClientImpl impl, GetTableMaintenanceJobStatusRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        requireNonNull(request.name(), "request.name is required");
        OperationInput input = SerdeTableConfigBasic.fromGetTableMaintenanceJobStatus(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableConfigBasic::toGetTableMaintenanceJobStatus);
    }

    // getTableMetadataLocation - sync
    public static GetTableMetadataLocationResult getTableMetadataLocation(ClientImpl impl, GetTableMetadataLocationRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        requireNonNull(request.name(), "request.name is required");
        OperationInput input = SerdeTableConfigBasic.fromGetTableMetadataLocation(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableConfigBasic.toGetTableMetadataLocation(output);
    }

    // getTableMetadataLocation - async
    public static CompletableFuture<GetTableMetadataLocationResult> getTableMetadataLocationAsync(ClientImpl impl, GetTableMetadataLocationRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        requireNonNull(request.name(), "request.name is required");
        OperationInput input = SerdeTableConfigBasic.fromGetTableMetadataLocation(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableConfigBasic::toGetTableMetadataLocation);
    }

    // updateTableMetadataLocation - sync
    public static UpdateTableMetadataLocationResult updateTableMetadataLocation(ClientImpl impl, UpdateTableMetadataLocationRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        requireNonNull(request.name(), "request.name is required");
        OperationInput input = SerdeTableConfigBasic.fromUpdateTableMetadataLocation(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableConfigBasic.toUpdateTableMetadataLocation(output);
    }

    // updateTableMetadataLocation - async
    public static CompletableFuture<UpdateTableMetadataLocationResult> updateTableMetadataLocationAsync(ClientImpl impl, UpdateTableMetadataLocationRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        requireNonNull(request.name(), "request.name is required");
        OperationInput input = SerdeTableConfigBasic.fromUpdateTableMetadataLocation(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableConfigBasic::toUpdateTableMetadataLocation);
    }

    // deleteTablePolicy - sync
    public static DeleteTablePolicyResult deleteTablePolicy(ClientImpl impl, DeleteTablePolicyRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        requireNonNull(request.name(), "request.name is required");
        OperationInput input = SerdeTableConfigBasic.fromDeleteTablePolicy(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableConfigBasic.toDeleteTablePolicy(output);
    }

    // deleteTablePolicy - async
    public static CompletableFuture<DeleteTablePolicyResult> deleteTablePolicyAsync(ClientImpl impl, DeleteTablePolicyRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        requireNonNull(request.name(), "request.name is required");
        OperationInput input = SerdeTableConfigBasic.fromDeleteTablePolicy(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableConfigBasic::toDeleteTablePolicy);
    }

    // getTablePolicy - sync
    public static GetTablePolicyResult getTablePolicy(ClientImpl impl, GetTablePolicyRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        requireNonNull(request.name(), "request.name is required");
        OperationInput input = SerdeTableConfigBasic.fromGetTablePolicy(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableConfigBasic.toGetTablePolicy(output);
    }

    // getTablePolicy - async
    public static CompletableFuture<GetTablePolicyResult> getTablePolicyAsync(ClientImpl impl, GetTablePolicyRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        requireNonNull(request.name(), "request.name is required");
        OperationInput input = SerdeTableConfigBasic.fromGetTablePolicy(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableConfigBasic::toGetTablePolicy);
    }

    // putTablePolicy - sync
    public static PutTablePolicyResult putTablePolicy(ClientImpl impl, PutTablePolicyRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        requireNonNull(request.name(), "request.name is required");
        OperationInput input = SerdeTableConfigBasic.fromPutTablePolicy(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableConfigBasic.toPutTablePolicy(output);
    }

    // putTablePolicy - async
    public static CompletableFuture<PutTablePolicyResult> putTablePolicyAsync(ClientImpl impl, PutTablePolicyRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        requireNonNull(request.namespace(), "request.namespace is required");
        requireNonNull(request.name(), "request.name is required");
        OperationInput input = SerdeTableConfigBasic.fromPutTablePolicy(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableConfigBasic::toPutTablePolicy);
    }

}
