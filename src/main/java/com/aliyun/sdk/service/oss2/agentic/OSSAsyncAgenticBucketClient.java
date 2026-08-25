package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import java.util.concurrent.CompletableFuture;

/**
 * A client for accessing OSS AgenticBucket asynchronously.
 * This can be created using the static {@link #newBuilder()} method.
 *
 * <p><b>Bucket name resolution</b><br>
 * The {@code bucket} field of every request is a <i>prefix</i>, not the full bucket name.
 * Internally it is resolved to {@code {prefix}-{accountId}-{region}-ab-apsr}, so both
 * {@code accountId} and {@code region} must be configured on the builder. Operations that
 * carry no bucket (for example {@link #listAgenticBucketsAsync}) are routed to the
 * region-level host instead of a bucket-level host.
 *
 * <p><b>Endpoint modes</b> (resolved from the configuration)<br>
 * If a custom {@code endpoint} is set it is used as-is (custom domain / CNAME); otherwise
 * the endpoint is derived from {@code region}: public {@code oss-{region}.aliyuncs.com}
 * (default) or internal {@code oss-{region}-internal.aliyuncs.com} when
 * {@code useInternalEndpoint} is enabled.
 */
public interface OSSAsyncAgenticBucketClient extends AutoCloseable {

    static OSSAsyncAgenticBucketClientBuilder newBuilder() {
        return new DefaultOSSAsyncAgenticBucketClientBuilder();
    }

    /**
     * Invokes an operation with the low-level input/output model.
     *
     * @param input The operation input.
     * @param opts  The operation options.
     * @return A {@link CompletableFuture} that completes with the operation output.
     */
    default CompletableFuture<OperationOutput> invokeOperationAsync(OperationInput input, OperationOptions opts) {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates an agentic bucket. The {@code bucket} field of the request is the prefix.
     *
     * @param request A {@link CreateAgenticBucketRequest} for CreateAgenticBucket operation.
     * @return A {@link CompletableFuture} of {@link CreateAgenticBucketResult} for CreateAgenticBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<CreateAgenticBucketResult> createAgenticBucketAsync(CreateAgenticBucketRequest request) { return createAgenticBucketAsync(request, OperationOptions.defaults()); }

    /**
     * Creates an agentic bucket. The {@code bucket} field of the request is the prefix.
     *
     * @param request A {@link CreateAgenticBucketRequest} for CreateAgenticBucket operation.
     * @param options The operation options.
     * @return A {@link CompletableFuture} of {@link CreateAgenticBucketResult} for CreateAgenticBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<CreateAgenticBucketResult> createAgenticBucketAsync(CreateAgenticBucketRequest request, OperationOptions options) { throw new UnsupportedOperationException(); }

    /**
     * Deletes an agentic bucket.
     *
     * @param request A {@link DeleteAgenticBucketRequest} for DeleteAgenticBucket operation.
     * @return A {@link CompletableFuture} of {@link DeleteAgenticBucketResult} for DeleteAgenticBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteAgenticBucketResult> deleteAgenticBucketAsync(DeleteAgenticBucketRequest request) { return deleteAgenticBucketAsync(request, OperationOptions.defaults()); }

    /**
     * Deletes an agentic bucket.
     *
     * @param request A {@link DeleteAgenticBucketRequest} for DeleteAgenticBucket operation.
     * @param options The operation options.
     * @return A {@link CompletableFuture} of {@link DeleteAgenticBucketResult} for DeleteAgenticBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteAgenticBucketResult> deleteAgenticBucketAsync(DeleteAgenticBucketRequest request, OperationOptions options) { throw new UnsupportedOperationException(); }

    /**
     * Gets the information of an agentic bucket.
     *
     * @param request A {@link GetAgenticBucketRequest} for GetAgenticBucket operation.
     * @return A {@link CompletableFuture} of {@link GetAgenticBucketResult} for GetAgenticBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetAgenticBucketResult> getAgenticBucketAsync(GetAgenticBucketRequest request) { return getAgenticBucketAsync(request, OperationOptions.defaults()); }

    /**
     * Gets the information of an agentic bucket.
     *
     * @param request A {@link GetAgenticBucketRequest} for GetAgenticBucket operation.
     * @param options The operation options.
     * @return A {@link CompletableFuture} of {@link GetAgenticBucketResult} for GetAgenticBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetAgenticBucketResult> getAgenticBucketAsync(GetAgenticBucketRequest request, OperationOptions options) { throw new UnsupportedOperationException(); }

    /**
     * Lists agentic buckets. This is a region-level operation and carries no bucket.
     *
     * @param request A {@link ListAgenticBucketsRequest} for ListAgenticBuckets operation.
     * @return A {@link CompletableFuture} of {@link ListAgenticBucketsResult} for ListAgenticBuckets operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListAgenticBucketsResult> listAgenticBucketsAsync(ListAgenticBucketsRequest request) { return listAgenticBucketsAsync(request, OperationOptions.defaults()); }

    /**
     * Lists agentic buckets. This is a region-level operation and carries no bucket.
     *
     * @param request A {@link ListAgenticBucketsRequest} for ListAgenticBuckets operation.
     * @param options The operation options.
     * @return A {@link CompletableFuture} of {@link ListAgenticBucketsResult} for ListAgenticBuckets operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListAgenticBucketsResult> listAgenticBucketsAsync(ListAgenticBucketsRequest request, OperationOptions options) { throw new UnsupportedOperationException(); }

    /**
     * Sets the status of an agentic bucket.
     *
     * @param request A {@link PutAgenticBucketStatusRequest} for PutAgenticBucketStatus operation.
     * @return A {@link CompletableFuture} of {@link PutAgenticBucketStatusResult} for PutAgenticBucketStatus operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutAgenticBucketStatusResult> putAgenticBucketStatusAsync(PutAgenticBucketStatusRequest request) { return putAgenticBucketStatusAsync(request, OperationOptions.defaults()); }

    /**
     * Sets the status of an agentic bucket.
     *
     * @param request A {@link PutAgenticBucketStatusRequest} for PutAgenticBucketStatus operation.
     * @param options The operation options.
     * @return A {@link CompletableFuture} of {@link PutAgenticBucketStatusResult} for PutAgenticBucketStatus operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutAgenticBucketStatusResult> putAgenticBucketStatusAsync(PutAgenticBucketStatusRequest request, OperationOptions options) { throw new UnsupportedOperationException(); }

    /**
     * Lists the bucket spaces of an agentic bucket.
     *
     * @param request A {@link ListBucketSpacesRequest} for ListBucketSpaces operation.
     * @return A {@link CompletableFuture} of {@link ListBucketSpacesResult} for ListBucketSpaces operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListBucketSpacesResult> listBucketSpacesAsync(ListBucketSpacesRequest request) { return listBucketSpacesAsync(request, OperationOptions.defaults()); }

    /**
     * Lists the bucket spaces of an agentic bucket.
     *
     * @param request A {@link ListBucketSpacesRequest} for ListBucketSpaces operation.
     * @param options The operation options.
     * @return A {@link CompletableFuture} of {@link ListBucketSpacesResult} for ListBucketSpaces operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListBucketSpacesResult> listBucketSpacesAsync(ListBucketSpacesRequest request, OperationOptions options) { throw new UnsupportedOperationException(); }

    /**
     * Sets the access control list (ACL) of an agentic bucket.
     *
     * @param request A {@link PutAgenticBucketAclRequest} for PutAgenticBucketAcl operation.
     * @return A {@link CompletableFuture} of {@link PutAgenticBucketAclResult} for PutAgenticBucketAcl operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutAgenticBucketAclResult> putAgenticBucketAclAsync(PutAgenticBucketAclRequest request) { return putAgenticBucketAclAsync(request, OperationOptions.defaults()); }

    /**
     * Sets the access control list (ACL) of an agentic bucket.
     *
     * @param request A {@link PutAgenticBucketAclRequest} for PutAgenticBucketAcl operation.
     * @param options The operation options.
     * @return A {@link CompletableFuture} of {@link PutAgenticBucketAclResult} for PutAgenticBucketAcl operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutAgenticBucketAclResult> putAgenticBucketAclAsync(PutAgenticBucketAclRequest request, OperationOptions options) { throw new UnsupportedOperationException(); }

    /**
     * Gets the access control list (ACL) of an agentic bucket.
     *
     * @param request A {@link GetAgenticBucketAclRequest} for GetAgenticBucketAcl operation.
     * @return A {@link CompletableFuture} of {@link GetAgenticBucketAclResult} for GetAgenticBucketAcl operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetAgenticBucketAclResult> getAgenticBucketAclAsync(GetAgenticBucketAclRequest request) { return getAgenticBucketAclAsync(request, OperationOptions.defaults()); }

    /**
     * Gets the access control list (ACL) of an agentic bucket.
     *
     * @param request A {@link GetAgenticBucketAclRequest} for GetAgenticBucketAcl operation.
     * @param options The operation options.
     * @return A {@link CompletableFuture} of {@link GetAgenticBucketAclResult} for GetAgenticBucketAcl operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetAgenticBucketAclResult> getAgenticBucketAclAsync(GetAgenticBucketAclRequest request, OperationOptions options) { throw new UnsupportedOperationException(); }

    /**
     * Sets the server-side encryption configuration of an agentic bucket.
     *
     * @param request A {@link PutAgenticBucketEncryptionRequest} for PutAgenticBucketEncryption operation.
     * @return A {@link CompletableFuture} of {@link PutAgenticBucketEncryptionResult} for PutAgenticBucketEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutAgenticBucketEncryptionResult> putAgenticBucketEncryptionAsync(PutAgenticBucketEncryptionRequest request) { return putAgenticBucketEncryptionAsync(request, OperationOptions.defaults()); }

    /**
     * Sets the server-side encryption configuration of an agentic bucket.
     *
     * @param request A {@link PutAgenticBucketEncryptionRequest} for PutAgenticBucketEncryption operation.
     * @param options The operation options.
     * @return A {@link CompletableFuture} of {@link PutAgenticBucketEncryptionResult} for PutAgenticBucketEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutAgenticBucketEncryptionResult> putAgenticBucketEncryptionAsync(PutAgenticBucketEncryptionRequest request, OperationOptions options) { throw new UnsupportedOperationException(); }

    /**
     * Gets the server-side encryption configuration of an agentic bucket.
     *
     * @param request A {@link GetAgenticBucketEncryptionRequest} for GetAgenticBucketEncryption operation.
     * @return A {@link CompletableFuture} of {@link GetAgenticBucketEncryptionResult} for GetAgenticBucketEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetAgenticBucketEncryptionResult> getAgenticBucketEncryptionAsync(GetAgenticBucketEncryptionRequest request) { return getAgenticBucketEncryptionAsync(request, OperationOptions.defaults()); }

    /**
     * Gets the server-side encryption configuration of an agentic bucket.
     *
     * @param request A {@link GetAgenticBucketEncryptionRequest} for GetAgenticBucketEncryption operation.
     * @param options The operation options.
     * @return A {@link CompletableFuture} of {@link GetAgenticBucketEncryptionResult} for GetAgenticBucketEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetAgenticBucketEncryptionResult> getAgenticBucketEncryptionAsync(GetAgenticBucketEncryptionRequest request, OperationOptions options) { throw new UnsupportedOperationException(); }

    /**
     * Deletes the server-side encryption configuration of an agentic bucket.
     *
     * @param request A {@link DeleteAgenticBucketEncryptionRequest} for DeleteAgenticBucketEncryption operation.
     * @return A {@link CompletableFuture} of {@link DeleteAgenticBucketEncryptionResult} for DeleteAgenticBucketEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteAgenticBucketEncryptionResult> deleteAgenticBucketEncryptionAsync(DeleteAgenticBucketEncryptionRequest request) { return deleteAgenticBucketEncryptionAsync(request, OperationOptions.defaults()); }

    /**
     * Deletes the server-side encryption configuration of an agentic bucket.
     *
     * @param request A {@link DeleteAgenticBucketEncryptionRequest} for DeleteAgenticBucketEncryption operation.
     * @param options The operation options.
     * @return A {@link CompletableFuture} of {@link DeleteAgenticBucketEncryptionResult} for DeleteAgenticBucketEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteAgenticBucketEncryptionResult> deleteAgenticBucketEncryptionAsync(DeleteAgenticBucketEncryptionRequest request, OperationOptions options) { throw new UnsupportedOperationException(); }

    /**
     * Sets the versioning state of an agentic bucket.
     *
     * @param request A {@link PutAgenticBucketVersioningRequest} for PutAgenticBucketVersioning operation.
     * @return A {@link CompletableFuture} of {@link PutAgenticBucketVersioningResult} for PutAgenticBucketVersioning operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutAgenticBucketVersioningResult> putAgenticBucketVersioningAsync(PutAgenticBucketVersioningRequest request) { return putAgenticBucketVersioningAsync(request, OperationOptions.defaults()); }

    /**
     * Sets the versioning state of an agentic bucket.
     *
     * @param request A {@link PutAgenticBucketVersioningRequest} for PutAgenticBucketVersioning operation.
     * @param options The operation options.
     * @return A {@link CompletableFuture} of {@link PutAgenticBucketVersioningResult} for PutAgenticBucketVersioning operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutAgenticBucketVersioningResult> putAgenticBucketVersioningAsync(PutAgenticBucketVersioningRequest request, OperationOptions options) { throw new UnsupportedOperationException(); }

    /**
     * Gets the versioning state of an agentic bucket.
     *
     * @param request A {@link GetAgenticBucketVersioningRequest} for GetAgenticBucketVersioning operation.
     * @return A {@link CompletableFuture} of {@link GetAgenticBucketVersioningResult} for GetAgenticBucketVersioning operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetAgenticBucketVersioningResult> getAgenticBucketVersioningAsync(GetAgenticBucketVersioningRequest request) { return getAgenticBucketVersioningAsync(request, OperationOptions.defaults()); }

    /**
     * Gets the versioning state of an agentic bucket.
     *
     * @param request A {@link GetAgenticBucketVersioningRequest} for GetAgenticBucketVersioning operation.
     * @param options The operation options.
     * @return A {@link CompletableFuture} of {@link GetAgenticBucketVersioningResult} for GetAgenticBucketVersioning operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetAgenticBucketVersioningResult> getAgenticBucketVersioningAsync(GetAgenticBucketVersioningRequest request, OperationOptions options) { throw new UnsupportedOperationException(); }

    /**
     * Sets the policy of an agentic bucket.
     *
     * @param request A {@link PutAgenticBucketPolicyRequest} for PutAgenticBucketPolicy operation.
     * @return A {@link CompletableFuture} of {@link PutAgenticBucketPolicyResult} for PutAgenticBucketPolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutAgenticBucketPolicyResult> putAgenticBucketPolicyAsync(PutAgenticBucketPolicyRequest request) { return putAgenticBucketPolicyAsync(request, OperationOptions.defaults()); }

    /**
     * Sets the policy of an agentic bucket.
     *
     * @param request A {@link PutAgenticBucketPolicyRequest} for PutAgenticBucketPolicy operation.
     * @param options The operation options.
     * @return A {@link CompletableFuture} of {@link PutAgenticBucketPolicyResult} for PutAgenticBucketPolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutAgenticBucketPolicyResult> putAgenticBucketPolicyAsync(PutAgenticBucketPolicyRequest request, OperationOptions options) { throw new UnsupportedOperationException(); }

    /**
     * Gets the policy of an agentic bucket.
     *
     * @param request A {@link GetAgenticBucketPolicyRequest} for GetAgenticBucketPolicy operation.
     * @return A {@link CompletableFuture} of {@link GetAgenticBucketPolicyResult} for GetAgenticBucketPolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetAgenticBucketPolicyResult> getAgenticBucketPolicyAsync(GetAgenticBucketPolicyRequest request) { return getAgenticBucketPolicyAsync(request, OperationOptions.defaults()); }

    /**
     * Gets the policy of an agentic bucket.
     *
     * @param request A {@link GetAgenticBucketPolicyRequest} for GetAgenticBucketPolicy operation.
     * @param options The operation options.
     * @return A {@link CompletableFuture} of {@link GetAgenticBucketPolicyResult} for GetAgenticBucketPolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetAgenticBucketPolicyResult> getAgenticBucketPolicyAsync(GetAgenticBucketPolicyRequest request, OperationOptions options) { throw new UnsupportedOperationException(); }

    /**
     * Deletes the policy of an agentic bucket.
     *
     * @param request A {@link DeleteAgenticBucketPolicyRequest} for DeleteAgenticBucketPolicy operation.
     * @return A {@link CompletableFuture} of {@link DeleteAgenticBucketPolicyResult} for DeleteAgenticBucketPolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteAgenticBucketPolicyResult> deleteAgenticBucketPolicyAsync(DeleteAgenticBucketPolicyRequest request) { return deleteAgenticBucketPolicyAsync(request, OperationOptions.defaults()); }

    /**
     * Deletes the policy of an agentic bucket.
     *
     * @param request A {@link DeleteAgenticBucketPolicyRequest} for DeleteAgenticBucketPolicy operation.
     * @param options The operation options.
     * @return A {@link CompletableFuture} of {@link DeleteAgenticBucketPolicyResult} for DeleteAgenticBucketPolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteAgenticBucketPolicyResult> deleteAgenticBucketPolicyAsync(DeleteAgenticBucketPolicyRequest request, OperationOptions options) { throw new UnsupportedOperationException(); }

    /**
     * Sets the public access block configuration of an agentic bucket.
     *
     * @param request A {@link PutAgenticBucketPublicAccessBlockRequest} for PutAgenticBucketPublicAccessBlock operation.
     * @return A {@link CompletableFuture} of {@link PutAgenticBucketPublicAccessBlockResult} for PutAgenticBucketPublicAccessBlock operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutAgenticBucketPublicAccessBlockResult> putAgenticBucketPublicAccessBlockAsync(PutAgenticBucketPublicAccessBlockRequest request) { return putAgenticBucketPublicAccessBlockAsync(request, OperationOptions.defaults()); }

    /**
     * Sets the public access block configuration of an agentic bucket.
     *
     * @param request A {@link PutAgenticBucketPublicAccessBlockRequest} for PutAgenticBucketPublicAccessBlock operation.
     * @param options The operation options.
     * @return A {@link CompletableFuture} of {@link PutAgenticBucketPublicAccessBlockResult} for PutAgenticBucketPublicAccessBlock operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutAgenticBucketPublicAccessBlockResult> putAgenticBucketPublicAccessBlockAsync(PutAgenticBucketPublicAccessBlockRequest request, OperationOptions options) { throw new UnsupportedOperationException(); }

    /**
     * Gets the public access block configuration of an agentic bucket.
     *
     * @param request A {@link GetAgenticBucketPublicAccessBlockRequest} for GetAgenticBucketPublicAccessBlock operation.
     * @return A {@link CompletableFuture} of {@link GetAgenticBucketPublicAccessBlockResult} for GetAgenticBucketPublicAccessBlock operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetAgenticBucketPublicAccessBlockResult> getAgenticBucketPublicAccessBlockAsync(GetAgenticBucketPublicAccessBlockRequest request) { return getAgenticBucketPublicAccessBlockAsync(request, OperationOptions.defaults()); }

    /**
     * Gets the public access block configuration of an agentic bucket.
     *
     * @param request A {@link GetAgenticBucketPublicAccessBlockRequest} for GetAgenticBucketPublicAccessBlock operation.
     * @param options The operation options.
     * @return A {@link CompletableFuture} of {@link GetAgenticBucketPublicAccessBlockResult} for GetAgenticBucketPublicAccessBlock operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetAgenticBucketPublicAccessBlockResult> getAgenticBucketPublicAccessBlockAsync(GetAgenticBucketPublicAccessBlockRequest request, OperationOptions options) { throw new UnsupportedOperationException(); }

    /**
     * Deletes the public access block configuration of an agentic bucket.
     *
     * @param request A {@link DeleteAgenticBucketPublicAccessBlockRequest} for DeleteAgenticBucketPublicAccessBlock operation.
     * @return A {@link CompletableFuture} of {@link DeleteAgenticBucketPublicAccessBlockResult} for DeleteAgenticBucketPublicAccessBlock operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteAgenticBucketPublicAccessBlockResult> deleteAgenticBucketPublicAccessBlockAsync(DeleteAgenticBucketPublicAccessBlockRequest request) { return deleteAgenticBucketPublicAccessBlockAsync(request, OperationOptions.defaults()); }

    /**
     * Deletes the public access block configuration of an agentic bucket.
     *
     * @param request A {@link DeleteAgenticBucketPublicAccessBlockRequest} for DeleteAgenticBucketPublicAccessBlock operation.
     * @param options The operation options.
     * @return A {@link CompletableFuture} of {@link DeleteAgenticBucketPublicAccessBlockResult} for DeleteAgenticBucketPublicAccessBlock operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteAgenticBucketPublicAccessBlockResult> deleteAgenticBucketPublicAccessBlockAsync(DeleteAgenticBucketPublicAccessBlockRequest request, OperationOptions options) { throw new UnsupportedOperationException(); }
}
