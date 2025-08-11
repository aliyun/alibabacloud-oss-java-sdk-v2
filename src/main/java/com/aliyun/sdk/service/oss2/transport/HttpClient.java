package com.aliyun.sdk.service.oss2.transport;

import java.util.concurrent.CompletableFuture;

public interface HttpClient {
    default CompletableFuture<ResponseMessage> sendAsync(RequestMessage request, RequestContext context) {
        throw new UnsupportedOperationException("Not Implemented.");
    }

    default ResponseMessage send(RequestMessage request, RequestContext context) {
        throw new UnsupportedOperationException("Not Implemented.");
    }

    /**
     * The well-formed client name of the client
     *
     * @return String containing the name of the client
     */
    default String name() {
        return "UNKNOWN";
    }
}
