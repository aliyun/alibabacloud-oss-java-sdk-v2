package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import java.util.Map;

public final class SerdePublicAccessBlock {


    public static OperationInput fromGetPublicAccessBlock(GetPublicAccessBlockRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("GetPublicAccessBlock")
            .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("publicAccessBlock", "");
        builder.parameters(parameters);

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetPublicAccessBlockResult toGetPublicAccessBlock(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, PublicAccessBlockConfiguration.class);
         
        return GetPublicAccessBlockResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromPutPublicAccessBlock(PutPublicAccessBlockRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("PutPublicAccessBlock")
            .method("PUT");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("publicAccessBlock", "");
        builder.parameters(parameters);

        // body
        BinaryData body = SerdeUtils.serializeXmlBody(request.publicAccessBlockConfiguration());
        builder.body(body);

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutPublicAccessBlockResult toPutPublicAccessBlock(OperationOutput output) {
        Object innerBody = null;
        return PutPublicAccessBlockResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromDeletePublicAccessBlock(DeletePublicAccessBlockRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("DeletePublicAccessBlock")
            .method("DELETE");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);
         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("publicAccessBlock", "");
        builder.parameters(parameters);

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static DeletePublicAccessBlockResult toDeletePublicAccessBlock(OperationOutput output) {
        Object innerBody = null;
        return DeletePublicAccessBlockResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }

}