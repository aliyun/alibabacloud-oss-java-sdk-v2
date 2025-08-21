package com.aliyun.sdk.service.oss2.internal;

import com.aliyun.sdk.service.oss2.io.StreamObserver;
import com.aliyun.sdk.service.oss2.retry.NopRetryer;
import com.aliyun.sdk.service.oss2.retry.Retryer;
import com.aliyun.sdk.service.oss2.transport.RequestMessage;
import com.aliyun.sdk.service.oss2.transport.ResponseMessage;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Middleware that adds retry capability to request execution
 */
class RetryerExecuteMiddleware implements ExecuteMiddleware {

    /**
     * Reference to the next middleware handler in the chain
     */
    private final ExecuteMiddleware nextHandler;

    /**
     * The retry strategy used for handling failures
     */
    private final Retryer retryer;

    /**
     * Constructor that initializes the retry middleware with a next handler and retry strategy
     *
     * @param nextHandler The next middleware to invoke
     * @param retryer     The retry strategy to use when errors occur
     */
    public RetryerExecuteMiddleware(ExecuteMiddleware nextHandler, Retryer retryer) {
        this.nextHandler = nextHandler;
        this.retryer = Optional.ofNullable(retryer).orElse(new NopRetryer());
    }

    /**
     * Synchronously executes the request with retry logic on failure
     *
     * @param request The request message object {@link RequestMessage}
     * @param context The execution context object {@link ExecuteContext}
     * @return Returns the successfully processed response message
     * @throws Exception If maximum attempts are reached or the error is not retryable
     */
    @Override
    public ResponseMessage execute(RequestMessage request, ExecuteContext context) throws Exception {
        int attempts = context.retryMaxAttempts;
        Exception error = null;
        Instant signTime = context.signingContext.getSignTime();
        Instant expirationTime = context.signingContext.getExpiration();

        for (int retries = 0; ; retries++) {

            try {
                return nextHandler.execute(request, context);
            } catch (Exception e) {
                error = e;
            }

            if (retries + 1 >= attempts) break;

            // TODO CancellationRequested

            if (!(request.body() == null || request.body().isReplayable())) {
                break;
            }

            if (!retryer.isErrorRetryable(error)) {
                break;
            }

            // delay
            Duration delay = retryer.retryDelay(retries + 1, error);
            try {
                TimeUnit.MILLISECONDS.sleep(delay.toMillis());
            } catch (InterruptedException e) {
                // Ignore
                error = e;
                break;
            }

            // reset to init state
            if (context.requestBodyObserver != null) {
                for (StreamObserver observer : context.requestBodyObserver) {
                    observer.reset();
                }
            }

            // reset signing time
            context.signingContext.setSignTime(signTime);
            context.signingContext.setExpiration(expirationTime);
        }
        throw error;
    }

    /**
     * Asynchronously executes the request with retry support
     *
     * @param request The request message object {@link RequestMessage}
     * @param context The execution context object {@link ExecuteContext}
     * @return A CompletableFuture containing the response message object {@link ResponseMessage}
     */
    @Override
    public CompletableFuture<ResponseMessage> executeAsync(RequestMessage request, ExecuteContext context) {
        int maxAttempts = context.retryMaxAttempts;
        Instant signTime = context.signingContext.getSignTime();
        CompletableFuture<ResponseMessage> future = new CompletableFuture<ResponseMessage>();
        attemptExecuteAsync(future, request, context, 0, maxAttempts, signTime);
        return future;
    }

    /**
     * Internal method that performs an async attempt with retry logic
     *
     * @param future      The future to complete with result or exception
     * @param request     The request message object {@link RequestMessage}
     * @param context     The execution context object {@link ExecuteContext}
     * @param retries     Number of retries attempted so far
     * @param maxAttempts Maximum number of retry attempts allowed
     */
    private void attemptExecuteAsync(
            CompletableFuture<ResponseMessage> future,
            RequestMessage request, ExecuteContext context,
            int retries, int maxAttempts, Instant signTime) {
        CompletableFuture<ResponseMessage> responseFuture = nextHandler.executeAsync(request, context);
        responseFuture.whenComplete((response, exception) -> {
            if (exception != null) {

                Throwable cause = exception.getCause();

                int nextRetries = retries + 1;

                if (nextRetries >= maxAttempts) {
                    future.completeExceptionally(exception);
                    return;
                }

                if (!(request.body() == null || request.body().isReplayable())) {
                    future.completeExceptionally(exception);
                    return;
                }

                if (!(context.dataConsumerSupplier == null || context.dataConsumerSupplier.isReplayable())) {
                    future.completeExceptionally(exception);
                    return;
                }

                // TODO CancellationRequested

                if (!this.retryer.isErrorRetryable(cause)) {
                    future.completeExceptionally(exception);
                    return;
                }

                // delay
                Duration delay = retryer.retryDelay(nextRetries, cause);
                try {
                    TimeUnit.MILLISECONDS.sleep(delay.toMillis());
                } catch (InterruptedException e) {
                    future.completeExceptionally(exception);
                    return;
                }

                // reset to init state
                if (context.requestBodyObserver != null) {
                    for (StreamObserver observer : context.requestBodyObserver) {
                        observer.reset();
                    }
                }

                // reset to init signTime
                context.signingContext.setSignTime(signTime);

                // retry
                attemptExecuteAsync(future, request, context, nextRetries, maxAttempts, signTime);

            } else {
                future.complete(response);
            }
        });
    }
}
