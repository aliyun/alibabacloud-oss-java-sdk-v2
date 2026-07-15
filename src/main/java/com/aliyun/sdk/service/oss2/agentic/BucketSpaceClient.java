package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.ClientConfiguration;
import com.aliyun.sdk.service.oss2.ClientOptions;
import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.types.BucketNameResolver;
import com.aliyun.sdk.service.oss2.types.EndpointProvider;
import com.aliyun.sdk.service.oss2.utils.HttpUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * A client for operating on BucketSpace resources.
 * <p>
 * This client automatically applies the BucketSpace provider to transform bucket names
 * to the physical BucketSpace format: {prefix}-{accountId}-{region}-bs-apsr
 */
public final class BucketSpaceClient {

    private BucketSpaceClient() {
    }

    /**
     * Creates a new BucketSpaceClient.
     *
     * @param config the client configuration
     * @param optFns optional functions to customize client options
     * @return a new OSSClient configured for BucketSpace operations
     */
    @SafeVarargs
    public static OSSClient create(ClientConfiguration config, Function<ClientOptions, ClientOptions>... optFns) {
        return create(config, Arrays.asList(optFns));
    }

    /**
     * Creates a new BucketSpaceClient.
     *
     * @param config the client configuration
     * @param optFns optional functions to customize client options
     * @return a new OSSClient configured for BucketSpace operations
     */
    public static OSSClient create(ClientConfiguration config, List<Function<ClientOptions, ClientOptions>> optFns) {
        List<Function<ClientOptions, ClientOptions>> allOptFns = new ArrayList<>(optFns);
        
        final String accountId = config.accountId().orElse("");
        final String region = config.region().orElse("");

        allOptFns.add(options -> {
            BucketSpaceProvider provider = new BucketSpaceProvider(
                    options.endpoint(), accountId, region);
            return options.toBuilder()
                    .endpointProvider(provider)
                    .bucketNameResolver(provider)
                    .build();
        });

        @SuppressWarnings("unchecked")
        Function<ClientOptions, ClientOptions>[] optFnsArray = allOptFns.toArray(new Function[0]);
        return new com.aliyun.sdk.service.oss2.DefaultOSSClient(config, optFnsArray);
    }

    static class BucketSpaceProvider implements EndpointProvider, BucketNameResolver {
        private final URI endpoint;
        private final String accountId;
        private final String region;
        private static final String SUFFIX = "bs-apsr";

        BucketSpaceProvider(URI endpoint, String accountId, String region) {
            this.endpoint = endpoint;
            this.accountId = accountId;
            this.region = region;
        }

        @Override
        public String buildBucketName(OperationInput input) {
            if (!input.bucket().isPresent()) return null;
            String prefix = input.bucket().get();
            return String.format("%s-%s-%s-%s", prefix, accountId, region, SUFFIX);
        }

        @Override
        public String buildURL(OperationInput input) {
            List<String> paths = new ArrayList<>();
            String host;

            if (input.bucket().isPresent()) {
                host = String.format("%s.%s", buildBucketName(input), endpoint.getAuthority());
            } else {
                host = endpoint.getAuthority();
            }

            if (input.key().isPresent()) {
                paths.add(HttpUtils.urlEncodePath(input.key().get()));
            }

            return String.format("%s://%s/%s",
                    endpoint.getScheme(),
                    host,
                    String.join("/", paths));
        }
    }
}
