package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.internal.ClientImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

/**
 * Internal implementation of {@link OSSClient}.
 */
public class DefaultOSSVectorsClient implements OSSVectorsClient {
    final ClientImpl clientImpl;

    public DefaultOSSVectorsClient(ClientConfiguration config) {
        this(config, new ArrayList<>());
    }

    @SafeVarargs
    public DefaultOSSVectorsClient(ClientConfiguration config, Function<ClientOptions, ClientOptions>... optFns) {
        this(config, Arrays.asList(optFns));
    }

    private DefaultOSSVectorsClient(ClientConfiguration config, Collection<Function<ClientOptions, ClientOptions>> optFns) {
        this.clientImpl = new ClientImpl(config, optFns);
    }

    @Override
    public OperationOutput invokeOperation(OperationInput input, OperationOptions opts) {
        return this.clientImpl.execute(input, opts);
    }

    @Override
    public void close() throws Exception {
        this.clientImpl.close();
    }
}
