package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.DoDataPipelineActionRequest;
import com.aliyun.sdk.service.oss2.models.DoDataPipelineActionResult;
import com.aliyun.sdk.service.oss2.models.DoMetaQueryActionRequest;
import com.aliyun.sdk.service.oss2.models.DoMetaQueryActionResult;
import com.aliyun.sdk.service.oss2.transform.SerdeDataBasic;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

public final class DataBasic {

    public static DoDataPipelineActionResult doDataPipelineAction(ClientImpl impl, DoDataPipelineActionRequest request, OperationOptions options) {

        requireNonNull(request.action(), "request.action is required");

        OperationInput input = SerdeDataBasic.fromDoDataPipelineAction(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeDataBasic.toDoDataPipelineAction(output);
    }

    public static CompletableFuture<DoDataPipelineActionResult> doDataPipelineActionAsync(ClientImpl impl, DoDataPipelineActionRequest request, OperationOptions options) {

        requireNonNull(request.action(), "request.action is required");

        OperationInput input = SerdeDataBasic.fromDoDataPipelineAction(request);
        return impl.executeAsync(input, options).thenApply(SerdeDataBasic::toDoDataPipelineAction);
    }

    public static DoMetaQueryActionResult doMetaQueryAction(ClientImpl impl, DoMetaQueryActionRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.action(), "request.action is required");

        OperationInput input = SerdeDataBasic.fromDoMetaQueryAction(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeDataBasic.toDoMetaQueryAction(output);
    }

    public static CompletableFuture<DoMetaQueryActionResult> doMetaQueryActionAsync(ClientImpl impl, DoMetaQueryActionRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.action(), "request.action is required");

        OperationInput input = SerdeDataBasic.fromDoMetaQueryAction(request);
        return impl.executeAsync(input, options).thenApply(SerdeDataBasic::toDoMetaQueryAction);
    }
}
