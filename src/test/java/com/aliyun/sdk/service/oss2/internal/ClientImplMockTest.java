package com.aliyun.sdk.service.oss2.internal;

import com.aliyun.sdk.service.oss2.*;
import com.aliyun.sdk.service.oss2.credentials.AnonymousCredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.Credentials;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.exceptions.InconsistentException;
import com.aliyun.sdk.service.oss2.exceptions.OperationException;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.hash.CRC64;
import com.aliyun.sdk.service.oss2.hash.CRC64Observer;
import com.aliyun.sdk.service.oss2.hash.CRC64ResponseChecker;
import com.aliyun.sdk.service.oss2.io.ObservableInputStream;
import com.aliyun.sdk.service.oss2.progress.ProgressListener;
import com.aliyun.sdk.service.oss2.progress.ProgressObserver;
import com.aliyun.sdk.service.oss2.retry.FixedDelayBackoff;
import com.aliyun.sdk.service.oss2.retry.NopRetryer;
import com.aliyun.sdk.service.oss2.retry.StandardRetryer;
import com.aliyun.sdk.service.oss2.transport.*;
import com.aliyun.sdk.service.oss2.utils.Base64Utils;
import com.aliyun.sdk.service.oss2.utils.IOUtils;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.zip.Checksum;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class ClientImplMockTest {

    static String ErrorXml =
            "<Error>\n" +
                    "  <Code>InvalidAccessKeyId</Code>\n" +
                    "  <Message>The OSS Access Key Id you provided does not exist in our records.</Message>\n" +
                    "  <RequestId>id-1234</RequestId>\n" +
                    "  <HostId>oss-cn-hangzhou.aliyuncs.com</HostId>\n" +
                    "  <OSSAccessKeyId>ak</OSSAccessKeyId>\n" +
                    "  <EC>0002-00000902</EC>\n" +
                    "  <RecommendDoc>https://api.aliyun.com/troubleshoot?q=0002-00000902</RecommendDoc>\n" +
                    "</Error>";

    private static String getNowDateAsString() {
        Instant now = Instant.now();
        return now.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static String repeatString(String value, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count is negative: " + count);
        }
        if (value == null || value.isEmpty() || count == 1) {
            return value;
        }
        if (count == 0) {
            return "";
        }
        if (value.length() > Integer.MAX_VALUE / count) {
            throw new OutOfMemoryError("Repeating " + value.length() + " bytes String " + count +
                    " times will produce a String exceeding maximum size.");
        }
        int len = value.length();
        int limit = len * count;
        char[] array = new char[limit];
        value.getChars(0, len, array, 0);
        int copied;
        for (copied = len; copied < limit - copied; copied <<= 1) {
            System.arraycopy(array, 0, array, copied, copied);
        }
        System.arraycopy(array, 0, array, copied, limit - copied);
        return new String(array);
    }

    public static <T extends Throwable> T findCause(Throwable throwable, Class<T> clazz) {
        if (throwable == null || clazz == null) {
            return null;
        }

        Throwable current = throwable;
        while (current != null) {
            if (clazz.isInstance(current)) {
                return clazz.cast(current);
            }
            current = current.getCause();
        }
        return null;
    }

    @Test
    public void invokeOperationSuccess() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(200)
                .body(new StringBinaryData(""))
                .build());

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();

            OperationOutput output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(output.statusCode()).isEqualTo(200);
            assertThat(output.status()).isEqualTo("");
            assertThat(output.headers()).hasSize(0);
            assertThat(output.input()).hasValue(input);
            assertThat(output.body()).isPresent();
            assertThat(output.opMetadata()).isNotPresent();
        }
    }

    @Test
    public void invokeOperationFail() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(403)
                .body(new StringBinaryData(ErrorXml))
                .build());

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();

            try {
                client.execute(input, OperationOptions.defaults());
                Assert.fail("should not here");
            } catch (OperationException e) {
                assertThat(mockHandler.requests).hasSize(1);
                assertThat(e.getCause()).isInstanceOf(ServiceException.class);
                ServiceException serr = (ServiceException) e.getCause();
                assertThat(serr.statusCode()).isEqualTo(403);
                assertThat(serr.errorCode()).isEqualTo("InvalidAccessKeyId");
                assertThat(serr.errorMessage()).isEqualTo("The OSS Access Key Id you provided does not exist in our records.");
                assertThat(serr.requestId()).isEqualTo("id-1234");
                assertThat(serr.ec()).isEqualTo("0002-00000902");

                assertThat(serr.errorFields().get("HostId")).isEqualTo("oss-cn-hangzhou.aliyuncs.com");
                assertThat(serr.errorFields().get("RecommendDoc")).isEqualTo("https://api.aliyun.com/troubleshoot?q=0002-00000902");
                assertThat(serr.errorFields().get("OSSAccessKeyId")).isEqualTo("ak");
            }
        }
    }

    @Test
    public void invokeOperationAsyncSuccess() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(200)
                .body(new StringBinaryData(""))
                .build());

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();

            CompletableFuture<OperationOutput> futureOutput = client.executeAsync(input, OperationOptions.defaults());
            OperationOutput output = futureOutput.get();
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(output.statusCode()).isEqualTo(200);
            assertThat(output.status()).isEqualTo("");
            assertThat(output.headers()).hasSize(0);
            assertThat(output.input()).hasValue(input);
            assertThat(output.body()).isPresent();
            assertThat(output.opMetadata()).isNotPresent();
        }
    }

    @Test
    public void invokeOperationAsyncFail() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(403)
                .body(new StringBinaryData(ErrorXml))
                .build());

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();

            try {
                CompletableFuture<OperationOutput> futureOutput = client.executeAsync(input, OperationOptions.defaults());
                futureOutput.get();
                Assert.fail("should not here");
            } catch (Exception e) {
                assertThat(mockHandler.requests).hasSize(1);
                assertThat(e.getCause()).isInstanceOf(OperationException.class);
                OperationException opErr = (OperationException) e.getCause();
                ServiceException serr = (ServiceException) opErr.contains(ServiceException.class);
                assertThat(serr.statusCode()).isEqualTo(403);
                assertThat(serr.errorCode()).isEqualTo("InvalidAccessKeyId");
                assertThat(serr.errorMessage()).isEqualTo("The OSS Access Key Id you provided does not exist in our records.");
                assertThat(serr.requestId()).isEqualTo("id-1234");
                assertThat(serr.ec()).isEqualTo("0002-00000902");

                assertThat(serr.errorFields().get("HostId")).isEqualTo("oss-cn-hangzhou.aliyuncs.com");
                assertThat(serr.errorFields().get("RecommendDoc")).isEqualTo("https://api.aliyun.com/troubleshoot?q=0002-00000902");
                assertThat(serr.errorFields().get("OSSAccessKeyId")).isEqualTo("ak");
            }
        }
    }

    @Test
    public void verifyExecuteArgsInvalidEndpoint() throws Exception {
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .endpoint("...")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build();

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();

            try {
                client.execute(input, OperationOptions.defaults());
                Assert.fail("should not here");
            } catch (Exception e) {
                assertThat(e).hasMessageContaining("endpoint or region is invalid");
            }
        }
    }

    @Test
    public void verifyExecuteArgsInvalidMethod() throws Exception {
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build();

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();

            try {
                client.execute(input, OperationOptions.defaults());
                Assert.fail("should not here");
            } catch (Exception e) {
                assertThat(e).hasMessageContaining("input.method is null or empty");
            }
        }
    }

    @Test
    public void verifyExecuteArgsInvalidBucketName() throws Exception {
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build();

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("12")
                    .build();

            try {
                client.execute(input, OperationOptions.defaults());
                Assert.fail("should not here");
            } catch (Exception e) {
                assertThat(e).hasMessageContaining("input.bucket is invalid, got 12.");
            }
        }
    }

    @Test
    public void verifyExecuteArgsInvalidObjectName() throws Exception {
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build();

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .key(repeatString("1", 1024))
                    .build();

            try {
                client.execute(input, OperationOptions.defaults());
                Assert.fail("should not here");
            } catch (Exception e) {
                assertThat(e).hasMessageContaining("input.key is invalid");
            }
        }
    }

    @Test
    public void verifyExecuteAsyncArgs() throws Exception {
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build();

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("12")
                    .build();

            try {
                client.executeAsync(input, OperationOptions.defaults()).get();
                Assert.fail("should not here");
            } catch (Exception e) {
                assertThat(e).hasMessageContaining("input.bucket is invalid, got 12.");
            }
        }
    }

    @Test
    public void configRetryMaxAttemptsFromClientOptions() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        // default max retry attempts is 3
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(500)
                .body(new StringBinaryData(""))
                .build());
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(500)
                .body(new StringBinaryData(""))
                .build());
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(500)
                .body(new StringBinaryData(""))
                .build());
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(500)
                .body(new StringBinaryData(""))
                .build());

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();

            try {
                client.execute(input, OperationOptions.defaults());
                Assert.fail("should not here");
            } catch (OperationException e) {
                assertThat(mockHandler.requests).hasSize(Defaults.MAX_ATTEMPTS);
                assertThat(mockHandler.responses).hasSize(1);
                assertThat(e.getCause()).isInstanceOf(ServiceException.class);
                ServiceException serr = (ServiceException) e.getCause();
                assertThat(serr.statusCode()).isEqualTo(500);
                assertThat(serr.errorCode()).isEqualTo("BadErrorResponse");
            }
        }

        //  max retry attempts is 4
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .retryMaxAttempts(4)
                .httpClient(mockHandler)
                .build();

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(500)
                .body(new StringBinaryData(""))
                .build());
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(500)
                .body(new StringBinaryData(""))
                .build());
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(500)
                .body(new StringBinaryData(""))
                .build());
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(500)
                .body(new StringBinaryData(""))
                .build());

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();

            try {
                client.execute(input, OperationOptions.defaults());
                Assert.fail("should not here");
            } catch (OperationException e) {
                assertThat(mockHandler.requests).hasSize(4);
                assertThat(e.getCause()).isInstanceOf(ServiceException.class);
                ServiceException serr = (ServiceException) e.getCause();
                assertThat(serr.statusCode()).isEqualTo(500);
                assertThat(serr.errorCode()).isEqualTo("BadErrorResponse");
            }
        }
    }

    @Test
    public void configRetryMaxAttemptsFromOperationOptions() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        // default max retry attempts is 3
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(500)
                .body(new StringBinaryData(""))
                .build());
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(500)
                .body(new StringBinaryData(""))
                .build());
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(500)
                .body(new StringBinaryData(""))
                .build());
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(500)
                .body(new StringBinaryData(""))
                .build());

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();

            try {
                client.execute(input, OperationOptions.defaults());
                Assert.fail("should not here");
            } catch (OperationException e) {
                assertThat(mockHandler.requests).hasSize(Defaults.MAX_ATTEMPTS);
                assertThat(mockHandler.responses).hasSize(1);
                assertThat(e.getCause()).isInstanceOf(ServiceException.class);
                ServiceException serr = (ServiceException) e.getCause();
                assertThat(serr.statusCode()).isEqualTo(500);
                assertThat(serr.errorCode()).isEqualTo("BadErrorResponse");
            }

            //  set Operation retry attempts to 2
            mockHandler.clear();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(500)
                    .body(new StringBinaryData(""))
                    .build());
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(500)
                    .body(new StringBinaryData(""))
                    .build());
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(500)
                    .body(new StringBinaryData(""))
                    .build());
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(500)
                    .body(new StringBinaryData(""))
                    .build());

            try {
                client.execute(input, OperationOptions.newBuilder().retryMaxAttempts(2).build());
                Assert.fail("should not here");
            } catch (OperationException e) {
                assertThat(mockHandler.requests).hasSize(2);
                assertThat(e.getCause()).isInstanceOf(ServiceException.class);
                ServiceException serr = (ServiceException) e.getCause();
                assertThat(serr.statusCode()).isEqualTo(500);
                assertThat(serr.errorCode()).isEqualTo("BadErrorResponse");
            }
        }
    }

    @Test
    public void configRetryMaxAttemptsNopRetryer() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .retryer(new NopRetryer())
                .build();

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(500)
                .body(new StringBinaryData(""))
                .build());
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(500)
                .body(new StringBinaryData(""))
                .build());
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(500)
                .body(new StringBinaryData(""))
                .build());
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(500)
                .body(new StringBinaryData(""))
                .build());

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();

            try {
                client.execute(input, OperationOptions.defaults());
                Assert.fail("should not here");
            } catch (OperationException e) {
                assertThat(mockHandler.requests).hasSize(1);
                assertThat(e.getCause()).isInstanceOf(ServiceException.class);
                ServiceException serr = (ServiceException) e.getCause();
                assertThat(serr.statusCode()).isEqualTo(500);
                assertThat(serr.errorCode()).isEqualTo("BadErrorResponse");
            }
        }
    }

    @Test
    public void configNoRetryError() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(403)
                .body(new StringBinaryData(ErrorXml))
                .build());
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(403)
                .body(new StringBinaryData(ErrorXml))
                .build());
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(403)
                .body(new StringBinaryData(ErrorXml))
                .build());
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(403)
                .body(new StringBinaryData(ErrorXml))
                .build());

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();

            try {
                client.execute(input, OperationOptions.defaults());
                Assert.fail("should not here");
            } catch (OperationException e) {
                assertThat(mockHandler.requests).hasSize(1);
                assertThat(e.getCause()).isInstanceOf(ServiceException.class);
                ServiceException serr = (ServiceException) e.getCause();
                assertThat(serr.statusCode()).isEqualTo(403);
                assertThat(serr.errorCode()).isEqualTo("InvalidAccessKeyId");
            }
        }
    }

    @Test
    public void configSeekableStream() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(500)
                .body(new StringBinaryData(""))
                .build());
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(501)
                .body(new StringBinaryData(""))
                .build());
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(502)
                .body(new StringBinaryData(""))
                .build());

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .body(BinaryData.fromStream(
                            new ByteArrayInputStream("hello world".getBytes())))
                    .build();

            try {
                client.execute(input, OperationOptions.defaults());
                Assert.fail("should not here");
            } catch (OperationException e) {
                assertThat(mockHandler.requests).hasSize(3);
                assertThat(e.getCause()).isInstanceOf(ServiceException.class);
                ServiceException serr = (ServiceException) e.getCause();
                assertThat(serr.statusCode()).isEqualTo(502);
                assertThat(serr.errorCode()).isEqualTo("BadErrorResponse");
            }
        }
    }

    @Test
    public void configNoSeekableStream() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(500)
                .body(new StringBinaryData(""))
                .build());
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(501)
                .body(new StringBinaryData(""))
                .build());
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(502)
                .body(new StringBinaryData(""))
                .build());

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .body(BinaryData.fromStream(
                            new NotRetryableStream(new ByteArrayInputStream("hello world".getBytes()))))
                    .build();

            try {
                client.execute(input, OperationOptions.defaults());
                Assert.fail("should not here");
            } catch (OperationException e) {
                assertThat(mockHandler.requests).hasSize(1);
                assertThat(e.getCause()).isInstanceOf(ServiceException.class);
                ServiceException serr = (ServiceException) e.getCause();
                assertThat(serr.statusCode()).isEqualTo(500);
                assertThat(serr.errorCode()).isEqualTo("BadErrorResponse");
            }
        }
    }

    @Test
    public void configSeekableStreamAsync() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(500)
                .body(new StringBinaryData(""))
                .build());
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(501)
                .body(new StringBinaryData(""))
                .build());
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(502)
                .body(new StringBinaryData(""))
                .build());

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .body(BinaryData.fromStream(
                            new ByteArrayInputStream("hello world".getBytes())))
                    .build();

            try {
                client.executeAsync(input, OperationOptions.defaults()).get();
                Assert.fail("should not here");
            } catch (Exception e) {
                assertThat(mockHandler.requests).hasSize(3);
                ServiceException serr = findCause(e, ServiceException.class);
                assertThat(serr.statusCode()).isEqualTo(502);
                assertThat(serr.errorCode()).isEqualTo("BadErrorResponse");
            }
        }
    }

    @Test
    public void configNoSeekableStreamAsync() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(500)
                .body(new StringBinaryData(""))
                .build());
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(501)
                .body(new StringBinaryData(""))
                .build());
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(502)
                .body(new StringBinaryData(""))
                .build());

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .body(BinaryData.fromStream(
                            new NotRetryableStream(new ByteArrayInputStream("hello world".getBytes()))))
                    .build();

            try {
                client.executeAsync(input, OperationOptions.defaults()).get();
                Assert.fail("should not here");
            } catch (Exception e) {
                assertThat(mockHandler.requests).hasSize(1);
                ServiceException serr = findCause(e, ServiceException.class);
                assertThat(serr.statusCode()).isEqualTo(500);
                assertThat(serr.errorCode()).isEqualTo("BadErrorResponse");
            }
        }
    }

    @Test
    public void configRetryMaxAttemptsAsync() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        // set max retry attempts to 2
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .retryMaxAttempts(2)
                .httpClient(mockHandler)
                .build();

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();


            mockHandler.clear();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(500)
                    .body(new StringBinaryData(""))
                    .build());
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(500)
                    .body(new StringBinaryData(""))
                    .build());
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(500)
                    .body(new StringBinaryData(""))
                    .build());
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(500)
                    .body(new StringBinaryData(""))
                    .build());

            try {
                client.executeAsync(input, OperationOptions.defaults()).get();
                Assert.fail("should not here");
            } catch (Exception e) {
                assertThat(mockHandler.requests).hasSize(2);
                ServiceException serr = findCause(e, ServiceException.class);
                assertThat(serr.statusCode()).isEqualTo(500);
                assertThat(serr.errorCode()).isEqualTo("BadErrorResponse");
            }

            //  set Operation retry attempts to 4
            mockHandler.clear();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(500)
                    .body(new StringBinaryData(""))
                    .build());
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(500)
                    .body(new StringBinaryData(""))
                    .build());
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(500)
                    .body(new StringBinaryData(""))
                    .build());
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(500)
                    .body(new StringBinaryData(""))
                    .build());

            try {
                client.execute(input, OperationOptions.newBuilder().retryMaxAttempts(4).build());
                Assert.fail("should not here");
            } catch (Exception e) {
                assertThat(mockHandler.requests).hasSize(4);
                ServiceException serr = findCause(e, ServiceException.class);
                assertThat(serr.statusCode()).isEqualTo(500);
                assertThat(serr.errorCode()).isEqualTo("BadErrorResponse");
            }
        }
    }

    @Test
    public void checkBackoffSleepTime() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        // max retry attempts is 3, delay backoff is 2s
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .retryer(StandardRetryer.newBuilder()
                        .backoffDelayer(new FixedDelayBackoff(Duration.ofSeconds(2)))
                        .build())
                .httpClient(mockHandler)
                .build();


        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            // sync
            mockHandler.clear();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(500)
                    .body(new StringBinaryData(""))
                    .build());
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(500)
                    .body(new StringBinaryData(""))
                    .build());
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(500)
                    .body(new StringBinaryData(""))
                    .build());
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(500)
                    .body(new StringBinaryData(""))
                    .build());

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();

            try {
                client.execute(input, OperationOptions.defaults());
                Assert.fail("should not here");
            } catch (OperationException e) {
                assertThat(mockHandler.requests).hasSize(Defaults.MAX_ATTEMPTS);
                assertThat(mockHandler.responses).hasSize(1);
                assertThat(e.getCause()).isInstanceOf(ServiceException.class);
                ServiceException serr = (ServiceException) e.getCause();
                assertThat(serr.statusCode()).isEqualTo(500);
                assertThat(serr.errorCode()).isEqualTo("BadErrorResponse");
                assertThat(mockHandler.requestDates.get(0)).isNotEqualTo(
                        mockHandler.requestDates.get(1));
                assertThat(mockHandler.requestDates.get(0)).isNotEqualTo(
                        mockHandler.requestDates.get(2));
                assertThat(mockHandler.requestDates.get(1)).isNotEqualTo(
                        mockHandler.requestDates.get(2));
            }

            // async
            mockHandler.clear();
            assertThat(mockHandler.requests).isNull();
            assertThat(mockHandler.requestDates).isNull();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(500)
                    .body(new StringBinaryData(""))
                    .build());
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(500)
                    .body(new StringBinaryData(""))
                    .build());
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(500)
                    .body(new StringBinaryData(""))
                    .build());
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(500)
                    .body(new StringBinaryData(""))
                    .build());

            try {
                client.executeAsync(input, OperationOptions.defaults()).get();
                Assert.fail("should not here");
            } catch (Exception e) {
                assertThat(mockHandler.requests).hasSize(Defaults.MAX_ATTEMPTS);
                assertThat(mockHandler.responses).hasSize(1);
                ServiceException serr = findCause(e, ServiceException.class);
                assertThat(serr.statusCode()).isEqualTo(500);
                assertThat(serr.errorCode()).isEqualTo("BadErrorResponse");
                assertThat(mockHandler.requestDates.get(0)).isNotEqualTo(
                        mockHandler.requestDates.get(1));
                assertThat(mockHandler.requestDates.get(0)).isNotEqualTo(
                        mockHandler.requestDates.get(2));
                assertThat(mockHandler.requestDates.get(1)).isNotEqualTo(
                        mockHandler.requestDates.get(2));
            }
        }
    }

    @Test
    public void configSignerV4() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(200)
                .body(new StringBinaryData(""))
                .build());

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();

            OperationOutput output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(1);
            String date = getNowDateAsString();
            assertThat(mockHandler.lastRequest.headers().get("Authorization"))
                    .startsWith("OSS4-HMAC-SHA256 Credential=ak/" + date + "/cn-hangzhou/oss/aliyun_v4_request,Signature=");
            assertThat(mockHandler.lastRequest.headers().get("x-oss-content-sha256"))
                    .isEqualTo("UNSIGNED-PAYLOAD");
            assertThat(mockHandler.lastRequest.headers().get("x-oss-date"))
                    .startsWith(date);
        }
    }

    @Test
    public void configSignerV1() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .signatureVersion("v1")
                .httpClient(mockHandler)
                .build();

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(200)
                .body(new StringBinaryData(""))
                .build());

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();

            OperationOutput output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.headers().get("Authorization"))
                    .startsWith("OSS ak:");
        }
    }

    @Test
    public void sendAnonymousRequest() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .httpClient(mockHandler)
                .build();

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(200)
                .body(new StringBinaryData(""))
                .build());

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();

            OperationOutput output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.headers().get("Authorization")).isNull();
        }
    }

    @Test
    public void returnsEmptyCredentials() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("", ""))
                .httpClient(mockHandler)
                .build();

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(200)
                .body(new StringBinaryData(""))
                .build());

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();

            try {
                OperationOutput output = client.execute(input, OperationOptions.defaults());
                Assert.fail("should not here");
            } catch (OperationException e) {
                assertThat(e.toString()).contains("Credentials is null or empty");
            }
        }
    }

    @Test
    public void returnsNullCredentials() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(() -> null)
                .httpClient(mockHandler)
                .build();

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(200)
                .body(new StringBinaryData(""))
                .build());

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();

            try {
                OperationOutput output = client.execute(input, OperationOptions.defaults());
                Assert.fail("should not here");
            } catch (OperationException e) {
                assertThat(e.toString()).contains("Credentials is null or empty");
            }
        }
    }

    @Test
    public void returnsFetchCredentialsThrowException() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(() -> {
                    throw new RuntimeException("fetch and throw exception.");
                })
                .httpClient(mockHandler)
                .build();

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(200)
                .body(new StringBinaryData(""))
                .build());

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();

            try {
                OperationOutput output = client.execute(input, OperationOptions.defaults());
                Assert.fail("should not here");
            } catch (OperationException e) {
                assertThat(e.toString()).contains("Fetch Credentials raised an exception");
                assertThat(e.toString()).contains("fetch and throw exception.");
            }
        }
    }

    @Test
    public void useVirtualHostAddressingMode() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();


        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("key", "value");

            // no bucket & key
            mockHandler.clear();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            OperationInput input = OperationInput.newBuilder()
                    .opName("InvokeOperation")
                    .method("PUT")
                    .parameters(parameters)
                    .build();

            OperationOutput output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.uri().toString()).isEqualTo("https://oss-cn-hangzhou.aliyuncs.com/?key=value");
            assertThat(output.statusCode()).isEqualTo(200);

            // bucket
            mockHandler.clear();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            input = OperationInput.newBuilder()
                    .opName("InvokeOperation")
                    .method("PUT")
                    .parameters(parameters)
                    .bucket("my-bucket")
                    .build();

            output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.uri().toString()).isEqualTo("https://my-bucket.oss-cn-hangzhou.aliyuncs.com/?key=value");
            assertThat(output.statusCode()).isEqualTo(200);

            // bucket and key
            mockHandler.clear();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            input = OperationInput.newBuilder()
                    .opName("InvokeOperation")
                    .method("PUT")
                    .parameters(parameters)
                    .bucket("my-bucket")
                    .key("my-key")
                    .build();

            output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.uri().toString()).isEqualTo("https://my-bucket.oss-cn-hangzhou.aliyuncs.com/my-key?key=value");
            assertThat(output.statusCode()).isEqualTo(200);
        }
    }

    @Test
    public void usePathAddressingMode() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .usePathStyle(true)
                .build();

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("key", "value");

            // no bucket & key
            mockHandler.clear();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            OperationInput input = OperationInput.newBuilder()
                    .opName("InvokeOperation")
                    .method("PUT")
                    .parameters(parameters)
                    .build();

            OperationOutput output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.uri().toString()).isEqualTo("https://oss-cn-hangzhou.aliyuncs.com/?key=value");
            assertThat(output.statusCode()).isEqualTo(200);

            // bucket
            mockHandler.clear();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            input = OperationInput.newBuilder()
                    .opName("InvokeOperation")
                    .method("PUT")
                    .parameters(parameters)
                    .bucket("my-bucket")
                    .build();

            output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.uri().toString()).isEqualTo("https://oss-cn-hangzhou.aliyuncs.com/my-bucket/?key=value");
            assertThat(output.statusCode()).isEqualTo(200);

            // bucket and key
            mockHandler.clear();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            input = OperationInput.newBuilder()
                    .opName("InvokeOperation")
                    .method("PUT")
                    .parameters(parameters)
                    .bucket("my-bucket")
                    .key("my-key+123")
                    .build();

            output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.uri().toString()).isEqualTo("https://oss-cn-hangzhou.aliyuncs.com/my-bucket/my-key%2B123?key=value");
            assertThat(output.statusCode()).isEqualTo(200);
        }
    }

    @Test
    public void useCNameAddressingMode() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .endpoint("http://www.cname.com")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .useCName(true)
                .build();

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("key", "value");

            // no bucket & key
            mockHandler.clear();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            OperationInput input = OperationInput.newBuilder()
                    .opName("InvokeOperation")
                    .method("PUT")
                    .parameters(parameters)
                    .build();

            OperationOutput output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.uri().toString()).isEqualTo("http://www.cname.com/?key=value");
            assertThat(output.statusCode()).isEqualTo(200);

            // bucket
            mockHandler.clear();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            input = OperationInput.newBuilder()
                    .opName("InvokeOperation")
                    .method("PUT")
                    .parameters(parameters)
                    .bucket("my-bucket")
                    .build();

            output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.uri().toString()).isEqualTo("http://www.cname.com/?key=value");
            assertThat(output.statusCode()).isEqualTo(200);

            // bucket and key
            mockHandler.clear();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            input = OperationInput.newBuilder()
                    .opName("InvokeOperation")
                    .method("PUT")
                    .parameters(parameters)
                    .bucket("my-bucket")
                    .key("my-key+123")
                    .build();

            output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.uri().toString()).isEqualTo("http://www.cname.com/my-key%2B123?key=value");
            assertThat(output.statusCode()).isEqualTo(200);
        }
    }

    @Test
    public void useIpEndpoint() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .endpoint("http://192.168.1.1:8080")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("key", "value");

            // no bucket & key
            mockHandler.clear();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            OperationInput input = OperationInput.newBuilder()
                    .opName("InvokeOperation")
                    .method("PUT")
                    .parameters(parameters)
                    .build();

            OperationOutput output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.uri().toString()).isEqualTo("http://192.168.1.1:8080/?key=value");
            assertThat(output.statusCode()).isEqualTo(200);

            // bucket
            mockHandler.clear();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            input = OperationInput.newBuilder()
                    .opName("InvokeOperation")
                    .method("PUT")
                    .parameters(parameters)
                    .bucket("my-bucket")
                    .build();

            output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.uri().toString()).isEqualTo("http://192.168.1.1:8080/my-bucket/?key=value");
            assertThat(output.statusCode()).isEqualTo(200);

            // bucket and key
            mockHandler.clear();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            input = OperationInput.newBuilder()
                    .opName("InvokeOperation")
                    .method("PUT")
                    .parameters(parameters)
                    .bucket("my-bucket")
                    .key("my-key+123")
                    .build();

            output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.uri().toString()).isEqualTo("http://192.168.1.1:8080/my-bucket/my-key%2B123?key=value");
            assertThat(output.statusCode()).isEqualTo(200);
        }
    }

    @Test
    public void useIpEndpointWithQuery() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .endpoint("http://www.test.com/123?key=value")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        // virtual host
        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("key1", "value1");

            // bucket
            mockHandler.clear();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            OperationInput input = OperationInput.newBuilder()
                    .opName("InvokeOperation")
                    .method("PUT")
                    .parameters(parameters)
                    .bucket("my-bucket")
                    .build();

            OperationOutput output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.uri().toString()).isEqualTo("http://my-bucket.www.test.com/?key1=value1");
            assertThat(output.statusCode()).isEqualTo(200);
        }

        // path
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .endpoint("http://www.test.com/123?key=value")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .usePathStyle(true)
                .httpClient(mockHandler)
                .build();
        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("key1", "value1");

            // bucket
            mockHandler.clear();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            OperationInput input = OperationInput.newBuilder()
                    .opName("InvokeOperation")
                    .method("PUT")
                    .parameters(parameters)
                    .bucket("my-bucket")
                    .build();

            OperationOutput output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.uri().toString()).isEqualTo("http://www.test.com/my-bucket/?key1=value1");
            assertThat(output.statusCode()).isEqualTo(200);
        }

        //cname
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .endpoint("http://www.test.com/123?key=value")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .useCName(true)
                .httpClient(mockHandler)
                .build();
        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("key1", "value1");

            // bucket
            mockHandler.clear();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            OperationInput input = OperationInput.newBuilder()
                    .opName("InvokeOperation")
                    .method("PUT")
                    .parameters(parameters)
                    .bucket("my-bucket")
                    .key("my-key+123/1.txt")
                    .build();

            OperationOutput output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.uri().toString()).isEqualTo("http://www.test.com/my-key%2B123/1.txt?key1=value1");
            assertThat(output.statusCode()).isEqualTo(200);
        }
    }

    @Test
    public void returnsServiceExceptionNormal() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        String xmlBody =
                "<Error>\n" +
                        "  <Code>NoSuchBucket</Code>\n" +
                        "  <Message>The specified bucket does not exist.</Message>\n" +
                        "  <RequestId>5C3D9175B6FC201293AD****</RequestId>\n" +
                        "  <HostId>test.oss-cn-hangzhou.aliyuncs.com</HostId>\n" +
                        "  <BucketName>test</BucketName>\n" +
                        "  <EC>0015-00000101</EC>\n" +
                        "</Error>";

        Map<String, String> respHeaders = MapUtils.caseInsensitiveMap();
        respHeaders.put("Content-Type", "application/xml");
        respHeaders.put("x-oss-request-id", "5C3D9175B6FC201293AD****");
        respHeaders.put("Date", "Fri, 24 Feb 2017 03:15:40 GMT");

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(404)
                .body(new StringBinaryData(xmlBody))
                .headers(respHeaders)
                .build());

        // error in xml body
        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();

            try {
                client.execute(input, OperationOptions.defaults());
                Assert.fail("should not here");
            } catch (OperationException e) {
                assertThat(mockHandler.requests).hasSize(1);
                assertThat(e.getCause()).isInstanceOf(ServiceException.class);
                ServiceException serr = (ServiceException) e.getCause();
                assertThat(serr.statusCode()).isEqualTo(404);
                assertThat(serr.errorCode()).isEqualTo("NoSuchBucket");
                assertThat(serr.errorMessage()).isEqualTo("The specified bucket does not exist.");
                assertThat(serr.requestId()).isEqualTo("5C3D9175B6FC201293AD****");
                assertThat(serr.ec()).isEqualTo("0015-00000101");
                assertThat(serr.timestamp()).isEqualTo(Instant.ofEpochSecond(1487906140));
            }
        }
    }

    @Test
    public void returnsServiceExceptionInHeader() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        String xmlBody =
                "<Error>\n" +
                        "  <Code>NoSuchBucket</Code>\n" +
                        "  <Message>The specified bucket does not exist.</Message>\n" +
                        "  <RequestId>5C3D9175B6FC201293AD****</RequestId>\n" +
                        "  <HostId>test.oss-cn-hangzhou.aliyuncs.com</HostId>\n" +
                        "  <BucketName>test</BucketName>\n" +
                        "  <EC>0015-00000101</EC>\n" +
                        "</Error>";

        Map<String, String> respHeaders = MapUtils.caseInsensitiveMap();
        respHeaders.put("Content-Type", "application/xml");
        respHeaders.put("x-oss-request-id", "5C3D9175B6FC201293AD****");
        respHeaders.put("Date", "Fri, 24 Feb 2017 03:15:40 GMT");
        respHeaders.put("x-oss-err", Base64Utils.encodeToString(xmlBody.getBytes()));

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(404)
                .body(new StringBinaryData(""))
                .headers(respHeaders)
                .build());

        // error in xml body
        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();

            try {
                client.execute(input, OperationOptions.defaults());
                Assert.fail("should not here");
            } catch (OperationException e) {
                assertThat(mockHandler.requests).hasSize(1);
                assertThat(e.getCause()).isInstanceOf(ServiceException.class);
                ServiceException serr = (ServiceException) e.getCause();
                assertThat(serr.statusCode()).isEqualTo(404);
                assertThat(serr.errorCode()).isEqualTo("NoSuchBucket");
                assertThat(serr.errorMessage()).isEqualTo("The specified bucket does not exist.");
                assertThat(serr.requestId()).isEqualTo("5C3D9175B6FC201293AD****");
                assertThat(serr.ec()).isEqualTo("0015-00000101");
                assertThat(serr.timestamp()).isEqualTo(Instant.ofEpochSecond(1487906140));
            }
        }
    }

    @Test
    public void returnsServiceExceptionEmptyBody() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        String xmlBody = "";

        Map<String, String> respHeaders = MapUtils.caseInsensitiveMap();
        respHeaders.put("Content-Type", "application/xml");
        respHeaders.put("x-oss-request-id", "5C3D9175B6FC201293AD****");
        respHeaders.put("x-oss-ec", "0015-00000101");
        respHeaders.put("Date", "Fri, 24 Feb 2017 03:15:40 GMT");

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(404)
                .body(new StringBinaryData(xmlBody))
                .headers(respHeaders)
                .build());

        // error in xml body
        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();

            try {
                client.execute(input, OperationOptions.defaults());
                Assert.fail("should not here");
            } catch (OperationException e) {
                assertThat(mockHandler.requests).hasSize(1);
                assertThat(e.getCause()).isInstanceOf(ServiceException.class);
                ServiceException serr = (ServiceException) e.getCause();
                assertThat(serr.statusCode()).isEqualTo(404);
                assertThat(serr.errorCode()).isEqualTo("BadErrorResponse");
                assertThat(serr.errorMessage()).isEqualTo("Empty body");
                assertThat(serr.requestId()).isEqualTo("5C3D9175B6FC201293AD****");
                assertThat(serr.ec()).isEqualTo("0015-00000101");
                assertThat(serr.timestamp()).isEqualTo(Instant.ofEpochSecond(1487906140));
            }
        }
    }

    @Test
    public void returnsServiceExceptionNotErrorFormat() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        String xmlBody =
                "<NotError>\n" +
                        "  <Code>NoSuchBucket</Code>\n" +
                        "  <Message>The specified bucket does not exist.</Message>\n" +
                        "  <RequestId>5C3D9175B6FC201293AD****</RequestId>\n" +
                        "  <HostId>test.oss-cn-hangzhou.aliyuncs.com</HostId>\n" +
                        "  <BucketName>test</BucketName>\n" +
                        "  <EC>0015-00000101</EC>\n" +
                        "</NotError>";

        Map<String, String> respHeaders = MapUtils.caseInsensitiveMap();
        respHeaders.put("Content-Type", "application/xml");
        //respHeaders.put("x-oss-request-id", "5C3D9175B6FC201293AD****");
        //respHeaders.put("Date", "Fri, 24 Feb 2017 03:15:40 GMT");

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(404)
                .body(new StringBinaryData(xmlBody))
                .headers(respHeaders)
                .build());

        // error in xml body
        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();

            try {
                client.execute(input, OperationOptions.defaults());
                Assert.fail("should not here");
            } catch (OperationException e) {
                assertThat(mockHandler.requests).hasSize(1);
                assertThat(e.getCause()).isInstanceOf(ServiceException.class);
                ServiceException serr = (ServiceException) e.getCause();
                assertThat(serr.statusCode()).isEqualTo(404);
                assertThat(serr.errorCode()).isEqualTo("BadErrorResponse");
                assertThat(serr.errorMessage()).startsWith("Not found tag <Error>, part response body ");
                assertThat(serr.requestId()).isEqualTo("");
                assertThat(serr.ec()).isEqualTo("");
                assertThat(serr.timestamp()).isNotNull();
            }
        }
    }

    @Test
    public void returnsServiceExceptionNotXmlFormat() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        String xmlBody =
                "NotError\n" +
                        "  <Code>NoSuchBucket</Code>\n" +
                        "  <Message>The specified bucket does not exist.</Message>\n" +
                        "  <RequestId>5C3D9175B6FC201293AD****</RequestId>\n" +
                        "  <HostId>test.oss-cn-hangzhou.aliyuncs.com</HostId>\n" +
                        "  <BucketName>test</BucketName>\n" +
                        "  <EC>0015-00000101</EC>\n" +
                        "</NotError>";

        Map<String, String> respHeaders = MapUtils.caseInsensitiveMap();
        respHeaders.put("Content-Type", "application/xml");
        //respHeaders.put("x-oss-request-id", "5C3D9175B6FC201293AD****");
        //respHeaders.put("Date", "Fri, 24 Feb 2017 03:15:40 GMT");

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(404)
                .body(new StringBinaryData(xmlBody))
                .headers(respHeaders)
                .build());

        // error in xml body
        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();

            try {
                client.execute(input, OperationOptions.defaults());
                Assert.fail("should not here");
            } catch (OperationException e) {
                assertThat(mockHandler.requests).hasSize(1);
                assertThat(e.getCause()).isInstanceOf(ServiceException.class);
                ServiceException serr = (ServiceException) e.getCause();
                assertThat(serr.statusCode()).isEqualTo(404);
                assertThat(serr.errorCode()).isEqualTo("BadErrorResponse");
                assertThat(serr.errorMessage()).startsWith("Failed to parse xml from response body, part response body");
                assertThat(serr.requestId()).isEqualTo("");
                assertThat(serr.ec()).isEqualTo("");
                assertThat(serr.timestamp()).isNotNull();
            }
        }
    }

    @Test
    public void returnsServiceExceptionComplexErrorFormat() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        String xmlBody =
                "<Error>\n" +
                        "  <Code>NoSuchBucket</Code>\n" +
                        "  <Message>The specified bucket does not exist.</Message>\n" +
                        "  <RequestId>5C3D9175B6FC201293AD****</RequestId>\n" +
                        "  <HostId>test.oss-cn-hangzhou.aliyuncs.com</HostId>\n" +
                        "  <BucketName>test</BucketName>\n" +
                        "  <EC>0015-00000101</EC>\n" +
                        "  <InnerError>\n" +
                        "       <Field1>filed-1-value</Field1>\n" +
                        "       <Field2>filed-2-value</Field2>\n" +
                        "   </InnerError>\n" +
                        "</Error>";

        Map<String, String> respHeaders = MapUtils.caseInsensitiveMap();
        respHeaders.put("Content-Type", "application/xml");
        respHeaders.put("x-oss-request-id", "5C3D9175B6FC201293AD****");
        respHeaders.put("Date", "Fri, 24 Feb 2017 03:15:40 GMT");

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(404)
                .body(new StringBinaryData(xmlBody))
                .headers(respHeaders)
                .build());

        // error in xml body
        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();

            try {
                client.execute(input, OperationOptions.defaults());
                Assert.fail("should not here");
            } catch (OperationException e) {
                assertThat(mockHandler.requests).hasSize(1);
                assertThat(e.getCause()).isInstanceOf(ServiceException.class);
                ServiceException serr = (ServiceException) e.getCause();
                assertThat(serr.statusCode()).isEqualTo(404);
                assertThat(serr.errorCode()).isEqualTo("NoSuchBucket");
                assertThat(serr.errorMessage()).isEqualTo("The specified bucket does not exist.");
                assertThat(serr.requestId()).isEqualTo("5C3D9175B6FC201293AD****");
                assertThat(serr.ec()).isEqualTo("0015-00000101");
                assertThat(serr.errorFields().get("InnerError")).isNotNull();
                assertThat(serr.timestamp()).isEqualTo(Instant.ofEpochSecond(1487906140));
            }
        }
    }

    @Test
    public void returnsServiceExceptionNullBody() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        String xmlBody = "";

        Map<String, String> respHeaders = MapUtils.caseInsensitiveMap();
        respHeaders.put("Content-Type", "application/xml");
        respHeaders.put("x-oss-request-id", "5C3D9175B6FC201293AD****");
        respHeaders.put("x-oss-ec", "0015-00000101");
        respHeaders.put("Date", "Fri, 24 Feb 2017 03:15:40 GMT");

        mockHandler.clear();
        mockHandler.responses = new ArrayList<>();
        mockHandler.responses.add(ResponseMessage.newBuilder()
                .statusCode(404)
                //.body(new StringBinaryData(xmlBody))
                .headers(respHeaders)
                .build());

        // error in xml body
        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");

            Map<String, String> parameters = MapUtils.caseSensitiveMap();
            parameters.put("acl", "");

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutBucketAcl")
                    .method("PUT")
                    .headers(headers)
                    .parameters(parameters)
                    .bucket("bucket")
                    .build();

            try {
                client.execute(input, OperationOptions.defaults());
                Assert.fail("should not here");
            } catch (OperationException e) {
                assertThat(mockHandler.requests).hasSize(1);
                assertThat(e.getCause()).isInstanceOf(ServiceException.class);
                ServiceException serr = (ServiceException) e.getCause();
                assertThat(serr.statusCode()).isEqualTo(404);
                assertThat(serr.errorCode()).isEqualTo("BadErrorResponse");
                assertThat(serr.errorMessage()).isEqualTo("Empty body");
                assertThat(serr.requestId()).isEqualTo("5C3D9175B6FC201293AD****");
                assertThat(serr.ec()).isEqualTo("0015-00000101");
                assertThat(serr.timestamp()).isEqualTo(Instant.ofEpochSecond(1487906140));
            }
        }
    }

    @Test
    public void testUploadObserverNormal_useCRCObserver() throws Exception {
        MockHttpClientPutObject mockHandler = new MockHttpClientPutObject();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "text/plain");

            // not set observer
            mockHandler.clear();
            assertThat(mockHandler.requests).isNull();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());
            OperationInput input = OperationInput.newBuilder()
                    .opName("PutObject")
                    .method("PUT")
                    .headers(headers)
                    .bucket("bucket")
                    .key("key")
                    .body(new StringBinaryData("Hello, OSS!"))
                    .build();

            OperationOutput output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.body()).isInstanceOf(StringBinaryData.class);
            assertThat(output.statusCode()).isEqualTo(200);
            assertThat(output.status()).isEqualTo("");
            assertThat(output.headers()).isNotEmpty();
            assertThat(output.headers().get("x-oss-hash-crc64ecma")).isEqualTo("2004446556369382352");

            // not set observer async
            mockHandler.clear();
            assertThat(mockHandler.requests).isNull();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            output = client.executeAsync(input, OperationOptions.defaults()).get();
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.body()).isInstanceOf(StringBinaryData.class);
            assertThat(output.statusCode()).isEqualTo(200);
            assertThat(output.status()).isEqualTo("");
            assertThat(output.headers()).isNotEmpty();
            assertThat(output.headers().get("x-oss-hash-crc64ecma")).isEqualTo("2004446556369382352");

            // empty observer
            AttributeMap opMetadata = AttributeMap.empty();
            opMetadata.put(AttributeKey.UPLOAD_OBSERVER, Collections.emptyList());

            mockHandler.clear();
            assertThat(mockHandler.requests).isNull();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());
            input = OperationInput.newBuilder()
                    .opName("PutObject")
                    .method("PUT")
                    .headers(headers)
                    .bucket("bucket")
                    .key("key")
                    .body(new StringBinaryData("Hello, OSS!"))
                    .opMetadata(opMetadata)
                    .build();

            output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.body()).isInstanceOf(StringBinaryData.class);
            assertThat(output.statusCode()).isEqualTo(200);
            assertThat(output.status()).isEqualTo("");
            assertThat(output.headers()).isNotEmpty();
            assertThat(output.headers().get("x-oss-hash-crc64ecma")).isEqualTo("2004446556369382352");

            // empty observer async
            mockHandler.clear();
            assertThat(mockHandler.requests).isNull();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            output = client.executeAsync(input, OperationOptions.defaults()).get();
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.body()).isInstanceOf(StringBinaryData.class);
            assertThat(output.statusCode()).isEqualTo(200);
            assertThat(output.status()).isEqualTo("");
            assertThat(output.headers()).isNotEmpty();
            assertThat(output.headers().get("x-oss-hash-crc64ecma")).isEqualTo("2004446556369382352");

            // set crc observer
            mockHandler.clear();
            assertThat(mockHandler.requests).isNull();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            opMetadata = AttributeMap.empty();
            CRC64Observer observer = new CRC64Observer();
            opMetadata.put(AttributeKey.UPLOAD_OBSERVER, Collections.singletonList(observer));

            input = OperationInput.newBuilder()
                    .opName("PutObject")
                    .method("PUT")
                    .headers(headers)
                    .bucket("bucket")
                    .key("key")
                    .body(new StringBinaryData("Hello, OSS!"))
                    .opMetadata(opMetadata)
                    .build();

            output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.body()).isInstanceOf(InputStreamBinaryData.class);
            assertThat(((InputStreamBinaryData) mockHandler.lastRequest.body()).unwrap()).isInstanceOf(ObservableInputStream.class);
            assertThat(output.statusCode()).isEqualTo(200);
            assertThat(output.status()).isEqualTo("");
            assertThat(output.headers()).isNotEmpty();
            assertThat(output.headers().get("x-oss-hash-crc64ecma")).isEqualTo("2004446556369382352");
            String clientCRC = Long.toUnsignedString(observer.getChecksum().getValue());
            assertThat(clientCRC).isEqualTo("2004446556369382352");

            // set crc observer async
            observer.reset();
            assertThat(observer.getChecksum().getValue()).isEqualTo(0L);
            mockHandler.clear();
            assertThat(mockHandler.requests).isNull();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.body()).isInstanceOf(InputStreamBinaryData.class);
            assertThat(((InputStreamBinaryData) mockHandler.lastRequest.body()).unwrap()).isInstanceOf(ObservableInputStream.class);
            assertThat(output.statusCode()).isEqualTo(200);
            assertThat(output.status()).isEqualTo("");
            assertThat(output.headers()).isNotEmpty();
            assertThat(output.headers().get("x-oss-hash-crc64ecma")).isEqualTo("2004446556369382352");
            clientCRC = Long.toUnsignedString(observer.getChecksum().getValue());
            assertThat(clientCRC).isEqualTo("2004446556369382352");
        }
    }

    @Test
    public void testUploadObserverRetryable_useCRCObserver() throws Exception {
        MockHttpClientPutObject mockHandler = new MockHttpClientPutObject();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "text/plain");

            // crc observer
            AttributeMap opMetadata = AttributeMap.empty();
            CRC64Observer observer = new CRC64Observer();
            opMetadata.put(AttributeKey.UPLOAD_OBSERVER, Collections.singletonList(observer));

            // set observer sync
            mockHandler.clear();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(500)
                    .body(new StringBinaryData(""))
                    .build());
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutObject")
                    .method("PUT")
                    .headers(headers)
                    .bucket("bucket")
                    .key("key")
                    .body(new StringBinaryData("Hello, OSS!"))
                    .opMetadata(opMetadata)
                    .build();

            OperationOutput output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(2);
            assertThat(mockHandler.lastRequest.body()).isInstanceOf(InputStreamBinaryData.class);
            assertThat(((InputStreamBinaryData) mockHandler.lastRequest.body()).unwrap()).isInstanceOf(ObservableInputStream.class);
            assertThat(output.statusCode()).isEqualTo(200);
            assertThat(output.status()).isEqualTo("");
            assertThat(output.headers()).isNotEmpty();
            assertThat(output.headers().get("x-oss-hash-crc64ecma")).isEqualTo("2004446556369382352");
            String clientCRC = Long.toUnsignedString(observer.getChecksum().getValue());
            assertThat(clientCRC).isEqualTo("2004446556369382352");

            // async
            mockHandler.clear();
            assertThat(mockHandler.requests).isNull();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(500)
                    .body(new StringBinaryData(""))
                    .build());
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            output = client.executeAsync(input, OperationOptions.defaults()).get();
            assertThat(mockHandler.requests).hasSize(2);
            assertThat(mockHandler.lastRequest.body()).isInstanceOf(InputStreamBinaryData.class);
            assertThat(((InputStreamBinaryData) mockHandler.lastRequest.body()).unwrap()).isInstanceOf(ObservableInputStream.class);
            assertThat(output.statusCode()).isEqualTo(200);
            assertThat(output.status()).isEqualTo("");
            assertThat(output.headers()).isNotEmpty();
            assertThat(output.headers().get("x-oss-hash-crc64ecma")).isEqualTo("2004446556369382352");
            clientCRC = Long.toUnsignedString(observer.getChecksum().getValue());
            assertThat(clientCRC).isEqualTo("2004446556369382352");
        }
    }

    @Test
    public void testUploadObserver_useMultiObserver() throws Exception {
        MockHttpClientPutObject mockHandler = new MockHttpClientPutObject();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "text/plain");

            AttributeMap opMetadata = AttributeMap.empty();

            // content
            byte[] content = TestUtils.generateTestData(100 * 1024 + 123);
            String patCrc = Long.toUnsignedString((new CRC64(content, content.length)).getValue());

            // crc observer
            CRC64Observer crcObserver = new CRC64Observer();
            // progress
            MockProgressListener progListener = new MockProgressListener();
            ProgressObserver progObserver = new ProgressObserver(progListener, (long) content.length);
            opMetadata.put(AttributeKey.UPLOAD_OBSERVER, Arrays.asList(crcObserver, progObserver));

            // set observer sync
            mockHandler.clear();
            assertThat(mockHandler.requests).isNull();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutObject")
                    .method("PUT")
                    .headers(headers)
                    .bucket("bucket")
                    .key("key")
                    .body(new ByteArrayBinaryData(content))
                    .opMetadata(opMetadata)
                    .build();

            OperationOutput output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.body()).isInstanceOf(InputStreamBinaryData.class);
            assertThat(((InputStreamBinaryData) mockHandler.lastRequest.body()).unwrap()).isInstanceOf(ObservableInputStream.class);
            assertThat(output.statusCode()).isEqualTo(200);
            assertThat(output.status()).isEqualTo("");
            assertThat(output.headers()).isNotEmpty();
            assertThat(output.headers().get("x-oss-hash-crc64ecma")).isEqualTo(patCrc);
            assertThat(crcObserver.getChecksum().getValue()).isNotEqualTo(0);
            assertThat(Long.toUnsignedString(crcObserver.getChecksum().getValue())).isEqualTo(patCrc);
            assertThat(progListener.total).isEqualTo(content.length);
            assertThat(progListener.incTotal).isEqualTo(content.length);
            assertThat(progListener.transferred).isEqualTo(content.length);
            assertTrue(progListener.finished);

            // set observer async
            mockHandler.clear();
            assertThat(mockHandler.requests).isNull();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            crcObserver = new CRC64Observer();
            // progress
            progListener = new MockProgressListener();
            progObserver = new ProgressObserver(progListener, (long) content.length);
            opMetadata.put(AttributeKey.UPLOAD_OBSERVER, Arrays.asList(crcObserver, progObserver));

            input = input.toBuilder().opMetadata(opMetadata).build();
            output = client.executeAsync(input, OperationOptions.defaults()).get();
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.body()).isInstanceOf(InputStreamBinaryData.class);
            assertThat(((InputStreamBinaryData) mockHandler.lastRequest.body()).unwrap()).isInstanceOf(ObservableInputStream.class);
            assertThat(output.statusCode()).isEqualTo(200);
            assertThat(output.status()).isEqualTo("");
            assertThat(output.headers()).isNotEmpty();
            assertThat(output.headers().get("x-oss-hash-crc64ecma")).isEqualTo(patCrc);
            assertThat(crcObserver.getChecksum().getValue()).isNotEqualTo(0);
            assertThat(Long.toUnsignedString(crcObserver.getChecksum().getValue())).isEqualTo(patCrc);
            assertThat(progListener.total).isEqualTo(content.length);
            assertThat(progListener.incTotal).isEqualTo(content.length);
            assertThat(progListener.transferred).isEqualTo(content.length);
            assertTrue(progListener.finished);
        }
    }

    @Test
    public void testUploadObserverRetryable_useProgObserver() throws Exception {
        MockHttpClientPutObject mockHandler = new MockHttpClientPutObject();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "text/plain");

            // content
            byte[] content = TestUtils.generateTestData(100 * 1024 + 123);
            String patCrc = Long.toUnsignedString((new CRC64(content, content.length)).getValue());

            // prog observer
            AttributeMap opMetadata = AttributeMap.empty();
            MockProgressListener progListener = new MockProgressListener();
            ProgressObserver progObserver = new ProgressObserver(progListener, (long) content.length);
            opMetadata.put(AttributeKey.UPLOAD_OBSERVER, Collections.singletonList(progObserver));

            // set observer sync
            mockHandler.clear();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(500)
                    .body(new StringBinaryData(""))
                    .build());
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutObject")
                    .method("PUT")
                    .headers(headers)
                    .bucket("bucket")
                    .key("key")
                    .body(new ByteArrayBinaryData(content))
                    .opMetadata(opMetadata)
                    .build();

            OperationOutput output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(2);
            assertThat(mockHandler.lastRequest.body()).isInstanceOf(InputStreamBinaryData.class);
            assertThat(((InputStreamBinaryData) mockHandler.lastRequest.body()).unwrap()).isInstanceOf(ObservableInputStream.class);
            assertThat(output.statusCode()).isEqualTo(200);
            assertThat(output.status()).isEqualTo("");
            assertThat(output.headers()).isNotEmpty();
            assertThat(output.headers().get("x-oss-hash-crc64ecma")).isEqualTo(patCrc);
            assertThat(progListener.total).isEqualTo(content.length);
            //assertThat(progListener.incTotal).isEqualTo((long)content.length);
            assertThat(progListener.transferred).isEqualTo(content.length);
            assertTrue(progListener.finished);

            // async
            mockHandler.clear();
            assertThat(mockHandler.requests).isNull();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(500)
                    .body(new StringBinaryData(""))
                    .build());
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            // progress
            progListener = new MockProgressListener();
            progObserver = new ProgressObserver(progListener, (long) content.length);
            assertThat(progListener.total).isEqualTo(0L);
            opMetadata.put(AttributeKey.UPLOAD_OBSERVER, Collections.singletonList(progObserver));
            input = input.toBuilder().opMetadata(opMetadata).build();

            output = client.executeAsync(input, OperationOptions.defaults()).get();
            assertThat(mockHandler.requests).hasSize(2);
            assertThat(mockHandler.lastRequest.body()).isInstanceOf(InputStreamBinaryData.class);
            assertThat(((InputStreamBinaryData) mockHandler.lastRequest.body()).unwrap()).isInstanceOf(ObservableInputStream.class);
            assertThat(output.statusCode()).isEqualTo(200);
            assertThat(output.status()).isEqualTo("");
            assertThat(output.headers()).isNotEmpty();
            assertThat(output.headers().get("x-oss-hash-crc64ecma")).isEqualTo(patCrc);
            assertThat(progListener.total).isEqualTo(content.length);
            //assertThat(progListener.incTotal).isEqualTo((long)content.length);
            assertThat(progListener.transferred).isEqualTo(content.length);
            assertTrue(progListener.finished);
        }
    }

    @Test
    public void testUploadDataAndCheckResponseCrc() throws Exception {
        MockHttpClientPutObject mockHandler = new MockHttpClientPutObject();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "text/plain");

            // content
            byte[] content = TestUtils.generateTestData(100 * 1024 + 123);
            String patCrc = Long.toUnsignedString((new CRC64(content, content.length)).getValue());

            // crc observer & response handler
            AttributeMap opMetadata = AttributeMap.empty();
            CRC64Observer crcObserver = new CRC64Observer();
            opMetadata.put(AttributeKey.UPLOAD_OBSERVER, Collections.singletonList(crcObserver));
            MockCRC64ResponseChecker crcHandler = new MockCRC64ResponseChecker(crcObserver.getChecksum());
            opMetadata.put(AttributeKey.RESPONSE_HANDLER, Collections.singletonList(crcHandler));
            assertFalse(crcHandler.accepted);

            // sync
            mockHandler.clear();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutObject")
                    .method("PUT")
                    .headers(headers)
                    .bucket("bucket")
                    .key("key")
                    .body(new ByteArrayBinaryData(content))
                    .opMetadata(opMetadata)
                    .build();

            OperationOutput output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.body()).isInstanceOf(InputStreamBinaryData.class);
            assertThat(((InputStreamBinaryData) mockHandler.lastRequest.body()).unwrap()).isInstanceOf(ObservableInputStream.class);
            assertThat(output.statusCode()).isEqualTo(200);
            assertThat(output.status()).isEqualTo("");
            assertThat(output.headers()).isNotEmpty();
            assertThat(output.headers().get("x-oss-hash-crc64ecma")).isEqualTo(patCrc);
            assertTrue(crcHandler.accepted);

            // async
            opMetadata = AttributeMap.empty();
            crcObserver = new CRC64Observer();
            opMetadata.put(AttributeKey.UPLOAD_OBSERVER, Collections.singletonList(crcObserver));
            crcHandler = new MockCRC64ResponseChecker(crcObserver.getChecksum());
            opMetadata.put(AttributeKey.RESPONSE_HANDLER, Collections.singletonList(crcHandler));
            assertFalse(crcHandler.accepted);

            mockHandler.clear();
            assertThat(mockHandler.requests).isNull();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            input = OperationInput.newBuilder()
                    .opName("PutObject")
                    .method("PUT")
                    .headers(headers)
                    .bucket("bucket")
                    .key("key")
                    .body(new ByteArrayBinaryData(content))
                    .opMetadata(opMetadata)
                    .build();

            output = client.executeAsync(input, OperationOptions.defaults()).get();
            assertThat(mockHandler.requests).hasSize(1);
            assertThat(mockHandler.lastRequest.body()).isInstanceOf(InputStreamBinaryData.class);
            assertThat(((InputStreamBinaryData) mockHandler.lastRequest.body()).unwrap()).isInstanceOf(ObservableInputStream.class);
            assertThat(output.statusCode()).isEqualTo(200);
            assertThat(output.status()).isEqualTo("");
            assertThat(output.headers()).isNotEmpty();
            assertThat(output.headers().get("x-oss-hash-crc64ecma")).isEqualTo(patCrc);
            assertTrue(crcHandler.accepted);
        }
    }

    @Test
    public void testUploadDataAndCheckResponseCrc_throwInconsistentException() throws Exception {
        MockHttpClientPutObject mockHandler = new MockHttpClientPutObject();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .retryMaxAttempts(1)
                .httpClient(mockHandler)
                .build();

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "text/plain");

            // content
            byte[] content = TestUtils.generateTestData(100 * 1024 + 123);
            String patCrc = Long.toUnsignedString((new CRC64(content, content.length)).getValue());

            // crc observer & response handler
            AttributeMap opMetadata = AttributeMap.empty();
            CRC64Observer crcObserver = new CRC64Observer();
            opMetadata.put(AttributeKey.UPLOAD_OBSERVER, Collections.singletonList(crcObserver));
            MockCRC64ResponseChecker crcHandler = new MockCRC64ResponseChecker(crcObserver.getChecksum());
            opMetadata.put(AttributeKey.RESPONSE_HANDLER, Collections.singletonList(crcHandler));
            assertFalse(crcHandler.accepted);

            // set invalid "x-oss-hash-crc64ecma"
            Map<String, String> respHeaders = MapUtils.caseInsensitiveMap();
            respHeaders.put("x-oss-hash-crc64ecma", "1234567");
            respHeaders.put("x-oss-request-id", "request-id-123");

            // sync
            mockHandler.clear();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .headers(respHeaders)
                    .body(new StringBinaryData(""))
                    .build());

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutObject")
                    .method("PUT")
                    .headers(headers)
                    .bucket("bucket")
                    .key("key")
                    .body(new ByteArrayBinaryData(content))
                    .opMetadata(opMetadata)
                    .build();

            try {
                client.execute(input, OperationOptions.defaults());
            } catch (Exception e) {
                assertThat(mockHandler.requests).hasSize(1);
                InconsistentException crcErr = findCause(e, InconsistentException.class);
                // "crc is inconsistent, client crc:%s, server crc:%s, requestId: %s."
                assertThat(e).hasMessageContaining("crc is inconsistent, ");
                assertThat(e).hasMessageContaining("client crc:" + patCrc);
                assertThat(e).hasMessageContaining("server crc:1234567");
                assertThat(e).hasMessageContaining("requestId:request-id-123");
                assertNotNull(crcErr);
                assertThat(crcErr.clientCRC()).isEqualTo(patCrc);
                assertThat(crcErr.serverCRC()).isEqualTo("1234567");
                assertThat(crcErr.requestId()).isEqualTo("request-id-123");
                assertThat(crcErr.headers()).isNotEmpty();
            }

            // async
            opMetadata = AttributeMap.empty();
            crcObserver = new CRC64Observer();
            opMetadata.put(AttributeKey.UPLOAD_OBSERVER, Collections.singletonList(crcObserver));
            crcHandler = new MockCRC64ResponseChecker(crcObserver.getChecksum());
            opMetadata.put(AttributeKey.RESPONSE_HANDLER, Collections.singletonList(crcHandler));
            assertFalse(crcHandler.accepted);

            mockHandler.clear();
            assertThat(mockHandler.requests).isNull();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .headers(respHeaders)
                    .body(new StringBinaryData(""))
                    .build());

            input = OperationInput.newBuilder()
                    .opName("PutObject")
                    .method("PUT")
                    .headers(headers)
                    .bucket("bucket")
                    .key("key")
                    .body(new ByteArrayBinaryData(content))
                    .opMetadata(opMetadata)
                    .build();

            try {
                client.executeAsync(input, OperationOptions.defaults()).get();
            } catch (Exception e) {
                assertThat(mockHandler.requests).hasSize(1);
                InconsistentException crcErr = findCause(e, InconsistentException.class);
                // "crc is inconsistent, client crc:%s, server crc:%s, requestId: %s."
                assertThat(e).hasMessageContaining("crc is inconsistent, ");
                assertThat(e).hasMessageContaining("client crc:" + patCrc);
                assertThat(e).hasMessageContaining("server crc:1234567");
                assertThat(e).hasMessageContaining("requestId:request-id-123");
                assertNotNull(crcErr);
                assertThat(crcErr.clientCRC()).isEqualTo(patCrc);
                assertThat(crcErr.serverCRC()).isEqualTo("1234567");
                assertThat(crcErr.requestId()).isEqualTo("request-id-123");
                assertThat(crcErr.headers()).isNotEmpty();
            }
        }
    }

    @Test
    public void testUploadDataAndCheckResponseCrc_retryInconsistentException() throws Exception {
        MockHttpClientPutObject mockHandler = new MockHttpClientPutObject();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        try (ClientImpl client = new ClientImpl(config)) {
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "text/plain");

            // content
            byte[] content = TestUtils.generateTestData(100 * 1024 + 123);
            String patCrc = Long.toUnsignedString((new CRC64(content, content.length)).getValue());

            // set invalid "x-oss-hash-crc64ecma"
            Map<String, String> respHeaders = MapUtils.caseInsensitiveMap();
            respHeaders.put("x-oss-hash-crc64ecma", "1234567");
            respHeaders.put("x-oss-request-id", "request-id-123");

            // crc observer & response handler
            AttributeMap opMetadata = AttributeMap.empty();
            CRC64Observer crcObserver = new CRC64Observer();
            opMetadata.put(AttributeKey.UPLOAD_OBSERVER, Collections.singletonList(crcObserver));
            MockCRC64ResponseChecker crcHandler = new MockCRC64ResponseChecker(crcObserver.getChecksum());
            opMetadata.put(AttributeKey.RESPONSE_HANDLER, Collections.singletonList(crcHandler));
            assertFalse(crcHandler.accepted);

            // sync
            mockHandler.clear();
            assertThat(mockHandler.requests).isNull();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .headers(respHeaders)
                    .build());
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            OperationInput input = OperationInput.newBuilder()
                    .opName("PutObject")
                    .method("PUT")
                    .headers(headers)
                    .bucket("bucket")
                    .key("key")
                    .body(new ByteArrayBinaryData(content))
                    .opMetadata(opMetadata)
                    .build();

            OperationOutput output = client.execute(input, OperationOptions.defaults());
            assertThat(mockHandler.requests).hasSize(2);
            assertThat(mockHandler.lastRequest.body()).isInstanceOf(InputStreamBinaryData.class);
            assertThat(((InputStreamBinaryData) mockHandler.lastRequest.body()).unwrap()).isInstanceOf(ObservableInputStream.class);
            assertThat(output.statusCode()).isEqualTo(200);
            assertThat(output.status()).isEqualTo("");
            assertThat(output.headers()).isNotEmpty();
            assertThat(output.headers().get("x-oss-hash-crc64ecma")).isEqualTo(patCrc);
            assertTrue(crcHandler.accepted);

            // async
            opMetadata = AttributeMap.empty();
            crcObserver = new CRC64Observer();
            opMetadata.put(AttributeKey.UPLOAD_OBSERVER, Collections.singletonList(crcObserver));
            crcHandler = new MockCRC64ResponseChecker(crcObserver.getChecksum());
            opMetadata.put(AttributeKey.RESPONSE_HANDLER, Collections.singletonList(crcHandler));
            assertFalse(crcHandler.accepted);

            mockHandler.clear();
            assertThat(mockHandler.requests).isNull();
            mockHandler.responses = new ArrayList<>();
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .headers(respHeaders)
                    .build());
            mockHandler.responses.add(ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build());

            input = OperationInput.newBuilder()
                    .opName("PutObject")
                    .method("PUT")
                    .headers(headers)
                    .bucket("bucket")
                    .key("key")
                    .body(new ByteArrayBinaryData(content))
                    .opMetadata(opMetadata)
                    .build();

            output = client.executeAsync(input, OperationOptions.defaults()).get();
            assertThat(mockHandler.requests).hasSize(2);
            assertThat(mockHandler.lastRequest.body()).isInstanceOf(InputStreamBinaryData.class);
            assertThat(((InputStreamBinaryData) mockHandler.lastRequest.body()).unwrap()).isInstanceOf(ObservableInputStream.class);
            assertThat(output.statusCode()).isEqualTo(200);
            assertThat(output.status()).isEqualTo("");
            assertThat(output.headers()).isNotEmpty();
            assertThat(output.headers().get("x-oss-hash-crc64ecma")).isEqualTo(patCrc);
            assertTrue(crcHandler.accepted);
        }
    }

    @Test
    public void presignInnerV4() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        try (ClientImpl client = new ClientImpl(config)) {

            // no headers & parameters
            OperationInput input = OperationInput.newBuilder()
                    .opName("GetObject")
                    .method("GET")
                    .bucket("bucket")
                    .key("key")
                    .build();

            Instant expiration = Instant.now().plusSeconds(60 * 60);
            input.opMetadata().put(AttributeKey.EXPIRATION_TIME, expiration);

            ClientImpl.PresignInnerResult result = client.presignInner(input, null);
            assertThat(result).isNotNull();
            assertThat(result.method).isEqualTo("GET");
            assertThat(result.url).contains("bucket.oss-cn-hangzhou.aliyuncs.com/key?");
            assertThat(result.url).contains("x-oss-date=");
            assertThat(result.url).containsAnyOf("x-oss-expires=3599", "x-oss-expires=3600");
            assertThat(result.url).contains("x-oss-signature=");
            assertThat(result.url).contains("x-oss-credential=ak%2F");
            assertThat(result.url).contains("x-oss-signature-version=OSS4-HMAC-SHA256");
            assertThat(result.expiration).isEqualTo(expiration);
            assertThat(result.signedHeaders).isNotNull();
            assertThat(result.signedHeaders).isEmpty();

            // default signed headers
            input = OperationInput.newBuilder()
                    .opName("GetObject")
                    .method("GET")
                    .bucket("bucket")
                    .key("key+123/subdir/1.txt")
                    .build();

            input.headers().put("Content-Type", "text");
            input.headers().put("Content-MD5", "md5-123");
            input.headers().put("x-oss-meta-key1", "value1");
            input.headers().put("abc", "abc-value1");
            input.headers().put("abc-2", "abc-value2");
            input.parameters().put("key#?+", "value#123/+123");
            input.parameters().put("key", "value");

            expiration = Instant.now().plusSeconds(60 * 60);
            input.opMetadata().put(AttributeKey.EXPIRATION_TIME, expiration);

            result = client.presignInner(input, null);
            assertThat(result).isNotNull();
            assertThat(result.method).isEqualTo("GET");
            assertThat(result.url).contains("bucket.oss-cn-hangzhou.aliyuncs.com/key%2B123/subdir/1.txt?");
            assertThat(result.url).contains("x-oss-date=");
            assertThat(result.url).containsAnyOf("x-oss-expires=3599", "x-oss-expires=3600");
            assertThat(result.url).contains("x-oss-signature=");
            assertThat(result.url).contains("x-oss-credential=ak%2F");
            assertThat(result.url).contains("x-oss-signature-version=OSS4-HMAC-SHA256");
            assertThat(result.url).contains("key=value");
            assertThat(result.url).contains("key%23%3F%2B=value%23123%2F%2B123");
            assertThat(result.expiration).isEqualTo(expiration);
            assertThat(result.signedHeaders).isNotNull();
            assertThat(result.signedHeaders).hasSize(3);
            assertThat(result.signedHeaders.get("Content-Type")).isEqualTo("text");
            assertThat(result.signedHeaders.get("Content-MD5")).isEqualTo("md5-123");
            assertThat(result.signedHeaders.get("x-oss-meta-key1")).isEqualTo("value1");
        }

        // additional-headers
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .additionalHeaders(Collections.singletonList("Abc"))
                .httpClient(mockHandler)
                .build();

        try (ClientImpl client = new ClientImpl(config)) {

            OperationInput input = OperationInput.newBuilder()
                    .opName("GetObject")
                    .method("GET")
                    .bucket("bucket")
                    .key("key+123/subdir/1.txt")
                    .build();

            input.headers().put("Content-Type", "text");
            input.headers().put("Content-MD5", "md5-123");
            input.headers().put("x-oss-meta-key1", "value1");
            input.headers().put("abc", "abc-value1");
            input.headers().put("abc-2", "abc-value2");
            input.parameters().put("key#?+", "value#123/+123");
            input.parameters().put("key", "value");

            Instant expiration = Instant.now().plusSeconds(60 * 60);
            input.opMetadata().put(AttributeKey.EXPIRATION_TIME, expiration);

            ClientImpl.PresignInnerResult result = client.presignInner(input, null);
            assertThat(result).isNotNull();
            assertThat(result.method).isEqualTo("GET");
            assertThat(result.url).contains("bucket.oss-cn-hangzhou.aliyuncs.com/key%2B123/subdir/1.txt?");
            assertThat(result.url).contains("x-oss-date=");
            assertThat(result.url).containsAnyOf("x-oss-expires=3599", "x-oss-expires=3600");
            assertThat(result.url).contains("x-oss-signature=");
            assertThat(result.url).contains("x-oss-credential=ak%2F");
            assertThat(result.url).contains("x-oss-signature-version=OSS4-HMAC-SHA256");
            assertThat(result.url).contains("key=value");
            assertThat(result.url).contains("key%23%3F%2B=value%23123%2F%2B123");
            assertThat(result.expiration).isEqualTo(expiration);
            assertThat(result.signedHeaders).isNotNull();
            assertThat(result.signedHeaders).hasSize(4);
            assertThat(result.signedHeaders.get("Content-Type")).isEqualTo("text");
            assertThat(result.signedHeaders.get("Content-MD5")).isEqualTo("md5-123");
            assertThat(result.signedHeaders.get("x-oss-meta-key1")).isEqualTo("value1");
            assertThat(result.signedHeaders.get("abc")).isEqualTo("abc-value1");
        }

        // token
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk", "token+1"))
                .additionalHeaders(Collections.singletonList("Abc"))
                .httpClient(mockHandler)
                .build();

        try (ClientImpl client = new ClientImpl(config)) {

            OperationInput input = OperationInput.newBuilder()
                    .opName("GetObject")
                    .method("GET")
                    .bucket("bucket")
                    .key("key+123/subdir/1.txt")
                    .build();

            input.headers().put("Content-Type", "text");
            input.headers().put("Content-MD5", "md5-123");
            input.headers().put("x-oss-meta-key1", "value1");
            input.headers().put("abc", "abc-value1");
            input.headers().put("abc-2", "abc-value2");
            input.parameters().put("key#?+", "value#123/+123");
            input.parameters().put("key", "value");

            Instant expiration = Instant.now().plusSeconds(60 * 60);
            input.opMetadata().put(AttributeKey.EXPIRATION_TIME, expiration);

            ClientImpl.PresignInnerResult result = client.presignInner(input, null);
            assertThat(result).isNotNull();
            assertThat(result.method).isEqualTo("GET");
            assertThat(result.url).contains("bucket.oss-cn-hangzhou.aliyuncs.com/key%2B123/subdir/1.txt?");
            assertThat(result.url).contains("x-oss-date=");
            assertThat(result.url).containsAnyOf("x-oss-expires=3599", "x-oss-expires=3600");
            assertThat(result.url).contains("x-oss-signature=");
            assertThat(result.url).contains("x-oss-credential=ak%2F");
            assertThat(result.url).contains("x-oss-signature-version=OSS4-HMAC-SHA256");
            assertThat(result.url).contains("x-oss-security-token=token%2B1");
            assertThat(result.url).contains("key=value");
            assertThat(result.url).contains("key%23%3F%2B=value%23123%2F%2B123");
            assertThat(result.expiration).isEqualTo(expiration);
            assertThat(result.signedHeaders).isNotNull();
            assertThat(result.signedHeaders).hasSize(4);
            assertThat(result.signedHeaders.get("Content-Type")).isEqualTo("text");
            assertThat(result.signedHeaders.get("Content-MD5")).isEqualTo("md5-123");
            assertThat(result.signedHeaders.get("x-oss-meta-key1")).isEqualTo("value1");
            assertThat(result.signedHeaders.get("abc")).isEqualTo("abc-value1");
        }
    }

    @Test
    public void presignInnerV4_defaultExpiration() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        try (ClientImpl client = new ClientImpl(config)) {

            // no headers & parameters
            OperationInput input = OperationInput.newBuilder()
                    .opName("GetObject")
                    .method("GET")
                    .bucket("bucket")
                    .key("key")
                    .build();

            ClientImpl.PresignInnerResult result = client.presignInner(input, null);
            assertThat(result).isNotNull();
            assertThat(result.method).isEqualTo("GET");
            assertThat(result.url).contains("bucket.oss-cn-hangzhou.aliyuncs.com/key?");
            assertThat(result.url).contains("x-oss-date=");
            assertThat(result.url).containsAnyOf("x-oss-expires=900", "x-oss-expires=899");
            assertThat(result.url).contains("x-oss-signature=");
            assertThat(result.url).contains("x-oss-credential=ak%2F");
            assertThat(result.url).contains("x-oss-signature-version=OSS4-HMAC-SHA256");
            assertThat(result.expiration).isNotNull();
            assertThat(result.signedHeaders).isNotNull();
            assertThat(result.signedHeaders).isEmpty();
        }
    }

    @Test
    public void presignInnerV1() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .signatureVersion("v1")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        try (ClientImpl client = new ClientImpl(config)) {

            // no headers & parameters
            OperationInput input = OperationInput.newBuilder()
                    .opName("GetObject")
                    .method("GET")
                    .bucket("bucket")
                    .key("key")
                    .build();

            Instant expiration = Instant.now().plusSeconds(60 * 60);
            input.opMetadata().put(AttributeKey.EXPIRATION_TIME, expiration);

            ClientImpl.PresignInnerResult result = client.presignInner(input, null);
            assertThat(result).isNotNull();
            assertThat(result.method).isEqualTo("GET");
            assertThat(result.url).contains("https://bucket.oss-cn-hangzhou.aliyuncs.com/key?");
            assertThat(result.url).contains("OSSAccessKeyId=ak");
            assertThat(result.url).contains("Expires=");
            assertThat(result.url).contains("Signature=");
            assertThat(result.expiration).isEqualTo(expiration);
            assertThat(result.signedHeaders).isNotNull();
            assertThat(result.signedHeaders).isEmpty();

            // default signed headers
            input = OperationInput.newBuilder()
                    .opName("GetObject")
                    .method("GET")
                    .bucket("bucket")
                    .key("key+123/subdir/1.txt")
                    .build();

            input.headers().put("Content-Type", "text");
            input.headers().put("Content-MD5", "md5-123");
            input.headers().put("x-oss-meta-key1", "value1");
            input.headers().put("abc", "abc-value1");
            input.headers().put("abc-2", "abc-value2");
            input.parameters().put("key#?+", "value#123/+123");
            input.parameters().put("key", "value");

            expiration = Instant.now().plusSeconds(60 * 60);
            input.opMetadata().put(AttributeKey.EXPIRATION_TIME, expiration);

            result = client.presignInner(input, null);
            assertThat(result).isNotNull();
            assertThat(result.method).isEqualTo("GET");
            assertThat(result.url).contains("https://bucket.oss-cn-hangzhou.aliyuncs.com/key%2B123/subdir/1.txt?");
            assertThat(result.url).contains("OSSAccessKeyId=ak");
            assertThat(result.url).contains("Expires=" + expiration.getEpochSecond());
            assertThat(result.url).contains("Signature=");
            assertThat(result.url).contains("key=value");
            assertThat(result.url).contains("key%23%3F%2B=value%23123%2F%2B123");
            assertThat(result.expiration).isEqualTo(expiration);
            assertThat(result.signedHeaders).isNotNull();
            assertThat(result.signedHeaders).hasSize(3);
            assertThat(result.signedHeaders.get("Content-Type")).isEqualTo("text");
            assertThat(result.signedHeaders.get("Content-MD5")).isEqualTo("md5-123");
            assertThat(result.signedHeaders.get("x-oss-meta-key1")).isEqualTo("value1");
        }

        // additional-headers
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .additionalHeaders(Collections.singletonList("Abc"))
                .signatureVersion("v1")
                .httpClient(mockHandler)
                .build();

        try (ClientImpl client = new ClientImpl(config)) {

            OperationInput input = OperationInput.newBuilder()
                    .opName("GetObject")
                    .method("GET")
                    .bucket("bucket")
                    .key("key+123/subdir/1.txt")
                    .build();

            input.headers().put("Content-Type", "text");
            input.headers().put("Content-MD5", "md5-123");
            input.headers().put("x-oss-meta-key1", "value1");
            input.headers().put("abc", "abc-value1");
            input.headers().put("abc-2", "abc-value2");
            input.parameters().put("key#?+", "value#123/+123");
            input.parameters().put("key", "value");

            Instant expiration = Instant.now().plusSeconds(60 * 60);
            input.opMetadata().put(AttributeKey.EXPIRATION_TIME, expiration);

            ClientImpl.PresignInnerResult result = client.presignInner(input, null);
            assertThat(result).isNotNull();
            assertThat(result.method).isEqualTo("GET");
            assertThat(result.url).contains("https://bucket.oss-cn-hangzhou.aliyuncs.com/key%2B123/subdir/1.txt?");
            assertThat(result.url).contains("OSSAccessKeyId=ak");
            assertThat(result.url).contains("Expires=" + expiration.getEpochSecond());
            assertThat(result.url).contains("Signature=");
            assertThat(result.url).contains("key=value");
            assertThat(result.url).contains("key%23%3F%2B=value%23123%2F%2B123");
            assertThat(result.expiration).isEqualTo(expiration);
            assertThat(result.signedHeaders).isNotNull();
            assertThat(result.signedHeaders).hasSize(3);
            assertThat(result.signedHeaders.get("Content-Type")).isEqualTo("text");
            assertThat(result.signedHeaders.get("Content-MD5")).isEqualTo("md5-123");
            assertThat(result.signedHeaders.get("x-oss-meta-key1")).isEqualTo("value1");
        }

        // token
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk", "token"))
                .signatureVersion("v1")
                .httpClient(mockHandler)
                .build();

        try (ClientImpl client = new ClientImpl(config)) {

            OperationInput input = OperationInput.newBuilder()
                    .opName("GetObject")
                    .method("GET")
                    .bucket("bucket")
                    .key("key+123/subdir/1.txt")
                    .build();

            input.headers().put("Content-Type", "text");
            input.headers().put("Content-MD5", "md5-123");
            input.headers().put("x-oss-meta-key1", "value1");
            input.headers().put("abc", "abc-value1");
            input.headers().put("abc-2", "abc-value2");
            input.parameters().put("key#?+", "value#123/+123");
            input.parameters().put("key", "value");

            Instant expiration = Instant.now().plusSeconds(60 * 60);
            input.opMetadata().put(AttributeKey.EXPIRATION_TIME, expiration);

            ClientImpl.PresignInnerResult result = client.presignInner(input, null);
            assertThat(result).isNotNull();
            assertThat(result.method).isEqualTo("GET");
            assertThat(result.url).contains("https://bucket.oss-cn-hangzhou.aliyuncs.com/key%2B123/subdir/1.txt?");
            assertThat(result.url).contains("OSSAccessKeyId=ak");
            assertThat(result.url).contains("Expires=" + expiration.getEpochSecond());
            assertThat(result.url).contains("Signature=");
            assertThat(result.url).contains("security-token=token");
            assertThat(result.url).contains("key=value");
            assertThat(result.url).contains("key%23%3F%2B=value%23123%2F%2B123");
            assertThat(result.expiration).isEqualTo(expiration);
            assertThat(result.signedHeaders).isNotNull();
            assertThat(result.signedHeaders).hasSize(3);
            assertThat(result.signedHeaders.get("Content-Type")).isEqualTo("text");
            assertThat(result.signedHeaders.get("Content-MD5")).isEqualTo("md5-123");
            assertThat(result.signedHeaders.get("x-oss-meta-key1")).isEqualTo("value1");
        }
    }

    @Test
    public void presignInnerV1_defaultExpiration() throws Exception {
        MockHttpClient mockHandler = new MockHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .signatureVersion("v1")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .build();

        try (ClientImpl client = new ClientImpl(config)) {

            // no headers & parameters
            OperationInput input = OperationInput.newBuilder()
                    .opName("GetObject")
                    .method("GET")
                    .bucket("bucket")
                    .key("key")
                    .build();

            ClientImpl.PresignInnerResult result = client.presignInner(input, null);
            assertThat(result).isNotNull();
            assertThat(result.method).isEqualTo("GET");
            assertThat(result.url).contains("https://bucket.oss-cn-hangzhou.aliyuncs.com/key?");
            assertThat(result.url).contains("OSSAccessKeyId=ak");
            assertThat(result.url).contains("Expires=");
            assertThat(result.url).contains("Signature=");
            assertThat(result.expiration).isNotNull();
            assertThat(result.expiration).isBefore(Instant.now().plusSeconds(15 * 60 + 2));
            assertThat(result.signedHeaders).isNotNull();
            assertThat(result.signedHeaders).isEmpty();
        }
    }

    @Test
    public void presignMisc() throws Exception {

        // empty ak&sk
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .signatureVersion("v1")
                .credentialsProvider(new StaticCredentialsProvider("", ""))
                .build();

        try (ClientImpl client = new ClientImpl(config)) {

            OperationInput input = OperationInput.newBuilder()
                    .opName("GetObject")
                    .method("GET")
                    .bucket("bucket")
                    .key("key")
                    .build();

            try {
                client.presignInner(input, null);
            } catch (Exception e) {
                assertThat(e).hasMessageContaining("Credentials is null or empty.");
            }
        }

        // Null CredentialsProvider
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .signatureVersion("v1")
                .credentialsProvider(new CredentialsProvider() {
                    @Override
                    public Credentials getCredentials() {
                        return null;
                    }
                })
                .build();

        try (ClientImpl client = new ClientImpl(config)) {

            OperationInput input = OperationInput.newBuilder()
                    .opName("GetObject")
                    .method("GET")
                    .bucket("bucket")
                    .key("key")
                    .build();

            try {
                client.presignInner(input, null);
            } catch (Exception e) {
                assertThat(e).hasMessageContaining("Credentials is null or empty.");
            }
        }
    }

    static class MockHttpClient implements HttpClient {

        public RequestMessage lastRequest;
        public List<ResponseMessage> responses;
        public List<RequestMessage> requests;
        public List<String> requestDates;

        @Override
        public CompletableFuture<ResponseMessage> sendAsync(RequestMessage request, RequestContext context) {
            saveRequest(request);
            CompletableFuture<ResponseMessage> future = new CompletableFuture<ResponseMessage>();
            future.complete(popResponse());
            return future;
        }

        @Override
        public ResponseMessage send(RequestMessage request, RequestContext context) {
            saveRequest(request);
            return popResponse();
        }

        private void saveRequest(RequestMessage request) {
            lastRequest = request;

            if (requests == null) {
                requests = new ArrayList<>();
            }
            requests.add(request);

            if (requestDates == null) {
                requestDates = new ArrayList<>();
            }
            requestDates.add(request.headers().get("Date"));
        }

        private ResponseMessage popResponse() {
            return responses.remove(0);
        }

        public void clear() {
            lastRequest = null;
            responses = null;
            requests = null;
            requestDates = null;
        }
    }

    static class NotRetryableStream extends FilterInputStream {

        public NotRetryableStream(InputStream in) {
            super(in);
        }

        @Override
        public boolean markSupported() {
            return false;
        }
    }

    static class MockHttpClientPutObject implements HttpClient {

        public RequestMessage lastRequest;
        public List<ResponseMessage> responses;
        public List<RequestMessage> requests;

        @Override
        public CompletableFuture<ResponseMessage> sendAsync(RequestMessage request, RequestContext context) {
            saveRequest(request);
            CompletableFuture<ResponseMessage> future = new CompletableFuture<ResponseMessage>();
            future.complete(popResponse());
            return future;
        }

        @Override
        public ResponseMessage send(RequestMessage request, RequestContext context) {
            saveRequest(request);
            return popResponse();
        }

        private void saveRequest(RequestMessage request) {
            lastRequest = request;

            if (requests == null) {
                requests = new ArrayList<>();
            }
            requests.add(request);
        }

        private ResponseMessage popResponse() {
            ResponseMessage resp = responses.remove(0);

            // consume request body
            InputStream is = lastRequest.body().toStream();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                IOUtils.copyLarge(is, os);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (resp.statusCode() == 200) {
                // update crc64
                Map<String, String> headers = Optional.ofNullable(resp.headers()).orElse(MapUtils.caseInsensitiveMap());
                byte[] bs = os.toByteArray();
                CRC64 crc = new CRC64(bs, bs.length);
                headers.putIfAbsent("x-oss-hash-crc64ecma", Long.toUnsignedString(crc.getValue()));
                resp = resp.toBuilder().headers(headers).build();
            }

            return resp;
        }

        public void clear() {
            lastRequest = null;
            responses = null;
            requests = null;
        }
    }

    static class MockProgressListener implements ProgressListener {
        public long total;
        public long incTotal;
        public long transferred;
        public boolean finished;

        public MockProgressListener() {
            this.total = 0;
            this.incTotal = 0;
            this.transferred = 0;
            this.finished = false;
        }

        @Override
        public void onProgress(long increment, long transferred, long total) {
            this.incTotal += increment;
            this.total = total;
            this.transferred = transferred;
            //System.out.printf("onProgress: %d, %d, %d\n", increment, transferred, total);
        }

        @Override
        public void onFinish() {
            this.finished = true;
        }
    }

    static class MockCRC64ResponseChecker extends CRC64ResponseChecker {
        public boolean accepted;

        public MockCRC64ResponseChecker(Checksum hash) {
            super(hash);
            this.accepted = false;
        }

        @Override
        public void accept(ResponseMessage responseMessage) {
            this.accepted = true;
            super.accept(responseMessage);
        }
    }
}
