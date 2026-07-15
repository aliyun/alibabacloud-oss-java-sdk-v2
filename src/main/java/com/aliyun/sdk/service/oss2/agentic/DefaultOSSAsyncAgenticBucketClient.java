package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.*;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.agentic.operations.*;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class DefaultOSSAsyncAgenticBucketClient implements OSSAsyncAgenticBucketClient {
    private final ClientImpl clientImpl;

    public DefaultOSSAsyncAgenticBucketClient(ClientConfiguration config) {
        this(config, new ArrayList<>());
    }

    @SafeVarargs
    public DefaultOSSAsyncAgenticBucketClient(ClientConfiguration config, Function<ClientOptions, ClientOptions>... optFns) {
        this(config, Arrays.asList(optFns));
    }

    private DefaultOSSAsyncAgenticBucketClient(ClientConfiguration config, Collection<Function<ClientOptions, ClientOptions>> optFns) {
        this.clientImpl = new ClientImpl(config, optFns);
    }

    @Override public void close() throws Exception { this.clientImpl.close(); }

    @Override public CompletableFuture<OperationOutput> invokeOperationAsync(OperationInput input, OperationOptions opts) { return this.clientImpl.executeAsync(input, opts); }

    @Override public CompletableFuture<CreateAgenticBucketResult> createAgenticBucketAsync(CreateAgenticBucketRequest request, OperationOptions options) { return AgenticBucketBasic.createAgenticBucketAsync(this.clientImpl, request, options); }
    @Override public CompletableFuture<DeleteAgenticBucketResult> deleteAgenticBucketAsync(DeleteAgenticBucketRequest request, OperationOptions options) { return AgenticBucketBasic.deleteAgenticBucketAsync(this.clientImpl, request, options); }
    @Override public CompletableFuture<GetAgenticBucketResult> getAgenticBucketAsync(GetAgenticBucketRequest request, OperationOptions options) { return AgenticBucketBasic.getAgenticBucketAsync(this.clientImpl, request, options); }
    @Override public CompletableFuture<ListAgenticBucketsResult> listAgenticBucketsAsync(ListAgenticBucketsRequest request, OperationOptions options) { return AgenticBucketBasic.listAgenticBucketsAsync(this.clientImpl, request, options); }
    @Override public CompletableFuture<PutAgenticBucketStatusResult> putAgenticBucketStatusAsync(PutAgenticBucketStatusRequest request, OperationOptions options) { return AgenticBucketBasic.putAgenticBucketStatusAsync(this.clientImpl, request, options); }
    @Override public CompletableFuture<ListBucketSpacesResult> listBucketSpacesAsync(ListBucketSpacesRequest request, OperationOptions options) { return AgenticBucketBasic.listBucketSpacesAsync(this.clientImpl, request, options); }

    @Override public CompletableFuture<PutAgenticBucketAclResult> putAgenticBucketAclAsync(PutAgenticBucketAclRequest request, OperationOptions options) { return AgenticBucketAcl.putAgenticBucketAclAsync(this.clientImpl, request, options); }
    @Override public CompletableFuture<GetAgenticBucketAclResult> getAgenticBucketAclAsync(GetAgenticBucketAclRequest request, OperationOptions options) { return AgenticBucketAcl.getAgenticBucketAclAsync(this.clientImpl, request, options); }

    @Override public CompletableFuture<PutAgenticBucketEncryptionResult> putAgenticBucketEncryptionAsync(PutAgenticBucketEncryptionRequest request, OperationOptions options) { return AgenticBucketEncryption.putAgenticBucketEncryptionAsync(this.clientImpl, request, options); }
    @Override public CompletableFuture<GetAgenticBucketEncryptionResult> getAgenticBucketEncryptionAsync(GetAgenticBucketEncryptionRequest request, OperationOptions options) { return AgenticBucketEncryption.getAgenticBucketEncryptionAsync(this.clientImpl, request, options); }
    @Override public CompletableFuture<DeleteAgenticBucketEncryptionResult> deleteAgenticBucketEncryptionAsync(DeleteAgenticBucketEncryptionRequest request, OperationOptions options) { return AgenticBucketEncryption.deleteAgenticBucketEncryptionAsync(this.clientImpl, request, options); }

    @Override public CompletableFuture<PutAgenticBucketVersioningResult> putAgenticBucketVersioningAsync(PutAgenticBucketVersioningRequest request, OperationOptions options) { return AgenticBucketVersioning.putAgenticBucketVersioningAsync(this.clientImpl, request, options); }
    @Override public CompletableFuture<GetAgenticBucketVersioningResult> getAgenticBucketVersioningAsync(GetAgenticBucketVersioningRequest request, OperationOptions options) { return AgenticBucketVersioning.getAgenticBucketVersioningAsync(this.clientImpl, request, options); }

    @Override public CompletableFuture<PutAgenticBucketPolicyResult> putAgenticBucketPolicyAsync(PutAgenticBucketPolicyRequest request, OperationOptions options) { return AgenticBucketPolicy.putAgenticBucketPolicyAsync(this.clientImpl, request, options); }
    @Override public CompletableFuture<GetAgenticBucketPolicyResult> getAgenticBucketPolicyAsync(GetAgenticBucketPolicyRequest request, OperationOptions options) { return AgenticBucketPolicy.getAgenticBucketPolicyAsync(this.clientImpl, request, options); }
    @Override public CompletableFuture<DeleteAgenticBucketPolicyResult> deleteAgenticBucketPolicyAsync(DeleteAgenticBucketPolicyRequest request, OperationOptions options) { return AgenticBucketPolicy.deleteAgenticBucketPolicyAsync(this.clientImpl, request, options); }

    @Override public CompletableFuture<PutAgenticBucketPublicAccessBlockResult> putAgenticBucketPublicAccessBlockAsync(PutAgenticBucketPublicAccessBlockRequest request, OperationOptions options) { return AgenticBucketPublicAccessBlock.putAgenticBucketPublicAccessBlockAsync(this.clientImpl, request, options); }
    @Override public CompletableFuture<GetAgenticBucketPublicAccessBlockResult> getAgenticBucketPublicAccessBlockAsync(GetAgenticBucketPublicAccessBlockRequest request, OperationOptions options) { return AgenticBucketPublicAccessBlock.getAgenticBucketPublicAccessBlockAsync(this.clientImpl, request, options); }
    @Override public CompletableFuture<DeleteAgenticBucketPublicAccessBlockResult> deleteAgenticBucketPublicAccessBlockAsync(DeleteAgenticBucketPublicAccessBlockRequest request, OperationOptions options) { return AgenticBucketPublicAccessBlock.deleteAgenticBucketPublicAccessBlockAsync(this.clientImpl, request, options); }
}
