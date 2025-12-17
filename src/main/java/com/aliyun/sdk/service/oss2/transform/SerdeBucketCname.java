package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.models.internal.ListCnameResultXml;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import java.util.Map;

public final class SerdeBucketCname {


    public static OperationInput fromPutCname(PutCnameRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("PutCname")
            .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);
         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("cname", "");
        parameters.put("comp", "add");
        builder.parameters(parameters);
        
        // body
        BinaryData body = SerdeUtils.serializeXmlBody(request.bucketCnameConfiguration());
        builder.body(body);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutCnameResult toPutCname(OperationOutput output) {
        Object innerBody = null;
        return PutCnameResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromListCname(ListCnameRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("ListCname")
            .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);
         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("cname", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static ListCnameResult toListCname(OperationOutput output) {
        Object innerBody = null;

        innerBody = SerdeUtils.deserializeXmlBody(output, ListCnameResultXml.class);
         
        return ListCnameResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromDeleteCname(DeleteCnameRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("DeleteCname")
            .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);
         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("cname", "");
        parameters.put("comp", "delete");
        builder.parameters(parameters);
        
        // body
        BinaryData body = SerdeUtils.serializeXmlBody(request.bucketCnameConfiguration());
        builder.body(body);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static DeleteCnameResult toDeleteCname(OperationOutput output) {
        Object innerBody = null;
        return DeleteCnameResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromGetCnameToken(GetCnameTokenRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("GetCnameToken")
            .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);
         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("comp", "token");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetCnameTokenResult toGetCnameToken(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, CnameToken.class);
         
        return GetCnameTokenResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromCreateCnameToken(CreateCnameTokenRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("CreateCnameToken")
            .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);
         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("cname", "");
        parameters.put("comp", "token");
        builder.parameters(parameters);
        
        // body
        BinaryData body = SerdeUtils.serializeXmlBody(request.bucketCnameConfiguration());
        builder.body(body);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static CreateCnameTokenResult toCreateCnameToken(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, CnameToken.class);
         
        return CreateCnameTokenResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }
}