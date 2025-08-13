package com.aliyun.sdk.service.oss2.retry;

import com.aliyun.sdk.service.oss2.exceptions.CredentialsFetchException;
import com.aliyun.sdk.service.oss2.exceptions.InconsistentException;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.transport.RequestException;
import com.aliyun.sdk.service.oss2.transport.ResponseException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorRetryableTest {

    @Test
    void testClientErrorRetryable() {

        ErrorRetryable retryable = new ClientErrorRetryable();
        assertThat(retryable.toString()).isNotEmpty();

        assertThat(retryable.isErrorRetryable(new RequestException(""))).isTrue();
        assertThat(retryable.isErrorRetryable(new ResponseException(""))).isTrue();
        assertThat(retryable.isErrorRetryable(new InconsistentException("", "", null))).isTrue();
        assertThat(retryable.isErrorRetryable(new CredentialsFetchException(new Exception()))).isTrue();

        assertThat(retryable.isErrorRetryable(new Exception())).isFalse();
        assertThat(retryable.isErrorRetryable(null)).isFalse();
    }

    @Test
    void testHTTPStatusCodeRetryable() {
        ErrorRetryable retryable = new HTTPStatusCodeRetryable();
        assertThat(retryable.toString()).isNotEmpty();

        assertThat(retryable.isErrorRetryable(ServiceException.newBuilder().statusCode(401).build())).isTrue();
        assertThat(retryable.isErrorRetryable(ServiceException.newBuilder().statusCode(408).build())).isTrue();
        assertThat(retryable.isErrorRetryable(ServiceException.newBuilder().statusCode(429).build())).isTrue();
        assertThat(retryable.isErrorRetryable(ServiceException.newBuilder().statusCode(500).build())).isTrue();
        assertThat(retryable.isErrorRetryable(ServiceException.newBuilder().statusCode(504).build())).isTrue();
        assertThat(retryable.isErrorRetryable(ServiceException.newBuilder().statusCode(599).build())).isTrue();

        assertThat(retryable.isErrorRetryable(ServiceException.newBuilder().statusCode(400).build())).isFalse();
        assertThat(retryable.isErrorRetryable(ServiceException.newBuilder().statusCode(403).build())).isFalse();
        assertThat(retryable.isErrorRetryable(ServiceException.newBuilder().build())).isFalse();
    }

    @Test
    void testServiceErrorCodeRetryable() {
        ErrorRetryable retryable = new ServiceErrorCodeRetryable();
        assertThat(retryable.toString()).isNotEmpty();

        Map<String, String> errorFields = new HashMap<>();
        errorFields.put("Code", "BadRequest");
        assertThat(retryable.isErrorRetryable(
                ServiceException.newBuilder()
                        .statusCode(401)
                        .errorFields(errorFields)
                        .build()))
                .isTrue();

        errorFields.put("Code", "RequestTimeTooSkewed");
        assertThat(retryable.isErrorRetryable(
                ServiceException.newBuilder()
                        .statusCode(401)
                        .errorFields(errorFields)
                        .build()))
                .isTrue();

        errorFields.put("Code", "UnSupportCode");
        assertThat(retryable.isErrorRetryable(
                ServiceException.newBuilder()
                        .statusCode(401)
                        .errorFields(errorFields)
                        .build()))
                .isFalse();

        assertThat(retryable.isErrorRetryable(
                ServiceException.newBuilder()
                        .statusCode(401)
                        .build()))
                .isFalse();

        assertThat(retryable.isErrorRetryable(
                new Exception()))
                .isFalse();

        assertThat(retryable.isErrorRetryable(
                null))
                .isFalse();
    }

    @Test
    void testToString() {
    }
}