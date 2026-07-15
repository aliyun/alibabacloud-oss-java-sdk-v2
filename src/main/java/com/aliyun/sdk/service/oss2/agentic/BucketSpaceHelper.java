package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.ClientConfiguration;

/**
 * A helper that resolves a bucket space prefix to its physical bucket name.
 * The physical name is {@code {prefix}-{accountId}-{region}-bs-apsr}, so both
 * {@code accountId} and {@code region} must be provided (via {@link ClientConfiguration}
 * or directly).
 */
public class BucketSpaceHelper {
    private final String accountId;
    private final String region;

    public BucketSpaceHelper(ClientConfiguration config) {
        this.accountId = config.accountId().orElse("");
        this.region = config.region().orElse("");
    }

    public BucketSpaceHelper(String accountId, String region) {
        this.accountId = accountId;
        this.region = region;
    }

    /**
     * Resolves a bucket space prefix to its physical bucket name
     * {@code {prefix}-{accountId}-{region}-bs-apsr}.
     */
    public String toBucketName(String prefix) {
        return String.format("%s-%s-%s-bs-apsr", prefix, accountId, region);
    }
}
