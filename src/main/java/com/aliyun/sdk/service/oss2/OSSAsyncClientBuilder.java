package com.aliyun.sdk.service.oss2;

/**
 * Builder for creating {@link OSSAsyncClient} instances (asynchronous API).
 * <p>
 * Example:
 * <pre>{@code
 * OSSAsyncClient client = OSSAsyncClient.builder()
 *     .region("cn-hangzhou")
 *     .credentialsProvider(credentialsProvider)
 *     .build();
 * }</pre>
 *
 * @see OSSAsyncClient
 * @see BaseClientBuilder
 */
public interface OSSAsyncClientBuilder extends BaseClientBuilder<OSSAsyncClientBuilder, OSSAsyncClient> {
}
