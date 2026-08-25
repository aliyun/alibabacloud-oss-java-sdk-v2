package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.agentic.paginator.ListAgenticBucketsIterable;
import com.aliyun.sdk.service.oss2.agentic.paginator.ListBucketSpacesIterable;
import com.aliyun.sdk.service.oss2.paginator.PaginatorOptions;

/**
 * A client for accessing OSS AgenticBucket synchronously.
 * This can be created using the static {@link #newBuilder()} method.
 *
 * <p><b>Bucket name resolution</b><br>
 * The {@code bucket} field of every request is a <i>prefix</i>, not the full bucket name.
 * Internally it is resolved to {@code {prefix}-{accountId}-{region}-ab-apsr}, so both
 * {@code accountId} and {@code region} must be configured on the builder. Operations that
 * carry no bucket (for example {@link #listAgenticBuckets}) are routed to the region-level
 * host instead of a bucket-level host.
 *
 * <p><b>Endpoint modes</b> (resolved from the configuration)<br>
 * If a custom {@code endpoint} is set it is used as-is (custom domain / CNAME); otherwise
 * the endpoint is derived from {@code region}: public {@code oss-{region}.aliyuncs.com}
 * (default) or internal {@code oss-{region}-internal.aliyuncs.com} when
 * {@code useInternalEndpoint} is enabled.
 */
public interface OSSAgenticBucketClient extends AutoCloseable {

    static OSSAgenticBucketClientBuilder newBuilder() {
        return new DefaultOSSAgenticBucketClientBuilder();
    }

    /**
     * Invokes an operation with the low-level input/output model.
     *
     * @param input The operation input.
     * @param opts  The operation options.
     * @return The operation output.
     */
    default OperationOutput invokeOperation(OperationInput input, OperationOptions opts) {
        throw new UnsupportedOperationException();
    }

    // basic APIs

    /**
     * Creates an agentic bucket. The {@code bucket} field of the request is the prefix.
     *
     * @param request A {@link CreateAgenticBucketRequest} for CreateAgenticBucket operation.
     * @return A {@link CreateAgenticBucketResult} for CreateAgenticBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CreateAgenticBucketResult createAgenticBucket(CreateAgenticBucketRequest request) {
        return createAgenticBucket(request, OperationOptions.defaults());
    }

    /**
     * Creates an agentic bucket. The {@code bucket} field of the request is the prefix.
     *
     * @param request A {@link CreateAgenticBucketRequest} for CreateAgenticBucket operation.
     * @param options The operation options.
     * @return A {@link CreateAgenticBucketResult} for CreateAgenticBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CreateAgenticBucketResult createAgenticBucket(CreateAgenticBucketRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes an agentic bucket.
     *
     * @param request A {@link DeleteAgenticBucketRequest} for DeleteAgenticBucket operation.
     * @return A {@link DeleteAgenticBucketResult} for DeleteAgenticBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteAgenticBucketResult deleteAgenticBucket(DeleteAgenticBucketRequest request) {
        return deleteAgenticBucket(request, OperationOptions.defaults());
    }

    /**
     * Deletes an agentic bucket.
     *
     * @param request A {@link DeleteAgenticBucketRequest} for DeleteAgenticBucket operation.
     * @param options The operation options.
     * @return A {@link DeleteAgenticBucketResult} for DeleteAgenticBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteAgenticBucketResult deleteAgenticBucket(DeleteAgenticBucketRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the information of an agentic bucket.
     *
     * @param request A {@link GetAgenticBucketRequest} for GetAgenticBucket operation.
     * @return A {@link GetAgenticBucketResult} for GetAgenticBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default GetAgenticBucketResult getAgenticBucket(GetAgenticBucketRequest request) {
        return getAgenticBucket(request, OperationOptions.defaults());
    }

    /**
     * Gets the information of an agentic bucket.
     *
     * @param request A {@link GetAgenticBucketRequest} for GetAgenticBucket operation.
     * @param options The operation options.
     * @return A {@link GetAgenticBucketResult} for GetAgenticBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default GetAgenticBucketResult getAgenticBucket(GetAgenticBucketRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Lists agentic buckets. This is a region-level operation and carries no bucket.
     *
     * @param request A {@link ListAgenticBucketsRequest} for ListAgenticBuckets operation.
     * @return A {@link ListAgenticBucketsResult} for ListAgenticBuckets operation.
     * @throws RuntimeException If an error occurs
     */
    default ListAgenticBucketsResult listAgenticBuckets(ListAgenticBucketsRequest request) {
        return listAgenticBuckets(request, OperationOptions.defaults());
    }

    /**
     * Lists agentic buckets. This is a region-level operation and carries no bucket.
     *
     * @param request A {@link ListAgenticBucketsRequest} for ListAgenticBuckets operation.
     * @param options The operation options.
     * @return A {@link ListAgenticBucketsResult} for ListAgenticBuckets operation.
     * @throws RuntimeException If an error occurs
     */
    default ListAgenticBucketsResult listAgenticBuckets(ListAgenticBucketsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the status of an agentic bucket.
     *
     * @param request A {@link PutAgenticBucketStatusRequest} for PutAgenticBucketStatus operation.
     * @return A {@link PutAgenticBucketStatusResult} for PutAgenticBucketStatus operation.
     * @throws RuntimeException If an error occurs
     */
    default PutAgenticBucketStatusResult putAgenticBucketStatus(PutAgenticBucketStatusRequest request) {
        return putAgenticBucketStatus(request, OperationOptions.defaults());
    }

    /**
     * Sets the status of an agentic bucket.
     *
     * @param request A {@link PutAgenticBucketStatusRequest} for PutAgenticBucketStatus operation.
     * @param options The operation options.
     * @return A {@link PutAgenticBucketStatusResult} for PutAgenticBucketStatus operation.
     * @throws RuntimeException If an error occurs
     */
    default PutAgenticBucketStatusResult putAgenticBucketStatus(PutAgenticBucketStatusRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Lists the bucket spaces of an agentic bucket.
     *
     * @param request A {@link ListBucketSpacesRequest} for ListBucketSpaces operation.
     * @return A {@link ListBucketSpacesResult} for ListBucketSpaces operation.
     * @throws RuntimeException If an error occurs
     */
    default ListBucketSpacesResult listBucketSpaces(ListBucketSpacesRequest request) {
        return listBucketSpaces(request, OperationOptions.defaults());
    }

    /**
     * Lists the bucket spaces of an agentic bucket.
     *
     * @param request A {@link ListBucketSpacesRequest} for ListBucketSpaces operation.
     * @param options The operation options.
     * @return A {@link ListBucketSpacesResult} for ListBucketSpaces operation.
     * @throws RuntimeException If an error occurs
     */
    default ListBucketSpacesResult listBucketSpaces(ListBucketSpacesRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    // Paginators

    /**
     * Creates an iterable that automatically paginates the ListAgenticBuckets operation.
     *
     * @param request A {@link ListAgenticBucketsRequest} for ListAgenticBuckets operation.
     * @return A {@link ListAgenticBucketsIterable} that iterates over all result pages.
     */
    default ListAgenticBucketsIterable listAgenticBucketsPaginator(ListAgenticBucketsRequest request) {
        return listAgenticBucketsPaginator(request, PaginatorOptions.defaults());
    }

    /**
     * Creates an iterable that automatically paginates the ListAgenticBuckets operation.
     *
     * @param request A {@link ListAgenticBucketsRequest} for ListAgenticBuckets operation.
     * @param options The paginator options.
     * @return A {@link ListAgenticBucketsIterable} that iterates over all result pages.
     */
    default ListAgenticBucketsIterable listAgenticBucketsPaginator(ListAgenticBucketsRequest request, PaginatorOptions options) {
        return new ListAgenticBucketsIterable(this, request, options);
    }

    /**
     * Creates an iterable that automatically paginates the ListBucketSpaces operation.
     *
     * @param request A {@link ListBucketSpacesRequest} for ListBucketSpaces operation.
     * @return A {@link ListBucketSpacesIterable} that iterates over all result pages.
     */
    default ListBucketSpacesIterable listBucketSpacesPaginator(ListBucketSpacesRequest request) {
        return listBucketSpacesPaginator(request, PaginatorOptions.defaults());
    }

    /**
     * Creates an iterable that automatically paginates the ListBucketSpaces operation.
     *
     * @param request A {@link ListBucketSpacesRequest} for ListBucketSpaces operation.
     * @param options The paginator options.
     * @return A {@link ListBucketSpacesIterable} that iterates over all result pages.
     */
    default ListBucketSpacesIterable listBucketSpacesPaginator(ListBucketSpacesRequest request, PaginatorOptions options) {
        return new ListBucketSpacesIterable(this, request, options);
    }

    // ACL

    /**
     * Sets the access control list (ACL) of an agentic bucket.
     *
     * @param request A {@link PutAgenticBucketAclRequest} for PutAgenticBucketAcl operation.
     * @return A {@link PutAgenticBucketAclResult} for PutAgenticBucketAcl operation.
     * @throws RuntimeException If an error occurs
     */
    default PutAgenticBucketAclResult putAgenticBucketAcl(PutAgenticBucketAclRequest request) {
        return putAgenticBucketAcl(request, OperationOptions.defaults());
    }

    /**
     * Sets the access control list (ACL) of an agentic bucket.
     *
     * @param request A {@link PutAgenticBucketAclRequest} for PutAgenticBucketAcl operation.
     * @param options The operation options.
     * @return A {@link PutAgenticBucketAclResult} for PutAgenticBucketAcl operation.
     * @throws RuntimeException If an error occurs
     */
    default PutAgenticBucketAclResult putAgenticBucketAcl(PutAgenticBucketAclRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the access control list (ACL) of an agentic bucket.
     *
     * @param request A {@link GetAgenticBucketAclRequest} for GetAgenticBucketAcl operation.
     * @return A {@link GetAgenticBucketAclResult} for GetAgenticBucketAcl operation.
     * @throws RuntimeException If an error occurs
     */
    default GetAgenticBucketAclResult getAgenticBucketAcl(GetAgenticBucketAclRequest request) {
        return getAgenticBucketAcl(request, OperationOptions.defaults());
    }

    /**
     * Gets the access control list (ACL) of an agentic bucket.
     *
     * @param request A {@link GetAgenticBucketAclRequest} for GetAgenticBucketAcl operation.
     * @param options The operation options.
     * @return A {@link GetAgenticBucketAclResult} for GetAgenticBucketAcl operation.
     * @throws RuntimeException If an error occurs
     */
    default GetAgenticBucketAclResult getAgenticBucketAcl(GetAgenticBucketAclRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    // Encryption

    /**
     * Sets the server-side encryption configuration of an agentic bucket.
     *
     * @param request A {@link PutAgenticBucketEncryptionRequest} for PutAgenticBucketEncryption operation.
     * @return A {@link PutAgenticBucketEncryptionResult} for PutAgenticBucketEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default PutAgenticBucketEncryptionResult putAgenticBucketEncryption(PutAgenticBucketEncryptionRequest request) {
        return putAgenticBucketEncryption(request, OperationOptions.defaults());
    }

    /**
     * Sets the server-side encryption configuration of an agentic bucket.
     *
     * @param request A {@link PutAgenticBucketEncryptionRequest} for PutAgenticBucketEncryption operation.
     * @param options The operation options.
     * @return A {@link PutAgenticBucketEncryptionResult} for PutAgenticBucketEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default PutAgenticBucketEncryptionResult putAgenticBucketEncryption(PutAgenticBucketEncryptionRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the server-side encryption configuration of an agentic bucket.
     *
     * @param request A {@link GetAgenticBucketEncryptionRequest} for GetAgenticBucketEncryption operation.
     * @return A {@link GetAgenticBucketEncryptionResult} for GetAgenticBucketEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default GetAgenticBucketEncryptionResult getAgenticBucketEncryption(GetAgenticBucketEncryptionRequest request) {
        return getAgenticBucketEncryption(request, OperationOptions.defaults());
    }

    /**
     * Gets the server-side encryption configuration of an agentic bucket.
     *
     * @param request A {@link GetAgenticBucketEncryptionRequest} for GetAgenticBucketEncryption operation.
     * @param options The operation options.
     * @return A {@link GetAgenticBucketEncryptionResult} for GetAgenticBucketEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default GetAgenticBucketEncryptionResult getAgenticBucketEncryption(GetAgenticBucketEncryptionRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes the server-side encryption configuration of an agentic bucket.
     *
     * @param request A {@link DeleteAgenticBucketEncryptionRequest} for DeleteAgenticBucketEncryption operation.
     * @return A {@link DeleteAgenticBucketEncryptionResult} for DeleteAgenticBucketEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteAgenticBucketEncryptionResult deleteAgenticBucketEncryption(DeleteAgenticBucketEncryptionRequest request) {
        return deleteAgenticBucketEncryption(request, OperationOptions.defaults());
    }

    /**
     * Deletes the server-side encryption configuration of an agentic bucket.
     *
     * @param request A {@link DeleteAgenticBucketEncryptionRequest} for DeleteAgenticBucketEncryption operation.
     * @param options The operation options.
     * @return A {@link DeleteAgenticBucketEncryptionResult} for DeleteAgenticBucketEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteAgenticBucketEncryptionResult deleteAgenticBucketEncryption(DeleteAgenticBucketEncryptionRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    // Versioning

    /**
     * Sets the versioning state of an agentic bucket.
     *
     * @param request A {@link PutAgenticBucketVersioningRequest} for PutAgenticBucketVersioning operation.
     * @return A {@link PutAgenticBucketVersioningResult} for PutAgenticBucketVersioning operation.
     * @throws RuntimeException If an error occurs
     */
    default PutAgenticBucketVersioningResult putAgenticBucketVersioning(PutAgenticBucketVersioningRequest request) {
        return putAgenticBucketVersioning(request, OperationOptions.defaults());
    }

    /**
     * Sets the versioning state of an agentic bucket.
     *
     * @param request A {@link PutAgenticBucketVersioningRequest} for PutAgenticBucketVersioning operation.
     * @param options The operation options.
     * @return A {@link PutAgenticBucketVersioningResult} for PutAgenticBucketVersioning operation.
     * @throws RuntimeException If an error occurs
     */
    default PutAgenticBucketVersioningResult putAgenticBucketVersioning(PutAgenticBucketVersioningRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the versioning state of an agentic bucket.
     *
     * @param request A {@link GetAgenticBucketVersioningRequest} for GetAgenticBucketVersioning operation.
     * @return A {@link GetAgenticBucketVersioningResult} for GetAgenticBucketVersioning operation.
     * @throws RuntimeException If an error occurs
     */
    default GetAgenticBucketVersioningResult getAgenticBucketVersioning(GetAgenticBucketVersioningRequest request) {
        return getAgenticBucketVersioning(request, OperationOptions.defaults());
    }

    /**
     * Gets the versioning state of an agentic bucket.
     *
     * @param request A {@link GetAgenticBucketVersioningRequest} for GetAgenticBucketVersioning operation.
     * @param options The operation options.
     * @return A {@link GetAgenticBucketVersioningResult} for GetAgenticBucketVersioning operation.
     * @throws RuntimeException If an error occurs
     */
    default GetAgenticBucketVersioningResult getAgenticBucketVersioning(GetAgenticBucketVersioningRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    // Policy

    /**
     * Sets the policy of an agentic bucket.
     *
     * @param request A {@link PutAgenticBucketPolicyRequest} for PutAgenticBucketPolicy operation.
     * @return A {@link PutAgenticBucketPolicyResult} for PutAgenticBucketPolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default PutAgenticBucketPolicyResult putAgenticBucketPolicy(PutAgenticBucketPolicyRequest request) {
        return putAgenticBucketPolicy(request, OperationOptions.defaults());
    }

    /**
     * Sets the policy of an agentic bucket.
     *
     * @param request A {@link PutAgenticBucketPolicyRequest} for PutAgenticBucketPolicy operation.
     * @param options The operation options.
     * @return A {@link PutAgenticBucketPolicyResult} for PutAgenticBucketPolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default PutAgenticBucketPolicyResult putAgenticBucketPolicy(PutAgenticBucketPolicyRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the policy of an agentic bucket.
     *
     * @param request A {@link GetAgenticBucketPolicyRequest} for GetAgenticBucketPolicy operation.
     * @return A {@link GetAgenticBucketPolicyResult} for GetAgenticBucketPolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default GetAgenticBucketPolicyResult getAgenticBucketPolicy(GetAgenticBucketPolicyRequest request) {
        return getAgenticBucketPolicy(request, OperationOptions.defaults());
    }

    /**
     * Gets the policy of an agentic bucket.
     *
     * @param request A {@link GetAgenticBucketPolicyRequest} for GetAgenticBucketPolicy operation.
     * @param options The operation options.
     * @return A {@link GetAgenticBucketPolicyResult} for GetAgenticBucketPolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default GetAgenticBucketPolicyResult getAgenticBucketPolicy(GetAgenticBucketPolicyRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes the policy of an agentic bucket.
     *
     * @param request A {@link DeleteAgenticBucketPolicyRequest} for DeleteAgenticBucketPolicy operation.
     * @return A {@link DeleteAgenticBucketPolicyResult} for DeleteAgenticBucketPolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteAgenticBucketPolicyResult deleteAgenticBucketPolicy(DeleteAgenticBucketPolicyRequest request) {
        return deleteAgenticBucketPolicy(request, OperationOptions.defaults());
    }

    /**
     * Deletes the policy of an agentic bucket.
     *
     * @param request A {@link DeleteAgenticBucketPolicyRequest} for DeleteAgenticBucketPolicy operation.
     * @param options The operation options.
     * @return A {@link DeleteAgenticBucketPolicyResult} for DeleteAgenticBucketPolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteAgenticBucketPolicyResult deleteAgenticBucketPolicy(DeleteAgenticBucketPolicyRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    // PublicAccessBlock

    /**
     * Sets the public access block configuration of an agentic bucket.
     *
     * @param request A {@link PutAgenticBucketPublicAccessBlockRequest} for PutAgenticBucketPublicAccessBlock operation.
     * @return A {@link PutAgenticBucketPublicAccessBlockResult} for PutAgenticBucketPublicAccessBlock operation.
     * @throws RuntimeException If an error occurs
     */
    default PutAgenticBucketPublicAccessBlockResult putAgenticBucketPublicAccessBlock(PutAgenticBucketPublicAccessBlockRequest request) {
        return putAgenticBucketPublicAccessBlock(request, OperationOptions.defaults());
    }

    /**
     * Sets the public access block configuration of an agentic bucket.
     *
     * @param request A {@link PutAgenticBucketPublicAccessBlockRequest} for PutAgenticBucketPublicAccessBlock operation.
     * @param options The operation options.
     * @return A {@link PutAgenticBucketPublicAccessBlockResult} for PutAgenticBucketPublicAccessBlock operation.
     * @throws RuntimeException If an error occurs
     */
    default PutAgenticBucketPublicAccessBlockResult putAgenticBucketPublicAccessBlock(PutAgenticBucketPublicAccessBlockRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the public access block configuration of an agentic bucket.
     *
     * @param request A {@link GetAgenticBucketPublicAccessBlockRequest} for GetAgenticBucketPublicAccessBlock operation.
     * @return A {@link GetAgenticBucketPublicAccessBlockResult} for GetAgenticBucketPublicAccessBlock operation.
     * @throws RuntimeException If an error occurs
     */
    default GetAgenticBucketPublicAccessBlockResult getAgenticBucketPublicAccessBlock(GetAgenticBucketPublicAccessBlockRequest request) {
        return getAgenticBucketPublicAccessBlock(request, OperationOptions.defaults());
    }

    /**
     * Gets the public access block configuration of an agentic bucket.
     *
     * @param request A {@link GetAgenticBucketPublicAccessBlockRequest} for GetAgenticBucketPublicAccessBlock operation.
     * @param options The operation options.
     * @return A {@link GetAgenticBucketPublicAccessBlockResult} for GetAgenticBucketPublicAccessBlock operation.
     * @throws RuntimeException If an error occurs
     */
    default GetAgenticBucketPublicAccessBlockResult getAgenticBucketPublicAccessBlock(GetAgenticBucketPublicAccessBlockRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes the public access block configuration of an agentic bucket.
     *
     * @param request A {@link DeleteAgenticBucketPublicAccessBlockRequest} for DeleteAgenticBucketPublicAccessBlock operation.
     * @return A {@link DeleteAgenticBucketPublicAccessBlockResult} for DeleteAgenticBucketPublicAccessBlock operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteAgenticBucketPublicAccessBlockResult deleteAgenticBucketPublicAccessBlock(DeleteAgenticBucketPublicAccessBlockRequest request) {
        return deleteAgenticBucketPublicAccessBlock(request, OperationOptions.defaults());
    }

    /**
     * Deletes the public access block configuration of an agentic bucket.
     *
     * @param request A {@link DeleteAgenticBucketPublicAccessBlockRequest} for DeleteAgenticBucketPublicAccessBlock operation.
     * @param options The operation options.
     * @return A {@link DeleteAgenticBucketPublicAccessBlockResult} for DeleteAgenticBucketPublicAccessBlock operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteAgenticBucketPublicAccessBlockResult deleteAgenticBucketPublicAccessBlock(DeleteAgenticBucketPublicAccessBlockRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
}
