package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.retry.Retryer;
import com.aliyun.sdk.service.oss2.signer.Signer;
import com.aliyun.sdk.service.oss2.transport.HttpClient;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;


/**
 * Base builder interface for creating OSS client instances.
 * <p>
 * This interface provides configuration options common to all OSS clients, including
 * {@link OSSClientBuilder} (synchronous), {@link OSSAsyncClientBuilder} (asynchronous),
 * and {@link OSSDualClientBuilder} (both synchronous and asynchronous).
 * <p>
 * <b>Minimal usage example:</b>
 * <pre>{@code
 * OSSClient client = OSSClient.builder()
 *     .region("cn-hangzhou")
 *     .credentialsProvider(credentialsProvider)
 *     .build();
 * }</pre>
 *
 * @param <B> the concrete builder type (for method chaining)
 * @param <T> the client type to be built
 */
public interface BaseClientBuilder<B extends BaseClientBuilder<B, T>, T> {
    /**
     * Builds and returns a new client instance from the configured properties.
     *
     * @return a new client instance of type {@code T}
     */
    T build();

    /**
     * Sets the region for the OSS service.
     * <p>
     * The region is used to automatically resolve the endpoint when {@link #endpoint(String)}
     * is not explicitly set. For example, setting the region to {@code "cn-hangzhou"} will
     * resolve the endpoint to {@code "https://oss-cn-hangzhou.aliyuncs.com"}.
     * <p>
     * Either {@code region} or {@code endpoint} must be specified. If both are set,
     * the {@code endpoint} takes precedence.
     *
     * @param value the region identifier, e.g. {@code "cn-hangzhou"}, {@code "us-west-1"}
     * @return this builder for method chaining
     */
    B region(String value);

    /**
     * Sets the endpoint URL for the OSS service.
     * <p>
     * When specified, this endpoint overrides the automatically resolved endpoint from
     * {@link #region(String)}. Use this when you need to connect to a specific endpoint,
     * such as a VPC internal endpoint or a custom domain.
     * <p>
     * Example values:
     * <ul>
     *   <li>{@code "https://oss-cn-hangzhou.aliyuncs.com"}</li>
     *   <li>{@code "https://oss-cn-hangzhou-internal.aliyuncs.com"}</li>
     * </ul>
     *
     * @param value the endpoint URL
     * @return this builder for method chaining
     */
    B endpoint(String value);

    /**
     * Sets a custom request signer.
     * <p>
     * By default, the SDK uses the signer determined by the {@link #signatureVersion(String)}.
     * Use this method to override the default signer with a custom implementation.
     *
     * @param value the custom {@link Signer} implementation
     * @return this builder for method chaining
     */
    B signer(Signer value);

    /**
     * Sets the maximum number of retry attempts for failed requests.
     * <p>
     * This is a shortcut for configuring retries without providing a full {@link Retryer}
     * implementation. If both {@code retryMaxAttempts} and {@link #retryer(Retryer)} are set,
     * the {@code retryer} takes precedence.
     * <p>
     * Default: {@code 3}
     *
     * @param value the maximum number of attempts (including the initial request)
     * @return this builder for method chaining
     */
    B retryMaxAttempts(Integer value);

    /**
     * Sets a custom retry strategy.
     * <p>
     * Use {@link com.aliyun.sdk.service.oss2.retry.StandardRetryer} for the built-in
     * retry strategy with configurable backoff, or implement the {@link Retryer} interface
     * for custom retry logic. If set, this overrides {@link #retryMaxAttempts(Integer)}.
     *
     * @param value the custom {@link Retryer} implementation
     * @return this builder for method chaining
     */
    B retryer(Retryer value);

    /**
     * Sets the credentials provider for request authentication.
     * <p>
     * The credentials provider supplies the AccessKey ID and AccessKey Secret
     * (and optionally a Security Token for STS) used to sign requests.
     *
     * @param value the {@link CredentialsProvider} implementation
     * @return this builder for method chaining
     */
    B credentialsProvider(CredentialsProvider value);

    /**
     * Sets the signature version for request signing.
     * <p>
     * Valid values:
     * <ul>
     *   <li>{@code "v4"} (default) — OSS4-HMAC-SHA256 signature, recommended for enhanced security</li>
     *   <li>{@code "v1"} — legacy HMAC-SHA1 signature, for backward compatibility</li>
     * </ul>
     *
     * @param value the signature version string
     * @return this builder for method chaining
     */
    B signatureVersion(String value);

    /**
     * Sets whether to disable SSL/TLS and use plain HTTP.
     * <p>
     * Default: {@code false} (SSL is enabled, using HTTPS).
     * <p>
     * <b>Warning:</b> Disabling SSL transmits data in plaintext and is not recommended
     * for production environments.
     *
     * @param value {@code true} to use HTTP, {@code false} to use HTTPS
     * @return this builder for method chaining
     */
    B disableSsl(Boolean value);

    /**
     * Sets whether to use the dual-stack (IPv4/IPv6) endpoint.
     * <p>
     * When enabled, the resolved endpoint supports both IPv4 and IPv6 access.
     * For example, the endpoint becomes {@code "<bucket>.cn-hangzhou.oss.aliyuncs.com"}.
     * <p>
     * Default: {@code false}
     *
     * @param value {@code true} to use the dual-stack endpoint
     * @return this builder for method chaining
     */
    B useDualStackEndpoint(Boolean value);

    /**
     * Sets whether to use the internal (VPC) endpoint.
     * <p>
     * Internal endpoints are accessible only within the same region's VPC or classic network
     * and do not incur public network traffic fees. For example, the endpoint becomes
     * {@code "oss-cn-hangzhou-internal.aliyuncs.com"}.
     * <p>
     * Default: {@code false}
     *
     * @param value {@code true} to use the internal endpoint
     * @return this builder for method chaining
     */
    B useInternalEndpoint(Boolean value);

    /**
     * Sets whether to use the transfer acceleration endpoint.
     * <p>
     * OSS transfer acceleration uses globally distributed acceleration points to speed up
     * data transfers over long distances. The bucket must have transfer acceleration enabled
     * in the OSS console. When enabled, the endpoint becomes
     * {@code "<bucket>.oss-accelerate.aliyuncs.com"}.
     * <p>
     * Default: {@code false}
     *
     * @param value {@code true} to use the acceleration endpoint
     * @return this builder for method chaining
     */
    B useAccelerateEndpoint(Boolean value);

    /**
     * Sets whether the endpoint is a custom domain (CNAME).
     * <p>
     * When enabled, the SDK treats the configured endpoint as a CNAME-mapped custom domain,
     * and adjusts request URL construction accordingly.
     * <p>
     * Default: {@code false}
     *
     * @param value {@code true} if the endpoint is a CNAME
     * @return this builder for method chaining
     */
    B useCName(Boolean value);

    /**
     * Sets whether to use path-style URL format instead of virtual-hosted-style.
     * <p>
     * <ul>
     *   <li>Path-style: {@code https://oss-cn-hangzhou.aliyuncs.com/<bucket>/<key>}</li>
     *   <li>Virtual-hosted-style (default): {@code https://<bucket>.oss-cn-hangzhou.aliyuncs.com/<key>}</li>
     * </ul>
     * <p>
     * Default: {@code false} (virtual-hosted-style)
     *
     * @param value {@code true} to use path-style URLs
     * @return this builder for method chaining
     */
    B usePathStyle(Boolean value);

    /**
     * Sets a custom HTTP client for sending requests.
     * <p>
     * By default, the SDK creates an HTTP client internally (Apache HttpClient 5 for synchronous,
     * Apache HttpAsyncClient 5 for asynchronous). Use this method to provide a pre-configured
     * HTTP client instance with custom connection pools, timeouts, or proxy settings.
     * <p>
     * When a custom HTTP client is set, connection-level options like {@link #connectTimeout(Duration)},
     * {@link #readWriteTimeout(Duration)}, {@link #proxyHost(String)}, and {@link #insecureSkipVerify(boolean)}
     * are ignored because they are already configured on the provided client.
     *
     * @param value the custom {@link HttpClient} implementation
     * @return this builder for method chaining
     */
    B httpClient(HttpClient value);

    /**
     * Sets additional HTTP headers to include in the signature calculation.
     * <p>
     * By default, only required headers (such as {@code Host}, {@code Content-Type}) are signed.
     * Use this method to include extra headers in the signature for enhanced request integrity.
     *
     * @param value a list of additional header names to sign
     * @return this builder for method chaining
     */
    B additionalHeaders(List<String> value);

    /**
     * Sets a custom user agent string appended to the default SDK user agent.
     *
     * @param value the custom user agent suffix
     * @return this builder for method chaining
     */
    B userAgent(String value);

    /**
     * Sets the connection timeout for HTTP requests.
     * <p>
     * This controls how long the client waits to establish a TCP connection.
     * Ignored if a custom {@link #httpClient(HttpClient)} is provided.
     *
     * @param value the connection timeout duration
     * @return this builder for method chaining
     */
    B connectTimeout(Duration value);

    /**
     * Sets the read/write (socket) timeout for HTTP requests.
     * <p>
     * This controls how long the client waits for data to be transferred after
     * a connection is established. Ignored if a custom {@link #httpClient(HttpClient)} is provided.
     *
     * @param value the read/write timeout duration
     * @return this builder for method chaining
     */
    B readWriteTimeout(Duration value);

    /**
     * Sets whether to skip SSL certificate verification.
     * <p>
     * Default: {@code false}
     * <p>
     * <b>Warning:</b> Enabling this option makes the connection vulnerable to
     * man-in-the-middle attacks. Only use for development or testing with self-signed certificates.
     *
     * @param value {@code true} to skip SSL certificate verification
     * @return this builder for method chaining
     */
    B insecureSkipVerify(boolean value);

    /**
     * Sets whether to enable HTTP redirect following.
     * <p>
     * Default: {@code false}
     *
     * @param value {@code true} to follow HTTP redirects (3xx responses)
     * @return this builder for method chaining
     */
    B enabledRedirect(boolean value);

    /**
     * Sets the HTTP proxy host.
     * <p>
     * Specify the proxy address for all HTTP/HTTPS requests made by the client.
     * Ignored if a custom {@link #httpClient(HttpClient)} is provided.
     * <p>
     * Example: {@code "http://proxy.example.com:8080"}
     *
     * @param value the proxy host URL
     * @return this builder for method chaining
     */
    B proxyHost(String value);

    /**
     * Sets whether to disable CRC64 integrity check on uploads.
     * <p>
     * By default, the SDK performs a CRC64 checksum verification after uploading data to
     * ensure data integrity. Disabling this may improve performance but at the cost of
     * reduced data integrity assurance.
     * <p>
     * Default: {@code false} (CRC64 check is enabled)
     *
     * @param value {@code true} to disable CRC64 check on uploads
     * @return this builder for method chaining
     */
    B disableUploadCRC64Check(boolean value);

    /**
     * Sets the account ID of the bucket owner.
     * <p>
     * This is required for operations that need to identify the bucket owner account,
     * such as cross-account access scenarios. Setting this can also improve request
     * routing performance.
     *
     * @param value the Alibaba Cloud account ID of the bucket owner
     * @return this builder for method chaining
     */
    B accountId(String value);

    /**
     * Configure the scheduled executor service used for scheduling internal SDK tasks
     * such as async retry attempts and timeout tracking.
     * <p>
     * If not set, the SDK will create a default single-thread scheduled executor internally.
     * <p>
     * <b>The SDK will not automatically close a user-provided executor when the client is closed.
     * It is the responsibility of the caller to shut down the executor after all clients using it
     * have been closed.</b>
     *
     * @param value the ScheduledExecutorService to use
     * @return this builder
     */
    B scheduledExecutorService(ScheduledExecutorService value);
}
