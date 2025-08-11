package com.aliyun.sdk.service.oss2.credentials;

/**
 * Anonymous Credentials Provider Class
 */
public class AnonymousCredentialsProvider implements CredentialsProvider {
    /**
     * Anonymous credential object with empty access key ID and secret
     */
    private final Credentials cred = new Credentials("", "");

    /**
     * Get the credential information
     * <p>
     * This method is used to return the Credentials object currently held by the instance.
     * It allows other authentication or authorization components to use these credentials for identity verification or permission checks.
     *
     * @return A read-only reference to the {@link Credentials} object being held by this provider
     */
    @Override
    public Credentials getCredentials() {
        return cred;
    }
}
