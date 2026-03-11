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
}
