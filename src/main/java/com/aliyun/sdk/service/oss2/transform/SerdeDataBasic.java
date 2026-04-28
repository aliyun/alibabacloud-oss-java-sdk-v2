package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.DoDataPipelineActionRequest;
import com.aliyun.sdk.service.oss2.models.DoDataPipelineActionResult;
import com.aliyun.sdk.service.oss2.models.DoMetaQueryActionRequest;
import com.aliyun.sdk.service.oss2.models.DoMetaQueryActionResult;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;

public final class SerdeDataBasic {

    public static OperationInput fromDoDataPipelineAction(DoDataPipelineActionRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DoDataPipelineAction")
                .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("dataPipeline", "");
        builder.parameters(parameters);

        // body
        if (request.body() != null) {
            builder.body(request.body());
        }

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static DoDataPipelineActionResult toDoDataPipelineAction(OperationOutput output) {
        BinaryData body = null;
        if (output.body != null) {
            body = output.body;
        }

        return DoDataPipelineActionResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .body(body)
                .build();
    }

    public static OperationInput fromDoMetaQueryAction(DoMetaQueryActionRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DoMetaQueryAction")
                .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/octet-stream");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("MetaQuery", "");
        builder.parameters(parameters);

        // body
        if (request.body() != null) {
            builder.body(request.body());
        }
        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static DoMetaQueryActionResult toDoMetaQueryAction(OperationOutput output) {
        BinaryData body = null;
        if (output.body != null) {
            body = output.body;
        }

        return DoMetaQueryActionResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .body(body)
                .build();
    }
}
