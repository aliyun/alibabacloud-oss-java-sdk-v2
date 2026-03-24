package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import java.util.Map;

public final class SerdeBucketObjectFcAccessPoint {


    public static OperationInput fromCreateAccessPointForObjectProcess(CreateAccessPointForObjectProcessRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("CreateAccessPointForObjectProcess")
                .method("PUT");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("accessPointForObjectProcess", "");
        builder.parameters(parameters);

        // body
        BinaryData body = SerdeUtils.serializeXmlBody(request.createAccessPointForObjectProcessConfiguration());
        builder.body(body);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static CreateAccessPointForObjectProcessResult toCreateAccessPointForObjectProcess(OperationOutput output) {
        Object innerBody = null;

        innerBody = SerdeUtils.deserializeXmlBody(output, CreateAccessPointForObjectProcessResultXml.class);

        return CreateAccessPointForObjectProcessResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromGetAccessPointForObjectProcess(GetAccessPointForObjectProcessRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetAccessPointForObjectProcess")
                .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("accessPointForObjectProcess", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetAccessPointForObjectProcessResult toGetAccessPointForObjectProcess(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, GetAccessPointForObjectProcessResultXml.class);

        return GetAccessPointForObjectProcessResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromListAccessPointsForObjectProcess(ListAccessPointsForObjectProcessRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("ListAccessPointsForObjectProcess")
                .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("accessPointForObjectProcess", "");
        builder.parameters(parameters);

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static ListAccessPointsForObjectProcessResult toListAccessPointsForObjectProcess(OperationOutput output) {
        Object innerBody = null;

        innerBody = SerdeUtils.deserializeXmlBody(output, ListAccessPointsForObjectProcessResultXml.class);

        return ListAccessPointsForObjectProcessResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromDeleteAccessPointForObjectProcess(DeleteAccessPointForObjectProcessRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DeleteAccessPointForObjectProcess")
                .method("DELETE");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("accessPointForObjectProcess", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static DeleteAccessPointForObjectProcessResult toDeleteAccessPointForObjectProcess(OperationOutput output) {
        Object innerBody = null;
        return DeleteAccessPointForObjectProcessResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromGetAccessPointConfigForObjectProcess(GetAccessPointConfigForObjectProcessRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetAccessPointConfigForObjectProcess")
                .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("accessPointConfigForObjectProcess", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetAccessPointConfigForObjectProcessResult toGetAccessPointConfigForObjectProcess(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, GetAccessPointConfigForObjectProcessResultXml.class);

        return GetAccessPointConfigForObjectProcessResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromPutAccessPointConfigForObjectProcess(PutAccessPointConfigForObjectProcessRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("PutAccessPointConfigForObjectProcess")
                .method("PUT");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);


        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("accessPointConfigForObjectProcess", "");
        builder.parameters(parameters);

        // body
        BinaryData body = SerdeUtils.serializeXmlBody(request.putAccessPointConfigForObjectProcessConfiguration());
        builder.body(body);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutAccessPointConfigForObjectProcessResult toPutAccessPointConfigForObjectProcess(OperationOutput output) {
        Object innerBody = null;
        return PutAccessPointConfigForObjectProcessResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromPutAccessPointPolicyForObjectProcess(PutAccessPointPolicyForObjectProcessRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("PutAccessPointPolicyForObjectProcess")
                .method("PUT");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("accessPointPolicyForObjectProcess", "");
        builder.parameters(parameters);

        // body
        builder.body(request.body());

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutAccessPointPolicyForObjectProcessResult toPutAccessPointPolicyForObjectProcess(OperationOutput output) {
        Object innerBody = null;
        return PutAccessPointPolicyForObjectProcessResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromGetAccessPointPolicyForObjectProcess(GetAccessPointPolicyForObjectProcessRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetAccessPointPolicyForObjectProcess")
                .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("accessPointPolicyForObjectProcess", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetAccessPointPolicyForObjectProcessResult toGetAccessPointPolicyForObjectProcess(OperationOutput output) {
        Object innerBody = output.body;

        return GetAccessPointPolicyForObjectProcessResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromDeleteAccessPointPolicyForObjectProcess(DeleteAccessPointPolicyForObjectProcessRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DeleteAccessPointPolicyForObjectProcess")
                .method("DELETE");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("accessPointPolicyForObjectProcess", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static DeleteAccessPointPolicyForObjectProcessResult toDeleteAccessPointPolicyForObjectProcess(OperationOutput output) {
        Object innerBody = null;
        return DeleteAccessPointPolicyForObjectProcessResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromWriteGetObjectResponse(WriteGetObjectResponseRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("WriteGetObjectResponse")
                .method("POST");

        // body
        if (request.body() != null) {
            builder.body(request.body());
        }

        // headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("x-oss-request-route", request.requestRoute());
        headers.put("x-oss-request-token", request.requestToken());
        headers.put("x-oss-fwd-status", request.fwdStatus());

        if (request.fwdHeaderAcceptRanges() != null) {
            headers.put("x-oss-fwd-header-Accept-Ranges", request.fwdHeaderAcceptRanges());
        }
        if (request.fwdHeaderCacheControl() != null) {
            headers.put("x-oss-fwd-header-Cache-Control", request.fwdHeaderCacheControl());
        }
        if (request.fwdHeaderContentDisposition() != null) {
            headers.put("x-oss-fwd-header-Content-Disposition", request.fwdHeaderContentDisposition());
        }
        if (request.fwdHeaderContentEncoding() != null) {
            headers.put("x-oss-fwd-header-Content-Encoding", request.fwdHeaderContentEncoding());
        }
        if (request.fwdHeaderContentLanguage() != null) {
            headers.put("x-oss-fwd-header-Content-Language", request.fwdHeaderContentLanguage());
        }
        if (request.fwdHeaderContentRange() != null) {
            headers.put("x-oss-fwd-header-Content-Range", request.fwdHeaderContentRange());
        }
        if (request.fwdHeaderContentType() != null) {
            headers.put("x-oss-fwd-header-Content-Type", request.fwdHeaderContentType());
        }
        if (request.fwdHeaderEtag() != null) {
            headers.put("x-oss-fwd-header-ETag", request.fwdHeaderEtag());
        }
        if (request.fwdHeaderExpires() != null) {
            headers.put("x-oss-fwd-header-Expires", request.fwdHeaderExpires());
        }
        if (request.fwdHeaderLastModified() != null) {
            headers.put("x-oss-fwd-header-Last-Modified", request.fwdHeaderLastModified());
        }

        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("x-oss-write-get-object-response", "");
        builder.parameters(parameters);

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static WriteGetObjectResponseResult toWriteGetObjectResponse(OperationOutput output) {
        Object innerBody = null;
        return WriteGetObjectResponseResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }
}