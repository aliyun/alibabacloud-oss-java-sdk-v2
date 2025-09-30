
package com.aliyun.sdk.service.oss2.vectors.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.vectors.models.*;
import com.aliyun.sdk.service.oss2.vectors.models.internal.ListVectorIndexesResultJson;
import java.util.Map;

import static com.aliyun.sdk.service.oss2.vectors.transform.SerdeJsonUtils.addContentMd5;

public final class SerdeVectorIndexBasic {

    public static OperationInput fromPutVectorIndex(PutVectorIndexRequest request) {
        // headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("putVectorIndex", "");

        // body
        BinaryData body = SerdeJsonUtils.toJson(request.bodyFields());

        OperationInput input = OperationInput.newBuilder()
                .opName("PutVectorIndex")
                .bucket(request.bucket())
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .body(body)
                .build();

        SerdeJsonUtils.serializeInput(request, input, addContentMd5);

        return input;
    }

    public static PutVectorIndexResult toPutVectorIndex(OperationOutput output) {
        Object innerBody = null;
        return PutVectorIndexResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    public static OperationInput fromGetVectorIndex(GetVectorIndexRequest request) {
        // headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("getVectorIndex", "");

        // body
        BinaryData body = SerdeJsonUtils.toJson(request.bodyFields());

        OperationInput input = OperationInput.newBuilder()
                .opName("GetVectorIndex")
                .bucket(request.bucket())
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .body(body)
                .build();

        SerdeJsonUtils.serializeInput(request, input, addContentMd5);

        return input;
    }

    public static GetVectorIndexResult toGetVectorIndex(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeJsonUtils.fromJsonBody(output, IndexInfo.class);

        return GetVectorIndexResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    public static OperationInput fromListVectorIndexes(ListVectorIndexesRequest request) {
        // headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("listVectorIndexes", "");

        // body
        BinaryData body = SerdeJsonUtils.toJson(request.bodyFields());

        OperationInput input = OperationInput.newBuilder()
                .opName("ListVectorIndexes")
                .bucket(request.bucket())
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .body(body)
                .build();

        SerdeJsonUtils.serializeInput(request, input, addContentMd5);

        return input;
    }

    public static ListVectorIndexesResult toListVectorIndexes(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeJsonUtils.fromJsonBody(output, ListVectorIndexesResultJson.class);

        return ListVectorIndexesResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    public static OperationInput fromDeleteVectorIndex(DeleteVectorIndexRequest request) {
        // headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("deleteVectorIndex", "");

        // body
        BinaryData body = SerdeJsonUtils.toJson(request.bodyFields());

        OperationInput input = OperationInput.newBuilder()
                .opName("DeleteVectorIndex")
                .bucket(request.bucket())
                .method("POST")
                .headers(headers)
                .parameters(parameters)
                .body(body)
                .build();

        SerdeJsonUtils.serializeInput(request, input, addContentMd5);

        return input;
    }

    public static DeleteVectorIndexResult toDeleteVectorIndex(OperationOutput output) {
        Object innerBody = null;
        return DeleteVectorIndexResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }
}