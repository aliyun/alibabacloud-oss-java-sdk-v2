package com.aliyun.sdk.service.oss2.signer;

/**
 * Interface defining the contract for signing requests.
 */
public interface Signer {
    /**
     * Signs the given signing context.
     *
     * @param signingContext The context containing data to be signed
     */
    void sign(SigningContext signingContext);
}
