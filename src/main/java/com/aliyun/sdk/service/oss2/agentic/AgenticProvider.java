package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.types.AddressStyleType;
import com.aliyun.sdk.service.oss2.types.BucketNameResolver;
import com.aliyun.sdk.service.oss2.types.EndpointProvider;
import com.aliyun.sdk.service.oss2.utils.HttpUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Resolves the physical bucket name and request URL for agentic buckets and bucket spaces.
 * <p>
 * The physical name is {@code {bucket}-{accountId}-{region}-{suffix}} where suffix is
 * {@code ab-apsr} for agentic buckets or {@code bs-apsr} for bucket spaces. A single
 * instance is wired as both the {@link BucketNameResolver} (used for signing) and the
 * {@link EndpointProvider} (used for the request host).
 * <p>
 * Under {@link AddressStyleType#VirtualHostedAlias} the host carries the short label
 * {@code {bucket}-alias-{suffix}} instead, while signing keeps the physical name.
 */
class AgenticProvider implements EndpointProvider, BucketNameResolver {
    /**
     * The literal segment that replaces {@code {accountId}-{region}} in the short host label.
     */
    private static final String ALIAS_TOKEN = "alias";

    private final URI endpoint;
    private final String accountId;
    private final String region;
    private final String suffix;
    private final AddressStyleType addressStyle;

    AgenticProvider(URI endpoint, String accountId, String region, String suffix, AddressStyleType addressStyle) {
        this.endpoint = endpoint;
        this.accountId = accountId == null ? "" : accountId;
        this.region = region == null ? "" : region;
        this.suffix = suffix;
        this.addressStyle = addressStyle;
    }

    @Override
    public String buildBucketName(OperationInput input) {
        if (!input.bucket().isPresent()) return null;
        if (accountId.isEmpty()) {
            throw new IllegalArgumentException("missing required field, AccountId");
        }
        if (region.isEmpty()) {
            throw new IllegalArgumentException("missing required field, Region");
        }
        String prefix = input.bucket().get();
        return String.format("%s-%s-%s-%s", prefix, accountId, region, suffix);
    }

    @Override
    public String buildURL(OperationInput input) {
        List<String> paths = new ArrayList<>();
        String host = endpoint.getAuthority();
        if (input.bucket().isPresent()) {
            switch (addressStyle) {
                case Path:
                    paths.add(buildBucketName(input));
                    if (!input.key().isPresent()) {
                        paths.add("");
                    }
                    break;
                case VirtualHostedAlias:
                    String label = String.format("%s-%s-%s", input.bucket().get(), ALIAS_TOKEN, suffix);
                    if (label.length() > 63) {
                        throw new IllegalArgumentException(String.format(
                                "the host label \"%s\" exceeds the maximum length of 63 characters", label));
                    }
                    host = String.format("%s.%s", label, endpoint.getAuthority());
                    break;
                default:
                    String fullName = buildBucketName(input);
                    if (fullName.length() > 63) {
                        throw new IllegalArgumentException(String.format(
                                "the host label \"%s\" exceeds the maximum length of 63 characters", fullName));
                    }
                    host = String.format("%s.%s", fullName, endpoint.getAuthority());
                    break;
            }
        }
        if (input.key().isPresent()) {
            paths.add(HttpUtils.urlEncodePath(input.key().get()));
        }
        return String.format("%s://%s/%s", endpoint.getScheme(), host, String.join("/", paths));
    }
}
