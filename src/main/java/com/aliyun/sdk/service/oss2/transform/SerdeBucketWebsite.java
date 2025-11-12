package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.AttributeMap;
import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transport.StringBinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import java.util.List;
import java.util.Map;
import static com.aliyun.sdk.service.oss2.AttributeKey.SUBRESOURCE;

public final class SerdeBucketWebsite {


    public static OperationInput fromGetBucketWebsite(GetBucketWebsiteRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("GetBucketWebsite")
            .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);
         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("website", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetBucketWebsiteResult toGetBucketWebsite(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, WebsiteConfiguration.class);
         
        return GetBucketWebsiteResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromPutBucketWebsite(PutBucketWebsiteRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("PutBucketWebsite")
            .method("PUT");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("website", "");
        builder.parameters(parameters);

        // body
        BinaryData body = SerdeUtils.serializeXmlBody(request.websiteConfiguration());
        builder.body(body);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutBucketWebsiteResult toPutBucketWebsite(OperationOutput output) {
        Object innerBody = null;
        return PutBucketWebsiteResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromDeleteBucketWebsite(DeleteBucketWebsiteRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("DeleteBucketWebsite")
            .method("DELETE");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);
         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("website", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static DeleteBucketWebsiteResult toDeleteBucketWebsite(OperationOutput output) {
        Object innerBody = null;
        return DeleteBucketWebsiteResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }
}