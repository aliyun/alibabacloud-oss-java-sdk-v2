package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.*;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.agentic.operations.*;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

public class DefaultOSSAgenticBucketClient implements OSSAgenticBucketClient {
    final ClientImpl clientImpl;

    public DefaultOSSAgenticBucketClient(ClientConfiguration config) {
        this(config, new ArrayList<>());
    }

    @SafeVarargs
    public DefaultOSSAgenticBucketClient(ClientConfiguration config, Function<ClientOptions, ClientOptions>... optFns) {
        this(config, Arrays.asList(optFns));
    }

    private DefaultOSSAgenticBucketClient(ClientConfiguration config, Collection<Function<ClientOptions, ClientOptions>> optFns) {
        this.clientImpl = new ClientImpl(config, optFns);
    }

    @Override
    public OperationOutput invokeOperation(OperationInput input, OperationOptions opts) {
        return this.clientImpl.execute(input, opts);
    }

    @Override public CreateAgenticBucketResult createAgenticBucket(CreateAgenticBucketRequest request, OperationOptions options) { return AgenticBucketBasic.createAgenticBucket(this.clientImpl, request, options); }
    @Override public DeleteAgenticBucketResult deleteAgenticBucket(DeleteAgenticBucketRequest request, OperationOptions options) { return AgenticBucketBasic.deleteAgenticBucket(this.clientImpl, request, options); }
    @Override public GetAgenticBucketResult getAgenticBucket(GetAgenticBucketRequest request, OperationOptions options) { return AgenticBucketBasic.getAgenticBucket(this.clientImpl, request, options); }
    @Override public ListAgenticBucketsResult listAgenticBuckets(ListAgenticBucketsRequest request, OperationOptions options) { return AgenticBucketBasic.listAgenticBuckets(this.clientImpl, request, options); }
    @Override public PutAgenticBucketStatusResult putAgenticBucketStatus(PutAgenticBucketStatusRequest request, OperationOptions options) { return AgenticBucketBasic.putAgenticBucketStatus(this.clientImpl, request, options); }
    @Override public ListBucketSpacesResult listBucketSpaces(ListBucketSpacesRequest request, OperationOptions options) { return AgenticBucketBasic.listBucketSpaces(this.clientImpl, request, options); }

    @Override public PutAgenticBucketAclResult putAgenticBucketAcl(PutAgenticBucketAclRequest request, OperationOptions options) { return AgenticBucketAcl.putAgenticBucketAcl(this.clientImpl, request, options); }
    @Override public GetAgenticBucketAclResult getAgenticBucketAcl(GetAgenticBucketAclRequest request, OperationOptions options) { return AgenticBucketAcl.getAgenticBucketAcl(this.clientImpl, request, options); }

    @Override public PutAgenticBucketEncryptionResult putAgenticBucketEncryption(PutAgenticBucketEncryptionRequest request, OperationOptions options) { return AgenticBucketEncryption.putAgenticBucketEncryption(this.clientImpl, request, options); }
    @Override public GetAgenticBucketEncryptionResult getAgenticBucketEncryption(GetAgenticBucketEncryptionRequest request, OperationOptions options) { return AgenticBucketEncryption.getAgenticBucketEncryption(this.clientImpl, request, options); }
    @Override public DeleteAgenticBucketEncryptionResult deleteAgenticBucketEncryption(DeleteAgenticBucketEncryptionRequest request, OperationOptions options) { return AgenticBucketEncryption.deleteAgenticBucketEncryption(this.clientImpl, request, options); }

    @Override public PutAgenticBucketVersioningResult putAgenticBucketVersioning(PutAgenticBucketVersioningRequest request, OperationOptions options) { return AgenticBucketVersioning.putAgenticBucketVersioning(this.clientImpl, request, options); }
    @Override public GetAgenticBucketVersioningResult getAgenticBucketVersioning(GetAgenticBucketVersioningRequest request, OperationOptions options) { return AgenticBucketVersioning.getAgenticBucketVersioning(this.clientImpl, request, options); }

    @Override public PutAgenticBucketPolicyResult putAgenticBucketPolicy(PutAgenticBucketPolicyRequest request, OperationOptions options) { return AgenticBucketPolicy.putAgenticBucketPolicy(this.clientImpl, request, options); }
    @Override public GetAgenticBucketPolicyResult getAgenticBucketPolicy(GetAgenticBucketPolicyRequest request, OperationOptions options) { return AgenticBucketPolicy.getAgenticBucketPolicy(this.clientImpl, request, options); }
    @Override public DeleteAgenticBucketPolicyResult deleteAgenticBucketPolicy(DeleteAgenticBucketPolicyRequest request, OperationOptions options) { return AgenticBucketPolicy.deleteAgenticBucketPolicy(this.clientImpl, request, options); }

    @Override public PutAgenticBucketPublicAccessBlockResult putAgenticBucketPublicAccessBlock(PutAgenticBucketPublicAccessBlockRequest request, OperationOptions options) { return AgenticBucketPublicAccessBlock.putAgenticBucketPublicAccessBlock(this.clientImpl, request, options); }
    @Override public GetAgenticBucketPublicAccessBlockResult getAgenticBucketPublicAccessBlock(GetAgenticBucketPublicAccessBlockRequest request, OperationOptions options) { return AgenticBucketPublicAccessBlock.getAgenticBucketPublicAccessBlock(this.clientImpl, request, options); }
    @Override public DeleteAgenticBucketPublicAccessBlockResult deleteAgenticBucketPublicAccessBlock(DeleteAgenticBucketPublicAccessBlockRequest request, OperationOptions options) { return AgenticBucketPublicAccessBlock.deleteAgenticBucketPublicAccessBlock(this.clientImpl, request, options); }

    @Override
    public void close() throws Exception {
        this.clientImpl.close();
    }
}
