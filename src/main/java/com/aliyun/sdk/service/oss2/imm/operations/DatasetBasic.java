package com.aliyun.sdk.service.oss2.imm.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.imm.models.*;
import com.aliyun.sdk.service.oss2.imm.transform.SerdeDatasetBasic;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

public final class DatasetBasic {

    public static CreateDatasetResult createDataset(ClientImpl impl, CreateDatasetRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromCreateDataset(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeDatasetBasic.toCreateDataset(output);
    }

    public static CompletableFuture<CreateDatasetResult> createDatasetAsync(ClientImpl impl, CreateDatasetRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromCreateDataset(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeDatasetBasic::toCreateDataset);
    }
}
