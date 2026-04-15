package com.aliyun.sdk.service.oss2.tables.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.tables.models.*;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableBucketConfigBasic;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

public final class TableBucketConfigBasic {

    // deleteTableBucketEncryption - sync
    public static DeleteTableBucketEncryptionResult deleteTableBucketEncryption(ClientImpl impl, DeleteTableBucketEncryptionRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        OperationInput input = SerdeTableBucketConfigBasic.fromDeleteTableBucketEncryption(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableBucketConfigBasic.toDeleteTableBucketEncryption(output);
    }

    // deleteTableBucketEncryption - async
    public static CompletableFuture<DeleteTableBucketEncryptionResult> deleteTableBucketEncryptionAsync(ClientImpl impl, DeleteTableBucketEncryptionRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        OperationInput input = SerdeTableBucketConfigBasic.fromDeleteTableBucketEncryption(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableBucketConfigBasic::toDeleteTableBucketEncryption);
    }

    // getTableBucketEncryption - sync
    public static GetTableBucketEncryptionResult getTableBucketEncryption(ClientImpl impl, GetTableBucketEncryptionRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        OperationInput input = SerdeTableBucketConfigBasic.fromGetTableBucketEncryption(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableBucketConfigBasic.toGetTableBucketEncryption(output);
    }

    // getTableBucketEncryption - async
    public static CompletableFuture<GetTableBucketEncryptionResult> getTableBucketEncryptionAsync(ClientImpl impl, GetTableBucketEncryptionRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        OperationInput input = SerdeTableBucketConfigBasic.fromGetTableBucketEncryption(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableBucketConfigBasic::toGetTableBucketEncryption);
    }

    // putTableBucketEncryption - sync
    public static PutTableBucketEncryptionResult putTableBucketEncryption(ClientImpl impl, PutTableBucketEncryptionRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        OperationInput input = SerdeTableBucketConfigBasic.fromPutTableBucketEncryption(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableBucketConfigBasic.toPutTableBucketEncryption(output);
    }

    // putTableBucketEncryption - async
    public static CompletableFuture<PutTableBucketEncryptionResult> putTableBucketEncryptionAsync(ClientImpl impl, PutTableBucketEncryptionRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        OperationInput input = SerdeTableBucketConfigBasic.fromPutTableBucketEncryption(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableBucketConfigBasic::toPutTableBucketEncryption);
    }

    // getTableBucketMaintenanceConfiguration - sync
    public static GetTableBucketMaintenanceConfigurationResult getTableBucketMaintenanceConfiguration(ClientImpl impl, GetTableBucketMaintenanceConfigurationRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        OperationInput input = SerdeTableBucketConfigBasic.fromGetTableBucketMaintenanceConfiguration(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableBucketConfigBasic.toGetTableBucketMaintenanceConfiguration(output);
    }

    // getTableBucketMaintenanceConfiguration - async
    public static CompletableFuture<GetTableBucketMaintenanceConfigurationResult> getTableBucketMaintenanceConfigurationAsync(ClientImpl impl, GetTableBucketMaintenanceConfigurationRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        OperationInput input = SerdeTableBucketConfigBasic.fromGetTableBucketMaintenanceConfiguration(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableBucketConfigBasic::toGetTableBucketMaintenanceConfiguration);
    }

    // putTableBucketMaintenanceConfiguration - sync
    public static PutTableBucketMaintenanceConfigurationResult putTableBucketMaintenanceConfiguration(ClientImpl impl, PutTableBucketMaintenanceConfigurationRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        OperationInput input = SerdeTableBucketConfigBasic.fromPutTableBucketMaintenanceConfiguration(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableBucketConfigBasic.toPutTableBucketMaintenanceConfiguration(output);
    }

    // putTableBucketMaintenanceConfiguration - async
    public static CompletableFuture<PutTableBucketMaintenanceConfigurationResult> putTableBucketMaintenanceConfigurationAsync(ClientImpl impl, PutTableBucketMaintenanceConfigurationRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        OperationInput input = SerdeTableBucketConfigBasic.fromPutTableBucketMaintenanceConfiguration(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableBucketConfigBasic::toPutTableBucketMaintenanceConfiguration);
    }


    // deleteTableBucketPolicy - sync
    public static DeleteTableBucketPolicyResult deleteTableBucketPolicy(ClientImpl impl, DeleteTableBucketPolicyRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        OperationInput input = SerdeTableBucketConfigBasic.fromDeleteTableBucketPolicy(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableBucketConfigBasic.toDeleteTableBucketPolicy(output);
    }

    // deleteTableBucketPolicy - async
    public static CompletableFuture<DeleteTableBucketPolicyResult> deleteTableBucketPolicyAsync(ClientImpl impl, DeleteTableBucketPolicyRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        OperationInput input = SerdeTableBucketConfigBasic.fromDeleteTableBucketPolicy(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableBucketConfigBasic::toDeleteTableBucketPolicy);
    }

    // getTableBucketPolicy - sync
    public static GetTableBucketPolicyResult getTableBucketPolicy(ClientImpl impl, GetTableBucketPolicyRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        OperationInput input = SerdeTableBucketConfigBasic.fromGetTableBucketPolicy(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableBucketConfigBasic.toGetTableBucketPolicy(output);
    }

    // getTableBucketPolicy - async
    public static CompletableFuture<GetTableBucketPolicyResult> getTableBucketPolicyAsync(ClientImpl impl, GetTableBucketPolicyRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        OperationInput input = SerdeTableBucketConfigBasic.fromGetTableBucketPolicy(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableBucketConfigBasic::toGetTableBucketPolicy);
    }

    // putTableBucketPolicy - sync
    public static PutTableBucketPolicyResult putTableBucketPolicy(ClientImpl impl, PutTableBucketPolicyRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        OperationInput input = SerdeTableBucketConfigBasic.fromPutTableBucketPolicy(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeTableBucketConfigBasic.toPutTableBucketPolicy(output);
    }

    // putTableBucketPolicy - async
    public static CompletableFuture<PutTableBucketPolicyResult> putTableBucketPolicyAsync(ClientImpl impl, PutTableBucketPolicyRequest request, OperationOptions options) {
        requireNonNull(request.tableBucketARN(), "request.tableBucketARN is required");
        OperationInput input = SerdeTableBucketConfigBasic.fromPutTableBucketPolicy(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeTableBucketConfigBasic::toPutTableBucketPolicy);
    }
}
