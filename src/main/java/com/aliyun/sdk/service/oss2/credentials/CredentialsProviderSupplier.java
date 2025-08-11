package com.aliyun.sdk.service.oss2.credentials;

import java.util.function.Supplier;

public class CredentialsProviderSupplier implements CredentialsProvider {
    /**
     * Credential supplier used to retrieve credential objects dynamically
     */
    private final Supplier<Credentials> supplier;

    /**
     * Constructor to create a credential provider with the given supplier
     *
     * @param supplier The source of the credentials
     */
    public CredentialsProviderSupplier(Supplier<Credentials> supplier) {
        this.supplier = supplier;
    }

    /**
     * Retrieves the current credential information
     *
     * @return A read-only reference to the {@link Credentials} object being held by this provider
     */
    @Override
    public Credentials getCredentials() {
        return supplier.get();
    }
}
