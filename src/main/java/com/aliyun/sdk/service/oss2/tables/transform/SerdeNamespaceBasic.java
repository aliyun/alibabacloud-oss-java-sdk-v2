package com.aliyun.sdk.service.oss2.tables.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.tables.models.*;
import com.aliyun.sdk.service.oss2.tables.models.internal.CreateNamespaceResultJson;
import com.aliyun.sdk.service.oss2.tables.models.internal.GetNamespaceResultJson;
import com.aliyun.sdk.service.oss2.tables.models.internal.ListNamespacesResultJson;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.HttpUtils;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;

import static com.aliyun.sdk.service.oss2.AttributeKey.IS_BUCKET_ARN;

public final class SerdeNamespaceBasic {

    // ==================== CreateNamespace ====================

    public static OperationInput fromCreateNamespace(CreateNamespaceRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        BinaryData body = SerdeJsonUtils.toJson(request.bodyFields());
        OperationInput input = OperationInput.newBuilder()
                .opName("CreateNamespace")
                .bucket(request.tableBucketARN())
                .key(String.format("namespaces/%s", HttpUtils.urlEncode(request.tableBucketARN())))
                .method("PUT")
                .headers(headers)
                .parameters(parameters)
                .body(body)
                .build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static CreateNamespaceResult toCreateNamespace(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeJsonUtils.fromJsonBody(output, CreateNamespaceResultJson.class);
        return CreateNamespaceResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }

    // ==================== DeleteNamespace ====================

    public static OperationInput fromDeleteNamespace(DeleteNamespaceRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DeleteNamespace")
                .method("DELETE");
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);
        builder.bucket(request.tableBucketARN());
        builder.key(String.format("namespaces/%s/%s", HttpUtils.urlEncode(request.tableBucketARN()), HttpUtils.urlEncode(request.namespace())));
        OperationInput input = builder.build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static DeleteNamespaceResult toDeleteNamespace(OperationOutput output) {
        Object innerBody = null;
        return DeleteNamespaceResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }

    // ==================== GetNamespace ====================

    public static OperationInput fromGetNamespace(GetNamespaceRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetNamespace")
                .method("GET");
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);
        builder.bucket(request.tableBucketARN());
        builder.key(String.format("namespaces/%s/%s", HttpUtils.urlEncode(request.tableBucketARN()), HttpUtils.urlEncode(request.namespace())));
        OperationInput input = builder.build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static GetNamespaceResult toGetNamespace(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeJsonUtils.fromJsonBody(output, GetNamespaceResultJson.class);
        return GetNamespaceResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }

    // ==================== ListNamespaces ====================

    public static OperationInput fromListNamespaces(ListNamespacesRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("ListNamespaces")
                .method("GET");
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);
        builder.bucket(request.tableBucketARN());
        builder.key(String.format("namespaces/%s", HttpUtils.urlEncode(request.tableBucketARN())));
        OperationInput input = builder.build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static ListNamespacesResult toListNamespaces(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeJsonUtils.fromJsonBody(output, ListNamespacesResultJson.class);
        return ListNamespacesResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }
}
