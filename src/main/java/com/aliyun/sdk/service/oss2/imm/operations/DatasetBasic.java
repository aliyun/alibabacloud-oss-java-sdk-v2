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

    // ==================== CreateDataset ====================

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

    // ==================== GetDataset ====================

    public static GetDatasetResult getDataset(ClientImpl impl, GetDatasetRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromGetDataset(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeDatasetBasic.toGetDataset(output);
    }

    public static CompletableFuture<GetDatasetResult> getDatasetAsync(ClientImpl impl, GetDatasetRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromGetDataset(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeDatasetBasic::toGetDataset);
    }

    // ==================== UpdateDataset ====================

    public static UpdateDatasetResult updateDataset(ClientImpl impl, UpdateDatasetRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromUpdateDataset(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeDatasetBasic.toUpdateDataset(output);
    }

    public static CompletableFuture<UpdateDatasetResult> updateDatasetAsync(ClientImpl impl, UpdateDatasetRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromUpdateDataset(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeDatasetBasic::toUpdateDataset);
    }

    // ==================== DeleteDataset ====================

    public static DeleteDatasetResult deleteDataset(ClientImpl impl, DeleteDatasetRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromDeleteDataset(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeDatasetBasic.toDeleteDataset(output);
    }

    public static CompletableFuture<DeleteDatasetResult> deleteDatasetAsync(ClientImpl impl, DeleteDatasetRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromDeleteDataset(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeDatasetBasic::toDeleteDataset);
    }

    // ==================== SimpleQuery ====================

    public static SimpleQueryResult simpleQuery(ClientImpl impl, SimpleQueryRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromSimpleQuery(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeDatasetBasic.toSimpleQuery(output);
    }

    public static CompletableFuture<SimpleQueryResult> simpleQueryAsync(ClientImpl impl, SimpleQueryRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromSimpleQuery(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeDatasetBasic::toSimpleQuery);
    }

    // ==================== SemanticQuery ====================

    public static SemanticQueryResult semanticQuery(ClientImpl impl, SemanticQueryRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromSemanticQuery(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeDatasetBasic.toSemanticQuery(output);
    }

    public static CompletableFuture<SemanticQueryResult> semanticQueryAsync(ClientImpl impl, SemanticQueryRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromSemanticQuery(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeDatasetBasic::toSemanticQuery);
    }
}
