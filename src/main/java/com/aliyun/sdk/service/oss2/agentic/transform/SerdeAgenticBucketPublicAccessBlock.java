package com.aliyun.sdk.service.oss2.agentic.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.models.PublicAccessBlockConfiguration;
import com.aliyun.sdk.service.oss2.transform.SerdeUtils;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;

public final class SerdeAgenticBucketPublicAccessBlock {

    public static OperationInput fromPutAgenticBucketPublicAccessBlock(PutAgenticBucketPublicAccessBlockRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("PutAgenticBucketPublicAccessBlock")
                .method("PUT");

        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("agenticBucket", "");
        parameters.put("publicAccessBlock", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        BinaryData body = SerdeUtils.serializeXmlBody(request.publicAccessBlockConfiguration());
        builder.body(body);

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutAgenticBucketPublicAccessBlockResult toPutAgenticBucketPublicAccessBlock(OperationOutput output) {
        return PutAgenticBucketPublicAccessBlockResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .build();
    }

    public static OperationInput fromGetAgenticBucketPublicAccessBlock(GetAgenticBucketPublicAccessBlockRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetAgenticBucketPublicAccessBlock")
                .method("GET");

        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("agenticBucket", "");
        parameters.put("publicAccessBlock", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetAgenticBucketPublicAccessBlockResult toGetAgenticBucketPublicAccessBlock(OperationOutput output) {
        Object innerBody = SerdeUtils.deserializeXmlBody(output, PublicAccessBlockConfiguration.class);
        return GetAgenticBucketPublicAccessBlockResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    public static OperationInput fromDeleteAgenticBucketPublicAccessBlock(DeleteAgenticBucketPublicAccessBlockRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DeleteAgenticBucketPublicAccessBlock")
                .method("DELETE");

        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("agenticBucket", "");
        parameters.put("publicAccessBlock", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static DeleteAgenticBucketPublicAccessBlockResult toDeleteAgenticBucketPublicAccessBlock(OperationOutput output) {
        return DeleteAgenticBucketPublicAccessBlockResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .build();
    }
}
