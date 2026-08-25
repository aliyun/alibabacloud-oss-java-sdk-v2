package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.BaseClientBuilder;

/**
 * A builder for {@link OSSAsyncAgenticBucketClient}.
 *
 * <p><b>Required configuration</b><br>
 * Both {@code accountId} and {@code region} must be set: the agentic client resolves each
 * request's {@code bucket} prefix to the physical name
 * {@code {prefix}-{accountId}-{region}-ab-apsr}. An invalid {@code accountId} (non-digit)
 * does not fail the build; the error is deferred and surfaced when an operation is invoked.
 *
 * <p><b>Endpoint</b><br>
 * If {@code endpoint} is set it is used as-is (custom domain / CNAME); otherwise it is
 * derived from {@code region}: public {@code oss-{region}.aliyuncs.com} by default, or
 * internal {@code oss-{region}-internal.aliyuncs.com} when {@code useInternalEndpoint} is
 * enabled.
 */
public interface OSSAsyncAgenticBucketClientBuilder extends BaseClientBuilder<OSSAsyncAgenticBucketClientBuilder, OSSAsyncAgenticBucketClient> {
}
