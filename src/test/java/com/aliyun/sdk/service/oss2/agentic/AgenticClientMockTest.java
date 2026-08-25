package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.ClientConfiguration;
import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.agentic.models.DeleteAgenticBucketRequest;
import com.aliyun.sdk.service.oss2.credentials.AnonymousCredentialsProvider;
import com.aliyun.sdk.service.oss2.transport.HttpClient;
import com.aliyun.sdk.service.oss2.transport.RequestContext;
import com.aliyun.sdk.service.oss2.transport.RequestMessage;
import com.aliyun.sdk.service.oss2.transport.ResponseMessage;
import com.aliyun.sdk.service.oss2.transport.StringBinaryData;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Client-level mock tests: they go through the public builders so the
 * configuration flags that select an addressing style are covered too.
 */
public class AgenticClientMockTest {

    @Test
    public void useVirtualHostedAliasSelectsAliasHost() throws Exception {
        RecordingHttpClient mock = new RecordingHttpClient();

        try (OSSAgenticBucketClient client = OSSAgenticBucketClient.newBuilder()
                .region("cn-hangzhou")
                .accountId("1234567890")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .httpClient(mock)
                .useVirtualHostedAlias(true)
                .build()) {

            client.deleteAgenticBucket(DeleteAgenticBucketRequest.newBuilder()
                    .bucket("my-bucket")
                    .build());
        }

        assertThat(mock.lastRequest).isNotNull();
        assertThat(mock.lastRequest.uri().toString())
                .isEqualTo("https://my-bucket-alias-ab-apsr.oss-cn-hangzhou.aliyuncs.com/?agenticBucket=");
    }

    @Test
    public void defaultStyleKeepsFullNameHost() throws Exception {
        RecordingHttpClient mock = new RecordingHttpClient();

        try (OSSAgenticBucketClient client = OSSAgenticBucketClient.newBuilder()
                .region("cn-hangzhou")
                .accountId("1234567890")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .httpClient(mock)
                .build()) {

            client.deleteAgenticBucket(DeleteAgenticBucketRequest.newBuilder()
                    .bucket("my-bucket")
                    .build());
        }

        assertThat(mock.lastRequest.uri().toString())
                .isEqualTo("https://my-bucket-1234567890-cn-hangzhou-ab-apsr.oss-cn-hangzhou.aliyuncs.com/?agenticBucket=");
    }

    @Test
    public void usePathStyleWinsOverUseVirtualHostedAlias() throws Exception {
        RecordingHttpClient mock = new RecordingHttpClient();

        try (OSSAgenticBucketClient client = OSSAgenticBucketClient.newBuilder()
                .region("cn-hangzhou")
                .accountId("1234567890")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .httpClient(mock)
                .usePathStyle(true)
                .useVirtualHostedAlias(true)
                .build()) {

            client.deleteAgenticBucket(DeleteAgenticBucketRequest.newBuilder()
                    .bucket("my-bucket")
                    .build());
        }

        assertThat(mock.lastRequest.uri().toString())
                .isEqualTo("https://oss-cn-hangzhou.aliyuncs.com/my-bucket-1234567890-cn-hangzhou-ab-apsr/?agenticBucket=");
    }

    @Test
    public void useVirtualHostedAliasStillRequiresAccountId() throws Exception {
        RecordingHttpClient mock = new RecordingHttpClient();

        // The short label drops accountId from the host, but signing keeps the full
        // name, so a missing accountId must still fail before the request is sent.
        try (OSSAgenticBucketClient client = OSSAgenticBucketClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .httpClient(mock)
                .useVirtualHostedAlias(true)
                .build()) {

            assertThatThrownBy(() -> client.deleteAgenticBucket(DeleteAgenticBucketRequest.newBuilder()
                    .bucket("my-bucket")
                    .build()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("AccountId");
        }

        assertThat(mock.lastRequest).isNull();
    }

    @Test
    public void bucketSpaceClientUseVirtualHostedAliasSelectsAliasHost() throws Exception {
        RecordingHttpClient mock = new RecordingHttpClient();

        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .accountId("1234567890")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .httpClient(mock)
                .useVirtualHostedAlias(true)
                .build();

        try (OSSClient client = BucketSpaceClient.create(config)) {
            client.invokeOperation(OperationInput.newBuilder()
                    .opName("Test")
                    .method("GET")
                    .bucket("my-space")
                    .key("test.txt")
                    .build(), OperationOptions.defaults());
        }

        assertThat(mock.lastRequest.uri().toString())
                .isEqualTo("https://my-space-alias-bs-apsr.oss-cn-hangzhou.aliyuncs.com/test.txt");
    }

    /**
     * Records the request that reaches the transport and replies with a fixed 200.
     */
    static class RecordingHttpClient implements HttpClient {

        RequestMessage lastRequest;

        @Override
        public ResponseMessage send(RequestMessage request, RequestContext context) {
            lastRequest = request;
            return okResponse();
        }

        @Override
        public CompletableFuture<ResponseMessage> sendAsync(RequestMessage request, RequestContext context) {
            lastRequest = request;
            CompletableFuture<ResponseMessage> future = new CompletableFuture<>();
            future.complete(okResponse());
            return future;
        }

        private static ResponseMessage okResponse() {
            return ResponseMessage.newBuilder()
                    .statusCode(200)
                    .body(new StringBinaryData(""))
                    .build();
        }
    }
}
