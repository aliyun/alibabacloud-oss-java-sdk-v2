package com.aliyun.sdk.service.oss2.signer;

/**
 * A no-operation signer implementation that performs no actual signing.
 */
public class NopSigner implements Signer {
    /**
     * Does nothing. Used in scenarios where signing is not required.
     *
     * @param signingContext The signing context
     */
    @Override
    public void sign(SigningContext signingContext) {

    }
}
