package com.aliyun.sdk.service.oss2.internal;

import com.aliyun.sdk.service.oss2.credentials.AnonymousCredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.Credentials;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.exceptions.CredentialsException;
import com.aliyun.sdk.service.oss2.exceptions.CredentialsFetchException;
import com.aliyun.sdk.service.oss2.signer.NopSigner;
import com.aliyun.sdk.service.oss2.signer.Signer;
import com.aliyun.sdk.service.oss2.transport.RequestMessage;
import com.aliyun.sdk.service.oss2.transport.ResponseMessage;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Middleware for signing requests before they are sent over the network
 */
public class SignerExecuteMiddleware implements ExecuteMiddleware {

    /**
     * Reference to the next middleware handler in the chain
     */
    private final ExecuteMiddleware nextHandler;

    /**
     * The signer implementation used to sign requests
     */
    private final Signer singer;

    /**
     * The credentials provider used to retrieve signing credentials
     */
    private final CredentialsProvider provider;

    /**
     * Constructor that initializes the signer middleware with dependencies
     *
     * @param nextHandler The next middleware to invoke
     * @param singer      The {@link Signer} implementation to use for signing
     * @param provider    The credentials provider for retrieving signing keys
     */
    public SignerExecuteMiddleware(ExecuteMiddleware nextHandler, Signer singer, CredentialsProvider provider) {
        this.nextHandler = nextHandler;
        this.singer = Optional.ofNullable(singer).orElse(new NopSigner());
        this.provider = provider;
    }

    /**
     * Synchronously executes the request after signing it
     *
     * @param request The request message object {@link RequestMessage}
     * @param context The execution context object {@link ExecuteContext}
     * @return Returns the processed response message object {@link ResponseMessage}
     * @throws Exception If an error occurs during execution
     */
    @Override
    public ResponseMessage execute(RequestMessage request, ExecuteContext context) throws Exception {
        return nextHandler.execute(signRequest(request, context), context);
    }

    /**
     * Asynchronously executes the request after signing it
     *
     * @param request The request message object {@link RequestMessage}
     * @param context The execution context object {@link ExecuteContext}
     * @return A CompletableFuture wrapping the response message object {@link ResponseMessage}
     */
    @Override
    public CompletableFuture<ResponseMessage> executeAsync(RequestMessage request, ExecuteContext context) {
        return nextHandler.executeAsync(signRequest(request, context), context);
    }

    /**
     * Signs the request using the configured signer and credentials provider
     *
     * @param request The request message object {@link RequestMessage}
     * @param context The execution context object {@link ExecuteContext}
     * @return Returns the original or modified request message after signing
     */
    private RequestMessage signRequest(RequestMessage request, ExecuteContext context) {
        if (this.provider == null || this.provider instanceof AnonymousCredentialsProvider) {
            return request;
        }

        Credentials cred;
        try {
            cred = this.provider.getCredentials();
        } catch (Exception e) {
            throw new CredentialsFetchException(e);
        }

        if (cred == null || !cred.hasKeys()) {
            throw new CredentialsException("Credentials is null or empty.");
        }
        context.signingContext.setCredentials(cred);
        context.signingContext.setRequest(request);
        this.singer.sign(context.signingContext);
        return context.signingContext.getRequest();
    }
}
