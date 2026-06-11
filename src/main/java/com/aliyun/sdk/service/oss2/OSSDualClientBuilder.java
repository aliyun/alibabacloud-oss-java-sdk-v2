package com.aliyun.sdk.service.oss2;

/**
 * Builder for creating {@link OSSDualClient} instances that support both synchronous and asynchronous APIs.
 * <p>
 * Example:
 * <pre>{@code
 * OSSDualClient client = OSSDualClient.builder()
 *     .region("cn-hangzhou")
 *     .credentialsProvider(credentialsProvider)
 *     .build();
 * }</pre>
 *
 * @see OSSDualClient
 * @see BaseClientBuilder
 */
public interface OSSDualClientBuilder extends BaseClientBuilder<OSSDualClientBuilder, OSSDualClient> {
}
