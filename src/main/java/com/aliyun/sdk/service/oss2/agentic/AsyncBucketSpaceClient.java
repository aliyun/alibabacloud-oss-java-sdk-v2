package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.ClientConfiguration;
import com.aliyun.sdk.service.oss2.ClientOptions;
import com.aliyun.sdk.service.oss2.OSSAsyncClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * An asynchronous client for operating on BucketSpace resources.
 * <p>
 * This client automatically applies the BucketSpace provider to transform bucket names
 * to the physical BucketSpace format: {prefix}-{accountId}-{region}-bs-apsr
 */
public final class AsyncBucketSpaceClient {

    private AsyncBucketSpaceClient() {
    }

    /**
     * Creates a new AsyncBucketSpaceClient.
     *
     * @param config the client configuration
     * @param optFns optional functions to customize client options
     * @return a new OSSAsyncClient configured for BucketSpace operations
     */
    @SafeVarargs
    public static OSSAsyncClient create(ClientConfiguration config, Function<ClientOptions, ClientOptions>... optFns) {
        return create(config, Arrays.asList(optFns));
    }

    /**
     * Creates a new AsyncBucketSpaceClient.
     *
     * @param config the client configuration
     * @param optFns optional functions to customize client options
     * @return a new OSSAsyncClient configured for BucketSpace operations
     */
    public static OSSAsyncClient create(ClientConfiguration config, List<Function<ClientOptions, ClientOptions>> optFns) {
        List<Function<ClientOptions, ClientOptions>> allOptFns = new ArrayList<>(optFns);

        final String accountId = config.accountId().orElse("");
        final String region = config.region().orElse("");

        allOptFns.add(options -> {
            AgenticProvider provider = new AgenticProvider(
                    options.endpoint(), accountId, region, "bs-apsr",
                    options.addressStyle());
            return options.toBuilder()
                    .endpointProvider(provider)
                    .bucketNameResolver(provider)
                    .build();
        });

        @SuppressWarnings("unchecked")
        Function<ClientOptions, ClientOptions>[] optFnsArray = allOptFns.toArray(new Function[0]);
        return new com.aliyun.sdk.service.oss2.DefaultOSSAsyncClient(config, optFnsArray);
    }
}
