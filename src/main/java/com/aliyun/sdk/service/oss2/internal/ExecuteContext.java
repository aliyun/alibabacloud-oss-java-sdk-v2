package com.aliyun.sdk.service.oss2.internal;

import com.aliyun.sdk.service.oss2.io.StreamObserver;
import com.aliyun.sdk.service.oss2.signer.SigningContext;
import com.aliyun.sdk.service.oss2.transport.ResponseMessage;

import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;

/**
 * Execution context class used to store runtime configuration and state information during request execution
 */
public class ExecuteContext {
    /**
     * Maximum number of retry attempts allowed for the current request
     */
    public int retryMaxAttempts;

    /**
     * Timeout duration for a single request attempt
     */
    public Duration requestOnceTimeout;

    /**
     * List of response message handlers used to process {@link ResponseMessage} objects
     */
    public List<Consumer<ResponseMessage>> onResponseMessage;

    /**
     * The context used for signing
     */
    public SigningContext signingContext;

    /**
     * List of observer used to track request’s body
     */
    public List<StreamObserver> requestBodyObserver;

    /**
     * The operation should complete as soon as a response is available and headers are read.
     * It is the streaming mode.
     */
    public Boolean responseHeadersRead;
}
