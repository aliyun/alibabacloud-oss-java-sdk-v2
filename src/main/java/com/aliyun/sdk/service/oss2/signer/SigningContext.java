package com.aliyun.sdk.service.oss2.signer;

import com.aliyun.sdk.service.oss2.credentials.Credentials;
import com.aliyun.sdk.service.oss2.transport.RequestMessage;

import java.time.Instant;
import java.util.List;

/**
 * Represents the context information required for signing operations, including the request,
 * credentials, and other related parameters.
 */
public class SigningContext {

    /**
     * Product identifier used to generate signing scope.
     */
    private String product;

    /**
     * Region identifier used to generate signing scope.
     */
    private String region;

    /**
     * Bucket name used to construct the request resource path.
     */
    private String bucket;

    /**
     * Object key used to construct the request resource path.
     */
    private String key;

    /**
     * The current request object containing HTTP method, URI, and headers.
     */
    private RequestMessage request;

    /**
     * The credential object used for signing.
     */
    private Credentials credentials;

    /**
     * Indicates whether query-based authentication should be used.
     */
    private boolean authMethodQuery;

    /**
     * The generated canonical string to be signed (for debugging or logging).
     */
    private String stringToSign;

    /**
     * The expiration time of the signature.
     */
    private Instant expiration;

    /**
     * The timestamp when the signature was created.
     */
    private Instant signTime;

    /**
     * Additional list of HTTP header fields that should participate in signing.
     */
    private List<String> additionalHeaders;

    /**
     * List of sub-resource parameters such as "acl", "versionId".
     */
    private List<String> subResource;

    /**
     * Gets the current product identifier.
     *
     * @return The product identifier string
     */
    public String getProduct() {
        return product;
    }

    /**
     * Sets the product identifier.
     *
     * @param product New product identifier
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * Gets the current region identifier.
     *
     * @return The region identifier string
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the region identifier.
     *
     * @param region New region identifier
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Gets the current bucket name.
     *
     * @return The bucket name string
     */
    public String getBucket() {
        return bucket;
    }

    /**
     * Sets the bucket name.
     *
     * @param bucket New bucket name
     */
    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    /**
     * Gets the current object key name.
     *
     * @return The object key name string
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the object key name.
     *
     * @param key New object key name
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Gets the current request object.
     *
     * @return The {@link RequestMessage} request object
     */
    public RequestMessage getRequest() {
        return request;
    }

    /**
     * Sets the current request object.
     *
     * @param request New request object
     */
    public void setRequest(RequestMessage request) {
        this.request = request;
    }

    /**
     * Gets the current credentials used for signing.
     *
     * @return The {@link Credentials} object being held by this provider
     */
    public Credentials getCredentials() {
        return credentials;
    }

    /**
     * Sets the current credentials used for signing.
     *
     * @param credentials The new credentials object
     */
    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    /**
     * Checks if query-based authentication is used.
     *
     * @return true if query-based auth is used, false otherwise
     */
    public boolean isAuthMethodQuery() {
        return authMethodQuery;
    }

    /**
     * Sets whether query-based authentication should be used.
     *
     * @param authMethodQuery Whether to use query-based authentication
     */
    public void setAuthMethodQuery(boolean authMethodQuery) {
        this.authMethodQuery = authMethodQuery;
    }

    /**
     * Gets the current canonical string to be signed.
     *
     * @return The string to be signed
     */
    public String getStringToSign() {
        return stringToSign;
    }

    /**
     * Sets the canonical string to be signed.
     *
     * @param stringToSign New string to be signed
     */
    public void setStringToSign(String stringToSign) {
        this.stringToSign = stringToSign;
    }

    /**
     * Gets the expiration time of the signature.
     *
     * @return The expiration instant
     */
    public Instant getExpiration() {
        return expiration;
    }

    /**
     * Sets the expiration time of the signature.
     *
     * @param expiration The new expiration instant
     */
    public void setExpiration(Instant expiration) {
        this.expiration = expiration;
    }

    /**
     * Gets the timestamp when the signature was created.
     *
     * @return The signing timestamp
     */
    public Instant getSignTime() {
        return signTime;
    }

    /**
     * Sets the timestamp when the signature was created.
     *
     * @param signTime The new signing timestamp
     */
    public void setSignTime(Instant signTime) {
        this.signTime = signTime;
    }

    /**
     * Gets the list of additional HTTP headers to be included in signing.
     *
     * @return List of additional headers
     */
    public List<String> getAdditionalHeaders() {
        return additionalHeaders;
    }

    /**
     * Sets the list of additional HTTP headers to be included in signing.
     *
     * @param additionalHeaders List of headers to include in signing
     */
    public void setAdditionalHeaders(List<String> additionalHeaders) {
        this.additionalHeaders = additionalHeaders;
    }

    /**
     * Gets the list of sub-resource parameters such as "acl", "versionId".
     *
     * @return List of sub-resources
     */
    public List<String> getSubResource() {
        return subResource;
    }

    /**
     * Sets the list of sub-resource parameters.
     *
     * @param subResource List of sub-resource parameters
     */
    public void setSubResource(List<String> subResource) {
        this.subResource = subResource;
    }
}
