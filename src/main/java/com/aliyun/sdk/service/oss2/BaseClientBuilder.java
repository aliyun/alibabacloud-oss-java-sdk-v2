package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.retry.Retryer;
import com.aliyun.sdk.service.oss2.signer.Signer;
import com.aliyun.sdk.service.oss2.transport.HttpClient;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;


/**
 * This includes configuration specific to OSS that is supported by
 * {@link OSSClientBuilder}, {@link OSSAsyncClientBuilder} and {@link OSSDualClientBuilder}
 */
public interface BaseClientBuilder<B extends BaseClientBuilder<B, T>, T> {
    /**
     * An immutable object that is created from the
     * properties that have been set on the builder.
     *
     * @return an instance of T
     */
    T build();

    B region(String value);

    B endpoint(String value);

    B signer(Signer value);

    B retryMaxAttempts(Integer value);

    B retryer(Retryer value);

    B credentialsProvider(CredentialsProvider value);

    B signatureVersion(String value);

    B disableSsl(Boolean value);

    B useDualStackEndpoint(Boolean value);

    B useInternalEndpoint(Boolean value);

    B useAccelerateEndpoint(Boolean value);

    B useCName(Boolean value);

    B usePathStyle(Boolean value);

    B httpClient(HttpClient value);

    B additionalHeaders(List<String> value);

    B userAgent(String value);

    B connectTimeout(Duration value);

    B readWriteTimeout(Duration value);

    B insecureSkipVerify(boolean value);

    B enabledRedirect(boolean value);

    B proxyHost(String value);

    B disableUploadCRC64Check(boolean value);

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
