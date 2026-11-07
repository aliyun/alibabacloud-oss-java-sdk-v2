package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;

public final class SerdeBatchOperations {

    // ==================== CreateJob ====================

    public static OperationInput fromCreateJob(CreateJobRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("CreateJob")
                .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("batchJob", "");
        builder.parameters(parameters);

        // body
        BinaryData body = SerdeUtils.serializeXmlBody(request.createJobBody());
        builder.body(body);

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static CreateJobResult toCreateJob(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, CreateJobResultBody.class);

        return CreateJobResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    // ==================== DescribeJob ====================

    public static OperationInput fromDescribeJob(DescribeJobRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DescribeJob")
                .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("batchJob", "");
        builder.parameters(parameters);

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static DescribeJobResult toDescribeJob(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, DescribeJobResultBody.class);

        return DescribeJobResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    // ==================== ListJobs ====================

    public static OperationInput fromListJobs(ListJobsRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("ListJobs")
                .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("batchJob", "");
        builder.parameters(parameters);

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static ListJobsResult toListJobs(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, ListJobsResultBody.class);

        return ListJobsResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    // ==================== UpdateJobPriority ====================

    public static OperationInput fromUpdateJobPriority(UpdateJobPriorityRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("UpdateJobPriority")
                .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("batchJobPriority", "");
        builder.parameters(parameters);

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static UpdateJobPriorityResult toUpdateJobPriority(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, UpdateJobPriorityResultBody.class);

        return UpdateJobPriorityResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    // ==================== UpdateJobStatus ====================

    public static OperationInput fromUpdateJobStatus(UpdateJobStatusRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("UpdateJobStatus")
                .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("batchJobStatus", "");
        builder.parameters(parameters);

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static UpdateJobStatusResult toUpdateJobStatus(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, UpdateJobStatusResultBody.class);

        return UpdateJobStatusResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }
}
