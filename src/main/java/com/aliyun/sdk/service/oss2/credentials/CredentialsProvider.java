package com.aliyun.sdk.service.oss2.credentials;

/**
 * Credentials Provider Interface
 */
public interface CredentialsProvider {
    /**
     * Gets the current credential object
     *
     * @return An instance
     * representing the current valid authentication information
     */
    Credentials getCredentials();
}
