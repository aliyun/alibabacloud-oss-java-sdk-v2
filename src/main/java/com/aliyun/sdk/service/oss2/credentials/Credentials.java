package com.aliyun.sdk.service.oss2.credentials;

/**
 * Credential Class for storing authentication information required for access
 */
public class Credentials {
    /**
     * Access Key ID, identifies the requester's identity
     */
    private final String accessKeyId;

    /**
     * Access Key Secret, used to sign requests and ensure request security
     */
    private final String accessKeySecret;

    /**
     * Security Token, used in temporary credential scenarios
     */
    private final String securityToken;

    /**
     * Constructor to create a credential object with access key ID and secret
     *
     * @param accessKeyId     The access key ID
     * @param accessKeySecret The access key secret
     */
    public Credentials(String accessKeyId, String accessKeySecret) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.securityToken = "";
    }

    /**
     * Constructor to create a credential object with access key ID, secret, and security token
     *
     * @param accessKeyId     The access key ID
     * @param accessKeySecret The access key secret
     * @param securityToken   The security token
     */
    public Credentials(String accessKeyId, String accessKeySecret, String securityToken) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.securityToken = securityToken;
    }

    /**
     * Checks if the provided credentials contain valid access key ID and secret
     *
     * @return true if valid keys are present, false otherwise
     */
    public boolean hasKeys() {
        return this.accessKeyId != null && !this.accessKeyId.isEmpty() &&
                this.accessKeySecret != null && !this.accessKeySecret.isEmpty();
    }

    /**
     * Gets the access key ID
     *
     * @return Returns the current access key ID
     */
    public String accessKeyId() {
        return this.accessKeyId;
    }

    /**
     * Gets the access key secret
     *
     * @return Returns the current access key secret
     */
    public String accessKeySecret() {
        return this.accessKeySecret;
    }

    /**
     * Gets the security token
     *
     * @return Returns the current security token, or empty string if not set
     */
    public String securityToken() {
        return this.securityToken;
    }
}
