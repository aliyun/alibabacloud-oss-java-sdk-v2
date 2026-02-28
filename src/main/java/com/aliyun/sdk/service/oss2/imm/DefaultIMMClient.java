package com.aliyun.sdk.service.oss2.imm;

import com.aliyun.sdk.service.oss2.*;
import com.aliyun.sdk.service.oss2.imm.models.CreateDatasetRequest;
import com.aliyun.sdk.service.oss2.imm.models.CreateDatasetResult;
import com.aliyun.sdk.service.oss2.imm.operations.DatasetBasic;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

/**
 * Internal implementation of {@link OSSClient}.
 */
public class DefaultIMMClient implements IMMClient {
    final ClientImpl clientImpl;

    public DefaultIMMClient(ClientConfiguration config) {
        this(config, new ArrayList<>());
    }

    @SafeVarargs
    public DefaultIMMClient(ClientConfiguration config, Function<ClientOptions, ClientOptions>... optFns) {
        this(config, Arrays.asList(optFns));
    }

    private DefaultIMMClient(ClientConfiguration config, Collection<Function<ClientOptions, ClientOptions>> optFns) {
        this.clientImpl = new ClientImpl(config, optFns);
    }

    @Override
    public OperationOutput invokeOperation(OperationInput input, OperationOptions opts) {
        return this.clientImpl.execute(input, opts);
    }

    @Override
    public CreateDatasetResult createDataset(CreateDatasetRequest request, OperationOptions options) {
        return DatasetBasic.createDataset(this.clientImpl, request, options);
    }

    @Override
    public void close() throws Exception {
        this.clientImpl.close();
    }
}
