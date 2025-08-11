package com.aliyun.sdk.service.oss2.exceptions;

import org.junit.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class ServiceErrorTest {

    @Test
    public void testEmptyServiceError() {
        ServiceError error = ServiceError.newBuilder().build();
        assertNotNull(error);

        assertSame(0, error.statusCode());
        assertTrue(error.errorFields().isEmpty());
        assertTrue(error.headers().isEmpty());
        assertSame("BadErrorResponse", error.errorCode());
        assertSame("", error.errorMessage());
        assertSame("", error.requestId());
        assertSame("", error.ec());
        assertSame("", error.requestTarget());
        assertNotNull(error.timestamp());
        assertNull(error.snapshot());

        assertThat(error).hasMessageContaining("Http Status Code: 0");
        assertThat(error).hasMessageContaining("Error Code: BadErrorResponse");
        assertThat(error).hasMessageContaining("Request Id: .");
        assertThat(error).hasMessageContaining("Message: .");
        assertThat(error).hasMessageContaining("EC: .");
        assertThat(error).hasMessageContaining("Timestamp: null.");
        assertThat(error).hasMessageContaining("Request Endpoint: null.");
    }

    @Test
    public void testFullServiceError() {
        Map<String, String> errorFields = new HashMap<String, String>() {
        };
        errorFields.put("Code", "MalformedXML");
        errorFields.put("Message", "The XML you provided was not well-formed or did not validate against our published schema.");
        errorFields.put("RequestId", "57ABD896CCB80C366955****");
        errorFields.put("EC", "0031-00000001");

        Map<String, String> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        headers.put("x-oss-request-id", "636B68BA80DA8539399F****");
        headers.put("x-oss-ec", "0003-00000001");

        int statusCode = 403;
        String requestTarget = "http://oss-cn-hangzhou.aliyuncs.com/1.txt";
        Instant timestamp = Instant.now();
        byte[] payload = "hello".getBytes();

        ServiceError error = ServiceError.newBuilder()
                .statusCode(statusCode)
                .headers(headers)
                .errorFields(errorFields)
                .requestTarget(requestTarget)
                .timestamp(timestamp)
                .snapshot(payload)
                .build();
        assertNotNull(error);

        assertEquals(statusCode, error.statusCode());
        assertSame("MalformedXML", error.errorCode());
        assertSame("The XML you provided was not well-formed or did not validate against our published schema.", error.errorMessage());
        assertSame("57ABD896CCB80C366955****", error.requestId());
        assertSame("0031-00000001", error.ec());
        assertSame(requestTarget, error.requestTarget());
        assertSame(timestamp, error.timestamp());
        assertSame(payload, error.snapshot());
        assertThat(error).hasMessageContaining("Http Status Code: 403");
        assertThat(error).hasMessageContaining("Error Code: MalformedXML");
        assertThat(error).hasMessageContaining("Request Id: 57ABD896CCB80C366955****");
        assertThat(error).hasMessageContaining("Message: The XML you provided was not well-formed or ");
        assertThat(error).hasMessageContaining("EC: 0031-00000001");
        assertThat(error).hasMessageContaining("Timestamp:");
        assertThat(error).hasMessageContaining("Request Endpoint: http://oss-cn-hangzhou.aliyuncs.com/1.txt.");

        // not errorFields
        error = ServiceError.newBuilder()
                .statusCode(statusCode)
                .headers(headers)
                .requestTarget(requestTarget)
                .timestamp(timestamp)
                .snapshot(payload)
                .build();
        assertNotNull(error);

        assertEquals(statusCode, error.statusCode());
        assertSame("BadErrorResponse", error.errorCode());
        assertSame("", error.errorMessage());
        assertSame("636B68BA80DA8539399F****", error.requestId());
        assertSame("0003-00000001", error.ec());
        assertSame(requestTarget, error.requestTarget());
        assertSame(timestamp, error.timestamp());
        assertSame(payload, error.snapshot());
        assertThat(error).hasMessageContaining("Http Status Code: 403");
        assertThat(error).hasMessageContaining("Error Code: BadErrorResponse");
        assertThat(error).hasMessageContaining("Request Id: 636B68BA80DA8539399F****");
        assertThat(error).hasMessageContaining("Message: .");
        assertThat(error).hasMessageContaining("EC: 0003-00000001");
        assertThat(error).hasMessageContaining("Timestamp:");
        assertThat(error).hasMessageContaining("Request Endpoint: http://oss-cn-hangzhou.aliyuncs.com/1.txt.");
    }
}
