package com.aliyun.sdk.service.oss2.internal;

import com.aliyun.sdk.service.oss2.*;
import com.aliyun.sdk.service.oss2.credentials.AnonymousCredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.Credentials;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.exceptions.CredentialsException;
import com.aliyun.sdk.service.oss2.exceptions.OperationException;
import com.aliyun.sdk.service.oss2.exceptions.PresignExpirationException;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.retry.Retryer;
import com.aliyun.sdk.service.oss2.retry.StandardRetryer;
import com.aliyun.sdk.service.oss2.signer.Signer;
import com.aliyun.sdk.service.oss2.signer.SignerV1;
import com.aliyun.sdk.service.oss2.signer.SignerV4;
import com.aliyun.sdk.service.oss2.signer.SigningContext;
import com.aliyun.sdk.service.oss2.transport.*;
import com.aliyun.sdk.service.oss2.transport.apache5client.Apache5AsyncHttpClient;
import com.aliyun.sdk.service.oss2.transport.apache5client.Apache5HttpClient;
import com.aliyun.sdk.service.oss2.transport.apache5client.Apache5MixedHttpClient;
import com.aliyun.sdk.service.oss2.types.AddressStyleType;
import com.aliyun.sdk.service.oss2.types.AuthMethodType;
import com.aliyun.sdk.service.oss2.types.FeatureFlagsType;
import com.aliyun.sdk.service.oss2.utils.*;
import com.fasterxml.jackson.databind.JsonNode;

import java.net.URI;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.aliyun.sdk.service.oss2.AttributeKey.SUBRESOURCE;
import static com.aliyun.sdk.service.oss2.internal.OssUtils.EndpointType;
import static com.aliyun.sdk.service.oss2.internal.OssUtils.buildHostPath;

/**
 * Implementation of the OSS client responsible for building and executing operation requests
 */
public class ClientImpl implements AutoCloseable {

    private final static OperationOptions defaultPresignOpOpt = OperationOptions.newBuilder()
            .authMethod(AuthMethodType.Query).build();
    /**
     * Configuration options for the client including endpoint, signer, credentials provider, etc.
     */
    final ClientOptions options;
    /**
     *
     */
    final InnerOptions innerOptions;
    /**
     * Execution stack used to process middleware logic such as signing, retries, and transport
     */
    final ExecuteStack executeStack;

    /**
     * Constructor that initializes the client instance and execution pipeline based on configuration
     *
     * @param config Base client configuration
     */
    public ClientImpl(ClientConfiguration config) {
        this(config, new ArrayList<>());
    }

    /**
     * Constructor that initializes the client instance and execution pipeline based on configuration
     *
     * @param config Base client configuration
     * @param optFns Array of functions used to customize client options
     */
    public ClientImpl(ClientConfiguration config, Collection<Function<ClientOptions, ClientOptions>> optFns) {
        ClientOptions opts = resolveConfig(config);
        for (Function<ClientOptions, ClientOptions> fn : optFns) {
            opts = fn.apply(opts);
        }
        this.options = opts;

        InnerOptions innerOpts = new InnerOptions();
        innerOpts.setUserAgent(resolveUserAgent(config));
        this.innerOptions = innerOpts;

        // build execute stack
        TransportExecuteMiddleware transport = new TransportExecuteMiddleware(opts.httpClient());
        ExecuteStack stack = new ExecuteStack(transport);

        stack.push(x -> new RetryerExecuteMiddleware(x, this.options.retryer()), "Retryer");
        stack.push(x -> new SignerExecuteMiddleware(x, this.options.signer(), this.options.credentialsProvider()), "Signer");
        stack.push(ResponseCheckerExecuteMiddleware::new, "ResponseChecker");
        this.executeStack = stack;
    }

    /**
     * Closes client resources including underlying HTTP connection pools
     *
     * @throws Exception If an error occurs while closing
     */
    @Override
    public void close() throws Exception {
        HttpClient httpClient = options.httpClient();
        if (httpClient instanceof AutoCloseable) {
            ((AutoCloseable) httpClient).close();
        }
    }

    /**
     * Synchronously executes the provided operation input and returns the output result
     *
     * @param input Input parameters for the operation
     * @param opts  Options specific to this operation
     * @return Returns a fully constructed {@link OperationOutput} object
     */
    public OperationOutput execute(OperationInput input, OperationOptions opts) {
        // verify input
        verifyOperation(input);

        // build execute context;
        Pair<RequestMessage, ExecuteContext> ctx = buildRequestContext(input, opts);

        // execute
        try {
            ResponseMessage response = this.executeStack.execute(ctx.first(), ctx.second());
            return OperationOutput.newBuilder()
                    .input(input)
                    .statusCode(response.statusCode())
                    .headers(response.headers())
                    .body(response.body())
                    .build();
        } catch (Exception e) {
            throw new OperationException(input.opName(), e);
        }
    }

    /**
     * Asynchronously executes the provided operation input and returns a future-based result
     *
     * @param input Input parameters for the operation
     * @param opts  Options specific to this operation
     * @return A CompletableFuture wrapping the operation output
     */
    public CompletableFuture<OperationOutput> executeAsync(OperationInput input, OperationOptions opts) {
        CompletableFuture<OperationOutput> future = new CompletableFuture<>();

        try {
            // verify input
            verifyOperation(input);

            // build execute context;
            Pair<RequestMessage, ExecuteContext> ctx = buildRequestContext(input, opts);

            this.executeStack.executeAsync(ctx.first(), ctx.second()).whenComplete((response, exception) -> {
                if (exception != null) {
                    // to Operation Exception
                    future.completeExceptionally(new OperationException(input.opName(), exception));
                } else {
                    // to OperationOutput
                    future.complete(OperationOutput.newBuilder()
                            .input(input)
                            .statusCode(response.statusCode())
                            .headers(response.headers())
                            .body(response.body())
                            .build());
                }
            });
        } catch (Exception e) {
            future.completeExceptionally(e);
        }

        return future;
    }

    /**
     * Sign the provided operation input and returns presigned url.
     *
     * @param input Input parameters for the operation
     * @param opts  Options specific to this operation
     * @return the presigned url.
     */
    public PresignInnerResult presignInner(OperationInput input, OperationOptions opts) {
        // verify input
        verifyOperation(input);

        // build execute context;
        opts = Optional.ofNullable(opts).orElse(defaultPresignOpOpt);
        Pair<RequestMessage, ExecuteContext> ctx = buildRequestContext(input, opts);
        CredentialsProvider provider = this.options.credentialsProvider();
        PresignInnerResult result = new PresignInnerResult();
        RequestMessage request = ctx.first();

        if (provider != null &&
                !(provider instanceof AnonymousCredentialsProvider) &&
                ctx.second().signingContext != null) {

            Credentials cred = provider.getCredentials();
            Signer signer = this.options.signer();
            if (cred == null || !cred.hasKeys()) {
                throw new CredentialsException("Credentials is null or empty.");
            }

            ctx.second().signingContext.setCredentials(cred);
            ctx.second().signingContext.setRequest(request);
            signer.sign(ctx.second().signingContext);
            request = ctx.second().signingContext.getRequest();

            // save to result
            result.expiration = ctx.second().signingContext.getExpiration();

            // signed headers
            // content-type, content-md5, x-oss- and additionalHeaders in sign v4
            List<String> expect = new ArrayList<>();
            expect.add("content-type");
            expect.add("content-md5");
            if (signer instanceof SignerV4) {
                List<String> addHeaders = ctx.second().signingContext.getAdditionalHeaders();
                if (addHeaders != null) {
                    addHeaders.forEach(x -> expect.add(x.toLowerCase()));
                }

                // check
                Instant nowTo7Days = Instant.now().plusSeconds(7 * 24 * 3600);
                if (result.expiration.isAfter(nowTo7Days)) {
                    throw new PresignExpirationException();
                }
            }

            //signed headers
            Map<String, String> signedHeaders = MapUtils.caseInsensitiveMap();
            request.headers().forEach((h, v) -> {
                String low = h.toLowerCase();
                if (expect.contains(low) || low.startsWith("x-oss-")) {
                    signedHeaders.put(h, v);
                }
            });
            result.signedHeaders = signedHeaders;
        }

        result.url = request.uri().toString();
        result.method = request.method();

        return result;
    }

    public int getFeatureFlags() {
        return options.featureFlags();
    }

    /**
     * Validates the operation input including endpoint, bucket name, and key constraints
     *
     * @param input The operation input to validate
     */
    private void verifyOperation(OperationInput input) {
        // check endpoint
        if (this.options.endpoint() == null) {
            throw new IllegalArgumentException("endpoint or region is invalid");
        }

        // check method
        if (StringUtils.isNullOrEmpty(input.method())) {
            throw new IllegalArgumentException("input.method is null or empty");
        }

        // check bucket name
        if (input.bucket().isPresent()) {
            String bucket = input.bucket().get();
            if (!Ensure.isValidateBucketName(bucket)) {
                throw new IllegalArgumentException("input.bucket is invalid, got " + bucket + ".");
            }
        }

        // check object name
        if (input.key().isPresent()) {
            String key = input.key().get();
            if (!Ensure.isValidateObjectName(key)) {
                throw new IllegalArgumentException("input.key is invalid, got " + key + ".");
            }
        }
    }

    /**
     * Builds the request message and execution context for network transmission
     *
     * @param input  The operation input object
     * @param opOpts The operation-specific configuration options
     * @return A Pair containing the request message and execution context
     */
    private Pair<RequestMessage, ExecuteContext> buildRequestContext(OperationInput input, OperationOptions opOpts) {
        if (opOpts == null) {
            opOpts = OperationOptions.defaults();
        }

        ExecuteContext context = new ExecuteContext();

        // default api options
        context.retryMaxAttempts = opOpts.retryMaxAttempts().orElse(options.retryer().maxAttempts());

        // track request body
        if (input.opMetadata().containsKey(AttributeKey.UPLOAD_OBSERVER)) {
            context.requestBodyObserver = new ArrayList<>();
            context.requestBodyObserver.addAll(input.opMetadata().get(AttributeKey.UPLOAD_OBSERVER));
        }

        // response handlers
        context.onResponseMessage = new ArrayList<>();
        context.onResponseMessage.add(OnServiceError.INSTANCE);
        if (input.opMetadata().containsKey(AttributeKey.RESPONSE_HANDLER)) {
            context.onResponseMessage.addAll(input.opMetadata().get(AttributeKey.RESPONSE_HANDLER));
        }

        if (input.opMetadata().containsKey(AttributeKey.RESPONSE_HEADERS_READ)) {
            context.responseHeadersRead = Boolean.TRUE;
        }

        if (input.opMetadata().containsKey(AttributeKey.RESPONSE_CONSUMER_SUPPLIER)) {
            context.dataConsumerSupplier = input.opMetadata().get(AttributeKey.RESPONSE_CONSUMER_SUPPLIER);
        }

        // signing context
        AuthMethodType authMethod = opOpts.authMethod().orElse(options.authMethod());
        SigningContext signCtx = new SigningContext();
        signCtx.setProduct(options.product());
        signCtx.setRegion(options.region());
        signCtx.setBucket(input.bucket().orElse(""));
        signCtx.setKey(input.key().orElse(""));
        signCtx.setAuthMethodQuery(authMethod == AuthMethodType.Query);
        signCtx.setSubResource(input.opMetadata().get(SUBRESOURCE));
        signCtx.setAdditionalHeaders(options.additionalHeaders());

        // signing time from user
        if (input.opMetadata().containsKey(AttributeKey.EXPIRATION_TIME)) {
            signCtx.setExpiration(input.opMetadata().get(AttributeKey.EXPIRATION_TIME));
        }
        context.signingContext = signCtx;

        // request
        // request::host & path & query
        StringBuilder url = new StringBuilder();
        if (options.endpointProvider() != null) {
            url.append(options.endpointProvider().buildURL(input));
        } else {
            URI endpoint = options.endpoint();
            url.append(endpoint.getScheme()).append("://");
            url.append(buildHostPath(input, endpoint.getAuthority(), options.addressStyle()));
        }
        Optional<String> query = HttpUtils.encodeQueryParameters(input.parameters());
        query.ifPresent(s -> url.append("?").append(s));

        // request::headers
        Map<String, String> headers = input.headers();
        headers.put("User-Agent", innerOptions.getUserAgent());

        // request::body
        BinaryData body = input.body().orElse(new ByteArrayBinaryData(new byte[0]));

        RequestMessage request = RequestMessage.newBuilder()
                .method(input.method())
                .uri(url.toString())
                .headers(headers)
                .body(body)
                .build();

        return new Pair<>(request, context);
    }

    /**
     * Resolves and constructs the final ClientOptions from the provided configuration
     *
     * @param config Base client configuration
     * @return The resolved ClientOptions instance {@link ClientOptions}
     */
    private ClientOptions resolveConfig(ClientConfiguration config) {
        if (!config.credentialsProvider().isPresent()) {
            throw new IllegalArgumentException("credentialsProvider is null");
        }
        URI endpoint = resolveEndpoint(config);
        return new ClientOptions.Builder()
                .product(Defaults.Product)
                .region(config.region().orElse(""))
                .endpoint(endpoint)
                .retryer(resolveRetryer(config))
                .signer(resolveSigner(config))
                .credentialsProvider(config.credentialsProvider().get())
                .httpClient(resolveHttpClient(config))
                .addressStyle(resolveAddressStyle(config, endpoint))
                .authMethod(AuthMethodType.Header)
                .additionalHeaders(config.additionalHeaders().orElse(Collections.emptyList()))
                .featureFlags(resolveFeatureFlags(config))
                .build();
    }

    /**
     * Parses and generates the service endpoint URI
     *
     * @param config Base client configuration
     * @return The parsed service endpoint URI {@link URI}
     */
    private URI resolveEndpoint(ClientConfiguration config) {
        if (!config.region().isPresent() &&
                !config.endpoint().isPresent()) {
            return null;
        }

        boolean disableSsl = config.disableSsl().orElse(Defaults.DisableSsl);
        String endpoint = config.endpoint().orElse("");
        String region = config.region().orElse("");

        if (config.endpoint().isPresent()) {
            endpoint = OssUtils.addScheme(endpoint, disableSsl);
        } else if (Ensure.isValidRegion(region)) {
            EndpointType type;
            if (config.useDualStackEndpoint().orElse(false)) {
                type = EndpointType.DualStack;
            } else if (config.useInternalEndpoint().orElse(false)) {
                type = EndpointType.Internal;
            } else if (config.useAccelerateEndpoint().orElse(false)) {
                type = EndpointType.Accelerate;
            } else {
                type = EndpointType.Default;
            }
            endpoint = OssUtils.regionToEndpoint(region, type, disableSsl);
        }

        try {
            URI uri = new URI(endpoint);
            return (uri.getHost() == null) ? null : uri;
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Determines the address style type to use (VirtualHosted / Path)
     *
     * @param config   Base client configuration
     * @param endpoint The resolved service endpoint
     * @return The resolved address style type {@link AddressStyleType}
     */
    private AddressStyleType resolveAddressStyle(ClientConfiguration config, URI endpoint) {
        AddressStyleType style;
        if (config.useCName().orElse(false)) {
            style = AddressStyleType.CName;
        } else if (config.usePathStyle().orElse(false)) {
            style = AddressStyleType.Path;
        } else {
            style = AddressStyleType.VirtualHosted;
        }

        //If it is IP or local host, set to path-style
        if (endpoint != null) {
            if (InetAddressUtils.isValid(endpoint.getHost()) ||
                    endpoint.getHost().equals("localhost")) {
                style = AddressStyleType.Path;
            }
        }
        return style;
    }

    /**
     * Resolves and returns the retry strategy to be used by this client
     *
     * @param config Base client configuration
     * @return The resolved Retryer instance {@link Retryer}
     */
    private Retryer resolveRetryer(ClientConfiguration config) {
        return config.retryer().orElseGet(() -> StandardRetryer.newBuilder()
                .maxAttempts(config.retryMaxAttempts().orElse(Defaults.MAX_ATTEMPTS))
                .build());
    }

    /**
     * Resolves and returns the signer implementation to be used by this client
     *
     * @param config Base client configuration
     * @return The resolved Signer instance {@link Signer}
     */
    private Signer resolveSigner(ClientConfiguration config) {
        return config.signer().orElseGet(() -> {
            if (config.signatureVersion().orElse("v4").equals("v1")) {
                return new SignerV1();
            }
            return new SignerV4();
        });
    }

    /**
     * Resolves and returns the HTTP client implementation to be used by this client
     *
     * @param config Base client configuration
     * @return The resolved HttpClient instance {@link HttpClient}
     */
    private HttpClient resolveHttpClient(ClientConfiguration config) {
        return config.httpClient().orElseGet(() -> {
            //JdkHttpClientBuilder builder = new JdkHttpClientBuilder();
            // TODO, connect timeout, proxy
            //return builder.build();
            //Apache5HttpClientBuilder builder = new Apache5HttpClientBuilder();
            //return builder.build();
            return new Apache5MixedHttpClient(
                    Apache5HttpClient.custom().build(),
                    Apache5AsyncHttpClient.custom().build()
            );
        });
    }

    /**
     * Resolves and returns the user agent
     *
     * @param config Base client configuration
     * @return The user agent
     */
    private String resolveUserAgent(ClientConfiguration config) {
        String ua = VersionInfoUtils.getDefaultUserAgent();
        // Append httpclient name
        if (this.options.httpClient() != null) {
            ua = ua + "/" + this.options.httpClient().name();
        }
        if (config.userAgent().isPresent()) {
            ua = ua + "/" + config.userAgent().get();
        }
        return ua;
    }

    private int resolveFeatureFlags(ClientConfiguration config) {
        int flags = Defaults.FEATURE_FLAGS;
        if (config.DisableUploadCRC64Check().orElse(false)) {
            flags = ~FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD.getValue() & flags;
        }
        return flags;
    }

    public static class InnerOptions {

        private String userAgent;

        public InnerOptions() {
        }

        public String getUserAgent() {
            return userAgent;
        }

        public void setUserAgent(String value) {
            this.userAgent = value;
        }
    }

    public static class PresignInnerResult {
        public String url;
        public String method;
        public Instant expiration;
        public Map<String, String> signedHeaders;
    }

    /**
     * Service error response handler used to process HTTP responses with non-2xx status codes.
     */
    static class OnServiceError implements Consumer<ResponseMessage> {
        /**
         * Static instance used globally to handle service error responses
         */
        public static OnServiceError INSTANCE = new OnServiceError();

        /**
         * Processes the response message.
         * This method overrides the accept method from the functional interface Consumer to process the response message.
         * Its main purpose is to handle non-2xx HTTP status codes in the response, and to process the response body or headers based on the status code.
         *
         * @param response The response message, containing the HTTP status code, headers, and body of the response.
         */
        @Override
        public void accept(ResponseMessage response) {
            Map<String, String> headers = Optional.ofNullable(response.headers()).orElse(MapUtils.caseInsensitiveMap());
            Map<String, String> errorFields = MapUtils.caseSensitiveMap();
            int statusCode = response.statusCode();

            if (statusCode / 100 == 2) {
                return;
            }

            byte[] data = response.body() != null ? response.body().toBytes() : null;

            if (data == null || data.length == 0) {
                //try to get error from x-oss-err header
                if (headers.containsKey("x-oss-err")) {
                    data = Base64Utils.decodeString(headers.get("x-oss-err"));
                }
            }
            data = Optional.ofNullable(data).orElse(new byte[0]);

            if ("application/json".equals(response.headers().get("Content-Type"))) {
                // json format
                try {
                    if (data.length > 0) {
                        JsonNode root = JsonUtils.getJsonRootElement(data);
                        if (root.has("Error")) {
                            Iterator<Map.Entry<String, JsonNode>> iterator = root.get("Error").fields();
                            while (iterator.hasNext()) {
                                Map.Entry<String, JsonNode> entry = iterator.next();
                                errorFields.put(entry.getKey(), entry.getValue().asText());
                            }
                        } else {
                            errorFields.put("Message", toErrorMessage("Not found key Error", data));
                        }
                    } else {
                        errorFields.put("Message", toErrorMessage("Empty body", data));
                    }
                } catch (Exception ignored) {
                    // Fail to parse xml
                    errorFields.put("Message", toErrorMessage("Failed to parse json from response body", data));
                }
            } else {
                // xml format
                try {
                    if (data.length > 0) {
                        JsonNode root = XmlUtils.getXmlRootElement(data);
                        if (root.has("Error")) {
                            Iterator<Map.Entry<String, JsonNode>> iterator = root.get("Error").fields();
                            while (iterator.hasNext()) {
                                Map.Entry<String, JsonNode> entry = iterator.next();
                                errorFields.put(entry.getKey(), entry.getValue().asText());
                            }
                        } else {
                            errorFields.put("Message", toErrorMessage("Not found tag <Error>", data));
                        }
                    } else {
                        errorFields.put("Message", toErrorMessage("Empty body", data));
                    }
                } catch (Exception ignored) {
                    // Fail to parse xml
                    errorFields.put("Message", toErrorMessage("Failed to parse xml from response body", data));
                }
            }

            // other
            Instant ts = null;
            try {
                ts = DateUtils.parseRfc822Date(headers.get("Date"));
            } catch (Exception ignored) {
            }

            String requestTarget = null;
            if (response.request() != null) {
                requestTarget = String.format("%s %s", response.request().method(), response.request().uri());
            }

            throw ServiceException.newBuilder()
                    .statusCode(statusCode)
                    .errorFields(errorFields)
                    .headers(headers)
                    .snapshot(data)
                    .requestTarget(requestTarget)
                    .timestamp(ts)
                    .build();
        }

        private String toErrorMessage(final String prefix, byte[] data) {
            if (data == null || data.length == 0) {
                return prefix;
            }
            String rawString = new String(data);
            return prefix + ", part response body " + rawString.substring(0, Math.min(255, rawString.length()));
        }
    }
}
