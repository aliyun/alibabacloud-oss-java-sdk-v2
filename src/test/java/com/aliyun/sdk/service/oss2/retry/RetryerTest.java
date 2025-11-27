package com.aliyun.sdk.service.oss2.retry;

import com.aliyun.sdk.service.oss2.exceptions.CredentialsFetchException;
import com.aliyun.sdk.service.oss2.exceptions.InconsistentException;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.transport.RequestException;
import com.aliyun.sdk.service.oss2.transport.ResponseException;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RetryerTest {

    @Test
    void testStandardRetryDefault() {
        Retryer retry = StandardRetryer.newBuilder().build();
        assertThat(retry.maxAttempts()).isEqualTo(3);

        // retryable test
        assertThat(retry.isErrorRetryable(new RequestException(""))).isTrue();
        assertThat(retry.isErrorRetryable(new ResponseException(""))).isTrue();
        assertThat(retry.isErrorRetryable(new InconsistentException("", "", null))).isTrue();
        assertThat(retry.isErrorRetryable(new CredentialsFetchException(new Exception()))).isTrue();

        assertThat(retry.isErrorRetryable(new Exception())).isFalse();
        assertThat(retry.isErrorRetryable(null)).isFalse();

        assertThat(retry.isErrorRetryable(ServiceException.newBuilder().statusCode(401).build())).isTrue();
        assertThat(retry.isErrorRetryable(ServiceException.newBuilder().statusCode(408).build())).isTrue();
        assertThat(retry.isErrorRetryable(ServiceException.newBuilder().statusCode(429).build())).isTrue();
        assertThat(retry.isErrorRetryable(ServiceException.newBuilder().statusCode(500).build())).isTrue();
        assertThat(retry.isErrorRetryable(ServiceException.newBuilder().statusCode(504).build())).isTrue();
        assertThat(retry.isErrorRetryable(ServiceException.newBuilder().statusCode(599).build())).isTrue();

        assertThat(retry.isErrorRetryable(ServiceException.newBuilder().statusCode(400).build())).isFalse();
        assertThat(retry.isErrorRetryable(ServiceException.newBuilder().statusCode(403).build())).isFalse();
        assertThat(retry.isErrorRetryable(ServiceException.newBuilder().build())).isFalse();

        Map<String, String> errorFields = new HashMap<>();
        errorFields.put("Code", "BadRequest");
        assertThat(retry.isErrorRetryable(
                ServiceException.newBuilder()
                        .statusCode(400)
                        .errorFields(errorFields)
                        .build()))
                .isTrue();

        errorFields.put("Code", "RequestTimeTooSkewed");
        assertThat(retry.isErrorRetryable(
                ServiceException.newBuilder()
                        .statusCode(403)
                        .errorFields(errorFields)
                        .build()))
                .isTrue();

        assertThat(retry.isErrorRetryable(
                ServiceException.newBuilder()
                        .statusCode(400)
                        .build()))
                .isFalse();

        //delay test
        Duration min = Duration.ofSeconds(0);
        Duration max = Duration.ofSeconds(20);
        Exception e = new Exception();
        assertThat(retry.retryDelay(0, e)).isGreaterThan(min);

        for (int i = 0; i < 128; i++) {
            Duration val = retry.retryDelay(i, e);
            assertThat(val).isGreaterThan(min);
            assertThat(val).isLessThanOrEqualTo(max);
        }
    }

    @Test
    void testStandardRetryWithCustomRetryable() {
        // Arrange
        ErrorRetryable r1 = new TestErrorRetryable();
        ErrorRetryable r2 = new ServiceErrorCodeRetryable();

        StandardRetryer retry = StandardRetryer.newBuilder()
                .maxAttempts(3)
                .errorRetryable(Arrays.asList(r1, r2))
                .build();

        // retryable test
        assertThat(retry.isErrorRetryable(new RequestException(""))).isFalse();
        assertThat(retry.isErrorRetryable(new ResponseException(""))).isFalse();
        assertThat(retry.isErrorRetryable(new InconsistentException("", "", null))).isFalse();
        assertThat(retry.isErrorRetryable(new CredentialsFetchException(new Exception()))).isFalse();

        assertThat(retry.isErrorRetryable(ServiceException.newBuilder().statusCode(401).build())).isFalse();
        assertThat(retry.isErrorRetryable(ServiceException.newBuilder().statusCode(408).build())).isFalse();
        assertThat(retry.isErrorRetryable(ServiceException.newBuilder().statusCode(429).build())).isFalse();
        assertThat(retry.isErrorRetryable(ServiceException.newBuilder().statusCode(500).build())).isFalse();
        assertThat(retry.isErrorRetryable(ServiceException.newBuilder().statusCode(599).build())).isFalse();

        assertThat(retry.isErrorRetryable(ServiceException.newBuilder().statusCode(400).build())).isFalse();

        Map<String, String> errorFields = new HashMap<>();
        errorFields.put("Code", "BadRequest");
        assertThat(retry.isErrorRetryable(
                ServiceException.newBuilder()
                        .statusCode(400)
                        .errorFields(errorFields)
                        .build()))
                .isTrue();

        errorFields.put("Code", "RequestTimeTooSkewed");
        assertThat(retry.isErrorRetryable(
                ServiceException.newBuilder()
                        .statusCode(403)
                        .errorFields(errorFields)
                        .build()))
                .isTrue();

        assertThat(retry.isErrorRetryable(
                ServiceException.newBuilder()
                        .statusCode(400)
                        .build()))
                .isFalse();
    }

    @Test
    void testStandardRetryWithCustomMaxAttempts() {

        StandardRetryer retry = StandardRetryer.newBuilder()
                .maxAttempts(4)
                .build();

        assertThat(retry.maxAttempts()).isEqualTo(4);

        // retryable test
        assertThat(retry.isErrorRetryable(new RequestException(""))).isTrue();
        assertThat(retry.isErrorRetryable(new Exception())).isFalse();
        assertThat(retry.isErrorRetryable(null)).isFalse();

        assertThat(retry.isErrorRetryable(ServiceException.newBuilder().statusCode(401).build())).isTrue();
        assertThat(retry.isErrorRetryable(ServiceException.newBuilder().statusCode(599).build())).isTrue();
        assertThat(retry.isErrorRetryable(ServiceException.newBuilder().statusCode(400).build())).isFalse();

        Map<String, String> errorFields = new HashMap<>();
        errorFields.put("Code", "BadRequest");
        assertThat(retry.isErrorRetryable(
                ServiceException.newBuilder()
                        .statusCode(400)
                        .errorFields(errorFields)
                        .build()))
                .isTrue();

        assertThat(retry.isErrorRetryable(
                ServiceException.newBuilder()
                        .statusCode(400)
                        .build()))
                .isFalse();
    }

    @Test
    void testStandardRetryWithCustomBackoff() {

        StandardRetryer retry = StandardRetryer.newBuilder()
                .backoffDelayer(new FixedDelayBackoff(Duration.ofSeconds(3)))
                .build();

        assertThat(retry.maxAttempts()).isEqualTo(3);

        // retryable test
        assertThat(retry.isErrorRetryable(new RequestException(""))).isTrue();
        assertThat(retry.isErrorRetryable(new Exception())).isFalse();
        assertThat(retry.isErrorRetryable(null)).isFalse();

        assertThat(retry.isErrorRetryable(ServiceException.newBuilder().statusCode(401).build())).isTrue();
        assertThat(retry.isErrorRetryable(ServiceException.newBuilder().statusCode(599).build())).isTrue();
        assertThat(retry.isErrorRetryable(ServiceException.newBuilder().statusCode(400).build())).isFalse();

        Map<String, String> errorFields = new HashMap<>();
        errorFields.put("Code", "BadRequest");
        assertThat(retry.isErrorRetryable(
                ServiceException.newBuilder()
                        .statusCode(400)
                        .errorFields(errorFields)
                        .build()))
                .isTrue();

        assertThat(retry.isErrorRetryable(
                ServiceException.newBuilder()
                        .statusCode(400)
                        .build()))
                .isFalse();

        //dealy test
        for (int i = 0; i < 128; i++) {
            Duration val = retry.retryDelay(i, null);
            assertThat(val).isEqualTo(Duration.ofSeconds(3));
        }
    }

    @Test
    void testNopRetryDefault() {
        Retryer retryer = new NopRetryer();
        assertThat(retryer.maxAttempts()).isEqualTo(1);
        assertThat(retryer.isErrorRetryable(ServiceException.newBuilder().statusCode(401).build())).isFalse();
        assertThat(retryer.retryDelay(1, null)).isEqualTo(Duration.ofSeconds(0));
    }

    static class TestErrorRetryable implements ErrorRetryable {
        @Override
        public boolean isErrorRetryable(Throwable error) {
            return false;
        }

        @Override
        public String toString() {
            return "<TestErrorRetryable>";
        }
    }
}


