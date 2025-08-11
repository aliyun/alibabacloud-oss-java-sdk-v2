package com.aliyun.sdk.service.oss2.credentials;

/**
 * Static Credentials Provider Class
 */
public class StaticCredentialsProvider implements CredentialsProvider {
    /**
     * The held static credentials object
     */
    private final Credentials credential;

    /**
     * Private constructor that creates a credential provider using an existing Credentials object
     *
     * @param credential The credential object to hold
     */
    private StaticCredentialsProvider(Credentials credential) {
        this.credential = credential;
    }

    /**
     * Creates a static credential provider using access key ID and secret
     *
     * @param accessKeyId     The access key ID
     * @param accessKeySecret The access key secret
     */
    public StaticCredentialsProvider(String accessKeyId, String accessKeySecret) {
        this(new Credentials(accessKeyId, accessKeySecret));
    }

    /**
     * Creates a static credential provider with access key ID, secret, and security token
     *
     * @param accessKeyId     The access key ID
     * @param accessKeySecret The access key secret
     * @param securityToken   The security token
     */
    public StaticCredentialsProvider(String accessKeyId, String accessKeySecret, String securityToken) {
        this(new Credentials(accessKeyId, accessKeySecret, securityToken));
    }

    /**
     * Retrieves the currently held static credentials
     *
     * @return A read-only reference to the {@link Credentials} object being held by this provider
     */
    @Override
    public Credentials getCredentials() {
        return this.credential;
    }
}
