package com.aliyun.sdk.service.oss2;

/**
 * Builder for creating {@link OSSClient} instances (synchronous API).
 * <p>
 * Example:
 * <pre>{@code
 * OSSClient client = OSSClient.builder()
 *     .region("cn-hangzhou")
 *     .credentialsProvider(credentialsProvider)
 *     .build();
 * }</pre>
 *
 * @see OSSClient
 * @see BaseClientBuilder
 */
public interface OSSClientBuilder extends BaseClientBuilder<OSSClientBuilder, OSSClient> {

    /**
     * Sets whether to use Apache HttpClient 4.x instead of the default Apache HttpClient 5.x.
     * <p>
     * Default: {@code false} (uses Apache HttpClient 5.x).
     * <p>
     * Set to {@code true} if your environment requires Apache HttpClient 4.x compatibility.
     * This option is ignored if a custom {@link #httpClient} is provided.
     *
     * @param value {@code true} to use Apache HttpClient 4.x
     * @return this builder for method chaining
     */
    OSSClientBuilder useApacheHttpClient4(boolean value);
}
