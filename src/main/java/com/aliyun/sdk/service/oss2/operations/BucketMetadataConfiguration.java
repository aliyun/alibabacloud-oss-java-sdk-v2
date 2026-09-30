package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketMetadataConfiguration;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

/**
 * Provides bucket metadata configuration operations.
 */
public final class BucketMetadataConfiguration {
    private BucketMetadataConfiguration() {
    }

    public static CreateBucketMetadataConfigurationResult createBucketMetadataConfiguration(
            ClientImpl impl, CreateBucketMetadataConfigurationRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.metadataConfiguration(), "request.metadataConfiguration is required");
        OperationInput input = SerdeBucketMetadataConfiguration.fromCreateBucketMetadataConfiguration(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketMetadataConfiguration.toCreateBucketMetadataConfiguration(output);
    }

    public static CompletableFuture<CreateBucketMetadataConfigurationResult> createBucketMetadataConfigurationAsync(
            ClientImpl impl, CreateBucketMetadataConfigurationRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.metadataConfiguration(), "request.metadataConfiguration is required");
        OperationInput input = SerdeBucketMetadataConfiguration.fromCreateBucketMetadataConfiguration(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeBucketMetadataConfiguration::toCreateBucketMetadataConfiguration);
    }

    public static GetBucketMetadataConfigurationResult getBucketMetadataConfiguration(
            ClientImpl impl, GetBucketMetadataConfigurationRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeBucketMetadataConfiguration.fromGetBucketMetadataConfiguration(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketMetadataConfiguration.toGetBucketMetadataConfiguration(output);
    }

    public static CompletableFuture<GetBucketMetadataConfigurationResult> getBucketMetadataConfigurationAsync(
            ClientImpl impl, GetBucketMetadataConfigurationRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeBucketMetadataConfiguration.fromGetBucketMetadataConfiguration(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeBucketMetadataConfiguration::toGetBucketMetadataConfiguration);
    }

    public static DeleteBucketMetadataConfigurationResult deleteBucketMetadataConfiguration(
            ClientImpl impl, DeleteBucketMetadataConfigurationRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeBucketMetadataConfiguration.fromDeleteBucketMetadataConfiguration(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketMetadataConfiguration.toDeleteBucketMetadataConfiguration(output);
    }

    public static CompletableFuture<DeleteBucketMetadataConfigurationResult> deleteBucketMetadataConfigurationAsync(
            ClientImpl impl, DeleteBucketMetadataConfigurationRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeBucketMetadataConfiguration.fromDeleteBucketMetadataConfiguration(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeBucketMetadataConfiguration::toDeleteBucketMetadataConfiguration);
    }

    public static UpdateBucketMetadataInventoryTableConfigurationResult updateBucketMetadataInventoryTableConfiguration(
            ClientImpl impl, UpdateBucketMetadataInventoryTableConfigurationRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.inventoryTableConfiguration(), "request.inventoryTableConfiguration is required");
        OperationInput input = SerdeBucketMetadataConfiguration.fromUpdateBucketMetadataInventoryTableConfiguration(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketMetadataConfiguration.toUpdateBucketMetadataInventoryTableConfiguration(output);
    }

    public static CompletableFuture<UpdateBucketMetadataInventoryTableConfigurationResult>
    updateBucketMetadataInventoryTableConfigurationAsync(ClientImpl impl,
            UpdateBucketMetadataInventoryTableConfigurationRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.inventoryTableConfiguration(), "request.inventoryTableConfiguration is required");
        OperationInput input = SerdeBucketMetadataConfiguration.fromUpdateBucketMetadataInventoryTableConfiguration(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeBucketMetadataConfiguration::toUpdateBucketMetadataInventoryTableConfiguration);
    }

    public static UpdateBucketMetadataJournalTableConfigurationResult updateBucketMetadataJournalTableConfiguration(
            ClientImpl impl, UpdateBucketMetadataJournalTableConfigurationRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.journalTableConfiguration(), "request.journalTableConfiguration is required");
        OperationInput input = SerdeBucketMetadataConfiguration.fromUpdateBucketMetadataJournalTableConfiguration(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketMetadataConfiguration.toUpdateBucketMetadataJournalTableConfiguration(output);
    }

    public static CompletableFuture<UpdateBucketMetadataJournalTableConfigurationResult>
    updateBucketMetadataJournalTableConfigurationAsync(ClientImpl impl,
            UpdateBucketMetadataJournalTableConfigurationRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.journalTableConfiguration(), "request.journalTableConfiguration is required");
        OperationInput input = SerdeBucketMetadataConfiguration.fromUpdateBucketMetadataJournalTableConfiguration(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeBucketMetadataConfiguration::toUpdateBucketMetadataJournalTableConfiguration);
    }
}
