package com.aliyun.sdk.service.oss2.dataprocess.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeUtils;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.dataprocess.models.*;

import java.util.Map;


public final class SerdeDatasetBasic {

    // ==================== CreateDataset ====================

    public static OperationInput fromCreateDataset(CreateDatasetRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("metaQuery", "");
        parameters.put("action", "createDataset");

        OperationInput input = OperationInput.newBuilder()
                .opName("CreateDataset")
                .bucket(request.bucket())
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .build();

        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);

        return input;
    }

    public static CreateDatasetResult toCreateDataset(OperationOutput output) {
        return CreateDatasetResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(SerdeUtils.deserializeXmlBody(output, CreateDatasetResponseBody.class))
                .build();
    }

    // ==================== GetDataset ====================

    public static OperationInput fromGetDataset(GetDatasetRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("metaQuery", "");
        parameters.put("action", "getDataset");

        OperationInput input = OperationInput.newBuilder()
                .opName("GetDataset")
                .bucket(request.bucket())
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .build();

        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);

        return input;
    }

    public static GetDatasetResult toGetDataset(OperationOutput output) {
        return GetDatasetResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(SerdeUtils.deserializeXmlBody(output, GetDatasetResponseBody.class))
                .build();
    }

    // ==================== UpdateDataset ====================

    public static OperationInput fromUpdateDataset(UpdateDatasetRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("metaQuery", "");
        parameters.put("action", "updateDataset");

        OperationInput input = OperationInput.newBuilder()
                .opName("UpdateDataset")
                .bucket(request.bucket())
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .build();

        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);

        return input;
    }

    public static UpdateDatasetResult toUpdateDataset(OperationOutput output) {
        return UpdateDatasetResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(SerdeUtils.deserializeXmlBody(output, UpdateDatasetResponseBody.class))
                .build();
    }

    // ==================== DeleteDataset ====================

    public static OperationInput fromDeleteDataset(DeleteDatasetRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("metaQuery", "");
        parameters.put("action", "deleteDataset");

        OperationInput input = OperationInput.newBuilder()
                .opName("DeleteDataset")
                .bucket(request.bucket())
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .build();

        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);

        return input;
    }

    public static DeleteDatasetResult toDeleteDataset(OperationOutput output) {
        return DeleteDatasetResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(null)
                .build();
    }

    // ==================== SimpleQuery ====================

    public static OperationInput fromSimpleQuery(SimpleQueryRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("metaQuery", "");
        parameters.put("action", "simpleQuery");

        OperationInput input = OperationInput.newBuilder()
                .opName("SimpleQuery")
                .bucket(request.bucket())
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .build();

        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);

        return input;
    }

    public static SimpleQueryResult toSimpleQuery(OperationOutput output) {
        return SimpleQueryResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(SerdeUtils.deserializeXmlBody(output, SimpleQueryResponseBody.class))
                .build();
    }

    // ==================== ListDatasets ====================

    public static OperationInput fromListDatasets(ListDatasetsRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("metaQuery", "");
        parameters.put("action", "listDatasets");

        OperationInput input = OperationInput.newBuilder()
                .opName("ListDatasets")
                .bucket(request.bucket())
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .build();

        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);

        return input;
    }

    public static ListDatasetsResult toListDatasets(OperationOutput output) {
        return ListDatasetsResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(SerdeUtils.deserializeXmlBody(output, ListDatasetsResponseBody.class))
                .build();
    }

    // ==================== SemanticQuery ====================

    public static OperationInput fromSemanticQuery(SemanticQueryRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("metaQuery", "");
        parameters.put("action", "semanticQuery");

        OperationInput input = OperationInput.newBuilder()
                .opName("SemanticQuery")
                .bucket(request.bucket())
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .build();

        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);

        return input;
    }

    public static SemanticQueryResult toSemanticQuery(OperationOutput output) {
        return SemanticQueryResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(SerdeUtils.deserializeXmlBody(output, SemanticQueryResponseBody.class))
                .build();
    }

    // ==================== OpenMetaQuery ====================

    public static OperationInput fromOpenMetaQuery(OpenMetaQueryRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("metaQuery", "");
        parameters.put("action", "openMetaQuery");

        OperationInput input = OperationInput.newBuilder()
                .opName("OpenMetaQuery")
                .bucket(request.bucket())
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .body(SerdeUtils.serializeXmlBody(request.metaQueryBody()))
                .build();

        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);

        return input;
    }

    public static OpenMetaQueryResult toOpenMetaQuery(OperationOutput output) {
        return OpenMetaQueryResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(null)
                .build();
    }

    // ==================== GetMetaQueryStatus ====================

    public static OperationInput fromGetMetaQueryStatus(GetMetaQueryStatusRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("metaQuery", "");
        parameters.put("action", "getMetaQueryStatus");

        OperationInput input = OperationInput.newBuilder()
                .opName("GetMetaQueryStatus")
                .bucket(request.bucket())
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .build();

        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);

        return input;
    }

    public static GetMetaQueryStatusResult toGetMetaQueryStatus(OperationOutput output) {
        return GetMetaQueryStatusResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(SerdeUtils.deserializeXmlBody(output, MetaQueryStatus.class))
                .build();
    }

    // ==================== CloseMetaQuery ====================

    public static OperationInput fromCloseMetaQuery(CloseMetaQueryRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("metaQuery", "");
        parameters.put("action", "closeMetaQuery");

        OperationInput input = OperationInput.newBuilder()
                .opName("CloseMetaQuery")
                .bucket(request.bucket())
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .build();

        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);

        return input;
    }

    public static CloseMetaQueryResult toCloseMetaQuery(OperationOutput output) {
        return CloseMetaQueryResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(null)
                .build();
    }

    // ==================== DoMetaQuery ====================

    public static OperationInput fromDoMetaQuery(DoMetaQueryRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("metaQuery", "");
        parameters.put("action", "doMetaQuery");

        OperationInput input = OperationInput.newBuilder()
                .opName("DoMetaQuery")
                .bucket(request.bucket())
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .body(SerdeUtils.serializeXmlBodyAsBytes(request.metaQueryBody()))
                .build();

        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);

        return input;
    }

    public static DoMetaQueryResult toDoMetaQuery(OperationOutput output) {
        return DoMetaQueryResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(SerdeUtils.deserializeXmlBody(output, DoMetaQueryResponseBody.class))
                .build();
    }

    // ==================== DeleteFileMeta ====================

    public static OperationInput fromDeleteFileMeta(DeleteFileMetaRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("metaQuery", "");
        parameters.put("action", "deleteFileMeta");

        OperationInput input = OperationInput.newBuilder()
                .opName("DeleteFileMeta")
                .bucket(request.bucket())
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .build();

        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);

        return input;
    }

    public static DeleteFileMetaResult toDeleteFileMeta(OperationOutput output) {
        return DeleteFileMetaResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(null)
                .build();
    }

    // ==================== CreateSmartCluster ====================

    public static OperationInput fromCreateSmartCluster(CreateSmartClusterRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("metaQuery", "");
        parameters.put("action", "createSmartCluster");

        OperationInput input = OperationInput.newBuilder()
                .opName("CreateSmartCluster")
                .bucket(request.bucket())
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .build();

        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);

        return input;
    }

    public static CreateSmartClusterResult toCreateSmartCluster(OperationOutput output) {
        return CreateSmartClusterResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(SerdeUtils.deserializeXmlBody(output, CreateSmartClusterResponseBody.class))
                .build();
    }

    // ==================== GetSmartCluster ====================

    public static OperationInput fromGetSmartCluster(GetSmartClusterRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("metaQuery", "");
        parameters.put("action", "getSmartCluster");

        OperationInput input = OperationInput.newBuilder()
                .opName("GetSmartCluster")
                .bucket(request.bucket())
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .build();

        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);

        return input;
    }

    public static GetSmartClusterResult toGetSmartCluster(OperationOutput output) {
        return GetSmartClusterResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(SerdeUtils.deserializeXmlBody(output, GetSmartClusterResponseBody.class))
                .build();
    }

    // ==================== UpdateSmartCluster ====================

    public static OperationInput fromUpdateSmartCluster(UpdateSmartClusterRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("metaQuery", "");
        parameters.put("action", "updateSmartCluster");

        OperationInput input = OperationInput.newBuilder()
                .opName("UpdateSmartCluster")
                .bucket(request.bucket())
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .build();

        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);

        return input;
    }

    public static UpdateSmartClusterResult toUpdateSmartCluster(OperationOutput output) {
        return UpdateSmartClusterResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(SerdeUtils.deserializeXmlBody(output, UpdateSmartClusterResponseBody.class))
                .build();
    }

    // ==================== DeleteSmartCluster ====================

    public static OperationInput fromDeleteSmartCluster(DeleteSmartClusterRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("metaQuery", "");
        parameters.put("action", "deleteSmartCluster");

        OperationInput input = OperationInput.newBuilder()
                .opName("DeleteSmartCluster")
                .bucket(request.bucket())
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .build();

        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);

        return input;
    }

    public static DeleteSmartClusterResult toDeleteSmartCluster(OperationOutput output) {
        return DeleteSmartClusterResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(null)
                .build();
    }

    // ==================== ListSmartClusters ====================

    public static OperationInput fromListSmartClusters(ListSmartClustersRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("metaQuery", "");
        parameters.put("action", "listSmartClusters");

        OperationInput input = OperationInput.newBuilder()
                .opName("ListSmartClusters")
                .bucket(request.bucket())
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .build();

        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);

        return input;
    }

    public static ListSmartClustersResult toListSmartClusters(OperationOutput output) {
        return ListSmartClustersResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(SerdeUtils.deserializeXmlBody(output, ListSmartClustersResponseBody.class))
                .build();
    }
}
