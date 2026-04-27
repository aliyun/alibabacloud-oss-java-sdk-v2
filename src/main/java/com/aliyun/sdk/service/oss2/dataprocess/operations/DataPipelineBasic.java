package com.aliyun.sdk.service.oss2.dataprocess.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.dataprocess.models.*;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDataPipelineBasic;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

public final class DataPipelineBasic {

    // ==================== PutDataPipelineConfiguration ====================

    public static PutDataPipelineConfigurationResult putDataPipelineConfiguration(ClientImpl impl, PutDataPipelineConfigurationRequest request, OperationOptions options) {
        OperationInput input = SerdeDataPipelineBasic.fromPutDataPipelineConfiguration(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeDataPipelineBasic.toPutDataPipelineConfiguration(output);
    }

    public static CompletableFuture<PutDataPipelineConfigurationResult> putDataPipelineConfigurationAsync(ClientImpl impl, PutDataPipelineConfigurationRequest request, OperationOptions options) {
        OperationInput input = SerdeDataPipelineBasic.fromPutDataPipelineConfiguration(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeDataPipelineBasic::toPutDataPipelineConfiguration);
    }

    // ==================== GetDataPipelineConfiguration ====================

    public static GetDataPipelineConfigurationResult getDataPipelineConfiguration(ClientImpl impl, GetDataPipelineConfigurationRequest request, OperationOptions options) {
        OperationInput input = SerdeDataPipelineBasic.fromGetDataPipelineConfiguration(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeDataPipelineBasic.toGetDataPipelineConfiguration(output);
    }

    public static CompletableFuture<GetDataPipelineConfigurationResult> getDataPipelineConfigurationAsync(ClientImpl impl, GetDataPipelineConfigurationRequest request, OperationOptions options) {
        OperationInput input = SerdeDataPipelineBasic.fromGetDataPipelineConfiguration(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeDataPipelineBasic::toGetDataPipelineConfiguration);
    }

    // ==================== DeleteDataPipelineConfiguration ====================

    public static DeleteDataPipelineConfigurationResult deleteDataPipelineConfiguration(ClientImpl impl, DeleteDataPipelineConfigurationRequest request, OperationOptions options) {
        OperationInput input = SerdeDataPipelineBasic.fromDeleteDataPipelineConfiguration(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeDataPipelineBasic.toDeleteDataPipelineConfiguration(output);
    }

    public static CompletableFuture<DeleteDataPipelineConfigurationResult> deleteDataPipelineConfigurationAsync(ClientImpl impl, DeleteDataPipelineConfigurationRequest request, OperationOptions options) {
        OperationInput input = SerdeDataPipelineBasic.fromDeleteDataPipelineConfiguration(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeDataPipelineBasic::toDeleteDataPipelineConfiguration);
    }

    // ==================== ListDataPipelineConfigurations ====================

    public static ListDataPipelineConfigurationsResult listDataPipelineConfigurations(ClientImpl impl, ListDataPipelineConfigurationsRequest request, OperationOptions options) {
        OperationInput input = SerdeDataPipelineBasic.fromListDataPipelineConfigurations(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeDataPipelineBasic.toListDataPipelineConfigurations(output);
    }

    public static CompletableFuture<ListDataPipelineConfigurationsResult> listDataPipelineConfigurationsAsync(ClientImpl impl, ListDataPipelineConfigurationsRequest request, OperationOptions options) {
        OperationInput input = SerdeDataPipelineBasic.fromListDataPipelineConfigurations(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeDataPipelineBasic::toListDataPipelineConfigurations);
    }

    // ==================== PauseDataPipeline ====================

    public static PauseDataPipelineResult pauseDataPipeline(ClientImpl impl, PauseDataPipelineRequest request, OperationOptions options) {
        OperationInput input = SerdeDataPipelineBasic.fromPauseDataPipeline(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeDataPipelineBasic.toPauseDataPipeline(output);
    }

    public static CompletableFuture<PauseDataPipelineResult> pauseDataPipelineAsync(ClientImpl impl, PauseDataPipelineRequest request, OperationOptions options) {
        OperationInput input = SerdeDataPipelineBasic.fromPauseDataPipeline(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeDataPipelineBasic::toPauseDataPipeline);
    }

    // ==================== RestartDataPipeline ====================

    public static RestartDataPipelineResult restartDataPipeline(ClientImpl impl, RestartDataPipelineRequest request, OperationOptions options) {
        OperationInput input = SerdeDataPipelineBasic.fromRestartDataPipeline(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeDataPipelineBasic.toRestartDataPipeline(output);
    }

    public static CompletableFuture<RestartDataPipelineResult> restartDataPipelineAsync(ClientImpl impl, RestartDataPipelineRequest request, OperationOptions options) {
        OperationInput input = SerdeDataPipelineBasic.fromRestartDataPipeline(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeDataPipelineBasic::toRestartDataPipeline);
    }
}
