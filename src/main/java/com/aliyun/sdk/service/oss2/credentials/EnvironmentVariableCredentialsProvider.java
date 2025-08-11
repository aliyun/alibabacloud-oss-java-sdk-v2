package com.aliyun.sdk.service.oss2.credentials;

/**
 * Environment Variable Credentials Provider Class
 */
public class EnvironmentVariableCredentialsProvider implements CredentialsProvider {
    /**
     * The credential object parsed from environment variables
     */
    private final Credentials credentials;

    /**
     * Constructor that loads credentials from system environment variables
     *
     * @throws IllegalArgumentException If any required environment variables (OSS_ACCESS_KEY_ID or OSS_ACCESS_KEY_SECRET) are missing
     */
    public EnvironmentVariableCredentialsProvider() {
        String accessKeyId = System.getenv("OSS_ACCESS_KEY_ID");
        String accessKeySecret = System.getenv("OSS_ACCESS_KEY_SECRET");
        String securityToken = System.getenv("OSS_SESSION_TOKEN");

        if (accessKeyId == null) accessKeyId = "";
        if (accessKeySecret == null) accessKeySecret = "";

        if (accessKeyId.isEmpty() || accessKeySecret.isEmpty()) {
            throw new IllegalArgumentException("Missing required environment variables: OSS_ACCESS_KEY_ID and/or OSS_ACCESS_KEY_SECRET");
        }

        this.credentials = new Credentials(accessKeyId, accessKeySecret, securityToken);
    }

    /**
     * Retrieves the credentials loaded from environment variables
     *
     * @return A read-only reference to the {@link Credentials} object being held by this provider
     */
    @Override
    public Credentials getCredentials() {
        return credentials;
    }
}
