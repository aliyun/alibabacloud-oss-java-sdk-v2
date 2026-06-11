package com.aliyun.sdk.service.oss2.dataprocess.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeUtils;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.dataprocess.models.*;

import java.util.Map;


public final class SerdeDataPipelineBasic {

    // ==================== PutDataPipelineConfiguration ====================

    public static OperationInput fromPutDataPipelineConfiguration(PutDataPipelineConfigurationRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("dataPipeline", "");
        parameters.put("action", "putDataPipelineConfiguration");

        if (request.dataPipelineName() != null) {
            parameters.put("dataPipelineName", request.dataPipelineName());
        }
        if (request.role() != null) {
            parameters.put("role", request.role());
        }
        BinaryData body = SerdeUtils.serializeXmlBodyAsBytes(request.putDataPipelineConfigurationConfiguration());
        OperationInput input = OperationInput.newBuilder()
                .opName("PutDataPipelineConfiguration")
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .body(body)
                .build();

        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);

        return input;
    }

    public static PutDataPipelineConfigurationResult toPutDataPipelineConfiguration(OperationOutput output) {
        return PutDataPipelineConfigurationResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .build();
    }

    // ==================== GetDataPipelineConfiguration ====================

    public static OperationInput fromGetDataPipelineConfiguration(GetDataPipelineConfigurationRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("dataPipeline", "");
        parameters.put("action", "getDataPipelineConfiguration");

        if (request.dataPipelineName() != null) {
            parameters.put("dataPipelineName", request.dataPipelineName());
        }

        OperationInput input = OperationInput.newBuilder()
                .opName("GetDataPipelineConfiguration")
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .build();

        return input;
    }

    public static GetDataPipelineConfigurationResult toGetDataPipelineConfiguration(OperationOutput output) {
        return GetDataPipelineConfigurationResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(SerdeUtils.deserializeXmlBody(output, DataPipelineConfiguration.class))
                .build();
    }

    // ==================== DeleteDataPipelineConfiguration ====================

    public static OperationInput fromDeleteDataPipelineConfiguration(DeleteDataPipelineConfigurationRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("dataPipeline", "");
        parameters.put("action", "deleteDataPipelineConfiguration");

        if (request.dataPipelineName() != null) {
            parameters.put("dataPipelineName", request.dataPipelineName());
        }

        OperationInput input = OperationInput.newBuilder()
                .opName("DeleteDataPipelineConfiguration")
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .build();

        return input;
    }

    public static DeleteDataPipelineConfigurationResult toDeleteDataPipelineConfiguration(OperationOutput output) {
        return DeleteDataPipelineConfigurationResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .build();
    }

    // ==================== ListDataPipelineConfigurations ====================

    public static OperationInput fromListDataPipelineConfigurations(ListDataPipelineConfigurationsRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("dataPipeline", "");
        parameters.put("action", "listDataPipelineConfigurations");

        if (request.maxResults() != null) {
            parameters.put("maxResults", request.maxResults());
        }
        if (request.prefix() != null) {
            parameters.put("prefix", request.prefix());
        }
        if (request.nextToken() != null) {
            parameters.put("nextToken", request.nextToken());
        }

        OperationInput input = OperationInput.newBuilder()
                .opName("ListDataPipelineConfigurations")
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .build();

        return input;
    }

    public static ListDataPipelineConfigurationsResult toListDataPipelineConfigurations(OperationOutput output) {
        return ListDataPipelineConfigurationsResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(SerdeUtils.deserializeXmlBody(output, ListDataPipelineConfigurationsResponseBody.class))
                .build();
    }

    // ==================== PauseDataPipeline ====================

    public static OperationInput fromPauseDataPipeline(PauseDataPipelineRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("dataPipeline", "");
        parameters.put("action", "pauseDataPipeline");

        if (request.dataPipelineName() != null) {
            parameters.put("dataPipelineName", request.dataPipelineName());
        }

        OperationInput input = OperationInput.newBuilder()
                .opName("PauseDataPipeline")
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .build();

        return input;
    }

    public static PauseDataPipelineResult toPauseDataPipeline(OperationOutput output) {
        return PauseDataPipelineResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .build();
    }

    // ==================== RestartDataPipeline ====================

    public static OperationInput fromRestartDataPipeline(RestartDataPipelineRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("dataPipeline", "");
        parameters.put("action", "restartDataPipeline");

        if (request.dataPipelineName() != null) {
            parameters.put("dataPipelineName", request.dataPipelineName());
        }

        OperationInput input = OperationInput.newBuilder()
                .opName("RestartDataPipeline")
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .build();

        return input;
    }

    public static RestartDataPipelineResult toRestartDataPipeline(OperationOutput output) {
        return RestartDataPipelineResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .build();
    }
}
