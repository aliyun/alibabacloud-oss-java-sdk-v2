package com.aliyun.sdk.service.oss2.dataprocess.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.dataprocess.models.*;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDatasetBasic;

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

    // ==================== ListDatasets ====================

    public static ListDatasetsResult listDatasets(ClientImpl impl, ListDatasetsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromListDatasets(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeDatasetBasic.toListDatasets(output);
    }

    public static CompletableFuture<ListDatasetsResult> listDatasetsAsync(ClientImpl impl, ListDatasetsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromListDatasets(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeDatasetBasic::toListDatasets);
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

    // ==================== OpenMetaQuery ====================

    public static OpenMetaQueryResult openMetaQuery(ClientImpl impl, OpenMetaQueryRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromOpenMetaQuery(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeDatasetBasic.toOpenMetaQuery(output);
    }

    public static CompletableFuture<OpenMetaQueryResult> openMetaQueryAsync(ClientImpl impl, OpenMetaQueryRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromOpenMetaQuery(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeDatasetBasic::toOpenMetaQuery);
    }

    // ==================== GetMetaQueryStatus ====================

    public static GetMetaQueryStatusResult getMetaQueryStatus(ClientImpl impl, GetMetaQueryStatusRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromGetMetaQueryStatus(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeDatasetBasic.toGetMetaQueryStatus(output);
    }

    public static CompletableFuture<GetMetaQueryStatusResult> getMetaQueryStatusAsync(ClientImpl impl, GetMetaQueryStatusRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromGetMetaQueryStatus(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeDatasetBasic::toGetMetaQueryStatus);
    }

    // ==================== CloseMetaQuery ====================

    public static CloseMetaQueryResult closeMetaQuery(ClientImpl impl, CloseMetaQueryRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromCloseMetaQuery(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeDatasetBasic.toCloseMetaQuery(output);
    }

    public static CompletableFuture<CloseMetaQueryResult> closeMetaQueryAsync(ClientImpl impl, CloseMetaQueryRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromCloseMetaQuery(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeDatasetBasic::toCloseMetaQuery);
    }

    // ==================== DoMetaQuery ====================

    public static DoMetaQueryResult doMetaQuery(ClientImpl impl, DoMetaQueryRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromDoMetaQuery(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeDatasetBasic.toDoMetaQuery(output);
    }

    public static CompletableFuture<DoMetaQueryResult> doMetaQueryAsync(ClientImpl impl, DoMetaQueryRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromDoMetaQuery(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeDatasetBasic::toDoMetaQuery);
    }

    // ==================== DeleteFileMeta ====================

    public static DeleteFileMetaResult deleteFileMeta(ClientImpl impl, DeleteFileMetaRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromDeleteFileMeta(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeDatasetBasic.toDeleteFileMeta(output);
    }

    public static CompletableFuture<DeleteFileMetaResult> deleteFileMetaAsync(ClientImpl impl, DeleteFileMetaRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromDeleteFileMeta(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeDatasetBasic::toDeleteFileMeta);
    }

    // ==================== CreateSmartCluster ====================

    public static CreateSmartClusterResult createSmartCluster(ClientImpl impl, CreateSmartClusterRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromCreateSmartCluster(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeDatasetBasic.toCreateSmartCluster(output);
    }

    public static CompletableFuture<CreateSmartClusterResult> createSmartClusterAsync(ClientImpl impl, CreateSmartClusterRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromCreateSmartCluster(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeDatasetBasic::toCreateSmartCluster);
    }

    // ==================== GetSmartCluster ====================

    public static GetSmartClusterResult getSmartCluster(ClientImpl impl, GetSmartClusterRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromGetSmartCluster(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeDatasetBasic.toGetSmartCluster(output);
    }

    public static CompletableFuture<GetSmartClusterResult> getSmartClusterAsync(ClientImpl impl, GetSmartClusterRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromGetSmartCluster(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeDatasetBasic::toGetSmartCluster);
    }

    // ==================== UpdateSmartCluster ====================

    public static UpdateSmartClusterResult updateSmartCluster(ClientImpl impl, UpdateSmartClusterRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromUpdateSmartCluster(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeDatasetBasic.toUpdateSmartCluster(output);
    }

    public static CompletableFuture<UpdateSmartClusterResult> updateSmartClusterAsync(ClientImpl impl, UpdateSmartClusterRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromUpdateSmartCluster(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeDatasetBasic::toUpdateSmartCluster);
    }

    // ==================== DeleteSmartCluster ====================

    public static DeleteSmartClusterResult deleteSmartCluster(ClientImpl impl, DeleteSmartClusterRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromDeleteSmartCluster(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeDatasetBasic.toDeleteSmartCluster(output);
    }

    public static CompletableFuture<DeleteSmartClusterResult> deleteSmartClusterAsync(ClientImpl impl, DeleteSmartClusterRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromDeleteSmartCluster(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeDatasetBasic::toDeleteSmartCluster);
    }

    // ==================== ListSmartClusters ====================

    public static ListSmartClustersResult listSmartClusters(ClientImpl impl, ListSmartClustersRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromListSmartClusters(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeDatasetBasic.toListSmartClusters(output);
    }

    public static CompletableFuture<ListSmartClustersResult> listSmartClustersAsync(ClientImpl impl, ListSmartClustersRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeDatasetBasic.fromListSmartClusters(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeDatasetBasic::toListSmartClusters);
    }
}
