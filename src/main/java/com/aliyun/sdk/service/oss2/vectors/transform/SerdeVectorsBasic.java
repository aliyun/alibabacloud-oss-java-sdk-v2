package com.aliyun.sdk.service.oss2.vectors.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.vectors.models.*;
import com.aliyun.sdk.service.oss2.vectors.models.internal.*;
import java.util.Map;

import static com.aliyun.sdk.service.oss2.vectors.transform.SerdeJsonUtils.serializeInput;
import static com.aliyun.sdk.service.oss2.vectors.transform.SerdeJsonUtils.addContentMd5;

public final class SerdeVectorsBasic {

    public static OperationInput fromPutVectors(PutVectorsRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("PutVectors")
                .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("putVectors", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        builder.body(SerdeJsonUtils.toJson(request.bodyFields()));

        OperationInput input = builder.build();
        serializeInput(request, input, addContentMd5);
        return input;
    }

    public static PutVectorsResult toPutVectors(OperationOutput output) {
        Object innerBody = null;
        return PutVectorsResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    public static OperationInput fromGetVectors(GetVectorsRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetVectors")
                .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("getVectors", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        // body
        builder.body(SerdeJsonUtils.toJson(request.bodyFields()));


        OperationInput input = builder.build();
        serializeInput(request, input, addContentMd5);
        return input;
    }

    public static GetVectorsResult toGetVectors(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeJsonUtils.fromJsonBody(output, VectorsInfo.class);

        return GetVectorsResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    public static OperationInput fromListVectors(ListVectorsRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("ListVectors")
                .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("listVectors", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        // body
        builder.body(SerdeJsonUtils.toJson(request.bodyFields()));

        OperationInput input = builder.build();
        serializeInput(request, input, addContentMd5);
        return input;
    }

    public static ListVectorsResult toListVectors(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeJsonUtils.fromJsonBody(output, ListVectorsResultJson.class);

        return ListVectorsResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    public static OperationInput fromDeleteVectors(DeleteVectorsRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DeleteVectors")
                .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("deleteVectors", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        // body
        builder.body(SerdeJsonUtils.toJson(request.bodyFields()));

        OperationInput input = builder.build();
        serializeInput(request, input, addContentMd5);
        return input;
    }

    public static DeleteVectorsResult toDeleteVectors(OperationOutput output) {
        Object innerBody = null;
        return DeleteVectorsResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    public static OperationInput fromQueryVectors(QueryVectorsRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("QueryVectors")
                .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("queryVectors", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        // body
        builder.body(SerdeJsonUtils.toJson(request.bodyFields()));

        OperationInput input = builder.build();
        serializeInput(request, input, addContentMd5);
        return input;
    }

    public static QueryVectorsResult toQueryVectors(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeJsonUtils.fromJsonBody(output, QueryVectorsJson.class);

        return QueryVectorsResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }
}
