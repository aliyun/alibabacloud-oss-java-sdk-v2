package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import java.util.Map;

public final class SerdeBucketPublicAccessBlock {


    public static OperationInput fromGetBucketPublicAccessBlock(GetBucketPublicAccessBlockRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("GetBucketPublicAccessBlock")
            .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);
         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("publicAccessBlock", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());
        

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetBucketPublicAccessBlockResult toGetBucketPublicAccessBlock(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, PublicAccessBlockConfiguration.class);
         
        return GetBucketPublicAccessBlockResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromPutBucketPublicAccessBlock(PutBucketPublicAccessBlockRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("PutBucketPublicAccessBlock")
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

        builder.bucket(request.bucket());
        

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutBucketPublicAccessBlockResult toPutBucketPublicAccessBlock(OperationOutput output) {
        Object innerBody = null;
        return PutBucketPublicAccessBlockResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromDeleteBucketPublicAccessBlock(DeleteBucketPublicAccessBlockRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("DeleteBucketPublicAccessBlock")
            .method("DELETE");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("publicAccessBlock", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());
        

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static DeleteBucketPublicAccessBlockResult toDeleteBucketPublicAccessBlock(OperationOutput output) {
        Object innerBody = null;
        return DeleteBucketPublicAccessBlockResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }

}